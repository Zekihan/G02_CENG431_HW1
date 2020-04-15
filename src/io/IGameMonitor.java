package io;

public interface IGameMonitor {

    public void endGameReport(String reportStr);

    public void collectedCurrency(String collectedCurrencyStr);

    public void avoidedObstacle(String avoidedObstacleStr);

    public void reachedDestination(String reachedDestinationStr);

    public String getKeyEvent();
}
