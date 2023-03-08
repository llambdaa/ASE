package level;

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
    
    private static final int SMALL_ROOM_WIDTH = 10;
    private static final int SMALL_ROOM_HEIGHT = 10;
    
    private int horizontal;
    private int vertical;
    
    public int getScaledWidth() {
        return this.horizontal * SMALL_ROOM_WIDTH;
    }
    
    public int getScaledHeight() {
        return this.vertical * SMALL_ROOM_HEIGHT;
    }
}
