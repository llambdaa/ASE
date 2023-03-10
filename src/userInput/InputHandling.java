package userInput;

import domain.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class InputHandling implements KeyListener {
    protected HashMap<Integer, Runnable> keyHandling = new HashMap<>();
    private Player player;

    public InputHandling(Player player) {
        this.player = player;
        registerKeys();
    }

    private void registerKeys(){
        keyHandling.put(KeyEvent.VK_W, () -> {
            //w
            System.out.println("w");
        });
        keyHandling.put(KeyEvent.VK_A, () -> {
            //a

        });
        keyHandling.put(KeyEvent.VK_S, () -> {
            //s

        });
        keyHandling.put(KeyEvent.VK_D, () -> {
            //d

        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        this.keyHandling.get(e.getKeyCode()).run();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
