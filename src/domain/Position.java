package domain;

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

    public void setX(Position newPosition) {

        this.x = newPosition.getX();
        this.y = newPosition.getY();
    }

    public static int comparePositions(Position basePos, Position comparedPos) {
        int xDiff = basePos.getX() - comparedPos.getX();
        int yDiff = basePos.getY() - comparedPos.getY();
        if (xDiff >= yDiff) {
            return xDiff;
        }
        return yDiff;
    }
}
