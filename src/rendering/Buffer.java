package rendering;

import utils.Numeric;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Buffer {
    private int width;
    private int height;
    private char[][] symbols;
    private String[][] colors;
    
    public Buffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.symbols = new char[height][width];
        this.colors = new String[height][width];
    }
    
    public static Buffer from(String source) {
        List<String> lines = Arrays.asList(source.split("\n"));
        Optional<Integer> maxWidth = lines.stream().map(String::length).max(Integer::compareTo);
        if (maxWidth.isEmpty()) {
            return null;
        }
        
        int width = maxWidth.get();
        int height = lines.size();
        
        Buffer buffer = new Buffer(width, height);
        for (int h = 0; h < height; h++) {
            char[] atomized = lines.get(h).toCharArray();
            
            for (int i = 0; i < atomized.length; i++) {
                buffer.symbols[h][i] = atomized[i];
            }
        }
        
        return buffer;
    }
    
    public static Buffer filled(int width, int height, char filler) {
        Buffer buffer = new Buffer(width, height);
        buffer.fill(filler);
        return buffer;
    }
    
    public static Buffer border(int width, int height, char filler) {
        Buffer base = Buffer.filled(width, height, filler);
        if (width > 2 && height > 2) {
            Buffer stencil = Buffer.filled(width - 2, height - 2, ' ');
            base.write(1, 1, stencil);
        }
        return base;
    }
    
    public String toString() {
        boolean colored = false;
        StringBuilder block = new StringBuilder();
        StringBuilder line = new StringBuilder();
        
        for (int h = 0; h < this.symbols.length; h++) {
            char[] horizontal = this.symbols[h];
            for (int w = 0; w < horizontal.length; w++) {
                String color = this.colors[h][w];
                if (color != null) {
                    line.append(color);
                    colored = true;
                } else if (colored) {
                    line.append(ANSIIColor.RESET);
                    colored = false;
                }
                
                char symbol = horizontal[w];
                line.append(symbol);
            }
            
            block.append(line);
            block.append("\n");
            line.setLength(0);
        }
        
        return block.toString();
    }
    
    public void fill(char filler) {
        this.fill(0, 0, this.width, this.height, filler);
    }
    
    public void fill(int x, int y, int width, int height, char filler) {
        int yStart = Numeric.clamp(0, this.height, y);
        int xStart = Numeric.clamp(0, this.width, x);
        int yEnd = Numeric.clamp(0, this.height, y + height);
        int xEnd = Numeric.clamp(0, this.width, x + width);
        
        for (int h = yStart; h < yEnd; h++) {
            for (int w = xStart; w < xEnd; w++) {
                this.symbols[h][w] = filler;
            }
        }
    }
    
    public void color(String ansii) {
        this.color(0, 0, this.width, this.height, ansii);
    }
    
    public void color(int x, int y, int width, int height, String ansii) {
        int yStart = Numeric.clamp(0, this.height, y);
        int xStart = Numeric.clamp(0, this.width, x);
        int yEnd = Numeric.clamp(0, this.height, y + height);
        int xEnd = Numeric.clamp(0, this.width, x + width);
        
        for (int h = yStart; h < yEnd; h++) {
            for (int w = xStart; w < xEnd; w++) {
                this.colors[h][w] = ansii;
            }
        }
    }
    
    public void clear() {
        fill(' ');
    }
    
    public void flipX() {
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width / 2; w++) {
                int oppositeIndex = this.width - 1 - w;
                
                // Flip symbols
                char oppositeSymbol = this.symbols[h][oppositeIndex];
                this.symbols[h][oppositeIndex] = this.symbols[h][w];
                this.symbols[h][w] = oppositeSymbol;
                
                // Flip colors
                String oppositeColor = this.colors[h][oppositeIndex];
                this.colors[h][oppositeIndex] = this.colors[h][w];
                this.colors[h][w] = oppositeColor;
            }
        }
    }
    
    public void flipY() {
        for (int h = 0; h < this.height / 2; h++) {
            int oppositeIndex = this.height - 1 - h;
    
            // Flip symbols
            char[] oppositeSymbols = this.symbols[oppositeIndex];
            this.symbols[oppositeIndex] = this.symbols[h];
            this.symbols[h] = oppositeSymbols;
            
            // Flip colors
            String[] oppositeColors = this.colors[oppositeIndex];
            this.colors[oppositeIndex] = this.colors[h];
            this.colors[h] = oppositeColors;
        }
    }
    
    public void write(int x, int y, Buffer buffer) {
        if (buffer == null) {
            return;
        }
        
        for (int h = 0; h < buffer.height; h++) {
            for (int w = 0; w < buffer.width; w++) {
                int absX = x + w;
                int absY = y + h;
                char symbol = buffer.symbols[h][w];
                
                // Null character is skipped
                if (symbol == 0) {
                    continue;
                }
                
                if (absX < 0 || absX >= this.width) {
                    continue;
                }
                
                if (absY < 0 || absY >= this.height) {
                    continue;
                }
                
                this.symbols[absY][absX] = symbol;
            }
        }
    }
    
    public void write(int x, int y, String pattern) {
        this.write(x, y, Buffer.from(pattern));
    }
    
    public void repeat(int x, int y, Buffer buffer, Direction direction, int times) {
        if (times <= 0) {
            return;
        }
        
        Buffer source = buffer.clone();
        if (direction == Direction.LEFT) {
            source.flipX();
            x += 1;
        } else if (direction == Direction.UP) {
            source.flipY();
            y += 1;
        }
        
        for (int t = 0; t < times; t++) {
            if (direction == Direction.LEFT) {
                x -= source.width;
            } else if (direction == Direction.UP) {
                y -= source.height;
            }
            
            this.write(x, y, source);
            
            if (direction == Direction.RIGHT) {
                x += source.width;
            } else if (direction == Direction.DOWN) {
                y += source.height;
            }
        }
    }
    
    public void repeat(int x, int y, String pattern, Direction direction, int times) {
        this.repeat(x, y, Buffer.from(pattern), direction, times);
    }
    
    public Buffer clone() {
        Buffer clone = new Buffer(this.width, this.height);
        clone.symbols = this.symbols.clone();
        clone.colors = this.colors.clone();
        return clone;
    }
    
    public void print() {
        System.out.println(this);
    }
}
