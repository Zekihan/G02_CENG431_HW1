package game.map;

import game.avoidables.IAvoidable;
import game.Collectable;

import java.util.HashMap;
import java.util.Map;

public class RunTrack implements ICircularMap {

    private int perimeter;
    private TrackType trackType;
    private Map<Integer, Collectable> collectableMap;
    private Map<Integer, IAvoidable> obstacleMap;

    public RunTrack(int perimeter, TrackType trackType, Map<Integer, Collectable> collectableMap, Map<Integer,IAvoidable> obstacleMap) {
        setPerimeter(perimeter);
        this.trackType = trackType;
        this.collectableMap = collectableMap;
        this.obstacleMap = obstacleMap;
    }

    public Collectable getCollectible(int position){
        if(isValidPosition(position)){
            throw new IllegalArgumentException("Position argument not valid.");
        }

        return collectableMap.get(position);
    }

    public IAvoidable getObstacle(int position){
        if(isValidPosition(position)){
            throw new IllegalArgumentException("Position argument not valid.");
        }

        return obstacleMap.get(position);
    }

    public boolean checkForCollectible(int position){
        if(isValidPosition(position)){
            throw new IllegalArgumentException("Position argument cannot be less than 0.");
        }

        return collectableMap.containsKey(position);
    }

    public boolean checkForObstacle(int position){
        if(isValidPosition(position)){
            throw new IllegalArgumentException("Position argument not valid.");
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
            throw new IllegalArgumentException("Given run track perimeter cannot be less than or equal to 0");
        }
        this.perimeter = perimeter;
    }

    //Necessary for saving progress to json
    public Map<Integer, Collectable> getCollectableMap() {
        Map<Integer, Collectable> copyMap = new HashMap<>(collectableMap.size());
        for(Integer nextPosition: collectableMap.keySet()){
            copyMap.put(nextPosition, collectableMap.get(nextPosition));
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

    public void setCollectableMap(Map<Integer, Collectable> currencyMap) {
        this.collectableMap = currencyMap;
    }

    public void removeCollectable(int position) {
        if(isValidPosition(position)){
            throw new IllegalArgumentException("Position argument not valid.");
        }
        collectableMap.remove(position);
    }

    //Checks whether the given position argument is negative, if so return true, false if otherwise
    private boolean isValidPosition(int position){
        return position < 0 && position < getPerimeter();
    }

}
