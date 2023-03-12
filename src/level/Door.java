package level;

import rendering.Buffer;
import utils.Direction;

public class Door implements Renderable {
    private static Buffer LOCKED_BUFFER = Buffer.from("X");
    private static Buffer UNLOCKED_BUFFER = Buffer.from("O");
    
    private DoorPlacement placement;
    private int x;
    private int y;
    private Room target;
    private boolean locked;
    
    public Door(int x, int y, boolean locked) {
        this.x = x;
        this.y = y;
        this.locked = locked;
    }
    
    public DoorPlacement getPlacement() {
        return this.placement;
    }
    
    public GridPosition getGridPosition() {
        return this.placement.position();
    }
    
    public Direction getFacingDirection() {
        return this.placement.facing();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setTargetRoom(Room target) {
        this.target = target;
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
