package io;

public interface IGameMonitor {

    /* Displays the player's game report (score, destination reached, collected items, etc.) */
    public void endGameReport(String reportStr);

    /* Displays the currently collected item's content to the screen */
    public void collectedItem(String collectedItemStr);

    /* Displays the currently avoided obstacle and its effect text to the screen */
    public void avoidedObstacle(String avoidedObstacleStr);

    /* Displays the currently reached destination to the screen */
    public void reachedDestination(String reachedDestinationStr);

    /* Displays the game's theme, difficulty, etc. properties to the screen */
    public void displayInitialGameProperties(String themeStr, String difficultyStr);

    /**/
    public String getKeyEvent();
}
