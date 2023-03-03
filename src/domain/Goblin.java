package domain;

public class Goblin extends Entity {
    private String name = "Goblin";

    public Goblin(Position position) {
        baseArmor = 2;
        health = 10;
        strength = 5;
        weapon = new Weapon("dagger", 5, 1);
        this.position = position;
    }

}
