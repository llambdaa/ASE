package domain;

public class Goblin extends Entity {

    public Goblin(Position position) {
        super(10, 2, 5, "Goblin");
        weapon = new Weapon("dagger", 5, 1);
        this.position = position;
    }

}
