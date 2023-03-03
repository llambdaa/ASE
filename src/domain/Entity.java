package domain;

public abstract class Entity {
    protected int health;
    protected Position position;
    protected int baseArmor;
    protected int strength;

    protected Weapon weapon;

    public void heal(int amount) {
        health += amount;
    }

    protected void damage(int amount) {
        health -= (amount - baseArmor);
    }

    public void attack(Entity opponent) {
        weapon.attack(this.position, strength, opponent);
    }

    public int getHealth() {
        return health;
    }
}
