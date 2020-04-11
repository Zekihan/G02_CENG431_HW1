package game;

public class FelledTreeObstacle extends Obstacle implements IObstacle{


    public FelledTreeObstacle(double avoidChance) {
        super(avoidChance);

        super.setMove(Move.SLIDE);
        super.setObstacleType(ObstacleType.FELLED_TREE);
    }
}
