package level;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {
    
    public static Level generate() {
        Door door = new Door(0, 2, null, false);
        List<Door> doors = new ArrayList<>();
        doors.add(door);
        
        Room room = new Room(10, 5, doors);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        
        Level level = new Level(rooms, room, room);
        return level;
    }
    
}
