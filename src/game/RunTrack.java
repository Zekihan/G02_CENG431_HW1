package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RunTrack implements IGameMap{

    private int perimeter;
    private TrackType trackType;
    private Map<Integer,Currency> currencyMap;
    private Map<Integer,IAvoidable> obstacleMap;


    public RunTrack(int perimeter, TrackType trackType) {
        setPerimeter(perimeter);
        this.trackType = trackType;
        this.currencyMap = new HashMap<>();
        this.obstacleMap = new HashMap<>();
        generateRandomCurrencies();
        generateRandomObstacles();
    }


    public Currency getCurrencyAtPosition(int position){

        return currencyMap.get(position);
    }

    public IAvoidable getObstacleAtPosition(int position){

        return obstacleMap.get(position);
    }

    public boolean checkForCurrency(int position){

        return currencyMap.containsKey(position);
    }

    public boolean checkForObstacle(int position){

        return obstacleMap.containsKey(position);
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

    public Map<Integer, IAvoidable> getObstacleMap() {
        return obstacleMap;
    }

    private void setPerimeter(int perimeter){
        if(perimeter <= 0){
            try{
                throw new IllegalRunTrackSizeException("Given run track perimeter cannot be less than or equal to 0!");

            }catch(IllegalRunTrackSizeException e){
                e.printStackTrace();
            }
        }else{
            this.perimeter = perimeter;
        }
    }

    //Generate a random currency
    private Currency createRandomCurrency() {
        Currency[] currencies = Currency.values();
        Random rand = new Random();
        int i = rand.nextInt(currencies.length);
        return currencies[i];
    }


    //TODO: Put the random generator functions inside the GameEngine.java class!!
    //Generate a random obstacle
    private IAvoidable createRandomObstacle(){
        //TODO: Make the list assignment more generic?
        IAvoidable[] obstacles = {new RockObstacle(), new SawObstacle(), new AqueductObstacle(), new FelledTreeObstacle()};
        Random rand = new Random();
        int i = rand.nextInt(obstacles.length);
        return obstacles[i];
    }


    //Create an obstacle map from randomly generated obstacles
    private void generateRandomObstacles(){
        for(int i=0; i < perimeter; i+=500){
            obstacleMap.put(i, createRandomObstacle());
        }
    }


    //Create an currency map from randomly generated currencies
    private void generateRandomCurrencies(){
        for(int i=0; i < perimeter; i+= 50){
            currencyMap.put(i, createRandomCurrency());
        }
    }
}
