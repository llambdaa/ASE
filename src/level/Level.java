package level;

import rendering.Buffer;

public class Level implements Renderable {
    private Room room;
    
    public Level(Room spawn) {
        this.enter(spawn);
    }
    
    public void enter(Room room) {
        this.room = room;
    }
    
    @Override
    public void render() {
        this.room.render();
    }
    
    @Override
    public Buffer getBuffer() {
        return this.room.getBuffer();
    }
}
