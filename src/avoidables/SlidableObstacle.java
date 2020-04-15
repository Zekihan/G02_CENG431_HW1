package avoidables;

import utilities.Move;

public abstract class SlidableObstacle extends Obstacle{

    public SlidableObstacle() {
        super(Move.SLIDE);
    }
}
