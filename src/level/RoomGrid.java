package level;

import rendering.ANSIIColor;

public class RoomGrid {
    private int width;
    private int height;
    private int horizontalOffset;
    private int verticalOffset;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private boolean[][] grid;
    
    public RoomGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.horizontalOffset = width / 2;
        this.verticalOffset = height / 2;
        
        this.xMin = -this.horizontalOffset;
        this.yMin = -this.verticalOffset;
        this.xMax = this.horizontalOffset;
        this.yMax = this.verticalOffset;
        
        if (this.width % 2 == 0) {
            this.xMax -= 1;
        }
        
        if (this.height % 2 == 0) {
            this.yMax -= 1;
        }
        
        this.grid = new boolean[height][width];
    }
    
    public int getMinX() {
        return this.xMin;
    }
    
    public int getMaxX() {
        return this.xMax;
    }
    
    public int getMinY() {
        return this.yMin;
    }
    
    public int getMaxY() {
        return this.yMax;
    }
    
    public boolean isInside(int x, int y) {
        if (x < 0 || x >= this.width) {
            return false;
        }
        
        if (y < 0 || y >= this.height) {
            return false;
        }
        
        return true;
    }
    
    public boolean isFree(GridPosition position) {
        int x = position.x() + this.horizontalOffset;
        int y = position.y() + this.verticalOffset;
        if (!isInside(x, y)) {
            return false;
        }
        
        return !this.grid[y][x];
    }
    
    public boolean isDenselySurrounded(GridPosition position, int threshold) {
        int occupied = 0;
        for (int h = -1; h <= 1; h++) {
            for (int w = -1; w <= 1; w++) {
                if (h == 0 && w == 0) {
                    continue;
                }
                
                int x = position.x() + w;
                int y = position.y() + h;
                GridPosition adjacent = new GridPosition(x, y);
                
                if (!this.isFree(adjacent)) {
                    occupied++;
                }
            }
        }
        
        return occupied >= threshold;
    }
    
    public void reserve(GridPosition position) {
        int x = position.x() + this.horizontalOffset;
        int y = position.y() + this.verticalOffset;
        if (!isInside(x, y)) {
            return;
        }
        
        this.grid[y][x] = true;
    }
    
    public void reserve(RoomPlacement placement) {
        int xMin = placement.x() + this.horizontalOffset;
        int xMax = xMin + placement.form().getHorizontalScale();
        int yMin = placement.y() + this.verticalOffset;
        int yMax = yMin + placement.form().getVerticalScale();
        
        for (int h = yMin; h < yMax; h++) {
            for (int w = xMin; w < xMax; w++) {
                this.grid[h][w] = true;
            }
        }
    }
    
    public void free(GridPosition position) {
        int x = position.x() + this.horizontalOffset;
        int y = position.y() + this.verticalOffset;
        
        if (isInside(x, y)) {
            this.grid[y][x] = false;
        }
    }
    
    public void free(RoomPlacement placement) {
        int xMin = placement.x() + this.horizontalOffset;
        int xMax = xMin + placement.form().getHorizontalScale();
        int yMin = placement.y() + this.verticalOffset;
        int yMax = yMin + placement.form().getVerticalScale();
        
        for (int h = yMin; h < yMax; h++) {
            for (int w = xMin; w < xMax; w++) {
                this.grid[h][w] = false;
            }
        }
    }
    
    public boolean fits(RoomPlacement placement) {
        int xMin = placement.x() + this.horizontalOffset;
        int xMax = xMin + placement.form().getHorizontalScale();
        int yMin = placement.y() + this.verticalOffset;
        int yMax = yMin + placement.form().getVerticalScale();
        
        for (int h = yMin; h < yMax; h++) {
            for (int w = xMin; w < xMax; w++) {
                if (!isInside(w, h) || !this.grid[h][w]) {
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
