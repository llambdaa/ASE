package domain;

public enum Weapon {
    DAGGER(5, 1),
    FISTS(1, 1);

    Weapon(int damage, int range) {
        this.damage = damage;
        this.range = range;
    }

    public void attack(Position position, int strength, Entity enemy) {
        enemy.damage(strength + damage);
    }

    private boolean isInRange(Position position, Position enemy) {
        int distance = position.getMaxAxisDistance(enemy);
        return distance <= range;
    }

    private int damage;
    private int range;
}
