package io;

public interface GameStorage {

    /* Read game progress from the specified file name
    * @return  the read progress if any
    * @throws FileNotFoundException if a file with the given name does not exist
    */
    public String load();

    /* Save the given game progress into the specified file.
    *  @param2 progressAsString     player's progress as String
    *  @return true if save operation is successful, false if any error occurs
    *  @throws IOException if write operation fails
    */
    public boolean save(String progressAsString);

    public Boolean checkSave();

    public Boolean deleteSave();
}
