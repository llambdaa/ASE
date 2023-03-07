package domain;

public class Player extends Entity {


    public Player(String name) {
        super(100, 5, 10, name);
        weapon = new Weapon("fists", 1, 1);
        position = new Position(0, 0);
    }

    public String getName() {
        return name;
    }

}
