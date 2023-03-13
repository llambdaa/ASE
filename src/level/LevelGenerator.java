package level;

import utils.Direction;
import utils.IndexRange;
import utils.IntRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelGenerator {
    private static final int MAX_ROOMS = 30;
    private static final int MAX_DEAD_ENDS = 6;
    private static final int MAX_ADJACENT_ROOMS = 4;
    private static final int GRID_WIDTH = 11;
    private static final int GRID_HEIGHT = 11;
    
    public static Level generate() {
        final Direction[] directions = Direction.values();
        
        // A level layout is generated until it fits
        // the requirements of dead-end and room count.
        while(true) {
            RoomGrid grid = new RoomGrid(GRID_WIDTH, GRID_HEIGHT);
            List<RoomPlacement> deadends = new ArrayList<>();
            List<RoomPlacement> rooms = new ArrayList<>();
            Map<RoomPlacement, List<Direction>> doors = new HashMap<>();
            
            // 1) Spawn Generation (Initially Dead-End)
            RoomPlacement spawn = RoomPlacement.small(0, 0);
            grid.reserve(spawn.position());
            deadends.add(spawn);
            rooms.add(spawn);
            
            // 2) Layout Generation
            generation:
            while (!deadends.isEmpty()) {
                RoomPlacement deadend = deadends.remove(0);
                GridPosition position = deadend.position();
                int doorCount = IntRange.from(1, 3).random();
                int[] directionIndices = IndexRange.from(directions).select(doorCount);
                
                for (int index : directionIndices) {
                    Direction direction = directions[index];
                    GridPosition adjacent = position.getAdjacentPosition(direction);
                    
                    if (grid.isFree(adjacent) && !grid.isDenselySurrounded(adjacent, MAX_ADJACENT_ROOMS)) {
                        RoomPlacement appended = RoomPlacement.small(adjacent);
                        grid.reserve(adjacent);
                        deadends.add(appended);
                        rooms.add(appended);
                        
                        // Register old -> new door
                        List<Direction> connected = doors.getOrDefault(deadend, new ArrayList<>());
                        connected.add(direction);
                        doors.put(deadend, connected);
                        
                        // Register new -> old door
                        Direction opposite = Direction.getOpposite(direction);
                        connected = doors.getOrDefault(appended, new ArrayList<>());
                        connected.add(opposite);
                        doors.put(appended, connected);
                        
                        if (rooms.size() == MAX_ROOMS) {
                            break generation;
                        }
                    }
                }
            }
            
            // 3) Requirement Validation
            // The generated level layout already meets the room count requirement.
            // It must also have the correct amount of dead-end rooms. Otherwise,
            // the next iteration is triggered, generating another layout.
            if (deadends.size() <= MAX_DEAD_ENDS) {
                System.out.println(grid.toString());
                break;
            }
        }
        
        return new Level(null);
    }
    
}
