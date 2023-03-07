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

    public int getMaxAxisDistance(Position comparedPos) {
        int xDiff = Math.abs(this.getX() - comparedPos.getX());
        int yDiff = Math.abs(this.getY() - comparedPos.getY());
        return (xDiff >= yDiff) ? xDiff : yDiff;
    }
}
