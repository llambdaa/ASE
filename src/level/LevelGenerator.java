package level;

import utils.Direction;
import utils.IndexRange;
import utils.IntRange;

import java.util.*;

public class LevelGenerator {
    private static final int MAX_ROOMS = 30;
    private static final int MAX_DEAD_ENDS = 6;
    private static final int MAX_ADJACENT_ROOMS = 4;
    private static final int GRID_WIDTH = 11;
    private static final int GRID_HEIGHT = 11;
    private static final GridPosition SPAWN = new GridPosition(0, 0);
    
    public static Level generate() {
        LevelBlueprint blueprint = LevelGenerator.findValidBlueprint();
        Map<RoomPlacement, List<DoorPlacement>> aggregated = LevelGenerator.aggregate(blueprint);
        Room spawn = materialize(aggregated);
        
        return new Level(spawn);
    }
    
    private static LevelBlueprint findValidBlueprint() {
        // Ultimately, the generator makes a blueprint, an outlining
        // of the level plan. It repeatedly generates levels until it
        // generated one that matches the given requirements.
        LevelBlueprint blueprint;
        
        while(true) {
            RoomGrid grid = new RoomGrid(GRID_WIDTH, GRID_HEIGHT);
            List<GridPosition> deadends = new ArrayList<>();
            List<GridPosition> rooms = new ArrayList<>();
            Map<GridPosition, List<Direction>> doors = new HashMap<>();
            
            // 1) Spawn Generation (Initially Dead-End)
            grid.reserve(SPAWN);
            deadends.add(SPAWN);
            rooms.add(SPAWN);
            
            // 2) Layout Generation
            expansion:
            while (!deadends.isEmpty()) {
                GridPosition deadend = deadends.remove(0);
                
                // A room's door positions (and amount of which)
                // are determined randomly for each room.
                int doorCount = IntRange.from(1, 3).random();
                int[] directionIndices = IndexRange.from(Direction.values()).select(doorCount);
                
                for (int index : directionIndices) {
                    Direction direction = Direction.values()[index];
                    GridPosition adjacent = deadend.getAdjacentPosition(direction);
                    
                    // Only if the grid position the door is leading to is
                    // not occupied and if it is not too densely surrounded,
                    // a room is indeed placed on that position.
                    if (grid.isFree(adjacent) && !grid.isDenselySurrounded(adjacent, MAX_ADJACENT_ROOMS)) {
                        grid.reserve(adjacent);
                        deadends.add(adjacent);
                        rooms.add(adjacent);
                        
                        // Register old -> new door
                        List<Direction> connected = doors.getOrDefault(deadend, new ArrayList<>());
                        connected.add(direction);
                        doors.put(deadend, connected);
                        
                        // Register new -> old door
                        Direction opposite = Direction.getOpposite(direction);
                        connected = doors.getOrDefault(adjacent, new ArrayList<>());
                        connected.add(opposite);
                        doors.put(adjacent, connected);
                        
                        if (rooms.size() == MAX_ROOMS) {
                            break expansion;
                        }
                    }
                }
            }
            
            // 3) Requirement Validation
            // The generated level layout already meets the room count requirement.
            // It must also have the correct amount of dead-end rooms. Otherwise,
            // the next iteration is triggered, generating another layout.
            if (deadends.size() <= MAX_DEAD_ENDS) {
                blueprint = new LevelBlueprint(grid, doors);
                break;
            }
        }
        
        return blueprint;
    }
    
    private static List<RoomPlacement> findPossibleAggregates(RoomGrid grid, FormFactor form) {
        List<RoomPlacement> placements = new ArrayList<>();
        
        for (int h = grid.getMinY(); h <= grid.getMaxY() - form.getVerticalScale(); h++) {
            for (int w = grid.getMinX(); w <= grid.getMaxX() - form.getHorizontalScale(); w++) {
                GridPosition position = new GridPosition(w, h);
                RoomPlacement placement = new RoomPlacement(position, form);
                
                if (grid.fits(placement)) {
                    placements.add(placement);
                }
            }
        }
        
        return placements;
    }
    
    private static List<RoomPlacement> selectPossibleAggregates(RoomGrid grid, FormFactor form) {
        List<RoomPlacement> found = LevelGenerator.findPossibleAggregates(grid, form);
        int possibilities = found.size();
        int amount = Math.max(1, (int) Math.round(possibilities / 3D));
        int[] indices = IndexRange.from(possibilities).select(amount);
        
        List<RoomPlacement> selected = new ArrayList<>();
        for (int index : indices) {
            selected.add(found.get(index));
        }
        
        return selected;
    }
    
    private static List<RoomPlacement> selectAggregates(RoomGrid grid) {
        List<RoomPlacement> aggregations = new ArrayList<>();
        List<FormFactor> forms = new ArrayList<>(Arrays.asList(FormFactor.values()));
        
        // The SMALL_SQUARE form factor cannot be aggregated and hence
        // is not considered. All other forms are shuffled in order to
        // prevent preferences because of their order in the enum.
        forms.remove(0);
        Collections.shuffle(forms);
        
        for (FormFactor form : forms) {
            // For each form, randomly selected possible room placements
            // are considered. They can overlap each other, so before
            // selecting one, it must first be checked whether the area
            // it covers has not yet been claimed by another placement.
            List<RoomPlacement> possibilities = selectPossibleAggregates(grid, form);
            for (RoomPlacement possible : possibilities) {
                // The complete area is not claimed yet,
                // hence this placement can claim it now.
                if (grid.fits(possible)) {
                    grid.free(possible);
                    aggregations.add(possible);
                }
            }
        }
        
        return aggregations;
    }
    
    private static Map<RoomPlacement, List<DoorPlacement>> aggregate(LevelBlueprint base) {
        // 1) Find Non-Overlapping Room Aggregates (Excluding Spawn)
        RoomGrid grid = base.grid();
        grid.free(SPAWN);
        List<RoomPlacement> aggregates = LevelGenerator.selectAggregates(grid);
        
        // 2) Find Valid Doors for Aggregate Rooms
        Map<GridPosition, List<Direction>> connectivity = base.graph();
        Map<RoomPlacement, List<DoorPlacement>> allDoors = new HashMap<>();
        
        for (RoomPlacement aggregate : aggregates) {
            // Iterate over each grid position enclosed in the room
            // and determine whether doors pointing from it lead
            // outside the room or into another section of the room.
            GridPosition corner = aggregate.position();
            FormFactor form = aggregate.form();
            List<DoorPlacement> roomDoors = new ArrayList<>();
            
            for (int h = 0; h < form.getVerticalScale(); h++) {
                for (int w = 0; w < form.getHorizontalScale(); w++) {
                    int x = corner.x() + w;
                    int y = corner.y() + h;
                    GridPosition section = new GridPosition(x, y);
                    List<Direction> sectionDoors = connectivity.remove(section);
                    
                    for (Direction direction : sectionDoors) {
                        GridPosition targetSection = section.getAdjacentPosition(direction);
                        // If the section's door points to a grid position that is
                        // not (!) contained within the aggregate's area, the door
                        // points outside the aggregate and is accepted. Otherwise,
                        // it is discarded as it points into the aggregate room itself.
                        if (!aggregate.contains(targetSection)) {
                            DoorPlacement acceptedDoor = new DoorPlacement(section, direction);
                            roomDoors.add(acceptedDoor);
                        }
                    }
                }
            }
            
            allDoors.put(aggregate, roomDoors);
        }
        
        // 3) Transfer Doors From Remaining Small Rooms
        for (var remaining : connectivity.entrySet()) {
            GridPosition position = remaining.getKey();
            List<Direction> directions = remaining.getValue();
            
            // Each remaining key is a small room. As they are composed
            // of only one section, each door automatically points into
            // another room and is therefore valid.
            RoomPlacement room = RoomPlacement.small(position);
            List<DoorPlacement> doors = new ArrayList<>();
            
            for (Direction direction : directions) {
                DoorPlacement door = new DoorPlacement(position, direction);
                doors.add(door);
            }
            
            allDoors.put(room, doors);
        }
        
        return allDoors;
    }
    
    private static Room materialize(Map<RoomPlacement, List<DoorPlacement>> aggregated) {
        // 1) Generate Actual Room for Each Placement
        Map<Room, List<DoorPlacement>> roomDoorMapping = new HashMap<>();
        for (var entry : aggregated.entrySet()) {
            Room room = new Room(entry.getKey());
            roomDoorMapping.put(room, entry.getValue());
        }
        
        // 2) Attach Doors To Each Room
        for (var entry : roomDoorMapping.entrySet()) {
            Room room = entry.getKey();
            for (DoorPlacement door : entry.getValue()) {
                // The adjacent position being pointed to by the
                // room's door points to the target room that is
                // to be entered when walking through that door.
                GridPosition adjacent = door.position().getAdjacentPosition(door.facing());
                
                // However, the room in which the position is
                // located in is not known yet.
                for (Room other : roomDoorMapping.keySet()) {
                    if (other.getPlacement().contains(adjacent)) {
                        room.addDoor(door, other);
                    }
                }
            }
        }
        
        // 3) Return Spawn Room
        for (Room room : roomDoorMapping.keySet()) {
            if (room.getPlacement().position().equals(SPAWN)) {
                return room;
            }
        }
        
        return null;
    }
    
}
