package io;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeyListen {

    JTextField textField;
    JTextField textField2;
    JFrame frame;

    public KeyListen(MyKeyListener action) {

        this.frame = new JFrame("Key Listener");

        Container contentPane = frame.getContentPane();


        this.textField = new JTextField();
        this.textField2 = new JTextField();
        textField2.setFocusable(false);
        textField.setEditable(false);

        action.setTextField(textField);
        textField.addKeyListener(action);

        contentPane.add(textField, BorderLayout.PAGE_END);
        contentPane.add(textField2, BorderLayout.PAGE_START);

        frame.pack();

        frame.setVisible(true);
    }

    public void setTextField2(String display) {
        textField2.setText(display);
    }

    public String getTextField() {
        return textField.getText();
    }
}