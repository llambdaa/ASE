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
        enemy.damage(strength + damage);

    }

}
