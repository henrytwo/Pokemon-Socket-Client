/**
 * Pokemon Arena
 * Opponent.java
 *
 * Class of CPU/Opponent
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Opponent {

    public ArrayList<Pokemon> deck;
    private String name;
    private Pokemon selectedPokemon;

    private static Random random = new Random();

    /**
     * Opponent constructor method
     */
    public Opponent() {
        this.name = computerName();
        this.deck = computerDeck();
        this.selectedPokemon = pickPokemon(this.deck);
    }

    /**
     * Randomly picks name for Computer
     *
     * @return                 String of chosen name for Computer
     */
    private static String computerName() {
        ArrayList<String> names = new ArrayList<>();
        String line;

        try {
            BufferedReader nameFile = new BufferedReader(new FileReader(new File("data/names.txt")));

            while (true) {
                line = nameFile.readLine();
                if (line != null) {
                    names.add(line);
                }
                else {
                    break;
                }
            }

        }
        catch (IOException e) {
            Interactive.delayTypeln("Error: FileIO Error");
            names.add("Computer");
        }

        return names.get(random.nextInt(names.size()));
    }

    /**
     * Computer randomly picks starting Pokemon
     *
     * @param deck             ArrayList of Pokemon objects computer can choose from
     * @return                 Pokemon object computer chose to use
     */
    public Pokemon pickPokemon(ArrayList<Pokemon> deck) {
        return deck.get(this.random.nextInt(deck.size()));
    }

    /**
     * Computer randomly picks action
     *
     * @param energy           Integer of current energy level
     * @param attackArrayList  ArrayList of Attack objects computer can use
     * @return                 String array of desired actions
     */
    public String[] computerTurn(int energy, ArrayList<Attack> attackArrayList) {

        ArrayList<Attack> validAttacks = new ArrayList<>();

        for (Attack attack : attackArrayList) {
            if (energy - attack.getEnergyCost() >= 0) {
                validAttacks.add(attack);
            }
        }

        Collections.shuffle(validAttacks);

        // Passes if valid move cannot be made
        return new String[] {(validAttacks.size() > 0) ? Integer.toString(attackArrayList.indexOf(validAttacks.get(0))) : "Pass"};

    }

    /**
     * Computer selected pokemon
     *
     * @return                 Pokemon object of computer selected pokemon
     */
    public Pokemon getSelectedPokemon() {
        return this.selectedPokemon;
    }

    /**
     * Computer name
     *
     * @return                 String with computer name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Computer picks deck
     *
     * @return                 ArrayList of Pokemon objects computer selected
     */
    private static ArrayList<Pokemon> computerDeck() {
        ArrayList<Pokemon> deck = new ArrayList<>();

        ArrayList<Pokemon> available = Utilities.deepCopy(Main.pokemonAvailable);
        Collections.shuffle(available);

        for (int i = 0; i < Deck.NUMPOKEMON; i++) {
            deck.add(available.get(i));
        }

        return deck;
    }
}
