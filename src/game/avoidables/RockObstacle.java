package game.avoidables;

public class RockObstacle extends JumpableObstacle{

    public RockObstacle() {
        super();
    }

    @Override
    public String avoidResult() { return "Hero has jumped over a rock obstacle."; }

    @Override
    public String stumbleResult() {
        return "Hero has failed to jump over a rock, hit his head.";
    }
}
