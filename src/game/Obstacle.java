package game;

public abstract class Obstacle implements IObstacle {

    private Move move;
    private ObstacleType obstacleType;
    private double avoidChance;

    public Obstacle(double avoidChance) {
        this.avoidChance = avoidChance;
    }

    public Move getMove() {
        return move;
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public double getAvoidChance() {
        return avoidChance;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setObstacleType(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }
}
