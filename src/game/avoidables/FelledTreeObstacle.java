package game.avoidables;

public class FelledTreeObstacle extends SlidableObstacle{

    public FelledTreeObstacle() {
        super();
    }

    @Override
    public String avoidResult() {
        return "Hero has slided under tree.";
    }

    @Override
    public String stumbleResult() {
        return "Hero has stumbled into a tree obstacle.";
    }
}
