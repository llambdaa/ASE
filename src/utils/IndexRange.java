package utils;

import java.util.ArrayList;
import java.util.List;

public record IndexRange(int min, int max) {
    public static IndexRange from(Object[] base) {
        return IndexRange.from(base.length);
    }
    public static IndexRange from(int amount) {
        return new IndexRange(0, amount - 1);
    }
    
    public int[] select(int amount) {
        // 1) Rectify Parameters
        int span = this.max - this.min + 1;
        amount = Math.min(amount, span);
        
        // 2) Initialize Index List
        List<Integer> indices = new ArrayList<>();
        for (int i = this.min; i <= this.max; i++) {
            indices.add(i);
        }
        
        // 3) Select n Indices
        int[] result = new int[amount];
        for (int i = 0; i < amount; i++) {
            IntRange range = new IntRange(0, indices.size() - 1);
            result[i] = indices.remove(range.random());
        }
        
        return result;
    }
    
}
