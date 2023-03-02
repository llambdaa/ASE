package domain;

public class Player extends Entity {
    private String name;


    public Player(String name) {
        this.name = name;
        baseArmor = 5;
        health = 100;
        baseDamage = 10;
    }

}
