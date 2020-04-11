package game;

import java.util.Map;

public class RunTrack {

    private int perimeter;
    private TrackType trackType;
    private Map<Integer,Currency> currencyMap;
    private Map<Integer,Obstacle> obstacleMap;


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
