/**
 * Pokemon Arena
 * Pokemon.java
 *
 * Class for Pokemon
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class Pokemon {
    private String name, type, resistance, weakness, ascii, dataIn;
    private int hp, totalhp, numAttacks, energy;
    private boolean disabled = false;
    private boolean stunned  = false;
    private ArrayList<Attack> attacks;

    // Constant index of data values
    private static final int ATTACKCAP  = 2;
    private static final int NAME       = 0;
    private static final int HP         = 1;
    private static final int TYPE       = 2;
    private static final int RESISTANCE = 3;
    private static final int WEAKNESS   = 4;
    private static final int NUMATTACKS = 5;
    private static final int ATTACKS    = 6;

    /**
     * Pokemon constructor method
     *
     * @param dataLine         String array of Pokemon data
     */
    public Pokemon(String[] dataLine) {
        this.name       = Interactive.correctCase(dataLine[NAME]);
        this.totalhp    = Integer.parseInt(dataLine[HP]);
        this.type       = Interactive.correctCase(dataLine[TYPE]);
        this.resistance = Interactive.correctCase(dataLine[RESISTANCE]);
        this.weakness   = Interactive.correctCase(dataLine[WEAKNESS]);
        this.numAttacks = Integer.parseInt(dataLine[NUMATTACKS]);
        this.attacks    = getAttacks(Arrays.copyOfRange(dataLine, ATTACKS, dataLine.length));
        this.hp         = this.totalhp;
        this.energy     = 50;

        // Gets ASCII of Pokemon from file
        try {
            BufferedReader asciiFile = new BufferedReader(new FileReader(new File(String.format("ascii/%s.txt", Utilities.filter(this.name.toLowerCase(), new String[] {".", "'", "-"})))));

            while (true){
                dataIn = asciiFile.readLine();
                if (dataIn != null) {
                    this.ascii += dataIn + "\n";
                }
                else {
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println(this.name);
            Interactive.delayTypeln("Error: FileIO Error");
            this.ascii = String.format("<Pretend this is a %s>", this.name);
        }
    }

    /**
     * Get ArrayList of Attacks this Pokemon can perform
     *
     * @param attackData       String array of attackData
     * @return                 ArrayList of Attack objects
     */
    private ArrayList<Attack> getAttacks(String[] attackData) {
        ArrayList<Attack> attacks = new ArrayList<>();

        for (int index = 0; index < this.numAttacks * 4; index += 4) {
            attacks.add(new Attack(attackData[index], attackData[index + 1], attackData[index + 2], (index + 3) < attackData.length ? attackData[index + 3] : "N/A"));
        }

        return attacks;
    }

    /**
     * Gets name of Pokemon
     * @return                 String of Pokemon name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets type of Pokemon
     * @return                 String of Pokemon type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets resistance of Pokemon
     * @return                 String of Pokemon resistance
     */
    public String getResistance() {
        return this.resistance;
    }

    /**
     * Gets weakness of Pokemon
     * @return                 String of Pokemon weakness
     */
    public String getWeakness() {
        return this.weakness;
    }

    /**
     * Gets Attacks of Pokemon
     * @return                 ArrayList of Pokemon Attacks
     */
    public ArrayList<Attack> getAttacks() {
        return this.attacks;
    }

    /**
     * Sets stun status of Pokemon
     *
     * @param stunned          Boolean of new stun status
     */
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    /**
     * Gets stun status of Pokemon
     * @return                 Boolean of stun status
     */
    public boolean getStunned() {
        return this.stunned;
    }

    /**
     * Sets disabled status of Pokemon
     *
     * @param disabled         Boolean of new disabled status
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Gets disabled status of Pokemon
     * @return                 Boolean of disabled status
     */
    public boolean getDisabled() {
        return this.disabled;
    }

    /**
     * Sets Pokemon HP
     *
     * @param hp               Integer with new HP level
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Gets total hp of Pokemon
     * @return                 Integer of HP total
     */
    public int getHpTotal() {
        return this.totalhp;
    }

    /**
     * Gets hp of Pokemon
     * @return                 Integer of current HP
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Sets energy of Pokemon
     *
     * @param energy           Integer with new energy level
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Gets name of Pokemon
     * @return                 String of Pokemon name
     */
    public int getEnergy() {
        return this.energy;
    }

    /**
     * Generate ASCII health bar
     *
     * @return                 String of health bar
     */
    public String generateHealthBar() {
        String bar = "[";

        int bars = (int) (20 * (float) this.hp / this.totalhp);

        for (int i = 0; i < 20; i++) {
            bar += (i < bars) ? "|" : " ";
        }

        return bar + "]";
    }

    /**
     * Pokemon ASCII art
     *
     * @return                 String of Pokemon ASCII art
     */
    public String getAscii() {
        return this.ascii;
    }

    /**
     * Simple Pokemon status
     *
     * @return                 String of Pokemon status
     */
    public String toStringSimple() {
        return String.format(" %-12s ║ HP: %s [%3d/%-3d] ║ E: [%3d/50 ] ", this.name, this.generateHealthBar(), this.hp, this.totalhp, this.energy);
    }

    /**
     * Card of Pokemon status [Detailed]
     *
     * @return                 String of Pokemon card
     */
    public String toCard(int number) {
        return String.format("╔═════════════════════════════════╗\n" +
                             "║ %-3s | NAME        %-13s ║\n" +
                             "║     | HP          [ %3d/%-3d ]   ║\n" +
                             "║     | ENERGY      [ %3d/50  ]   ║\n" +
                             "║     | TYPE        %-13s ║\n" +
                             "║     | RESISTANCE  %-13s ║\n" +
                             "║     | WEAKNESS    %-13s ║\n" +
                             "╚═════════════════════════════════╝", number != -1 ? number + 1 : "", this.name, this.hp, this.totalhp, this.energy, this.type, this.resistance, this.weakness);
    }
}
