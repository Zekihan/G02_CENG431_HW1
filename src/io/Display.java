package io;

public class Display implements IGameMonitor{

    public void displayEndGameReport(String reportInString){
        System.out.println(reportInString);
    }

    public void displayCollectedCurrency(String collectedCurrencyInString){
        String screenText = "Hero has collected " + collectedCurrencyInString + "!!";
        System.out.println(screenText);
    }

    public void displayAvoidedObstacle(String avoidedObstacleInString){
        System.out.println(avoidedObstacleInString);
    }


    public void displayReachedDestination(String reachedDestinationInString) {
        String destinationInString = String.valueOf(reachedDestinationInString);
        String textScreen = "Hero has reached to " + destinationInString + " meters!!";
        System.out.println(textScreen);
    }

}
