package domain;

public enum Weapon {
    DAGGER(5,1),
    FISTS(1,1);

    Weapon(int damage, int range) {
        this.damage = damage;
        this.range = range;
    }

    public void attack(Position position, int strength, Entity enemy) {
        if(outOfRange(position,enemy.position)){
            System.out.println("enemy out of range");
            return;
        }
        enemy.damage(strength + damage);

    }

    private boolean outOfRange(Position position, Position enemy){
        int distance = Position.getMaxDistance(position,enemy);
        if(distance>range || distance<(-range)){
            return true;
        }
        return false;
    }

    private int damage;
    private int range;
}
