package domain;

public class Player extends Entity {
    private String name;


    public Player(String name) {
        this.name = name;
        baseArmor = 5;
        health = 100;
        strength = 10;
        weapon = new Weapon("fists", 1, 1);
        position = new Position(0, 0);
    }

    public String getName() {
        return name;
    }

}
