package level;

public record RoomPlacement(GridPosition start, FormFactor form) {
    public RoomPlacement(int x, int y, FormFactor form) {
        this(new GridPosition(x, y), form);
    }
    
    public int x() {
        return this.start.x();
    }
    
    public int y() {
        return this.start.y();
    }
}
