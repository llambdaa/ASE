package domain;

public abstract class Entity {
    protected String name;
    protected int health;
    protected Position position;
    protected int baseArmor;
    protected int strength;
    protected Weapon weapon;

    public Entity(int health, int baseArmor, int strength, String name, Weapon weapon, Position position){
        this.health = health;
        this.baseArmor = baseArmor;
        this.strength = strength;
        this.name = name;
        this.weapon = weapon;
        this.position = position;
    }

    public void heal(int amount) {
        health += amount;
    }

    protected void damage(int amount) {
        health -= (amount - baseArmor);
    }

    private boolean isInRange(Position enemy) {
        int distance = position.getMaxAxisDistance(enemy);
        return distance <= weapon.getRange();
    }

    public void attack(Entity opponent) {
        opponent.damage(strength + weapon.getDamage());
    }

    public int getHealth() {
        return health;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public String getName() {
        return name;
    }
}
