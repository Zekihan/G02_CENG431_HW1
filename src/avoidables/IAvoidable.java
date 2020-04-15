package avoidables;

import utilities.Move;

public interface IAvoidable {

    public String avoidEffect();

    public String stumbleEffect();

    public double getAvoidChance();

    public int getAvoidPoint();

    public Move getNecessaryMove();

}
