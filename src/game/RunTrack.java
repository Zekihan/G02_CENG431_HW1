package game;

import java.util.HashMap;
import java.util.Map;

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

        for(int i=0; i<perimeter; i+=500){
            obstacleMap.put(i,)
        }


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
