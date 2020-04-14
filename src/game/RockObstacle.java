package game;

public class RockObstacle extends JumpableObstacle{

    public RockObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "Hero has jumped over a rock obstacle.";
    }

    @Override
    public String stumbleEffect() {
        return "Hero has failed to jump over a rock, hit his head and died brutally.";
    }
}
