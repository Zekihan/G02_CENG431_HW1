package game.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.FileIO;

public class ProgressHandler {

    //Save progress (JSON object) to the file
    public boolean saveGameProgress(ObjectNode progressAsJSON){
        boolean saved = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String progressAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(progressAsJSON);
            FileIO fileIOHandler = new FileIO("game-progress.json");
            saved = fileIOHandler.saveToFile(progressAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saved;
    }
}
