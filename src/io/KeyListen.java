package io;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeyListen {

    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextField textField5;
    JTextField textField6;
    JFrame frame;

    public KeyListen(MyKeyListener action) {

        this.frame = new JFrame("Key Listener");
        frame.setTitle("Runner Hero");
        frame.setResizable(true);

        Container contentPane = frame.getContentPane();

        this.textField = new JTextField();
        textField.setEditable(false);

        this.textField2 = new JTextField();
        textField2.setFocusable(false);
        textField2.setSize(3,3);
        this.textField3 = new JTextField();
        textField3.setFocusable(false);
        this.textField4 = new JTextField();
        textField4.setFocusable(false);
        this.textField5 = new JTextField();
        textField5.setFocusable(false);
        this.textField6 = new JTextField();
        textField6.setFocusable(false);

        action.setTextField(textField);
        textField.addKeyListener(action);

        contentPane.add(textField, BorderLayout.PAGE_END);
        contentPane.add(textField2, BorderLayout.PAGE_START);
        contentPane.add(textField3, BorderLayout.AFTER_LAST_LINE);
        contentPane.add(textField4, BorderLayout.AFTER_LAST_LINE);
        contentPane.add(textField5, BorderLayout.AFTER_LAST_LINE);
        contentPane.add(textField6, BorderLayout.AFTER_LAST_LINE);

        frame.pack();

        frame.setVisible(true);
    }

    public void setTextField2(String display) {
        textField2.setText(display);
    }

    public void displayReport(String display) {
        textField2.setText(display);
    }

    public String getTextField() {
        return textField.getText();
    }
}