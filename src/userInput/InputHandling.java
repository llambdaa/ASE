package userInput;

import java.util.HashMap;
import java.util.function.Function;

public class InputHandling {
    protected  HashMap<Integer, Runnable> keyHandling = new HashMap<>();
     public InputHandling(){
        keyHandling.put(87, () -> {

        });
        keyHandling.put(65, () -> {
            //a

        });
        keyHandling.put(83, () -> {
            //s

        });
        keyHandling.put(68, () -> {
            //d

        });
    }

}
