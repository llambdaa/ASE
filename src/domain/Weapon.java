package domain;

public enum Weapon {
    DAGGER(5, 1),
    FISTS(1, 1);

    Weapon(int damage, int range) {
        this.damage = damage;
        this.range = range;
    }

    public int getRange(){
        return range;
    }

    public int getDamage() {
        return damage;
    }

    private int damage;
    private int range;
}
