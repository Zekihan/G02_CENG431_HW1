package game;

public class FelledTreeObstacle extends SlidableObstacle{

    public FelledTreeObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "....";
    }

    @Override
    public String stumbleEffect() {
        return "User has stumbled into a tree obstacle and died.";
    }
}
