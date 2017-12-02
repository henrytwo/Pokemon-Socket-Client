import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Pokemon {
    private String name, type, resistance, weakness, ascii, dataIn;
    private int hp, totalhp, numAttacks, energy;
    private boolean disabled = false;
    private HashSet<Attack> attacks;

    private static final int ATTACKCAP = 2;
    private static final int NAME = 0;
    private static final int HP = 1;
    private static final int TYPE = 2;
    private static final int RESISTANCE = 3;
    private static final int WEAKNESS = 4;
    private static final int NUMATTACKS = 5;
    private static final int ATTACKS = 6;

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

        try {
            BufferedReader asciiFile = new BufferedReader(new FileReader(new File(String.format("ascii/%s.txt", this.name.toLowerCase().replace(".", "").replace("'", "").replace(" ", "-")))));

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

    private HashSet<Attack> getAttacks(String[] attackData) {
        HashSet<Attack> attacks = new HashSet();

        for (int index = 0; index < this.numAttacks * 4; index += 4) {
            attacks.add(new Attack(attackData[index], attackData[index + 1], attackData[index + 2], (index + 3) < attackData.length ? attackData[index + 3] : "N/A"));
        }

        return attacks;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getResistance() {
        return this.resistance;
    }

    public String getWeakness() {
        return this.weakness;
    }

    public int getNumAttacks() {
        return this.numAttacks;
    }

    public HashSet<Attack> getAttacks() {
        return this.attacks;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return this.hp;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return this.energy;
    }

    public String generateHealthBar() {
        String bar = "[";

        int bars = (int) (20 * (float) this.hp / this.totalhp);

        for (int i = 0; i < 20; i++) {
            bar += (i < bars) ? "|" : " ";
        }

        return bar + "]";
    }

    public String generateAttacks() {
        String formattedAttacks = "";

        for (Attack currentAttack : this.attacks) {
            formattedAttacks += (" " + currentAttack.toString());
        }

        if (this.attacks.size() < this.ATTACKCAP) {
            formattedAttacks += String.format("[ %-15s EC: %-3s D: %-3s S: %-10s ]", "N/A", "N/A", "N/A", "N/A");
        }

        return formattedAttacks;
    }

    public void draw() {

    }

    public String getAscii() {
        return this.ascii;
    }

    public String toString() {
        return String.format("║ %-15s ║ %s [%3d/%-3d] ║ %-15s ║ %-15s ║ %-15s ║%s ║", this.name, this.generateHealthBar(), this.hp, this.totalhp, this.type, this.resistance, this.weakness, this.generateAttacks());
    }

    public String toStringSimple() {
        return String.format("║ %-12s ║ %-3d ║ %-12s ║ %-12s ║ %-12s ║", this.name, this.totalhp, this.type, this.resistance, this.weakness);
    }

    public String toCard(int number) {
        return String.format("╔═════════════════════════════════╗\n" +
                             "║ %-3d | NAME        %-13s ║\n" +
                             "║     | HP          [ %3d/%-3d ]   ║\n" +
                             "║     | ENERGY      [ %3d/50  ]   ║\n" +
                             "║     | TYPE        %-13s ║\n" +
                             "║     | RESISTANCE  %-13s ║\n" +
                             "║     | WEAKNESS    %-13s ║\n" +
                             "╚═════════════════════════════════╝", number + 1, this.name, this.hp, this.totalhp, this.energy, this.type, this.resistance, this.weakness);
    }
}
