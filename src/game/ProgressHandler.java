package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProgressHandler {

    private String fileName;

    public ProgressHandler(String fileName) {

            setFileName(fileName);

    }

    public boolean saveGameProgress(String progressAsString){
        boolean saved = false;

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(progressAsString);
            writer.close();
            saved = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved;
    }

    public String readGameProgress(){
        StringBuilder readProgress = new StringBuilder();

        try {
            File fileToRead = new File(fileName);
            Scanner myReader = new Scanner(fileToRead);
            while (myReader.hasNextLine()) {
                readProgress.append(myReader.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readProgress.toString();
    }


    private void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){ return fileName; }


}
