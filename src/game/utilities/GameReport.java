package game.utilities;

public class GameReport {

    public GameReport() {}

    public String createGameReport(String deathReason, int trackPerimeter
            , String trackType, int totalCurrency, int totalDiamonds, int score, String level){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----------------\n")
                    .append("Game Over\n")
                    .append("-----------------\n")
                    .append("REPORT\n")
                    .append("Level: ").append(level).append(" \n")
                    .append("Track type: ").append(trackType).append(" \n")
                    .append("Track perimeter: ").append(trackPerimeter).append(" \n")
                    .append("Death reason: ").append(deathReason).append(" \n")
                    .append("Total collected currencies: ").append(totalCurrency).append(" \n")
                    .append("Total collected diamonds: ").append(totalDiamonds).append(" \n")
                    .append("Total score: ").append(score).append(" \n");
        return stringBuilder.toString();
    }

}
