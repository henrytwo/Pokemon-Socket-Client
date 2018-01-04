/**
 * Pokemon Arena
 * Attack.java
 *
 * Class of Attacks
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

public class Attack {
    private String name, special;
    private int energyCost, damage;

    /**
     * Attack constructor method
     *
     * @param name             String of Attack name
     * @param energyCost       Integer of Attack cost
     * @param damage           Integer of Attack damage
     * @param special          String of Attack special
     */
    public Attack(String name, String energyCost, String damage, String special) {
        this.name = Interactive.correctCase(name);
        this.energyCost = Integer.parseInt(energyCost);
        this.damage = Integer.parseInt(damage);
        this.special = Interactive.correctCase(special);
    }

    /**
     * Get Attack name
     *
     * @return                 String of Attack Name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get Attack energy cost
     *
     * @return                 Integer of Attack energy cost
     */
    public int getEnergyCost(){
        return this.energyCost;
    }

    /**
     * Get Attack energy damage
     *
     * @return                 Integer of Attack damage
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * Get Attack special cost
     *
     * @return                 String of Attack special
     */
    public String getSpecial(){
        return this.special;
    }

    /**
     * Get Attack stats
     *
     * @return                 String of Attack statistics
     */
    public String toString() {
        return String.format("%-15s [ EC: %-3d D: %-3d S: %-10s ]", this.name, this.energyCost, this.damage, this.special);
    }
}
