package domain;

public enum Equipment {
    CLOTH(0),
    LEATHER(1),
    IRON(5);

    Equipment(int armor){
        this.armor = armor;
    }

    public int getArmor(){
        return armor;
    }

    private int armor;

}
