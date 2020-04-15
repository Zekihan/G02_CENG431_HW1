package avoidables;

public class FelledTreeObstacle extends SlidableObstacle{

    public FelledTreeObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "Hero has slided under tree.";
    }

    @Override
    public String stumbleEffect() {
        return "Hero has stumbled into a tree obstacle.";
    }
}
