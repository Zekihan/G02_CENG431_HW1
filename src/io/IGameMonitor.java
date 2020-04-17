package io;

public interface IGameMonitor {

    /* Displays the player's game report (score, destination reached, collected items, etc.)
    * @param end game report as String
    * @throws IllegalArgumentException if the given argument is null
    */
    public void endGameReport(String reportStr);

    /* Displays the currently collected item's content to the screen
    *  @param collectedItemStr      collected item as String
    * @throws IllegalArgumentException if the given argument is null
    */
    public void collectedItem(String collectedItemStr);

    /* Displays the currently avoided obstacle and its effect text to the screen
    *  @param avoidedObstacleStr    obstacle avoided by the player as String
    *  @throws IllegalArgumentException if the given argument is null
    */
    public void avoidedObstacle(String avoidedObstacleStr);

    /* Displays the currently reached destination to the screen
     *  @param reachedDestinationStr    player's reached destination as String
     *  @throws IllegalArgumentException if the given argument is null
     */
    public void reachedDestination(String reachedDestinationStr);

    /* Displays the game's theme, difficulty, etc. properties to the screen
     *  @param1 themeStr    game's theme as String
     *  @param2 difficultyStr game's difficulty as String
     *  @throws IllegalArgumentException if one of the given String arguments is null
     */
    public void gameProperties(String themeStr, String difficultyStr);

    /* Displays the monster encountered */
    public void encounteredMonster();

    /* Displays the game is loaded from saved progress*/
    public void loadedGame();

    /**/
    public String getKeyEvent();


}
