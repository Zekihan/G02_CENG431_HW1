package game;

public class AqueductObstacle extends Obstacle implements IObstacle{

    public AqueductObstacle(double avoidChance) {
        super(avoidChance);
        super.setMove(Move.SLIDE);
        super.setObstacleType(ObstacleType.AQUEDUCT);
    }
}
