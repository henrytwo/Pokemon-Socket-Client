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

    public Opponent() {
        this.name = computerName();
        this.deck = computerDeck();
        this.selectedPokemon = deck.get(random.nextInt(deck.size()));
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
