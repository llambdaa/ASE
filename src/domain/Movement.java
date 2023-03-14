package domain;

public enum Movement {
    UP(0,1),
    LEFT(-1,0),
    DOWN(0,-1),
    RIGHT(1,0);

    Movement(int xDiff, int yDiff){
       this.xDiff = xDiff;
       this.yDiff = yDiff;
    }

    private int xDiff;
    private int yDiff;

    public int getXDiff(){
        return this.xDiff;
    }

    public int getYDiff(){
        return this.yDiff;
    }

}
