package userInput;

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
            Position currentPos = player.getPosition();
            player.setPosition(new Position(currentPos.getX(), currentPos.getY() + 1));
        });
        keyHandling.put(KeyEvent.VK_A, () -> {
            //a
            Position currentPos = player.getPosition();
            player.setPosition(new Position(currentPos.getX() - 1, currentPos.getY()));
        });
        keyHandling.put(KeyEvent.VK_S, () -> {
            //s
            Position currentPos = player.getPosition();
            player.setPosition(new Position(currentPos.getX(), currentPos.getY() - 1));
        });
        keyHandling.put(KeyEvent.VK_D, () -> {
            //d
            Position currentPos = player.getPosition();
            player.setPosition(new Position(currentPos.getX() + 1, currentPos.getY()));
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
