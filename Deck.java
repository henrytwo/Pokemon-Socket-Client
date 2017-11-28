import java.io.File;
import java.io.IOException;
import java.util.*;

public class Deck {

    private HashMap<String, Pokemon> pokemonData = new HashMap<>();
    private ArrayList<String> pokemonName        = new ArrayList<>();
    private static final int NAME                = 0;

    public Deck(String filePath) {
        try {
            Scanner inFile = new Scanner(new File(filePath));
            while (inFile.hasNextLine()) {
                String[] dataLine = inFile.nextLine().replaceAll(", ,", ",N/A,").split(",");
                pokemonData.put(dataLine[NAME], new Pokemon(dataLine));
                pokemonName.add(dataLine[NAME]);
            }
            Collections.sort(pokemonName);
            inFile.close();
        }
        catch (IOException e) {
            Interactive.delayTypeln("Error: FileIO error\nTerminating program");
            System.exit(0);
        }
    }

    public ArrayList<String> getPokemonName() {
        return pokemonName;
    }

    // Gets String Array of Pokemon from ArrayList of Pokemon objects
    public static String[] getPokemonName(ArrayList<Pokemon> pokemonArray) {
        String[] pokemonNameArray = new String[pokemonArray.size()];

        for (int i = 0; i < pokemonArray.size(); i++) {
            pokemonNameArray[i] = pokemonArray.get(i).getName();
        }

        return pokemonNameArray;
    }

    public Pokemon getPokemon(String pokemon) {
        return pokemonData.get(pokemon);
    }

}
