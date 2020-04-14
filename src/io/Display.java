package io;

import game.Currency;
import game.Obstacle;

public class Display implements IGameMonitor{

    public void displayEndGameReport(String reportInString){
        System.out.println(reportInString);
    }

    public void displayCollectedCurrency(Currency collectedCurrency){
        String currencyAsString = collectedCurrency.toString();
        String screenText = "Hero has collected a(n) " + currencyAsString + "!!";
        System.out.println(screenText);
    }

    public void displayAvoidedObstacle(Obstacle avoidedObstacle){
        System.out.println(avoidedObstacle.avoidEffect());
    }


    public void displayReachedDestination(int reachedDestination) {
        String destinationInString = String.valueOf(reachedDestination);
        String textScreen = "Hero has reached to " + destinationInString + " meters!!";
        System.out.println(textScreen);
    }

}
