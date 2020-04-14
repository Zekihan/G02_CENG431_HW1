package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.Display;
import io.IGameMonitor;
import io.KeyListen;

import java.util.Random;

public class GameEngine {

    private RunTrack runTrack;
    private Hero hero;
    private int totalMeters;
    private int score;
    private Level level;
    private boolean gameOver;

    public GameEngine() {
        this(new RunTrack(RandomEngine.randPerimeterInRange(1000,10000), RandomEngine.randTrackType())
                , new Hero(), 0, 0, RandomEngine.randLevel(), false);
    }

    //TODO: Use setters to set fields (inside which there will be pre and postconditions).
    //TODO: Put setters inside a initiateGame() method?
    private GameEngine(RunTrack runTrack, Hero hero, int totalMeters, int score, Level level, boolean gameOver) {
        this.runTrack = runTrack;
        this.hero = hero;
        this.totalMeters = totalMeters;
        this.score = score;
        this.level = level;
        this.gameOver = gameOver;
        KeyListen.qToEnd();
    }

    public void startGame(){

        while(!gameOver){

            if(checkMonsterCondition()){
                gameOver = true;
                endReport("Eaten by a monster");
            }

            if(runTrack.checkForObstacle(hero.getPosition())){
                IAvoidable obstacleEncountered = runTrack.getObstacleAtPosition(hero.getPosition());

                if(checkStumbleCondition(obstacleEncountered)){
                    gameOver = true;
                    endReport(obstacleEncountered.stumbleEffect());

                }else {
                    score += obstacleEncountered.getAvoidPoint() * level.getMultiplier();
                }
            }

            if(runTrack.checkForCurrency(hero.getPosition())){
                Currency currency = runTrack.getCurrencyAtPosition(hero.getPosition());

                if(!currency.requiresMagnet() || hero.hasMagnet()){
                    hero.collect(currency);
                    score += currency.getValue() * level.getMultiplier();
                }

            }

            if(!hero.hasMagnet() && score > 5000){
                hero.acquireMagnet();
            }

            totalMeters++;
            hero.incrementPosition();

            if(hero.getPosition() > runTrack.getPerimeter()){
                hero.resetPosition();
            }
        }
    }

    private boolean checkStumbleCondition(IAvoidable obstacleEncountered){
        Random rand = new Random();
        double randVal = rand.nextDouble();
        return randVal < obstacleEncountered.getAvoidChance();
    }

    private boolean checkMonsterCondition(){
        Random rand = new Random();
        if(totalMeters % 1500 == 0) {
            double randVal = rand.nextDouble();
            return randVal < Probability.MONSTER.getChance();
        }
        return false;
    }

    //TODO: Complete the method.
    private void saveProgress(){

        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = "{\n";
        try {
            jsonString += mapper.writeValueAsString(hero) + "\n";
            jsonString += mapper.writeValueAsString(runTrack) + "\n";
            jsonString += mapper.writeValueAsString(totalMeters) + "\n";
            jsonString += mapper.writeValueAsString(score) + "\n";
            jsonString += mapper.writeValueAsString(level) + "\n}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
    }

    private void endReport(String deathReason){
        GameReport gameReport = new GameReport();
        String report = gameReport.createGameReport(deathReason,runTrack,hero,totalMeters,score,level);
        IGameMonitor reportDisplayer = new Display();
        reportDisplayer.displayEndGameReport(report);

    }


}
