package io;

public interface GameStorage {

    /* Read game progress from the specified file name
    * @param filePath   file path to read from
    * @return  the read progress if any
    * @throws FileNotFoundException if a file with the given name does not exist
    */
    public String read(String filePath);

    /* Save the given game progress into the specified file.
    *  @param1 filePath             file path to save the progress into
    *  @param2 progressAsString     player's progress as String
    *  @return true if save operation is successful, false if any error occurs
    *  @throws IOException if write operation fails
    */
    public boolean save(String filePath, String progressAsString);
}
