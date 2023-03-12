package level;

import utils.Direction;
import utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FormFactor {
    SMALL_SQUARE(1, 1, 5),
    MEDIUM_SQUARE(2, 2, 2),
    MEDIUM_LINE_X(2, 1, 6),
    MEDIUM_LINE_Y(1, 2, 6),
    LARGE_SQUARE(3, 3, 1),
    LARGE_LINE_X(3, 1, 3),
    LARGE_LINE_Y(1, 3, 3),
    LARGE_DLINE_X(3, 2, 2),
    LARGE_DLINE_Y(2, 3, 2);
    
    FormFactor(int horizontal, int vertical, int preference) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.preference = preference;
    }
    
    private static final int SMALL_ROOM_WIDTH = 11;
    private static final int SMALL_ROOM_HEIGHT = 11;
    public static final int DOOR_DEVIATION = 1;
    public static final Map<FormFactor, List<Tuple<Direction, Integer>>> BUFFER_DOOR_LOCATIONS = new HashMap<>();
    
    static {
        int halfRoomWidth = FormFactor.SMALL_ROOM_WIDTH / 2;
        int halfRoomHeight = FormFactor.SMALL_ROOM_HEIGHT / 2;
        
        for (FormFactor form : FormFactor.values()) {
            List<Tuple<Direction, Integer>> locations = new ArrayList<>();
            
            for (int h = 0; h < form.horizontal; h++) {
                int offset = halfRoomWidth + h * FormFactor.SMALL_ROOM_WIDTH;
                locations.add(new Tuple<>(Direction.LEFT, offset));
                locations.add(new Tuple<>(Direction.RIGHT, offset));
            }
            
            for (int v = 0; v < form.vertical; v++) {
                int offset = halfRoomHeight + v * FormFactor.SMALL_ROOM_HEIGHT;
                locations.add(new Tuple<>(Direction.UP, offset));
                locations.add(new Tuple<>(Direction.DOWN, offset));
            }
            
            FormFactor.BUFFER_DOOR_LOCATIONS.put(form, locations);
        }
    }
    
    private int horizontal;
    private int vertical;
    private int preference;
    
    public int getHorizontalScale() {
        return this.horizontal;
    }
    
    public int getVerticalScale() {
        return this.vertical;
    }
    
    public int getPreference() {
        return this.preference;
    }
    
    public int getScaledWidth() {
        return this.horizontal * SMALL_ROOM_WIDTH;
    }
    
    public int getScaledHeight() {
        return this.vertical * SMALL_ROOM_HEIGHT;
    }
    
    public int getWallCount() {
        return 2 * (this.horizontal + this.vertical);
    }
    
    public List<DoorPlacement> getDoorPlacements(GridPosition start) {
        List<DoorPlacement> adjacent = new ArrayList<>();
        int x, y;
        
        for (int h = 0; h < this.vertical; h++) {
            y = start.y() + h;
            
            for (int w = 0; w < this.horizontal; w++) {
                x = start.x() + w;
                if (w == 0) {
                    // Left-most position
                    adjacent.add(new DoorPlacement(x, y, Direction.LEFT));
                }
                
                if (w == this.horizontal - 1) {
                    // Right-most position
                    adjacent.add(new DoorPlacement(x, y, Direction.RIGHT));
                }
                
                if (h == 0) {
                    // Top-most position
                    adjacent.add(new DoorPlacement(x, y, Direction.UP));
                }
                
                if (h == this.vertical - 1) {
                    // Bottom-most position
                    adjacent.add(new DoorPlacement(x, y, Direction.DOWN));
                }
            }
        }
        return adjacent;
    }
    
}
