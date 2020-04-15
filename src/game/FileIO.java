package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {

    private String fileName;

    public FileIO(String fileName) { setFileName(fileName); }

    //Write the given string to the file
    public boolean saveToFile(String progressAsString){
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

    //Read string from the file
    public String readFromFile(){
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
