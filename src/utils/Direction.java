package utils;

public enum Direction {
    LEFT(true),
    RIGHT(true),
    UP(false),
    DOWN(false);
    
    Direction(boolean horizontal) {
        this.horizontal = horizontal;
    }
    
    private boolean horizontal;
    
    public boolean isHorizontal() {
        return this.horizontal;
    }
    
    public boolean isVertical() {
        return !this.horizontal;
    }
    
}
