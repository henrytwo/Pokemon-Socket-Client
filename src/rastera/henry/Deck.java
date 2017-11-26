package rastera.henry;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Deck {

    private HashMap<String, Pokemon> pokemonData = new HashMap<>();
    private ArrayList<String> pokemonName = new ArrayList<>();

    public Deck(String filePath) {
        try {
            Scanner inFile = new Scanner(new File(filePath));
            while (inFile.hasNextLine()) {
                String[] dataLine = inFile.nextLine().replaceAll(", ,", ",N/A,").split(",");
                pokemonData.put(dataLine[0], new Pokemon(dataLine));
                pokemonName.add(dataLine[0]);
            }
            Collections.sort(pokemonName);
            inFile.close();
        }
        catch (IOException e) {
            System.out.println("Error: FileIO error");
        }
    }

    public ArrayList<String> getPokemonName() {
        return pokemonName;
    }

    public Pokemon getPokemon(String pokemon) {
        return pokemonData.get(pokemon);
    }

}
