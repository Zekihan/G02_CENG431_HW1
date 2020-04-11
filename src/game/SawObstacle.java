package game;

public class SawObstacle extends Obstacle implements IObstacle{

    public SawObstacle(double avoidChance) {
        super(avoidChance);

        super.setMove(Move.JUMP);
        super.setObstacleType(ObstacleType.SAW);
    }
}
