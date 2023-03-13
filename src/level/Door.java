package level;

import rendering.Buffer;

public class Door implements Renderable {
    private static Buffer BUFFER = Buffer.from("O");
    
    private int x;
    private int y;
    private Room target;
    
    public Door(int x, int y, Room target) {
        this.x = x;
        this.y = y;
        this.target = target;
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
    
    @Override
    public void render() {}
    
    @Override
    public Buffer getBuffer() {
       return Door.BUFFER;
    }
}
