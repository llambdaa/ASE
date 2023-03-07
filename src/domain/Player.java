package domain;

public class Player extends Entity {


    public Player(String name) {
        super(100, 5, 10, name, Weapon.FISTS, new Position(0,0), Equipment.LEATHER);
    }

    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
    }

}
