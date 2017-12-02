import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Battle {

    private static Scanner stdin = new Scanner(System.in);

    private int selection;

    public String[] getUserAction(ArrayList<Pokemon> playerPokemons, String pokemonName, int energy, HashSet<Attack> attackHashSet) {

        ArrayList<Attack> attackArrayList = new ArrayList<>(attackHashSet);

        String[] attackNames = new String[attackArrayList.size()];
        String[] attackStats = new String[attackArrayList.size() + 1];

        for (int i = 0; i < attackArrayList.size(); i++) {
            attackNames[i] = attackArrayList.get(i).getName();
            attackStats[i] = attackArrayList.get(i).toString();
        }

        attackStats[attackStats.length - 1] = "Back";

        switch (Interactive.singleSelectMenu(String.format("Select action for %s", pokemonName), new String[] {"Attack", "Retreat", "Pass"}, false)) {
            case 1:
                int attackNum;

                while (true) {
                    attackNum = Interactive.singleSelectMenu(String.format("Select Attack [%3d EC Available]", energy), attackStats);

                    Interactive.clearConsole();

                    if (attackNum == attackStats.length) {
                        return new String[] {"Back"};
                    }
                    else if(Interactive.booleanSelectMenu(String.format("Are you sure you want to use %s? [Y/n]", attackNames[attackNum - 1]))) {
                        return new String[] {Integer.toString(attackNum - 1)};
                    }
                }
            case 2:
                return new String[] {"Retreat", playerChoosePokemon(playerPokemons).getName()};
            case 3:
                return new String[] {"Pass"};
        }

        return new String[] {};
    }

    public Pokemon playerChoosePokemon(ArrayList<Pokemon> playerPokemons) {
        while (true) {
            Interactive.clearConsole();
            Interactive.delayTypeln("Pick a Pokemon to use in battle:\n");
            Interactive.displayPokemonCards(playerPokemons);
            Interactive.delayType("[Enter Selection]> ");

            try {
                selection = stdin.nextInt() - 1;

                if (selection >= 0 && selection <= Main.pokemonAvailable.size()) {
                    Interactive.clearConsole();

                    if (Interactive.booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", playerPokemons.get(selection).getName()))) {
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
