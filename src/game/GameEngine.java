package game;

import io.Display;
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
        this(new RunTrack(Randoms.randPerimeterInRange(1000,10000), Randoms.randTrackType())
                , new Hero(), 0, 0, Randoms.randLevel(), false);
    }

    public GameEngine(RunTrack runTrack, Hero hero, int totalMeters, int score, Level level, boolean gameOver) {
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
                Obstacle obstacleEncountered = runTrack.getObstacleAtPosition(hero.getPosition());

                if(checkStumbleCondition(obstacleEncountered)){
                    gameOver = true;
                    endReport("Failed to " + obstacleEncountered.getNecessaryMove()
                            + " stumbled into a fuckin " + obstacleEncountered.name());

                }else {
                    score += obstacleEncountered.getPoint() * level.getMultiplier();
                }
            }

            if(runTrack.checkForCurrency(hero.getPosition())){
                Currency currency = runTrack.getCurrencyAtPosition(hero.getPosition());

                if(!currency.isMagnetic() || hero.hasMagnet()){
                    hero.putToChest(currency);
                    score += currency.getValue() * level.getMultiplier();
                }

            }

            if(!hero.hasMagnet() && score > 5000){
                hero.giveMagnet();
            }

            totalMeters++;
            hero.incrementPosition();

            if(hero.getPosition() > runTrack.getPerimeter()){
                hero.resetPosition();
            }
        }
    }

    private boolean checkStumbleCondition(Obstacle obstacleEncountered){
        Random rand = new Random();
        double randVal = rand.nextDouble();
        return randVal < obstacleEncountered.getChance();
    }

    private boolean checkMonsterCondition(){
        Random rand = new Random();
        if(totalMeters % 1500 == 0) {
            double randVal = rand.nextDouble();
            return randVal < Probability.MONSTER.getChance();
        }
        return false;
    }

    private void saveProgress(){
        Progress progress = new Progress(runTrack,hero,totalMeters,score,level);

    }

    private void endReport(String deathReason){
        Progress progress = new Progress(runTrack,hero,totalMeters,score,level);
        String report = progress.gameReport(deathReason);
        Display.show(report);

    }


}
