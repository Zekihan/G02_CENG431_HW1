package game.map;

import game.avoidables.IAvoidable;
import game.Collectable;

public interface ICircularMap {

    /* Returns the collectible at the given position. */
    public Collectable getCollectibleAtPosition(int position);

    /* Returns the avoidable (obstacle) at the given position. */
    public IAvoidable getObstacleAtPosition(int position);

    /* Returns true if a collectible is present at the given position, false if not. */
    public boolean checkForCollectible(int position);

    /* Returns true if an obstacle (IAvoidable) is present at the given position, false if not. */
    public boolean checkForObstacle(int position);

    /* Get the perimeter of the circular map. */
    public int getPerimeter();

    /* Get the track type of the map. */
    public TrackType getTrackType();

}

