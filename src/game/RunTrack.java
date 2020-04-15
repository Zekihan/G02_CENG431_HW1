package game;

import exceptions.IllegalRunTrackSizeException;

import java.util.HashMap;
import java.util.Map;

public class RunTrack implements IGameMap{

    private int perimeter;
    private TrackType trackType;
    private Map<Integer, Collectable> currencyMap;
    private Map<Integer,IAvoidable> obstacleMap;


    public RunTrack(int perimeter, TrackType trackType, Map<Integer, Collectable> currencyMap, Map<Integer,IAvoidable> obstacleMap) {
        setPerimeter(perimeter);
        this.trackType = trackType;
        this.currencyMap = currencyMap;
        this.obstacleMap = obstacleMap;
    }


    public Collectable getCurrencyAtPosition(int position){

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
    public Map<Integer, Collectable> getCurrencyMap() {
        Map<Integer, Collectable> copyMap = new HashMap<Integer, Collectable>(currencyMap.size());
        for(Integer nextPosition: currencyMap.keySet()){
            copyMap.put(nextPosition, currencyMap.get(nextPosition));
        }
        return copyMap;
    }


    public Map<Integer, IAvoidable> getObstacleMap() {
        Map<Integer, IAvoidable> copyMap = new HashMap<Integer, IAvoidable>(obstacleMap.size());
        for(Integer nextPosition: obstacleMap.keySet()){
            copyMap.put(nextPosition, obstacleMap.get(nextPosition));
        }
        return copyMap;
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
