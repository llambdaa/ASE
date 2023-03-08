package level;

import utils.Direction;
import utils.IntRange;
import utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {
    private static final int MIN_ROOM_COUNT = 5;
    private static final int MAX_ROOM_COUNT = 30;
    
    public static Level generate() {
        List<Room> rooms = new ArrayList<>();
        int wantedRoomCount = IntRange.from(MIN_ROOM_COUNT, MAX_ROOM_COUNT).random();
        int generatedRoomCount = 0;
        
        // 1) Spawn room generation
        FormFactor form = FormFactor.SMALL_SQUARE;
        int spawnRoomDoorCount = IntRange.from(1, form.getWallCount()).random();
        Room room = LevelGenerator.generateRoom(form, spawnRoomDoorCount);
        rooms.add(room);
        
        Level level = new Level(rooms);
        return level;
    }
    
    public static Room generateRoom(FormFactor form, int doorCount) {
        Room room = new Room(form);
        List<Tuple<Direction, Integer>> doorsPositions = LevelGenerator.selectDoorPositions(form, doorCount);
        
        for (Tuple<Direction, Integer> door : doorsPositions) {
            room.addDoor(door._1, door._2);
        }
        
        return room;
    }
    
    private static List<Tuple<Direction, Integer>> selectDoorPositions(FormFactor form, int doorCount) {
        List<Tuple<Direction, Integer>> left = new ArrayList(FormFactor.DOOR_LOCATIONS.get(form));
        IntRange indices = IntRange.from(0, left.size() - 1);
        int toRemove = left.size() - doorCount;
        
        for (int i = 0; i < toRemove; i++) {
            int index = indices.random() % left.size();
            left.remove(index);
        }
        
        return left;
    }
    
}
