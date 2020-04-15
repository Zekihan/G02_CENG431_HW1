package maps;

import avoidables.IAvoidable;
import utilities.Collectable;

public interface IGameMap {

    public Collectable getCurrencyAtPosition(int position);

    public IAvoidable getObstacleAtPosition(int position);

    public boolean checkForCurrency(int position);

    public boolean checkForObstacle(int position);
}
