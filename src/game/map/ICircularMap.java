package game.map;

import game.avoidables.IAvoidable;
import game.Collectable;

import java.util.Map;

public interface ICircularMap {

    /* Returns the collectible at the given position.
    * @param position   position of the Collectible to be returned.
    * @return the collectible at the given position.
    * @throws IllegalArgumentException  if the given position is less than 0.
    */
    public Collectable getCollectible(int position);

    /* Returns the avoidable (obstacle) at the given position.
     * @param position   position of the IAvoidable to be returned
     * @return the IAvoidable at the given position.
     * @throws IllegalArgumentException  if the given position is less than 0.
     */
    public IAvoidable getObstacle(int position);

    /* Returns true if a collectible is present at the given position, false if not.
     * @param position   position to be checked if there's any collectible.
     * @return true if a collectible is present at the given position, false if not.
     * @throws IllegalArgumentException  if the given position is less than 0.
     */
    public boolean checkForCollectible(int position);

    /* Returns true if an obstacle (IAvoidable) is present at the given position, false if not.
     * @param position   position to be checked if there's any IAvoidable.
     * @return true if an IAvoidable is present at the given position, false if not.
     * @throws IllegalArgumentException  if the given position is less than 0
     */
    public boolean checkForObstacle(int position);

    /* Get the perimeter of the circular map.
    *  @return the perimeter of the circular map.
    */
    public int getPerimeter();

    /* Get the track type of the map.
    * @return the track type of the map.
    */
    public TrackType getTrackType();

    /* Get the obstacle map.
    * @return a copy of the IAvoidable map.
    */
    public Map<Integer, IAvoidable> getObstacleMap();

    /* Removes collectible at given position.
    *  param position   position of the Collectible to be collected by the hero.
    *  @throws IllegalArgumentException if the given position argument is a negative value.
    */
    public void removeCollectable(int position);

    /* Get the collectible map.
    *  @return a copy of the collectible map.
    */
    public Map<Integer, Collectable> getCollectableMap();

    /* Set the collectible map
    * @param collectibleMap    Map of collectible items along with their positions to be set.
    */
    public void setCollectableMap(Map<Integer, Collectable> collectibleMap);

    }

