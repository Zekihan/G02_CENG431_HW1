package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListen{

    public static void qToEnd(){

        KeyListener listener = new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_Q) {
                    System.out.println("yay");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }

}
