package io;

import game.Currency;
import game.Obstacle;

public interface IGameMonitor {

    public void displayEndGameReport(String reportInString);

    public void displayCollectedCurrency(Currency collectedCurrency);

    public void displayAvoidedObstacle(Obstacle avoidedObstacle);

    public void displayReachedDestination(int reachedDestination);
}
