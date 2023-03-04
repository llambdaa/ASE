package rendering;

import utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public void add(int x, int y, Buffer buffer) {
        this.buffers.add(buffer);
        this.coordinates.put(buffer, new Tuple<>(x, y));
    }
    
    public void render() {
        this.fill(this.background);
        for (Buffer buffer : this.buffers) {
            Tuple<Integer, Integer> coordinates = this.coordinates.get(buffer);
            int x = coordinates._1;
            int y = coordinates._2;
            this.write(x, y, buffer);
        }
    }
    
}
