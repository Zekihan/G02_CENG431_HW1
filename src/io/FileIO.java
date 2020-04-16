package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO implements GameSave{

    //Write the given string to the file
    public boolean save(String filePath, String progressAsString){
        boolean saved = false;

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(progressAsString);
            writer.close();
            saved = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved;
    }

    //Read string from the file
    public String read(String filePath){
        StringBuilder readProgress = new StringBuilder();

        try {
            File fileToRead = new File(filePath);
            Scanner myReader = new Scanner(fileToRead);
            while (myReader.hasNextLine()) {
                readProgress.append(myReader.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readProgress.toString();
    }
}
