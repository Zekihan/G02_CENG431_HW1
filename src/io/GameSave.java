package io;

public interface GameSave {

    public String read(String filePath);

    public boolean save(String filePath, String progressAsString);
}
