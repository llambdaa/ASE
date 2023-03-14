package userInput;

import domain.Movement;
import domain.Player;
import domain.Position;

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

    private void registerKeys() {
        keyHandling.put(KeyEvent.VK_W, () -> {
            //w
           player.move(Movement.UP);
        });
        keyHandling.put(KeyEvent.VK_A, () -> {
            //a
           player.move(Movement.LEFT);
        });
        keyHandling.put(KeyEvent.VK_S, () -> {
            //s
            player.move(Movement.DOWN);
        });
        keyHandling.put(KeyEvent.VK_D, () -> {
            //d
            player.move(Movement.RIGHT);
        });
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
