package domain;

public abstract class Enemy extends Entity{

    public Enemy(int health, int baseArmor, int strength, String name, Weapon weapon, Position position, Equipment equipment){
        super(health, baseArmor, strength, name, weapon, position, equipment);
    }


}
