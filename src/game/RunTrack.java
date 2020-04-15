package game;

import exceptions.IllegalRunTrackSizeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RunTrack implements IGameMap{

    private int perimeter;
    private TrackType trackType;
    private Map<Integer,Currency> currencyMap;
    private Map<Integer,IAvoidable> obstacleMap;


    public RunTrack(int perimeter, TrackType trackType, Map<Integer,Currency> currencyMap, Map<Integer,IAvoidable> obstacleMap) {
        setPerimeter(perimeter);
        this.trackType = trackType;
        this.currencyMap = currencyMap;
        this.obstacleMap = obstacleMap;
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

    //Necessary for saving progress to json
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
}
