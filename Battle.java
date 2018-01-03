import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Battle {

    private static Scanner stdin = new Scanner(System.in);
    private static int selection;

    public String[] getUserAction(ArrayList<Pokemon> playerPokemons, Pokemon playerPokemon) {

        String pokemonName                = playerPokemon.getName();
        int energy                        = playerPokemon.getEnergy();

        String[] attackNames              = new String[playerPokemon.getAttacks().size()];
        String[] attackStats              = new String[playerPokemon.getAttacks().size() + 1];

        for (int i = 0; i < playerPokemon.getAttacks().size(); i++) {
            attackNames[i] = playerPokemon.getAttacks().get(i).getName();
            attackStats[i] = playerPokemon.getAttacks().get(i).toString();
        }

        attackStats[attackStats.length - 1] = "Back";

        Interactive.delayTypeln(playerPokemon.toStringSimple());
        switch (Interactive.singleSelectMenu(String.format("Select action for %s", pokemonName), new String[] {"Attack", "Retreat", "Pass", "Info"}, false)) {
            case 1:
                int attackNum;

                while (true) {
                    attackNum = Interactive.singleSelectMenu(String.format("Select Attack [%3d EC Available]", energy), attackStats);

                    Interactive.clearConsole();

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

    public static Pokemon playerChoosePokemon(ArrayList<Pokemon> playerPokemons) {
        return playerChoosePokemon(playerPokemons, true);
    }

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
