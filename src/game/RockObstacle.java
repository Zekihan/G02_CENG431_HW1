package game;

public class RockObstacle extends Obstacle{

    public RockObstacle(Probability probability) {
        super(probability.getChance());
        super.setMove(Move.JUMP);
        super.setObstacleType(ObstacleType.ROCK);
    }
}
