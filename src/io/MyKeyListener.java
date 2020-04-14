package io;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class MyKeyListener  implements KeyListener {

    JTextField textField;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Key Pressed, Q");
            textField.setText("q");
        }
        else{
            textField.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
}