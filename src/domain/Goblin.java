package domain;

public class Goblin extends Enemy {

    public Goblin(Position position) {
        super(10, 2, 5, "Goblin", Weapon.DAGGER, position, Equipment.LEATHER);
    }

}
