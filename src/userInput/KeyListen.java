package userInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListen extends InputHandling implements KeyListener {
    public KeyListen(){

    }
    @Override
    public void keyPressed(KeyEvent e) {
        this.keyHandling.get(e.getKeyCode()).run();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
