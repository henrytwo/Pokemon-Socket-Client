package rastera.henry;

public class Attack {
    private String name, special;
    private int energyCost, damage;

    public Attack(String name, String energyCost, String damage, String special) {
        this.name = name;
        this.energyCost = Integer.parseInt(energyCost);
        this.damage = Integer.parseInt(damage);
        this.special = special;
    }

    public String getName(){
        return this.name;
    }

    public int getEnergyCost(){
        return this.energyCost;
    }

    public int getDamage(){
        return this.damage;
    }

    public String getSpecial(){
        return this.special;
    }
}
