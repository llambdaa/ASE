package level;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {
    
    public static Level generate() {
        Room room = new Room(10, 5);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        
        Level level = new Level(rooms, room, room);
        return level;
    }
    
}
