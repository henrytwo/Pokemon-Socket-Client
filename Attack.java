public class Attack {
    private String name, special;
    private int energyCost, damage;

    public Attack(String name, String energyCost, String damage, String special) {
        this.name = Interactive.correctCase(name);
        this.energyCost = Integer.parseInt(energyCost);
        this.damage = Integer.parseInt(damage);
        this.special = Interactive.correctCase(special);
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

    public String toString() {
        return String.format("[ %-15s EC: %-3d D: %-3d S: %-10s ]", this.name, this.energyCost, this.damage, this.special);
    }
}