package level;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {
    
    public static Level generate() {
        Door door = new Door(0, 2, false);
        List<Door> doors = new ArrayList<>();
        doors.add(door);
        
        Room room = new Room(FormFactor.SMALL_SQUARE);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        
        Level level = new Level(rooms);
        return level;
    }
    
}
