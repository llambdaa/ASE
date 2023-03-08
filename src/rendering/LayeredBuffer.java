package rendering;

import utils.Tuple;

import java.util.*;

public class LayeredBuffer extends Buffer {
    private List<Buffer> buffers;
    private Map<Buffer, Tuple<Integer, Integer>> coordinates;
    private char background;
    
    public LayeredBuffer(int width, int height, char background) {
        super(width, height);
        this.buffers = new ArrayList<>();
        this.coordinates = new HashMap<>();
        this.background = background;
    }
    
    public LayeredBuffer(int width, int height) {
        this(width, height, ' ');
    }
    
    public void add(Buffer buffer, int x, int y) {
        this.buffers.add(buffer);
        this.coordinates.put(buffer, new Tuple<>(x, y));
    }
    
    public void move(Buffer target, int x, int y) {
        if (this.coordinates.containsKey(target)) {
            this.coordinates.put(target, new Tuple<>(x, y));
        }
    }
    
    public void render() {
        this.fill(this.background);
        this.color(ANSIIColor.RESET);
        
        for (Buffer buffer : this.buffers) {
            if (buffer instanceof LayeredBuffer) {
                ((LayeredBuffer) buffer).render();
            }
            
            Tuple<Integer, Integer> coordinates = this.coordinates.get(buffer);
            int x = coordinates._1;
            int y = coordinates._2;
            this.write(x, y, buffer);
        }
    }
    
}
