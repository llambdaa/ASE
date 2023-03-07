package level;

import rendering.Buffer;
import rendering.LayeredBuffer;

public class Room {
    private int width;
    private int height;
    private LayeredBuffer buffer;
    
    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        
        int paddedWidth = width + 2;
        int paddedHeight = height + 2;
        this.buffer = new LayeredBuffer(paddedWidth, paddedHeight);
        this.buffer.add(0, 0, Buffer.filled(paddedWidth, paddedHeight, 'x'));
    }
    
    public void render() {
        this.buffer.render();
    }
    
    public Buffer getBuffer() {
        return this.buffer;
    }
    
}
