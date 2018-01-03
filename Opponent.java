import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Opponent {

    public ArrayList<Pokemon> deck;
    private String name;
    private Pokemon selectedPokemon;

    private static Random random = new Random();

    public Opponent() {
        this.name = computerName();
        this.deck = computerDeck();
        this.selectedPokemon = pickPokemon(this.deck);
    }

    public static String computerName() {
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

    public Pokemon pickPokemon(ArrayList<Pokemon> deck) {
        return deck.get(this.random.nextInt(deck.size()));
    }

    public String[] computerTurn(String pokemonName, int energy, ArrayList<Attack> attackArrayList) {

        String[] attackNames = new String[attackArrayList.size()];
        String[] attackStats = new String[attackArrayList.size()];

        for (int i = 0; i < attackArrayList.size(); i++) {
            attackNames[i] = attackArrayList.get(i).getName();
            attackStats[i] = attackArrayList.get(i).toString();
        }

        ArrayList<Attack> validAttacks = new ArrayList<>();

        for (Attack attack : attackArrayList) {
            if (energy - attack.getEnergyCost() >= 0) {
                validAttacks.add(attack);
            }
        }

        Collections.shuffle(validAttacks);

        return new String[] {(validAttacks.size() > 0) ? Integer.toString(attackArrayList.indexOf(validAttacks.get(0))) : "Pass"};

    }

    public Pokemon getSelectedPokemon() {
        return this.selectedPokemon;
    }

    public String getName() {
        return this.name;
    }

    public static ArrayList<Pokemon> computerDeck() {
        ArrayList<Pokemon> deck = new ArrayList<>();

        ArrayList<Pokemon> available = Utilities.deepCopy(Main.pokemonAvailable);
        Collections.shuffle(available);

        for (int i = 0; i < Deck.NUMPOKEMON; i++) {
            deck.add(available.get(i));
        }

        return deck;
    }
}
