package game.avoidables;

public abstract class JumpableObstacle extends Obstacle{

    public JumpableObstacle() {
        super(Move.JUMP);
    }
}
