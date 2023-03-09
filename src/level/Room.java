package level;

import rendering.Buffer;
import rendering.LayeredBuffer;
import utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Room implements Renderable {
    private int width;
    private int height;
    private List<Door> doors;
    
    private Buffer wall;
    private LayeredBuffer buffer;
    
    public Room(FormFactor form) {
        this.width = form.getScaledWidth();
        this.height = form.getScaledHeight();
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
    
    public List<Door> getDoors() {
        return this.doors;
    }
    
    public void addDoor(Direction wall, int offset) {
        this.addDoor(wall, offset, false);
    }
    
    public void addDoor(Direction wall, int offset, boolean locked) {
        // The offset must specify a position along the wall that is
        // inside the room. If it does not, the door is not placed.
        if (offset < 0) {
            return;
        }
        
        // The technical door coordinates lie outside the room, as
        // the door is embedded inside the wall. This simplifies the
        // check for whether a player is walking into/through a door.
        // Hence, they are offset in regard to the room coordinates
        // and can be negative or greater than the dimensional sizes.
        int x, y;
        if (wall == Direction.LEFT || wall == Direction.RIGHT) {
            if (offset >= this.height) {
                return;
            }
            x = (wall == Direction.LEFT) ? -1 : this.width;
            y = offset;
        } else {
            if (offset >= this.width) {
                return;
            }
            x = offset;
            y = (wall == Direction.UP) ? -1 : this.height;
        }
        
        // The door may only be placed when no other door is located
        // in the same position.
        for (Door existing : this.doors) {
            if (existing.getX() == x && existing.getY() == y) {
                return;
            }
        }
        
        Door door = new Door(x, y, locked);
        this.doors.add(door);
        
        // During rendering however, the coordinates are related back
        // to the coordinates of the wall, as the door is drawn onto
        // the wall buffer.
        this.wall.write(door.getX() + 1, door.getY() + 1, door.getBuffer());
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
