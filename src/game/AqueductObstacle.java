package game;

public class AqueductObstacle extends SlidableObstacle{

    public AqueductObstacle() {
        super();
    }

    @Override
    public String avoidEffect() {
        return "User has slided over the aqueduct.";
    }

    @Override
    public String stumbleEffect() {
        return "User has failed to slide over the aqueduct, fell into the water and drowned to his death.";
    }
}
