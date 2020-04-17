package game.map;

import exceptions.IllegalRunTrackSizeException;
import game.avoidables.IAvoidable;
import game.Collectable;

import java.util.HashMap;
import java.util.Map;

public class RunTrack implements ICircularMap {

    private int perimeter;
    private TrackType trackType;
    private Map<Integer, Collectable> currencyMap;
    private Map<Integer, IAvoidable> obstacleMap;


    public RunTrack(int perimeter, TrackType trackType, Map<Integer, Collectable> currencyMap, Map<Integer,IAvoidable> obstacleMap) {
        setPerimeter(perimeter);
        this.trackType = trackType;
        this.currencyMap = currencyMap;
        this.obstacleMap = obstacleMap;
    }


    public Collectable getCollectible(int position){
        if(isPositionNegative(position)){
            throw new IllegalArgumentException("Position argument cannot be less than 0.");
        }

        return currencyMap.get(position);
    }

    public IAvoidable getObstacle(int position){
        if(isPositionNegative(position)){
            throw new IllegalArgumentException("Position argument cannot be less than 0.");
        }

        return obstacleMap.get(position);
    }

    public boolean checkForCollectible(int position){
        if(isPositionNegative(position)){
            throw new IllegalArgumentException("Position argument cannot be less than 0.");
        }

        return currencyMap.containsKey(position);
    }

    public boolean checkForObstacle(int position){
        if(isPositionNegative(position)){
            throw new IllegalArgumentException("Position argument cannot be less than 0.");
        }

        return obstacleMap.containsKey(position);
    }


    public int getPerimeter() {
        return perimeter;
    }


    public TrackType getTrackType() {
        return trackType;
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

    //Necessary for saving progress to json
    public Map<Integer, Collectable> getCollectibleMap() {
        Map<Integer, Collectable> copyMap = new HashMap<>(currencyMap.size());
        for(Integer nextPosition: currencyMap.keySet()){
            copyMap.put(nextPosition, currencyMap.get(nextPosition));
        }
        return copyMap;
    }

    public Map<Integer, IAvoidable> getObstacleMap() {
        Map<Integer, IAvoidable> copyMap = new HashMap<>(obstacleMap.size());
        for(Integer nextPosition: obstacleMap.keySet()){
            copyMap.put(nextPosition, obstacleMap.get(nextPosition));
        }
        return copyMap;
    }

    public void setCollectibleMap(Map<Integer, Collectable> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public void removeCollectable(int position) {
        if(isPositionNegative(position)){
            throw new IllegalArgumentException("Position of the collectible cannot be null!");
        }
        currencyMap.remove(position);
    }

    //Checks whether the given position argument is negative, if so return true, false if otherwise
    private boolean isPositionNegative(int position){
        boolean negative = false;
        if(position < 0){
           negative = true;
        }
        return negative;
    }

}
