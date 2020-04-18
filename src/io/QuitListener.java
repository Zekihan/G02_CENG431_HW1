package io;

import exceptions.IncorrectQuitButtonException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class QuitListener implements KeyListener {

    JTextField textField;

    @Override
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Key Pressed, Q");
            textField.setText("q");
        }
        else{
            try{
                textField.setText("Incorrect key is given, you can press q to quit!");
                throw new IncorrectQuitButtonException("Incorrect key is given, you can press Q to quit!");

            }catch (IncorrectQuitButtonException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
}