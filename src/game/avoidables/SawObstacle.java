package game.avoidables;

public class SawObstacle extends JumpableObstacle{

    public SawObstacle() {
        super();
    }

    @Override
    public String avoidResult() {
        return "Hero has jumped over the saw obstacle.";
    }

    @Override
    public String stumbleResult() {
        return "Hero has rushed into a saw obstacle, split into two.";
    }
}
