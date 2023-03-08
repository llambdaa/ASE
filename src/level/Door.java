package level;

import rendering.Buffer;

public class Door extends Renderable {
    private static Buffer LOCKED_BUFFER = Buffer.from("X");
    private static Buffer UNLOCKED_BUFFER = Buffer.from("O");
    
    private int x;
    private int y;
    private Room target;
    private boolean locked;
    
    public Door(int x, int y, Room target, boolean locked) {
        this.x = x;
        this.y = y;
        this.target = target;
        this.locked = locked;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Room getTargetRoom() {
        return this.target;
    }
    
    public void unlock() {
        this.locked = false;
    }
    
    @Override
    public void render() {}
    
    @Override
    public Buffer getBuffer() {
       return this.locked ? Door.LOCKED_BUFFER : Door.UNLOCKED_BUFFER;
    }
}
