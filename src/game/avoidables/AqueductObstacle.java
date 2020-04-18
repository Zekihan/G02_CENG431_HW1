package game.avoidables;

public class AqueductObstacle extends SlidableObstacle{

    public AqueductObstacle() {
        super();
    }

    @Override
    public String avoidResult() { return "Hero has slided under the aqueduct."; }

    @Override
    public String stumbleResult() {
        return "Hero has failed to slide under the aqueduct.";
    }
}
