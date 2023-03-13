package level;

public record RoomPlacement(GridPosition position, FormFactor form) {
    public RoomPlacement(int x, int y, FormFactor form) {
        this(new GridPosition(x, y), form);
    }
    
    public static RoomPlacement small(GridPosition position) {
        return RoomPlacement.small(position.x(), position.y());
    }
    
    public static RoomPlacement small(int x, int y) {
        return new RoomPlacement(x, y, FormFactor.SMALL_SQUARE);
    }
    
    public GridPosition position() {
        return this.position;
    }
    
    public int x() {
        return this.position.x();
    }
    
    public int y() {
        return this.position.y();
    }
    
    public boolean contains(GridPosition position) {
        int x = position.x();
        int y = position.y();
        
        if (x < this.x() || x > this.x() + form.getHorizontalScale()) {
            return false;
        }
        
        if (y < this.y() || y > this.y() + form.getVerticalScale()) {
            return false;
        }
        
        return true;
    }
    
}
