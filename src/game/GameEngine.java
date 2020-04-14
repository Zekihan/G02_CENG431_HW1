package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.InvalidTypeException;
import io.Display;
import io.IGameMonitor;
import io.MyKeyListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private RunTrack runTrack;
    private Hero hero;
    private int totalMeters;
    private int score;
    private Level level;
    private boolean gameOver;
    private IGameMonitor display;

    public GameEngine() {
        this(new RunTrack(RandomEngine.randPerimeterInRange(1000,10000), RandomEngine.randTrackType())
                , new Hero(), 0, 0, RandomEngine.randLevel(), false);
    }


    private GameEngine(RunTrack runTrack, Hero hero, int totalMeters, int score, Level level, boolean gameOver) {
        this.runTrack = runTrack;
        this.hero = hero;
        this.totalMeters = totalMeters;
        this.score = score;
        this.level = level;
        this.gameOver = gameOver;
        this.display = new Display(new MyKeyListener());
    }

    public void startGame(){

        while(!gameOver){

            //Player presses 'q' to quit
            if (display.getKeyEvent().equals("q")){
                gameOver = true;
                endReport("Player quitted");
                try{
                    saveProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            //Monster eats the hero
            if(checkMonsterCondition()){
                gameOver = true;
                endReport("Eaten by a monster");
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

                 //Hero avoids the obstacle
                }else {
                    score += obstacleEncountered.getAvoidPoint() * level.getMultiplier();
                    display.displayEndGameReport(obstacleEncountered.avoidEffect());
                }
            }

            //Hero sees a currency
            if(runTrack.checkForCurrency(hero.getPosition())){
                Currency currency = runTrack.getCurrencyAtPosition(hero.getPosition());

                //Currency requires magnet (Magnetic Coin)
                if(!currency.requiresMagnet() || hero.hasMagnet()){
                    hero.collect(currency);
                    score += currency.getValue() * level.getMultiplier();
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
                hero.resetPosition();
            }

        }
    }

    //Determines whether the hero will stumble or not
    private boolean checkStumbleCondition(IAvoidable obstacleEncountered){
        Random rand = new Random();
        double randVal = rand.nextDouble();
        return randVal < obstacleEncountered.getAvoidChance();
    }

    //Determines whether the monster will eat the hero or not
    private boolean checkMonsterCondition(){
        Random rand = new Random();
        if(totalMeters % 1500 == 0) {
            double randVal = rand.nextDouble();
            return randVal < Probability.MONSTER.getChance();
        }
        return false;
    }


    private void saveProgress() throws InvalidTypeException {
        ProgressHandler progressHandler = new ProgressHandler("./game_progress.json", Operation.WRITE);
        ObjectMapper mapper = new ObjectMapper();
        boolean saved = false;
        //Converting the Object to JSONString
        String jsonString = "{";
        jsonString += "\"GameProgress\":[";
        try {
            jsonString += mapper.writeValueAsString(hero) + ",\n";
            jsonString += mapper.writeValueAsString(runTrack) + ",\n";
            jsonString += mapper.writeValueAsString(totalMeters) + ",\n";
            jsonString += mapper.writeValueAsString(score) + ",\n";
            jsonString += mapper.writeValueAsString(level) + "\n]}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        saved = progressHandler.saveGameProgress(jsonString);
        System.out.println("Progress saved: " + String.valueOf(saved));

    }

    private void endReport(String deathReason){
        GameReport gameReport = new GameReport();
        String report = gameReport.createGameReport(deathReason,runTrack,hero,totalMeters,score,level);
        display.displayEndGameReport(report);
    }


}
