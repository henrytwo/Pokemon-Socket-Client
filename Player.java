/**
 * Pokemon Arena
 * Player.java
 *
 * Player class
 * Handles UI and human interaction
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private static Scanner stdin = new Scanner(System.in);
    private static int selection;

    /**
     * Displays menu with Pokemon actions and gets user's decision
     *
     * @param playerPokemons   ArrayList of player's current Pokemon
     * @param playerPokemon    Player's current Pokemon object
     * @return                 String with encoded Player action
     */
    public String[] getUserAction(ArrayList<Pokemon> playerPokemons, Pokemon playerPokemon) {

        String pokemonName                = playerPokemon.getName();
        int energy                        = playerPokemon.getEnergy();

        // Breaking attacks into 2 components to make displaying easier
        String[] attackNames              = new String[playerPokemon.getAttacks().size()];
        String[] attackStats              = new String[playerPokemon.getAttacks().size() + 1];

        for (int i = 0; i < playerPokemon.getAttacks().size(); i++) {
            attackNames[i] = playerPokemon.getAttacks().get(i).getName();
            attackStats[i] = playerPokemon.getAttacks().get(i).toString();
        }

        // Adds back option to Attack Selection screen
        attackStats[attackStats.length - 1] = "Back";

        // Pokemon Stat
        Interactive.delayTypeln(playerPokemon.toStringSimple());

        // Menu
        switch (Interactive.singleSelectMenu(String.format("Select action for %s", pokemonName), new String[] {"Attack", "Retreat", "Pass", "Info"}, false)) {
            case 1:
                int attackNum;

                // Action loop
                while (true) {
                    attackNum = Interactive.singleSelectMenu(String.format("Select Attack [%3d EC Available]", energy), attackStats);

                    Interactive.clearConsole();

                    // We handle 'Back' as an attack. Engine knows to ignore this.
                    if (attackNum == attackStats.length) {
                        return new String[] {"Back"};
                    }
                    else if(Interactive.booleanSelectMenu(String.format("Are you sure you want to use %s? [Y/n]", attackNames[attackNum - 1]))) {
                        Interactive.clearConsole();
                        return new String[] {Integer.toString(attackNum - 1)};
                    }
                }
            case 2:
                return new String[] {"Retreat", playerChoosePokemon(playerPokemons).getName()};
            case 3:
                return new String[] {"Pass"};
            case 4:
                return new String[] {"Info"};
        }

        return new String[] {};
    }

    /**
     * Displays menu with all Pokemon available to player
     * Allows user to pick active Pokemon
     *
     * Helper method to clear display by default
     *
     * @param playerPokemons   ArrayList of Player's Pokemon
     * @return                 Selected Pokemon objet
     */
    public static Pokemon playerChoosePokemon(ArrayList<Pokemon> playerPokemons) {
        return playerChoosePokemon(playerPokemons, true);
    }

    /**
     * Displays menu with all Pokemon available to player
     * Allows user to pick active Pokemon
     *
     * @param playerPokemons   ArrayList of Player's Pokemon
     * @return                 Selected Pokemon objet
     */
    public static Pokemon playerChoosePokemon(ArrayList<Pokemon> playerPokemons, boolean clear) {
        boolean initialClear = false;

        while (true) {
            if (clear || initialClear) {
                Interactive.clearConsole();
            }
            else if (!clear) {
                initialClear = true;
            }
            Interactive.delayTypeln("Pick a Pokemon to use in battle:\n");
            Interactive.displayPokemonCards(playerPokemons);
            Interactive.delayType("[Enter Selection]> ");

            try {
                selection = stdin.nextInt() - 1;

                if (selection >= 0 && selection <= Main.pokemonAvailable.size()) {
                    Interactive.clearConsole();

                    if (Interactive.booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", playerPokemons.get(selection).getName()))) {
                        Interactive.clearConsole();
                        return  playerPokemons.get(selection);
                    }
                } else {
                    Interactive.confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, playerPokemons.size()));
                }
            } catch (Exception e) {
                stdin.nextLine();
                Interactive.confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, playerPokemons.size()));
            }
        }
    }
}
