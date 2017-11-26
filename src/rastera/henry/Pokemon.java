package rastera.henry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Pokemon {
    private String name, type, resistance, weakness;
    private int hp, numAttacks;
    private HashMap<String, Attack> attacks;

    public Pokemon(String[] dataLine) {
        this.name = dataLine[0];
        this.hp = Integer.parseInt(dataLine[1]);
        this.type = dataLine[2];
        this.resistance = dataLine[3];
        this.weakness = dataLine[4];
        this.numAttacks = Integer.parseInt(dataLine[5]);
        this.attacks = getAttacks(Arrays.copyOfRange(dataLine, 6, dataLine.length));
    }

    private HashMap<String, Attack> getAttacks(String[] attackData) {
        HashMap<String, Attack> attacks = new HashMap();

        for (int index = 0; index < this.numAttacks * 4; index += 4) {
            attacks.put(attackData[index], new Attack(attackData[index], attackData[index + 1], attackData[index + 2], (index + 3) < attackData.length ? attackData[index + 3] : " "));
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

    public int getHp() {
        return this.hp;
    }

    public int getNumAttacks() {
        return this.numAttacks;
    }

    public HashMap<String, Attack> getAttacks() {
        return this.attacks;
    }

    public String generateHealthBar() {
        String bar = "";

        for (int i = 0; i < 20; i++) {
            bar += "|";
        }

        return bar;
    }

    public String generateAttacks() {
        return "Pineapple";
    }

    public String toString() {
        return String.format("║ %15s ║ %s ║ %15s ║ %15s ║ %15s ║ [%s] ║", this.name, this.generateHealthBar(), this.type, this.resistance, this.weakness, this.generateAttacks());
    }
}
