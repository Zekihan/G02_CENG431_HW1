package avoidables;

import utilities.Move;

public abstract class JumpableObstacle extends Obstacle{

    public JumpableObstacle() {
        super(Move.JUMP);
    }
}
