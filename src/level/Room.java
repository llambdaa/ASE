package level;

import rendering.Buffer;
import rendering.LayeredBuffer;

import java.util.List;

public class Room extends Renderable {
    private int width;
    private int height;
    private List<Door> doors;
    
    private Buffer wall;
    private LayeredBuffer buffer;
    
    public Room(int width, int height, List<Door> doors) {
        this.width = width;
        this.height = height;
        this.doors = doors;
        this.initialize();
    }
    
    private void initialize() {
        int width = this.width + 2;
        int height = this.height + 2;
        
        // 1) Generate wall with doors
        this.wall = Buffer.border(width, height, '█');
        for (Door door : this.doors) {
            this.wall.write(door.getX(), door.getY(), door.getBuffer());
        }
        
        // 2) Generate room buffer
        this.buffer = new LayeredBuffer(width, height);
        this.buffer.add(0, 0, this.wall);
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
