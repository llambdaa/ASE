package level;

import rendering.ANSIIColor;

public class RoomGrid {
    private int width;
    private int height;
    private int horizontalOffset;
    private int verticalOffset;
    private boolean[][] grid;
    
    public RoomGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.horizontalOffset = width / 2;
        this.verticalOffset = height / 2;
        this.grid = new boolean[height][width];
    }
    
    public boolean isFree(GridPosition position) {
        int x = position.x() + horizontalOffset;
        int y = position.y() + verticalOffset;
        
        if (x < 0 || x >= this.width) {
            return false;
        }
        
        if (y < 0 || y >= this.height) {
            return false;
        }
        
        return !this.grid[y][x];
    }
    
    public void reserve(GridPosition position) {
        int x = position.x() + horizontalOffset;
        int y = position.y() + verticalOffset;
        this.grid[y][x] = true;
    }
    
    public boolean fits(RoomPlacement placement) {
        int xMin = placement.x() + this.horizontalOffset;
        int xMax = xMin + placement.form().getHorizontalScale();
        int yMin = placement.y() + this.verticalOffset;
        int yMax = yMin + placement.form().getVerticalScale();
        
        for (int h = yMin; h < yMax; h++) {
            for (int w = xMin; w < xMax; w++) {
                if (!this.grid[h][w]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width; w++) {
                boolean populated = this.grid[h][w];
                if (populated) {
                    result.append(ANSIIColor.GREEN);
                    result.append("1 ");
                } else {
                    result.append(ANSIIColor.GRAY);
                    result.append("0 ");
                }
            }
            
            result.append("\n");
        }
        
        return result.toString();
    }
    
}
