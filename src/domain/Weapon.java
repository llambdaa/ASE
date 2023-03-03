package domain;

public class Weapon {
    private String name;
    private int damage;
    private int range;

    public Weapon(String name, int damage, int range) {
        this.name = name;
        this.damage = damage;
        this.range = range;
    }

    public void attack(Position position, int strength, Entity enemy) {
        if(outOfRange(position,enemy.position)){
            System.out.println("enemy out of range");
        }
        enemy.damage(strength + damage);

    }

    private boolean outOfRange(Position position, Position enemy){
        int distance = Position.comparePositions(position,enemy);
        if(distance>=range || -distance<=-range){
            return true;
        }
        return false;
    }
}
