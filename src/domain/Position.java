package domain;
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        if(x < 0){
            this.x = 0;
        }
        if(y < 0){
            this.y = 0;
        }
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

    @Override
    public String toString(){
        return new String("X: " + this.x + ", Y: " + this.y);
    }
}
