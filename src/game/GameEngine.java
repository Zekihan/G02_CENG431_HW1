package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.avoidables.*;
import game.map.*;
import game.utilities.*;
import io.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameEngine {

    private IGameMonitor display;
    private ICircularMap runTrack;
    private Hero hero;
    private Monster monster;
    private int totalMeters;
    private int score;
    private Level level;
    private boolean gameOver;

    public GameEngine() {
        this.display = new Display(new Gamepad());
        if(loadProgress()){
            display.loadedGame();
        }else{
            init();
        }
    }

    private void init(){
        int perimeter = RandomEngine.randPerimeterInRange(1000,10000);
        TrackType trackType = RandomEngine.randTrackType();
        this.runTrack = new RunTrack(perimeter, trackType, generateCollectables(perimeter), generateObstacles(perimeter));
        this.hero = new Hero();
        this.monster = new Monster();
        this.totalMeters = 0;
        this.score = 0;
        this.level = RandomEngine.randLevel();
        this.gameOver = false;
    }

    public void startGame(){

        display.gameProperties(runTrack.getTrackType().toString(), level.toString());
        waitDisplay(1500);

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

                IAvoidable obstacleEncountered = runTrack.getObstacle(hero.getPosition());

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
                Collectable collectable = runTrack.getCollectible(hero.getPosition());

                //Hero collects the collectible
                if(!collectable.requiresMagnet() || hero.hasMagnet()){
                    hero.collect(collectable);
                    runTrack.removeCollectable(hero.getPosition());
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

                // Reset Collectibles
                runTrack.setCollectibleMap(refreshRandomCollectables(runTrack.getPerimeter(),runTrack.getCollectibleMap()));

                display.reachedDestination(String.valueOf(totalMeters));
                resetPosition();
            }
        }
    }

    //Generate a random currency
    private Collectable createRandomCollectable() {
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
    private Map<Integer, IAvoidable> generateObstacles(int perimeter){
        Map<Integer,IAvoidable> obstacleMap = new HashMap<>();
        for(int i=0; i < perimeter; i+=500){
            obstacleMap.put(i, createRandomObstacle());
        }
        return obstacleMap;
    }

    //Create a collectible  from randomly generated currencies
    private Map<Integer, Collectable> generateCollectables(int perimeter){
        Map<Integer, Collectable> currencyMap = new HashMap<>();
        for(int i=0; i < perimeter; i+= 50){
            currencyMap.put(i, createRandomCollectable());
        }
        return currencyMap;
    }

    //Create a collectible map from randomly generated obstacles and collectible map
    private Map<Integer, Collectable> refreshRandomCollectables(int perimeter, Map<Integer, Collectable> currencyMap){
        for(int i=0; i < perimeter; i+=50){
            if(!currencyMap.containsKey(i)){
                currencyMap.put(i, createRandomCollectable());
            }
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
    private boolean saveProgress() {
        boolean saved = false;
        GameStorage gameStorage = new FileIO("game-progress.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNodeFactory f = JsonNodeFactory.instance;
        ObjectNode gameProgress = f.objectNode();
        try {
            ObjectNode runTrackNode = f.objectNode();
            Map<Integer, IAvoidable> obstacleMap = runTrack.getObstacleMap();
            Map<Integer, String> obstacleMapJson =
                    obstacleMap.entrySet().stream().collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().getClass().getSimpleName())
                    );
            runTrackNode.put("perimeter", mapper.valueToTree(runTrack.getPerimeter()));
            runTrackNode.put("trackType", mapper.valueToTree(runTrack.getTrackType()));
            runTrackNode.put("obstacleMap", mapper.valueToTree(obstacleMapJson));
            runTrackNode.put("currencyMap", mapper.valueToTree(runTrack.getCollectibleMap()));

            gameProgress.put("hero", mapper.readTree(mapper.writeValueAsString(hero)));
            gameProgress.put("runTrack",runTrackNode);
            gameProgress.put("totalMeters", mapper.valueToTree(totalMeters));
            gameProgress.put("score", mapper.valueToTree(score));
            gameProgress.put("level", mapper.valueToTree(level));

            ObjectNode json = f.objectNode();
            json.put("gameProgress",gameProgress);
            String progressAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            saved = gameStorage.save(progressAsString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved;
    }

    //Loads player's progress from the game-progress.json file as a json object
    @SuppressWarnings("unchecked")
    private boolean loadProgress() {
        GameStorage gameStorage = new FileIO("game-progress.json");
        ObjectMapper mapper = new ObjectMapper();

        if(gameStorage.checkSave()) {
            String progressAsString = gameStorage.load();
            gameStorage.deleteSave();
            try {
                ObjectNode gameProgress = (ObjectNode) mapper.readTree(progressAsString).get("gameProgress");

                Map<String, String> currencyMapJson =
                        mapper.convertValue(gameProgress.get("runTrack").get("currencyMap"), Map.class);
                Map<Integer, Collectable> currencyMap =
                        currencyMapJson.entrySet().stream().collect(Collectors.toMap(
                                entry -> Integer.parseInt(entry.getKey()),
                                entry -> Collectable.valueOf(entry.getValue()))
                        );

                Map<String, String> obstacleMapJson =
                        mapper.convertValue(gameProgress.get("runTrack").get("obstacleMap"), Map.class);

                Map<Integer, IAvoidable> obstacleMap =
                        obstacleMapJson.entrySet().stream().collect(Collectors.toMap(
                                entry -> Integer.parseInt(entry.getKey()),
                                entry -> getNewAvoidable(entry.getValue()))
                        );

                this.runTrack = new RunTrack(mapper.convertValue(gameProgress.get("runTrack").get("perimeter"), int.class),
                        mapper.convertValue(gameProgress.get("runTrack").get("trackType"), TrackType.class),
                        currencyMap, obstacleMap);
                this.hero = mapper.convertValue(gameProgress.get("hero"), Hero.class);
                this.totalMeters = mapper.convertValue(gameProgress.get("totalMeters"), int.class);
                this.score = mapper.convertValue(gameProgress.get("score"), int.class);
                this.level = mapper.convertValue(gameProgress.get("level"), Level.class);
                this.monster = new Monster();
                this.gameOver = false;

                return true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private IAvoidable getNewAvoidable(String entry){
        switch (entry) {
            case "FelledTreeObstacle":
                return new FelledTreeObstacle();
            case "SawObstacle":
                return new SawObstacle();
            case "AqueductObstacle":
                return new AqueductObstacle();
            default:
                return new RockObstacle();
        }
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

    private void waitDisplay(long milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}