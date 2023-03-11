package level;

import utils.Direction;
import utils.Tuple;

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
    
    public Tuple<Integer, Integer> getAdjacentPosition(Tuple<Integer, Integer> reference, Direction direction) {
        return this.getAdjacentPosition(reference._1, reference._2, direction);
    }
    
    public Tuple<Integer, Integer> getAdjacentPosition(int x, int y, Direction direction) {
        return switch (direction) {
            case UP: {
                yield new Tuple<>(x, y - 1);
            }
            case DOWN: {
                yield new Tuple<>(x, y + 1);
            }
            case LEFT: {
                yield new Tuple<>(x - 1, y);
            }
            case RIGHT: {
                yield new Tuple<>(x + 1, y);
            }
        };
    }
    
    public boolean isFree(int x, int y, FormFactor form) {
        int xMin = x + this.horizontalOffset;
        int xMax = xMin + form.getHorizontalScale();
        int yMin = y + this.verticalOffset;
        int yMax = yMin + form.getVerticalScale();
        
        for (int h = yMin; h < yMax; h++) {
            for (int w = xMin; w < xMax; w++) {
                if (w < 0 || w >= this.width) {
                    return false;
                }
                
                if (h < 0 || h >= this.height) {
                    return false;
                }
                
                if (this.grid[h][w]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void place(RoomPlacement placement) {
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
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width; w++) {
                boolean populated = this.grid[h][w];
                if (populated) {
                    result.append("1 ");
                } else {
                    result.append("0 ");
                }
            }
            
            result.append("\n");
        }
        
        return result.toString();
    }
    
}
