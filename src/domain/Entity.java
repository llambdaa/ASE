package domain;

public abstract class Entity {
    protected int health;
    protected int x;
    protected int y;
    protected int baseArmor;
    protected int baseDamage;

    public void heal(int amount) {
        health += amount;
    }

    protected void damage(int amount) {
        health -= (amount - baseArmor);
    }

    public void attack(Entity opponent) {
        opponent.damage(baseDamage);
    }
    public int getHealth(){
        return health;
    }
}
