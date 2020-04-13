package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RunTrack {

    private int perimeter;
    private TrackType trackType;
    private Map<Integer,Currency> currencyMap;
    private Map<Integer,Obstacle> obstacleMap;


    public RunTrack(int perimeter, TrackType trackType) {
        this.perimeter = perimeter;
        this.trackType = trackType;
        this.currencyMap = new HashMap<>();
        this.obstacleMap = new HashMap<>();

        for(int i=0; i < perimeter; i+=500){
            obstacleMap.put(i, createRandomObstacle());
        }

        for(int i=0; i < perimeter; i+= 50){
            currencyMap.put(i, createRandomCurrency());
        }
    }


    public Currency getCurrencyAtPosition(int position){

        return currencyMap.get(position);
    }

    public Obstacle getObstacleAtPosition(int position){

        return obstacleMap.get(position);
    }

    public boolean checkForCurrency(int position){

        return currencyMap.containsKey(position);
    }

    public boolean checkForObstacle(int position){

        return obstacleMap.containsKey(position);
    }

    private Currency createRandomCurrency() {

        Currency[] currencies = Currency.values();
        Random rand = new Random();
        int i = rand.nextInt(currencies.length);
        return currencies[i];

    }

    private Obstacle createRandomObstacle(){

        Obstacle[] obstacles = Obstacle.values();
        Random rand = new Random();
        int i = rand.nextInt(obstacles.length);
        return obstacles[i];
    }



    public int getPerimeter() {
        return perimeter;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public Map<Integer, Currency> getCurrencyMap() {
        return currencyMap;
    }

    public Map<Integer, Obstacle> getObstacleMap() {
        return obstacleMap;
    }


}
