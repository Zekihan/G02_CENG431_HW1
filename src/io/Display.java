package io;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Display implements IGameMonitor{

    JTextField textField;
    JTextArea textArea;
    JFrame frame;

    public Display(Gamepad action) {
        initializeGUI(action);
    }

    private void initializeGUI(Gamepad action){
        this.frame = new JFrame("Key Listener");
        frame.setTitle("Runner Hero");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(500,320));

        Container contentPane = frame.getContentPane();

        this.textField = new JTextField();
        textField.setEditable(false);

        this.textArea = new JTextArea();
        textArea.setFont(textArea.getFont().deriveFont(16f));
        textArea.setFocusable(false);
        textArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        action.setTextField(textField);
        textField.addKeyListener(action);
        JScrollPane scrollPane = new JScrollPane(textArea);

        contentPane.add(textField, BorderLayout.PAGE_END);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);

    }


    public String getKeyEvent() {
        return textField.getText();
    }

    public void endGameReport(String reportStr){
        if(isArgumentNull(reportStr)){
            throw new IllegalArgumentException("Report argument cannot be null.");
        }

        System.out.println(reportStr);
        setText("\n**********GAME REPORT**********\n", reportStr);
    }

    public void collectedItem(String collectedItemStr){
        if(isArgumentNull(collectedItemStr)){
            throw new IllegalArgumentException("Collected item argument cannot be null.");
        }

        String screenText = "Hero has collected " + collectedItemStr + "!";
        System.out.println(screenText);
        setText(" -> ", screenText);
    }

    public void avoidedObstacle(String avoidedObstacleStr){
        if(isArgumentNull(avoidedObstacleStr)){
            throw new IllegalArgumentException("Obstacle argument cannot be null.");
        }

        System.out.println(avoidedObstacleStr);
        setText(" -> ", avoidedObstacleStr);
    }

    public void reachedDestination(String reachedDestinationStr) {
        if(isArgumentNull(reachedDestinationStr)){
            throw new IllegalArgumentException("Destination argument cannot be null.");
        }

        String textScreen = "Hero has finished a lap, reached to " + reachedDestinationStr + " meters!!";
        System.out.println(textScreen);
        setText(" -> ",textScreen);
    }

    public void gameProperties(String themeStr, String difficultyStr){
        if(isArgumentNull(themeStr) || isArgumentNull(difficultyStr)){
            throw new IllegalArgumentException("Theme/Difficulty arguments cannot be null.");
        }

        String msg ="\t*********************\n" +
                    "\t  RUNNER HERO  \n" +
                    "\t*********************\n" +
                    " -> Theme: " + themeStr + "\n" +
                    " -> Difficulty: " + difficultyStr;
        System.out.println(msg);
        setText("", msg);
    }

    public void encounteredMonster(){
        String msg = "Hero encountered a bloody monster !";
        System.out.println(msg);
        setText(" -> ", msg);
    }

    public void loadedGame(){
        String msg = "Loaded game from the saved progress\n";
        System.out.println(msg);
        setText(" -> ", msg);
    }

    //Returns true if the given String argument is null.
    private boolean isArgumentNull(String arg){
        boolean isNull = false;
        if(arg == null){
            isNull = true;
        }
        return isNull;
    }

    //Displays the given 'display' text with title in front to the GUI
    private void setText(String title, String display) {
        textArea.append(title);
        textArea.append(display+"\n");
    }

}
