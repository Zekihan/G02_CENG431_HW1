package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO implements GameStorage{

    private final String filePath;

    public FileIO(String filePath) {
        this.filePath = filePath;
    }

    //Write the given string to the file
    public boolean save(String progressAsString){
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
    public String load(){
        StringBuilder readProgress = new StringBuilder();

        try {
            File f = new File(filePath);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                readProgress.append(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readProgress.toString();
    }

    public Boolean checkSave(){

        File f = new File(filePath);

        return f.exists() && !f.isDirectory();
    }

    public Boolean deleteSave(){
        File f = new File(filePath);
        return f.delete();
    }
}
