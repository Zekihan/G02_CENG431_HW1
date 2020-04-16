package game.avoidables;

public interface IAvoidable {

    /* Returns IAvoidable's individual avoid text information.
    * @return the IAvoidable's individual avoid text.
    */
    public String avoidEffect();

    /* Returns IAvoidable's individual stumble text information.
     * @return the IAvoidable's individual stumble text.
     */
    public String stumbleEffect();

    /* Returns IAvoidable's individual avoid chance information.
     * @return the IAvoidable's individual avoid chance as a double.
     */
    public double getAvoidChance();

     /* Gets IAvoidable's individual avoid point information.
     * @return the IAvoidable's individual avoid point.
     */
    public int getAvoidPoint();

    /* Gets move that is necessary to avoid the callee IAvoidable.
     * @return the IAvoidable's individual avoid move.
     */
    public Move getNecessaryMove();

}
