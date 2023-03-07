package level;

import rendering.Buffer;

import java.util.List;

public class Level {
    private List<Room> rooms;
    private Room spawnRoom;
    private Room bossRoom;
    private Room activeRoom;
    
    public Level(List<Room> rooms, Room spawnRoom, Room bossRoom) {
        this.rooms = rooms;
        this.spawnRoom = spawnRoom;
        this.bossRoom = bossRoom;
        this.activeRoom = spawnRoom;
    }
    
    public void enter(Room room) {
        this.activeRoom = room;
    }
    
    public void render() {
        this.activeRoom.render();
    }
    
    public Buffer getBuffer() {
        return this.activeRoom.getBuffer();
    }
}
