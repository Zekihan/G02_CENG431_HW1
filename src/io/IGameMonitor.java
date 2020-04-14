package io;

import game.Currency;
import game.Obstacle;

public interface IGameMonitor {

    public void displayEndGameReport(String reportInString);

    public void displayCollectedCurrency(String collectedCurrencyInString);

    public void displayAvoidedObstacle(String avoidedObstacleInString);

    public void displayReachedDestination(String reachedDestinationInString);
}
