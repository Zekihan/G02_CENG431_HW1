package game;

public interface IObstacle {

    public Move getMove();

    public ObstacleType getObstacleType();

    public double getAvoidChance();

}
