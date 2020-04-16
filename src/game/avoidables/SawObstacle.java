package game.avoidables;

public class SawObstacle extends JumpableObstacle{

    public SawObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "Hero has jumped over the saw obstacle.";
    }

    @Override
    public String stumbleEffect() {
        return "Hero has rushed into a saw obstacle, split into two.";
    }
}