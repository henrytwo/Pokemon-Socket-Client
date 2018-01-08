/**
 * Pokemon Arena
 * Deck.java
 *
 * Class of Pokemon Deck
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
import java.util.*;

public class Deck {

    private HashMap<String, Pokemon> pokemonData = new HashMap<String, Pokemon>();
    private ArrayList<String> pokemonName        = new ArrayList<String>();
    private static final int NAME                = 0;
    public static final int NUMPOKEMON           = 6;
    String dataIn;

    /**
     * Deck constructor method
     *
     * @param filePath         String with path to Pokemon datafile
     */
    public Deck(String filePath) {
        try {
            // Reads file line by line and generates Pokemon objects
            BufferedReader inFile = new BufferedReader(new FileReader(new File(filePath)));
            while (true) {
                dataIn = inFile.readLine();

                if (dataIn != null) {
                    String[] dataLine = dataIn.replaceAll(", ,", ",N/A,").split(",");
                    pokemonData.put(dataLine[NAME], new Pokemon(dataLine));
                    pokemonName.add(dataLine[NAME]);
                }
                else {
                    break;
                }
            }
            Collections.sort(pokemonName);
            inFile.close();
        }
        catch (IOException e) {
            Interactive.delayTypeln("Error: FileIO error\nTerminating program");
            System.exit(0);
        }
    }

    /**
     * Gets HashMap of Pokemon objects
     *
     * @return                 HashMap of Pokemon objects [pokemonName:pokemonObject]
     */
    public HashMap<String, Pokemon> getPokemonData() {
        return pokemonData;
    }

    /**
     * Gets ArrayList of Pokemon Names
     *
     * @return                 String ArrayList of Pokemon names
     */
    public ArrayList<String> getPokemonNames() {
        return pokemonName;
    }

    /**
     * Gets String array of Pokemon Names from ArrayList of Pokemon Objects
     *
     * @param pokemonArray     ArrayList of Pokemon objects
     * @return                 String Array of Pokemon object names
     */
    public static String[] getPokemonName(ArrayList<Pokemon> pokemonArray) {
        String[] pokemonNameArray = new String[pokemonArray.size()];

        for (int i = 0; i < pokemonArray.size(); i++) {
            pokemonNameArray[i] = pokemonArray.get(i).getName();
        }

        return pokemonNameArray;
    }

    /**
     * Gets ArrayList of Pokemon objects from HashMap of objects and ArrayList of Pokemon names
     *
     * @param pokemonName      ArrayList of Pokemon names
     * @param pokemonData      HashMap of Pokemon objects [pokemonName:pokemonObject]
     * @return                 ArrayList of Pokemon objects
     */
    public static ArrayList<Pokemon> getPokemonObjects(ArrayList<String> pokemonName, HashMap<String, Pokemon> pokemonData) {
        ArrayList<Pokemon> pokemonArrayList = new ArrayList<Pokemon>();

        for (String name : pokemonName) {
            pokemonArrayList.add(pokemonData.get(name));
        }

        return pokemonArrayList;
    }

}
