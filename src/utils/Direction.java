package utils;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;
    
    public static Direction getOpposite(Direction direction) {
        return switch(direction) {
            case LEFT:
                yield RIGHT;
            case RIGHT:
                yield LEFT;
            case UP:
                yield DOWN;
            case DOWN:
                yield UP;
        };
    }
    
}
