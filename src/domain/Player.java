package domain;

public class Player extends Entity {


    public Player(String name) {
        super(100, 5, 10, name);
        weapon = Weapon.FISTS;
        position = new Position(0, 0);
    }

    public String getName() {
        return name;
    }

}
