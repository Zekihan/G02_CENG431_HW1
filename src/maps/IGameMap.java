package maps;

import avoidables.IAvoidable;
import utilities.Collectable;

public interface IGameMap {

    /* Returns the collectible at the given position of the game map */
    public Collectable getCollectibleAtPosition(int position);

    /* Returns the avoidable (obstacle) at the given position of the map */
    public IAvoidable getObstacleAtPosition(int position);

    /* Returns true if a collectible is present at the given position, false if not */
    public boolean checkForCollectible(int position);

    /* Returns true if an obstacle (IAvoidable) is present at the given position, false if not */
    public boolean checkForObstacle(int position);
}
