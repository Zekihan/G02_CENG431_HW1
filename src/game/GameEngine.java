package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.Display;
import io.Gamepad;
import io.IGameMonitor;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private RunTrack runTrack;
    private Hero hero;
    private Monster monster;
    private int totalMeters;
    private int score;
    private Level level;
    private boolean gameOver;
    private IGameMonitor display;


    public GameEngine() {
        this(new RunTrack(RandomEngine.randPerimeterInRange(1000,10000), RandomEngine.randTrackType())
                , new Hero(), new Monster(), 0, 0, RandomEngine.randLevel(), false);
    }

    private GameEngine(RunTrack runTrack, Hero hero, Monster monster, int totalMeters, int score, Level level, boolean gameOver) {
        this.runTrack = runTrack;
        this.hero = hero;
        this.monster = monster;
        this.totalMeters = totalMeters;
        this.score = score;
        this.level = level;
        this.gameOver = gameOver;
        this.display = new Display(new Gamepad());
    }

    public void startGame(){

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
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                IAvoidable obstacleEncountered = runTrack.getObstacleAtPosition(hero.getPosition());

                //Hero stumbles upon the obstacle
                if(checkStumbleCondition(obstacleEncountered)){
                    gameOver = true;
                    endReport(obstacleEncountered.stumbleEffect());
                    break;

                 //Hero avoids the obstacle
                }else {
                    score += obstacleEncountered.getAvoidPoint() * level.getMultiplier();
                    display.avoidedObstacle(obstacleEncountered.avoidEffect());
                }
            }

            //Hero sees a currency
            if(runTrack.checkForCurrency(hero.getPosition())){
                Currency currency = runTrack.getCurrencyAtPosition(hero.getPosition());

                //Currency requires magnet (Magnetic Coin)
                if(!currency.requiresMagnet() || hero.hasMagnet()){
                    hero.collect(currency);
                    score += currency.getValue() * level.getMultiplier();
                    display.collectedCurrency(currency.toString());
                }

            }

            //Hero gets a magnet whenever his/her score is over 5000
            if(!hero.hasMagnet() && score > 5000){
                hero.acquireMagnet();
            }

            //Increment the total distance traveled
            totalMeters++;
            hero.incrementPosition();

            //Hero has completed one iteration of run track, reset the run track's content (currencies)
            if(hero.getPosition() > runTrack.getPerimeter()){
                display.reachedDestination(String.valueOf(totalMeters));
                hero.resetPosition();
            }

        }
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
        if(totalMeters % 1500 == 0) {

            double randVal = rand.nextDouble();
            return randVal < monster.getEatChance();
        }
        return false;
    }


    //Save player's progress into the game_progress.json file as a json object
    private void saveProgress() {
        ProgressHandler progressHandler = new ProgressHandler("./game_progress.json");
        ObjectMapper mapper = new ObjectMapper();
        boolean saved = false;
        JsonNodeFactory f = JsonNodeFactory.instance;
        ObjectNode gameProgress = f.objectNode();
        ArrayNode objectArray = gameProgress.putArray("gameProgress");
        ObjectNode valuesNode = f.objectNode();
        try {
            valuesNode.put("hero", mapper.readTree(mapper.writeValueAsString(hero)));
            valuesNode.put("runTrack", mapper.readTree(mapper.writeValueAsString(runTrack)));
            valuesNode.put("totalMeters", mapper.readTree(mapper.writeValueAsString(totalMeters)));
            valuesNode.put("score", mapper.readTree(mapper.writeValueAsString(score)));
            valuesNode.put("level", mapper.readTree(mapper.writeValueAsString(level)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectArray.add(valuesNode);
        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameProgress);
            saved = progressHandler.saveGameProgress(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Progress saved: " + saved);

    }

    //End of the game, player's score, cause of death, etc. is displayed
    private void endReport(String deathReason){
        GameReport gameReport = new GameReport();
        String report = gameReport.createGameReport(deathReason,runTrack,hero,totalMeters,score,level);
        display.endGameReport(report);
    }


}
