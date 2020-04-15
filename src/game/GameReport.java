package game;

public class GameReport {

    public String createGameReport(String deathReason, RunTrack runTrack, Hero hero, int totalMeters, int score, Level level){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----------------\n")
                    .append("Game Over\n")
                    .append("-----------------\n")
                    .append("REPORT\n")
                    .append("Level: ").append(level.name()).append(" \n")
                    .append("Track type: ").append(runTrack.getTrackType().name()).append(" \n")
                    .append("Track perimeter: ").append(runTrack.getPerimeter()).append(" \n")
                    .append("Death reason: ").append(deathReason).append(" \n")
                    .append("Total collected currencies: ").append(hero.totalItems()).append(" \n")
                    .append("Total collected diamonds: ").append(hero.totalDiamonds()).append(" \n")
                    .append("Total score: ").append(score).append(" \n");
        return stringBuilder.toString();
    }

}
