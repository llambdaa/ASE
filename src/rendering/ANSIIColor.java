package rendering;

public class ANSIIColor {
    public static String RESET = "\u001b[0m";
    public static String BLACK = "\u001b[30m";
    public static String RED = "\u001b[31m";
    public static String GREEN = "\u001b[32m";
    public static String YELLOW = "\u001b[33m";
    public static String BLUE = "\u001b[34m";
    public static String MAGENTA = "\u001b[35m";
    public static String CYAN = "\u001b[36m";
    public static String GRAY = "\u001b[37m";
    
    public static String getCustom(int a, int b) {
        return String.format("\u001b[38;5;%sm", a * 16 + b);
    }
    
}
