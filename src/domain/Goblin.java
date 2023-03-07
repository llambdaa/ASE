package domain;

public class Goblin extends Entity {

    public Goblin(Position position) {
        super(10, 2, 5, "Goblin");
        weapon = Weapon.DAGGER;
        this.position = position;
    }

}
