package rastera.henry;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Deck {

    private HashMap<String, Pokemon> pokemonData = new HashMap<>();

    public Deck(String filePath) {
        try {
            Scanner inFile = new Scanner(new File(filePath));
            while (inFile.hasNextLine()) {
                String[] dataLine = inFile.nextLine().split(",");
                pokemonData.put(dataLine[0], new Pokemon(dataLine));

                System.out.println(pokemonData.get(dataLine[0]));
            }
            inFile.close();

        }
        catch (IOException e) {
            System.out.println("Error: FileIO error");
        }
    }

}
