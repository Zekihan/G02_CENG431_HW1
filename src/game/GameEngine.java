package game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.avoidables.*;
import game.map.ICircularMap;
import game.map.RunTrack;
import game.map.TrackType;
import game.utilities.GameReport;
import game.utilities.RandomEngine;
import io.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameEngine {

    private ICircularMap runTrack;
    private Hero hero;
    private Monster monster;
    private int totalMeters;
    private int score;
    private Level level;
    private boolean gameOver;
    private IGameMonitor display;

    public GameEngine() {
        this(new Hero(), new Monster(), 0, 0, RandomEngine.randLevel(), false);

        loadProgress();
    }

    private GameEngine(Hero hero, Monster monster, int totalMeters, int score, Level level, boolean gameOver) {
        int perimeter = RandomEngine.randPerimeterInRange(1000,10000);
        this.runTrack = new RunTrack(perimeter, RandomEngine.randTrackType(), generateRandomCollectibles(perimeter), generateRandomObstacles(perimeter));
        this.hero = hero;
        this.monster = monster;
        this.totalMeters = totalMeters;
        this.score = score;
        this.level = level;
        this.gameOver = gameOver;
        this.display = new Display(new Gamepad());
    }

    public void startGame(){

        displayGameProperties();
        waitDisplay(2000);

        while(!gameOver){

            //Player presses 'q' to quit
            if (display.getKeyEvent().equals("q")){
                gameOver = true;
                endReport("Player quit");
                try{
                    saveProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }

            //Monster eats the hero
            if(checkMonsterCondition()){
                gameOver = true;
                endReport(monster.getEatResult());
                break;
            }

            //Hero encounters an obstacle
            if(runTrack.checkForObstacle(hero.getPosition())){
                //Wait function for pretty print the events

                IAvoidable obstacleEncountered = runTrack.getObstacleAtPosition(hero.getPosition());

                //Hero stumbles upon the obstacle
                if(checkStumbleCondition(obstacleEncountered) && totalMeters!=0){
                    gameOver = true;
                    endReport(obstacleEncountered.stumbleEffect());
                    break;

                 //Hero avoids the obstacle
                }else {
                    score += obstacleEncountered.getAvoidPoint() * level.getMultiplier();
                    display.avoidedObstacle(obstacleEncountered.avoidEffect());
                    waitDisplay(1000);
                }
            }

            //Hero sees a collectible
            if(runTrack.checkForCollectible(hero.getPosition())){
                Collectable collectable = runTrack.getCollectibleAtPosition(hero.getPosition());


                //Hero collects the collectible
                if(!collectable.requiresMagnet() || hero.hasMagnet()){
                    hero.collect(collectable);
                    runTrack.collectCollectible(hero.getPosition());
                    score += collectable.getValue() * level.getMultiplier();
                    display.collectedItem(collectable.toString());
                    waitDisplay(250);
                }

                //Collectible requires a magnet and hero doesn't have a magnet
                //Do nothing. (Hero cannot collect the collectible)
            }

            //Hero gets a magnet whenever his/her score is over 5000
            if(!hero.hasMagnet() && score > 5000){
                hero.acquireMagnet();
            }

            //Increment the total distance traveled
            totalMeters++;
            forwardHero();

            //Hero has completed one iteration of the circular-map, reset his/her position.
            if(hero.getPosition() > runTrack.getPerimeter()){

                // Reset Collectables
                runTrack.setCurrencyMap(refreshRandomCollectibles(runTrack.getPerimeter(),runTrack.getCurrencyMap()));

                display.reachedDestination(String.valueOf(totalMeters));
                resetPosition();
            }

        }
    }


    private void waitDisplay(long milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Generate a random currency
    private Collectable createRandomCurrency() {
        Collectable[] currencies = Collectable.values();
        Random rand = new Random();
        int i = rand.nextInt(currencies.length);
        return currencies[i];
    }

    //Generate a random obstacle
    private IAvoidable createRandomObstacle(){
        IAvoidable[] obstacles = {new RockObstacle(), new SawObstacle(), new AqueductObstacle(), new FelledTreeObstacle()};
        Random rand = new Random();
        int i = rand.nextInt(obstacles.length);
        return obstacles[i];
    }


    //Create an obstacle map from randomly generated obstacles
    private Map<Integer, IAvoidable> generateRandomObstacles(int perimeter){
        Map<Integer,IAvoidable> obstacleMap = new HashMap<>();
        for(int i=0; i < perimeter; i+=500){
            obstacleMap.put(i, createRandomObstacle());
        }
        return obstacleMap;
    }

    //Create a collectible map from randomly generated obstacles and collectible map
    private Map<Integer, Collectable> refreshRandomCollectibles(int perimeter, Map<Integer, Collectable> currencyMap){
        for(int i=0; i < perimeter; i+=50){
            if(!currencyMap.containsKey(i)){
                currencyMap.put(i, createRandomCurrency());
            }
        }
        return currencyMap;
    }


    //Create a collectible  from randomly generated currencies
    private Map<Integer, Collectable> generateRandomCollectibles(int perimeter){
        Map<Integer, Collectable> currencyMap = new HashMap<>();
        for(int i=0; i < perimeter; i+= 50){
            currencyMap.put(i, createRandomCurrency());
        }
        return currencyMap;
    }

    //Determines whether the hero will stumble or not
    private boolean checkStumbleCondition(IAvoidable obstacleEncountered){
        Random rand = new Random();
        double randVal = rand.nextDouble();
        return !(randVal < obstacleEncountered.getAvoidChance());
    }

    //Determines whether the monster will eat the hero or not
    private boolean checkMonsterCondition(){
        Random rand = new Random();
        if(totalMeters % 1500 == 0 && totalMeters!= 0) {
            display.encounteredMonster();
            waitDisplay(1000);
            double randVal = rand.nextDouble();
            return randVal < monster.getEatChance();
        }
        return false;
    }


    //Save player's progress into the game_progress.json file as a json object
    @SuppressWarnings("deprecation")
    private void saveProgress() {
        GameSave saver = new FileIO();
        boolean saved = false;

        ObjectMapper mapper = new ObjectMapper();

        JsonNodeFactory f = JsonNodeFactory.instance;
        ObjectNode gameProgress = f.objectNode();
        try {
            gameProgress.put("hero", mapper.readTree(mapper.writeValueAsString(hero)));

            ObjectNode runTrackNode = f.objectNode();
            runTrackNode.put("perimeter", mapper.valueToTree(runTrack.getPerimeter()));
            runTrackNode.put("trackType", mapper.valueToTree(runTrack.getTrackType()));

            Map<Integer, IAvoidable> obstacleMap = runTrack.getObstacleMap();
            Map<Integer, String> obstacleMapJson =
                    obstacleMap.entrySet().stream().collect(Collectors.toMap(
                            entry -> entry.getKey(),
                            entry -> entry.getValue().getClass().getSimpleName())
                    );
            runTrackNode.put("obstacleMap", mapper.valueToTree(obstacleMapJson));

            runTrackNode.put("currencyMap", mapper.valueToTree(runTrack.getCurrencyMap()));
            gameProgress.put("runTrack",runTrackNode);

            gameProgress.put("totalMeters", mapper.valueToTree(totalMeters));
            gameProgress.put("score", mapper.valueToTree(score));
            gameProgress.put("level", mapper.valueToTree(level));

            ObjectNode json = f.objectNode();
            json.put("gameProgress",gameProgress);
            String progressAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            saved = saver.save("game-progress.json",progressAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Progress saved: " + saved);

    }

    private IAvoidable test(String entry){
        switch (entry) {
            case "FelledTreeObstacle":
                return new FelledTreeObstacle();
            case "RockObstacle":
                return new RockObstacle();
            case "SawObstacle":
                return new SawObstacle();
            case "AqueductObstacle":
                return new AqueductObstacle();
            default:
                return null;
        }
    }

    //Loads player's progress into the game_progress.json file as a json object
    @SuppressWarnings("unchecked")
    private void loadProgress() {
        GameSave loader = new FileIO();
        boolean load = false;
        ObjectMapper mapper = new ObjectMapper();

        String progressAsString = loader.read("game-progress.json");

        try {
            ObjectNode gameProgress = (ObjectNode) mapper.readTree(progressAsString).get("gameProgress");
            hero = mapper.convertValue(gameProgress.get("hero"),Hero.class);

            Map<String, String> currencyMapJson = mapper.convertValue(gameProgress.get("runTrack").get("currencyMap"),Map.class);
            Map<Integer, Collectable> currencyMap =
                    currencyMapJson.entrySet().stream().collect(Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey()),
                            entry -> Collectable.valueOf(entry.getValue()))
                    );


            Map<String, String> obstacleMapJson = mapper.convertValue(gameProgress.get("runTrack").get("obstacleMap"),Map.class);;
            Map<Integer, IAvoidable> obstacleMap =
                    obstacleMapJson.entrySet().stream().collect(Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey()),
                            entry -> test(entry.getValue()))
                    );

            runTrack = new RunTrack(mapper.convertValue(gameProgress.get("runTrack").get("perimeter"),int.class),
                    mapper.convertValue(gameProgress.get("runTrack").get("trackType"), TrackType.class),
                    currencyMap,obstacleMap);
            totalMeters = mapper.convertValue(gameProgress.get("totalMeters"),int.class);
            score = mapper.convertValue(gameProgress.get("score"), int.class);
            level = mapper.convertValue(gameProgress.get("level"), Level.class);
            load = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Progress loaded: " + load);
    }

    //End of the game, player's score, cause of death, etc. is displayed
    private void endReport(String deathReason){
        GameReport gameReport = new GameReport();
        String report = gameReport.createGameReport(deathReason, runTrack.getPerimeter()
                , runTrack.getTrackType().toString(), hero.totalItems(), hero.totalDiamonds(), score, level.toString());
        display.endGameReport(report);
    }


    //Increment hero's position
    private void forwardHero(){
        int currentPosition = hero.getPosition();
        hero.setPosition(currentPosition + 1);
    }

    //Reset hero's position
    private void resetPosition(){
        hero.setPosition(0);
    }

    //Display game's initially generated properties
    private void displayGameProperties(){
        display.initialGameProperties(runTrack.getTrackType().toString(), level.toString());
    }

}
