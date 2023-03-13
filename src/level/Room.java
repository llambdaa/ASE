package level;

import rendering.Buffer;
import rendering.LayeredBuffer;
import utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Room implements Renderable {
    private RoomPlacement placement;
    private int width;
    private int height;
    private List<Door> doors;
    
    private Buffer wall;
    private LayeredBuffer buffer;
    
    public Room(RoomPlacement placement) {
        this.placement = placement;
        this.width = placement.form().getScaledWidth();
        this.height = placement.form().getScaledHeight();
        this.doors = new ArrayList<>();
        this.build();
    }
    
    private void build() {
        int totalWidth = this.width + 2;
        int totalHeight = this.height + 2;
        
        this.wall = Buffer.border(totalWidth, totalHeight, '█');
        this.buffer = new LayeredBuffer(totalWidth, totalHeight);
        this.buffer.add(this.wall, 0, 0);
    }
    
    public RoomPlacement getPlacement() {
        return this.placement;
    }
    
    public void addDoor(DoorPlacement placement, Room target) {
        GridPosition position = placement.position();
        Direction facing = placement.facing();
        
        int x, y;
        if (facing == Direction.LEFT || facing == Direction.RIGHT) {
            // Horizontal placement
            int dy = position.y() - this.placement.position().y();
            x = (facing == Direction.LEFT) ? 0 : this.width + 1;
            y = FormFactor.DOOR_OFFSET_Y + dy * FormFactor.SMALL_ROOM_HEIGHT + 1;
        } else {
            // Vertical placement
            int dx = position.x() - this.placement.position().x();
            y = (facing == Direction.UP) ? 0 : this.height + 1;
            x = FormFactor.DOOR_OFFSET_X + dx * FormFactor.SMALL_ROOM_WIDTH + 1;
        }
        
        Door door = new Door(x, y, target);
        this.doors.add(door);
        this.wall.write(x, y, door.getBuffer());
    }
    
    @Override
    public void render() {
        this.buffer.render();
    }
    
    @Override
    public Buffer getBuffer() {
        return this.buffer;
    }
    
}
