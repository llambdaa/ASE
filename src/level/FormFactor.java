package level;

import utils.Direction;
import utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FormFactor {
    SMALL_SQUARE(1, 1),
    MEDIUM_SQUARE(2, 2),
    MEDIUM_LINE_X(2, 1),
    MEDIUM_LINE_Y(1, 2),
    LARGE_SQUARE(3, 3),
    LARGE_LINE_X(3, 1),
    LARGE_LINE_Y(1, 3),
    LARGE_DLINE_X(3, 2),
    LARGE_DLINE_Y(2, 3);
    
    FormFactor(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
    
    private static final int SMALL_ROOM_WIDTH = 11;
    private static final int SMALL_ROOM_HEIGHT = 11;
    public static final int DOOR_DEVIATION = 1;
    public static final Map<FormFactor, List<Tuple<Direction, Integer>>> DOOR_LOCATIONS = new HashMap<>();
    
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
            
            FormFactor.DOOR_LOCATIONS.put(form, locations);
        }
    }
    
    private int horizontal;
    private int vertical;
    
    public int getHorizontalScale() {
        return this.horizontal;
    }
    
    public int getVerticalScale() {
        return this.vertical;
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
    
}
