package io;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Display implements IGameMonitor{

    JTextField textField;
    JTextArea textField2;
    JFrame frame;

    public Display(MyKeyListener action) {

        this.frame = new JFrame("Key Listener");
        frame.setTitle("Runner Hero");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(560,250));

        Container contentPane = frame.getContentPane();

        this.textField = new JTextField();
        textField.setEditable(false);

        this.textField2 = new JTextArea();
        textField2.setFocusable(false);
        textField2.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)textField2.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        action.setTextField(textField);
        textField.addKeyListener(action);
        JScrollPane scrollPane = new JScrollPane(textField2);

        contentPane.add(textField, BorderLayout.PAGE_END);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);
    }

    private void setText(String display) {
        textField2.append(display+"\n");
    }

    public String getKeyEvent() {
        return textField.getText();
    }

    public void displayEndGameReport(String reportInString){
        System.out.println(reportInString);
        setText(reportInString);
    }

    public void displayCollectedCurrency(String collectedCurrencyInString){
        String screenText = "Hero has collected " + collectedCurrencyInString + "!!";
        System.out.println(screenText);
        setText(screenText);
    }

    public void displayAvoidedObstacle(String avoidedObstacleInString){
        System.out.println(avoidedObstacleInString);
        setText(avoidedObstacleInString);
    }


    public void displayReachedDestination(String reachedDestinationInString) {
        String destinationInString = String.valueOf(reachedDestinationInString);
        String textScreen = "Hero has reached to " + destinationInString + " meters!!";
        System.out.println(textScreen);
        setText(textScreen);
    }

}
