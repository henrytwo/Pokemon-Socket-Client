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

    public HashMap<String, Pokemon> getPokemonData() {
        return pokemonData;
    }

    public ArrayList<String> getPokemonNames() {
        return pokemonName;
    }

    // Gets String Array of Pokemon names from ArrayList of Pokemon objects
    public static String[] getPokemonName(ArrayList<Pokemon> pokemonArray) {
        String[] pokemonNameArray = new String[pokemonArray.size()];

        for (int i = 0; i < pokemonArray.size(); i++) {
            pokemonNameArray[i] = pokemonArray.get(i).getName();
        }

        return pokemonNameArray;
    }

    public Pokemon getPokemonObject(String pokemon) {
        return pokemonData.get(pokemon);
    }

    public static ArrayList<Pokemon> getPokemonObjects(ArrayList<String> pokemonName, HashMap<String, Pokemon> pokemonData) {
        ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();

        for (String name : pokemonName) {
            pokemonArrayList.add(pokemonData.get(name));
        }

        return pokemonArrayList;
    }

}
