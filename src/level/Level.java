package level;

import rendering.Buffer;

import java.util.List;

public class Level extends Renderable {
    private List<Room> rooms;
    private Room currentRoom;
    
    public Level(List<Room> rooms) {
        this.rooms = rooms;
        this.enter(rooms.get(0));
    }
    
    public void enter(Room room) {
        this.currentRoom = room;
    }
    
    @Override
    public void render() {
        this.currentRoom.render();
    }
    
    @Override
    public Buffer getBuffer() {
        return this.currentRoom.getBuffer();
    }
}
