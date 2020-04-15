package io;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Display implements IGameMonitor{

    JTextField textField;
    JTextArea textField2;
    JFrame frame;

    public Display(Gamepad action) {
        initializeGUI(action);
    }

    private void initializeGUI(Gamepad action){
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

    private void setText(String title, String display) {
        textField2.append(title);
        textField2.append(display+"\n");
    }

    public String getKeyEvent() {
        return textField.getText();
    }

    public void endGameReport(String reportStr){
        System.out.println(reportStr);
        setText("\n**********GAME REPORT**********\n", reportStr);
    }

    public void collectedItem(String collectedItemStr){
        String screenText = "Hero has collected " + collectedItemStr + "!";
        System.out.println(screenText);
        setText("+SCORE -> ", screenText);
    }

    public void avoidedObstacle(String avoidedObstacleStr){
        System.out.println(avoidedObstacleStr);
        setText("+SCORE -> ", avoidedObstacleStr);
    }

    public void reachedDestination(String reachedDestinationStr) {
        String textScreen = "Hero has reached to " + reachedDestinationStr + " meters!!";
        System.out.println(textScreen);
        setText("",textScreen);
    }

    public void displayInitialGameProperties(String themeStr, String difficultyStr){
        setText("\t\t", "*********************");
        setText("\t\t", "  RUNNER HERO  ");
        setText("\t\t", "*********************");
        setText(" -> Theme: ", themeStr);
        setText(" -> Difficulty: ", difficultyStr + "\n");
    }

}
