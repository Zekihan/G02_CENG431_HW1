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

    private void setText(String title, String display) {
        textArea.append(title);
        textArea.append(display+"\n");
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
        setText(" -> ", screenText);
    }

    public void avoidedObstacle(String avoidedObstacleStr){
        System.out.println(avoidedObstacleStr);
        setText(" -> ", avoidedObstacleStr);
    }

    public void reachedDestination(String reachedDestinationStr) {
        String textScreen = "Hero has reached to " + reachedDestinationStr + " meters!!";
        System.out.println(textScreen);
        setText(" -> ",textScreen);
    }

    public void initialGameProperties(String themeStr, String difficultyStr){
        String msg ="\t*********************\n" +
                    "\t  RUNNER HERO  \n" +
                    "\t*********************\n" +
                    " -> Theme: " + themeStr + "\n" +
                    " -> Difficulty: " + difficultyStr + "\n";
        System.out.println(msg);
        setText("", msg);


    }

    public void encounteredMonster(){
        String msg = "Hero encountered a bloody monster !";
        System.out.println(msg);
        setText(" -> ", msg);
    }

}
