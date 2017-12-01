import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Battle {

    private static Scanner stdin = new Scanner(System.in);

    public ArrayList<Pokemon> playerPokemons, opponentPokemons;
    private String playerName, opponentName;
    private Pokemon playerSelectedPokemon, opponentSelectedPokemon;
    private Engine engine;
    private int selection;

    public Battle(Engine engine, ArrayList<Pokemon> playerPokemons, String playerName, ArrayList<Pokemon> opponentPokemons, String opponentName) {
        this.playerName       = playerName;
        this.opponentName     = opponentName;
        this.playerPokemons   = Utilities.deepCopy(playerPokemons);
        this.opponentPokemons = Utilities.deepCopy(opponentPokemons);
        this.engine           = engine;

        playerChoosePokemon();
        engine.startBattle(this);
    }

    public void scene(String caption) {
        Interactive.clearConsole();
        Interactive.delayTypeln(caption);
    }

    public String getUserAction(String pokemonName, int energy, HashSet<Attack> attackHashSet) {

        ArrayList<Attack> attackArrayList = new ArrayList<>(attackHashSet);

        String[] attackNames = new String[attackArrayList.size()];
        String[] attackStats = new String[attackArrayList.size()];

        for (int i = 0; i < attackArrayList.size(); i++) {
            attackNames[i] = attackArrayList.get(i).getName();
            attackStats[i] = attackArrayList.get(i).toString();
        }

        switch (Interactive.singleSelectMenu(String.format("Select action for %s", pokemonName), new String[] {"Attack", "Retreat", "Pass"}, false)) {
            case 1:
                int attackNum;

                while (true) {
                    attackNum = Interactive.singleSelectMenu(String.format("Select Attack [%3d EC Available]", energy), attackStats);

                    Interactive.clearConsole();

                    if (Interactive.booleanSelectMenu(String.format("Are you sure you want to use %s? [Y/n]", attackNames[attackNum - 1]))) {
                        return attackNames[attackNum - 1];
                    }
                }
            case 2:
                playerChoosePokemon();
                return "Retreat";
            case 3:
                return "Pass";
        }

        return "";
    }

    public void opponentChoosePokemon(Pokemon pokemon) {
        this.opponentSelectedPokemon = pokemon;
    }

    public void opponentChoosePokemon(String pokemonName) {
        for (Pokemon pokemon : this.opponentPokemons) {
            if (pokemon.getName() == pokemonName) {
                this.opponentSelectedPokemon = pokemon;
            }
        }
    }

    public void playerChoosePokemon() {

        while (true) {

            Interactive.clearConsole();

            Interactive.delayTypeln("Pick a Pokemon to use in battle:\n");
            Interactive.displayPokemonCards(this.playerPokemons);

            Interactive.delayType("[Enter Selection]> ");

            try {
                selection = stdin.nextInt() - 1;

                if (selection >= 0 && selection <= Main.pokemonAvailable.size()) {

                    Interactive.clearConsole();

                    if (Interactive.booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", this.playerPokemons.get(selection).getName()))) {
                        this.playerSelectedPokemon = this.playerPokemons.get(selection);
                        break;
                    }

                } else {
                    Interactive.confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, this.playerPokemons.size()));
                }
            } catch (Exception e) {
                stdin.nextLine();
                Interactive.confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, this.playerPokemons.size()));
            }
        }
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getOpponentName() {
        return this.opponentName;
    }

    public Pokemon getPlayerSelectedPokemon() {
        return playerSelectedPokemon;
    }

    public Pokemon getOpponentSelectedPokemon() {
        return opponentSelectedPokemon;
    }
}
