/**
 * Pokemon Arena
 * LocalEngine.java
 *
 * Class for LocalEngine
 * Interface between Server and UI
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LocalEngine {

    private static Random random = new Random();

    private ArrayList<Pokemon> playerPokemons;
    private String playerName, gameCode, uuid;
    private Pokemon playerSelectedPokemon;
    private Player player;
    private Communicator connector;

    /**
     * Constructor method for LocalEngine
     *
     * @param connector        Communicator object
     * @param gameCode         String with client gameCode
     * @param uuid             String with client uuid
     * @param player           Player object for Player UI
     * @param playerPokemons   ArrayList of player's Pokemon
     * @param playerName       String of player's name
     */
    public LocalEngine(Communicator connector, String gameCode, String uuid, Player player, ArrayList<Pokemon> playerPokemons, String playerName) {

        this.connector      = connector;
        this.gameCode       = gameCode;
        this.uuid           = uuid;
        this.player         = player;

        this.playerName     = playerName;
        this.playerPokemons = Utilities.deepCopy(playerPokemons);

        Interactive.clearConsole();
        Interactive.confirmBoxClear(game());
    }

    /**
     * Main gameloop
     * Handles all gameplay aside from logic
     *
     * @return                 String with game outcome
     */
    private String game() {
        // Sends initial pokemon selection
        this.playerSelectedPokemon = Player.playerChoosePokemon(this.playerPokemons);
        this.connector.get(String.format("2 // InitPkmn // %s", this.playerSelectedPokemon.getName()));

        String message;
        String[] messageIn;
        ArrayList<String> doNotUpdate = new ArrayList<>() {{add("Draw"); add("Message"); add("Result");}};
        String messageOut = "Ready";
        boolean forcedRetreat = false;

        while (true) {
            // Sets Communicator to listen
            messageIn = this.connector.get(false, String.format("2 // %s", messageOut));

            // Checks if incoming message is valid (2)
            if (messageIn[0].equals("2")) {

                // Updates local Pokemon data
                if (messageIn.length > 2 && !doNotUpdate.contains(messageIn[1])) {
                    updatePokemons(Arrays.copyOfRange(messageIn, 3, messageIn.length));

                    // Checks if player was forced to retreat (If selected pokemon no longer exists)
                    forcedRetreat = getPokemonString(messageIn[2], this.playerPokemons) == null;

                    if (!forcedRetreat) {
                        this.playerSelectedPokemon = getPokemonString(messageIn[2], this.playerPokemons);
                    }
                }

                // Performs subaction
                switch (messageIn[1]) {
                    case "Draw":
                        Interactive.delayTypeln(1,getPokemonString(messageIn[2], Main.allPokemon).getAscii());
                        messageOut = "Ready";
                        break;
                    case "Message":
                        if(messageIn[2].contains("&c")) {
                            Interactive.clearConsole();
                            message = messageIn[2].substring(2);
                        }
                        else {
                            message = messageIn[2];
                        }
                        Interactive.delayTypeln(message);
                        messageOut = "Ready";
                        break;
                    case "Result":
                        return messageIn[2];
                    case "MakeAction":
                        messageOut = String.join(" // ", player.getUserAction(this.playerPokemons, this.playerSelectedPokemon));
                        break;
                    case "MakeChoose":
                        messageOut = String.format("Retreat // %s", player.playerChoosePokemon(this.playerPokemons).getName());
                        break;
                    case "Info":
                        Interactive.clearConsole();
                        Interactive.delayTypeln("Pokemon Statistics");
                        Interactive.displayPokemonCards(this.playerPokemons);
                        messageOut = "Ready";
                        break;
                }
            }
            else {
                return messageIn[1];
            }
        }
    }

    /*
    Protocol

    C >> S
    InitPkmn   - Init Game w/ player data
    Ready      - Client is ready to listen
    Action     - Client is performing Action
    Choose     - Client is choosing Pokemon

    S >> C
    Result     - Terminate Game w/ outcome
    MakeAction - Tell client to make action
    MakeChoose - Tell client to choose pokemon
    Message    - Tell client something
     */

    /**
     * Gets Pokemon object based on String name
     *
     * @param pokemonName      String with target Pokemon name
     * @param pokemonArray     ArrayList with local Pokemon objects
     * @return                 Pokemon object
     */
    private Pokemon getPokemonString(String pokemonName, ArrayList<Pokemon> pokemonArray) {
        for (Pokemon pokemon : pokemonArray) {
            if (pokemon.getName().equals(pokemonName)) {
                return pokemon;
            }
        }
        return null;
    }

    /**
     * Updates local Pokemon ArrayList with new data from server
     *
     * @param pokemonData      String array with updated data
     */
    private void updatePokemons(String[] pokemonData) {
        Pokemon updatePokemon;
        int updateIndex;
        int kill = -1;

        for (int i = this.playerPokemons.size() - 1; i >= 0; i--) {
            updatePokemon = this.playerPokemons.get(i);
            updateIndex   = Utilities.indexOf(pokemonData, updatePokemon.getName());

            if (updateIndex != -1) {
                if (Integer.parseInt(pokemonData[updateIndex + 1]) <= 0) {
                    kill = i;
                    continue;
                }

                updatePokemon.setHp(Integer.parseInt(pokemonData[updateIndex + 1]));
                updatePokemon.setEnergy(Integer.parseInt(pokemonData[updateIndex + 2]));

                this.playerPokemons.set(i, updatePokemon);
            }
        }

        if (kill != -1) {
            this.playerPokemons.remove(kill);
        }

    }
}
