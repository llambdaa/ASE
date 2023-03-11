package level;

import utils.Direction;
import utils.IntRange;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {
    private static final int MAX_ROOM_COUNT = 30;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    public static final RoomGrid GRID = new RoomGrid(GRID_WIDTH, GRID_HEIGHT);
    
    public static Level generate() {
        return null;
    }
    
    public static RoomPlacement selectRoomPlacement(int x, int y, Direction direction) {
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
