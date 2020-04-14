package game;

import com.sun.jdi.InvalidTypeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProgressHandler {

    private String fileNameToSaveIn;
    private String fileNameToReadFrom;
    private Operation operationType;

    public ProgressHandler(String fileNameToReadFrom, String fileNameToSaveIn){
        setFileNameToReadFrom(fileNameToReadFrom);
        setFileNameToSaveIn(fileNameToSaveIn);
        this.operationType = Operation.READ_WRITE;
    }

    public ProgressHandler(String fileName, Operation operationType) throws InvalidTypeException {

        if(operationType == Operation.READ){
            setFileNameToReadFrom(fileName);

        }else if(operationType == Operation.WRITE){
            setFileNameToSaveIn(fileName);

        }else{
            throw new InvalidTypeException("Given operation type cannot be READ_WRITE with only one file name given.");
        }
    }


    public boolean saveGameProgress(String progressAsString){
        boolean saved = false;

        try {
            FileWriter writer = new FileWriter(fileNameToSaveIn);
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
            File fileToRead = new File(fileNameToReadFrom);
            Scanner myReader = new Scanner(fileToRead);
            while (myReader.hasNextLine()) {
                readProgress.append(myReader.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readProgress.toString();
    }

    private void setFileNameToSaveIn(String fileNameToSaveIn){
        if(fileNameToSaveIn == null || fileNameToSaveIn.trim().isEmpty()){
            //throw InvalidFileNameException();
        }else{
            this.fileNameToSaveIn = fileNameToSaveIn;
        }
    }

    private void setFileNameToReadFrom(String fileNameToReadFrom){
        if(fileNameToReadFrom == null || fileNameToReadFrom.trim().isEmpty()){
            try {
                throw new InvalidFileNameException("Given file name is either null or empty.");
            } catch (InvalidFileNameException e) {
                e.printStackTrace();
            }
        }else{
            this.fileNameToReadFrom = fileNameToReadFrom;
        }
    }

    public String getFileNameToSaveIn(){ return fileNameToSaveIn; }

    public String getFileNameToReadFrom(){ return fileNameToReadFrom; }

}
