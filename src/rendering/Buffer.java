package rendering;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Buffer {
    private int width;
    private int height;
    private char[][] content;
    
    public Buffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.content = new char[height][width];
    }
    
    public static Buffer from(String source) {
        List<String> lines = Arrays.asList(source.split("\n"));
        int width = lines.stream().map(String::length).max(Integer::compareTo).get();
        int height = lines.size();
        
        Buffer buffer = new Buffer(width, height);
        for (int h = 0; h < height; h++) {
            char[] atomized = lines.get(h).toCharArray();
            
            for (int i = 0; i < atomized.length; i++) {
                buffer.content[h][i] = atomized[i];
            }
        }
        
        return buffer;
    }
    
    public static Buffer filled(int width, int height, char filler) {
        Buffer buffer = new Buffer(width, height);
        buffer.fill(filler);
        return buffer;
    }
    
    public String toString() {
        return Arrays.asList(this.content).stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }
    
    public void fill(char filler) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                this.content[h][w] = filler;
            }
        }
    }
    
    public void blank() {
        fill(' ');
    }
    
    public void flipX() {
        for (int h = 0; h < height; h++) {
            char[] original = this.content[h];
            char[] reversed = new char[this.width];
            
            for (int w = 0; w < this.width; w++) {
                reversed[w] = original[this.width - 1 - w];
            }
            
            this.content[h] = reversed;
        }
    }
    
    public void flipY() {
        for (int h = 0; h < this.height / 2; h++) {
            int oppositeIndex = this.height - 1 - h;
            char[] opposite = this.content[oppositeIndex];
            this.content[oppositeIndex] = this.content[h];
            this.content[h] = opposite;
        }
    }
    
    public void write(int x, int y, Buffer buffer) {
        for (int h = 0; h < buffer.height; h++) {
            for (int w = 0; w < buffer.width; w++) {
                int absX = x + w;
                int absY = y + h;
                char symbol = buffer.content[h][w];
                
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
                
                this.content[absY][absX] = symbol;
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
        clone.content = this.content.clone();
        return clone;
    }
}
