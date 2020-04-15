package avoidables;

public class AqueductObstacle extends SlidableObstacle{

    public AqueductObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "Hero has slided under the aqueduct.";
    }

    @Override
    public String stumbleEffect() {
        return "Hero has failed to slide under the aqueduct.";
    }
}
