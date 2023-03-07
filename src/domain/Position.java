package domain;

import static java.lang.Math.abs;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getMaxDistance(Position basePos, Position comparedPos) {
        int xDiff = basePos.getX() - comparedPos.getX();
        int yDiff = basePos.getY() - comparedPos.getY();
        if (abs(xDiff) >= abs(yDiff)) {
            return xDiff;
        }
        return yDiff;
    }
}
