package utils;

public class IntRange {
    private int min;
    private int max;
    
    private IntRange(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    public static IntRange from(int min, int max) {
        return new IntRange(min, max);
    }
    
    public int random() {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    public int getMin() {
        return this.min;
    }
    
    public int getMax() {
        return this.max;
    }
    
}
