package level;

import utils.Direction;
import utils.IntRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LevelGenerator {
    private static final int MAX_ROOM_COUNT = 10;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    public static final RoomGrid GRID = new RoomGrid(GRID_WIDTH, GRID_HEIGHT);
    
    public static Level generate() {
        List<Room> generatedRooms = new ArrayList<>();
        List<Room> untouchedRooms = new ArrayList<>();
        int wantedRoomCount = IntRange.from(2, MAX_ROOM_COUNT).random();
        int generatedRoomCount = 0;
        
        // 1) Generate spawn room
        Room spawn = GRID.generateSpawnRoom();
        untouchedRooms.add(spawn);
        generatedRoomCount += 1;
        
        // 2) Generate neighboring rooms
        generation:
        while (!untouchedRooms.isEmpty() && generatedRoomCount < wantedRoomCount) {
            Room seed = untouchedRooms.remove(0);
            int walls = seed.getFormFactor().getWallCount();
            int missingRooms = wantedRoomCount - generatedRoomCount;
            int doors = IntRange.from(1, Math.max(missingRooms, walls)).random();
            
            List<DoorPlacement> vacant = findVacantDoorPlacements(seed);
            Collections.shuffle(vacant);
            
            for (int d = 0; d < doors; d++) {
                Iterator<DoorPlacement> it = vacant.iterator();
                while (it.hasNext()) {
                    DoorPlacement candidate = it.next();
                    GridPosition position = candidate.position();
                    Direction facing = candidate.facing();
                    GridPosition target = position.getAdjacentPosition(facing);
                    
                    // The door points onto an empty grid position.
                    // Another room is generated there.
                    if (GRID.isFree(target, FormFactor.SMALL_SQUARE)) {
                        Room generated = generateRoom(candidate);
                        untouchedRooms.add(generated);
                        generatedRoomCount++;
                        
                        if (generatedRoomCount >= wantedRoomCount) {
                            break generation;
                        }
                    }
                    
                    it.remove();
                }
            }
            
            generatedRooms.add(seed);
        }
        
        generatedRooms.addAll(untouchedRooms);
        Level result = new Level(generatedRooms);
        return result;
    }
    
    private static Room generateRoom(DoorPlacement door) {
        GridPosition position = door.position();
        RoomPlacement placement = selectRoomPlacement(position.x(), position.y(), door.facing());
        GRID.place(placement);
        
        // TODO # CONNECT BOTH ROOMS
        // TODO # AND LET DOORS DRAW
        // TODO # WITH OFFSET
        
        
        return new Room(placement);
    }
    
    private static List<DoorPlacement> findVacantDoorPlacements(Room room) {
        List<DoorPlacement> placements = room.getFormFactor().getDoorPlacements(room.getGridStart());
        for (Door inherited : room.getDoors()) {
            placements.remove(inherited.getPlacement());
        }
        
        return placements;
    }
    
    private static RoomPlacement selectRoomPlacement(int x, int y, Direction direction) {
        List<RoomPlacement> placements = findValidRoomPlacements(x, y, direction);
        
        // Each placement is represented by multiple duplicate indices to it,
        // depending on the preference value of the underlying form. The higher
        // the preference, the more often the placement's index occurs.
        List<Integer> indices = new ArrayList<>();
        for (int p = 0; p < placements.size(); p++) {
            RoomPlacement placement = placements.get(p);
            int preference = placement.form().getPreference();
            
            for (int i = 0; i < preference; i++) {
                indices.add(p);
            }
        }
        
        // Then, any of the indices is selected, which then
        // gets resolved into a concrete placement
        int selection = indices.get(IntRange.from(0, indices.size() - 1).random());
        return placements.get(selection);
    }
    
    private static List<RoomPlacement> findValidRoomPlacements(int x, int y, Direction direction) {
        if (direction.isHorizontal()) {
            return findValidHorizontalRoomPlacements(x, y, direction == Direction.LEFT);
        } else {
            return findValidVerticalRoomPlacements(x, y, direction == Direction.UP);
        }
    }
    
    private static List<RoomPlacement> findValidHorizontalRoomPlacements(int x, int y, boolean left) {
        List<RoomPlacement> result = new ArrayList<>();
        for (FormFactor form : FormFactor.values()) {
            // When looking for room placements to the right, the search mask merely is moved
            // up and down, leaving the x-coordinate of the upper left corner put (x+1).
            // However, when looking to the left, the search mask must be shifted by the
            // form factor's width.
            int xu = left ?
                    x - form.getHorizontalScale() :
                    x + 1;
            
            for (int h = 0; h < form.getVerticalScale(); h++) {
                int yu = y - h;
                if (GRID.isFree(xu, yu, form)) {
                    result.add(new RoomPlacement(xu, yu, form));
                }
            }
        }
        
        return result;
    }
    
    private static List<RoomPlacement> findValidVerticalRoomPlacements(int x, int y, boolean up) {
        List<RoomPlacement> result = new ArrayList<>();
        for (FormFactor form : FormFactor.values()) {
            int yu = up ?
                    y - form.getVerticalScale() :
                    y + 1;
            
            for (int w = 0; w < form.getHorizontalScale(); w++) {
                int xu = x - w;
                if (GRID.isFree(xu, yu, form)) {
                    result.add(new RoomPlacement(xu, yu, form));
                }
            }
        }
        
        return result;
    }
    
}
