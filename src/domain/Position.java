package domain;
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = Math.max(x,0);
        this.y = Math.max(y,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xDiff){
        this.x += xDiff;
    }

    public void setY(int yDiff){
        this.y += yDiff;
    }

    public int getMaxAxisDistance(Position comparedPos) {
        int xDiff = Math.abs(this.getX() - comparedPos.getX());
        int yDiff = Math.abs(this.getY() - comparedPos.getY());
        return (xDiff >= yDiff) ? xDiff : yDiff;
    }

    @Override
    public String toString(){
        return "X: " + this.x + ", Y: " + this.y;
    }
}
