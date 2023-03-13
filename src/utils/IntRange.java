package utils;

public record IntRange(int min, int max) {
    public static IntRange from(int min, int max) {
        return new IntRange(min, max);
    }
    
    public int random() {
        return (int) Math.round(((Math.random() * (max - min)) + min));
    }
    
}
