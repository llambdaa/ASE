package level;

import utils.IntRange;

public enum FormFactor {
    SMALL_SQUARE(1, 1, IntRange.from(1, 4)),
    MEDIUM_SQUARE(2, 2, IntRange.from(1, 4)),
    MEDIUM_LINE_X(2, 1, IntRange.from(1, 4)),
    MEDIUM_LINE_Y(1, 2, IntRange.from(1, 4)),
    LARGE_SQUARE(3, 3, IntRange.from(1, 8)),
    LARGE_LINE_X(3, 1, IntRange.from(1, 6)),
    LARGE_LINE_Y(1, 3, IntRange.from(1, 6)),
    LARGE_DLINE_X(3, 2, IntRange.from(1, 6)),
    LARGE_DLINE_Y(2, 3, IntRange.from(1, 6));
    
    FormFactor(int horizontal, int vertical, IntRange doorCount) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.doorCount = doorCount;
    }
    
    private static final int SMALL_ROOM_WIDTH = 10;
    private static final int SMALL_ROOM_HEIGHT = 10;
    
    private int horizontal;
    private int vertical;
    private IntRange doorCount;
    
    public int getScaledWidth() {
        return this.horizontal * SMALL_ROOM_WIDTH;
    }
    
    public int getScaledHeight() {
        return this.vertical * SMALL_ROOM_HEIGHT;
    }
    
    public IntRange getDoorCountRange() {
        return this.doorCount;
    }
    
}
