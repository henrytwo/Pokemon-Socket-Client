import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class LocalEngine {

    private static Random random = new Random();

    private ArrayList<Pokemon> playerPokemons;
    private String playerName, gameCode, uuid;
    private Pokemon playerSelectedPokemon;
    private Battle battle;
    private Communicator connector;

    public LocalEngine(Communicator connector, String gameCode, String uuid, Battle battle, ArrayList<Pokemon> playerPokemons, String playerName) {

        this.connector = connector;
        this.gameCode = gameCode;
        this.uuid = uuid;
        this.battle = battle;

        this.playerName = playerName;
        this.playerPokemons = Utilities.deepCopy(playerPokemons);

        Interactive.clearConsole();
        Interactive.confirmBoxClear(game());
    }

    public String game() {
        this.playerSelectedPokemon = Battle.playerChoosePokemon(this.playerPokemons);
        this.connector.get(String.format("2 // InitPkmn // %s", this.playerSelectedPokemon.getName()));

        String message;
        String[] messageIn;
        ArrayList<String> doNotUpdate = new ArrayList<>() {{add("Draw"); add("Message"); add("Result");}};
        String messageOut = "Ready";

        while (true) {
            messageIn = this.connector.get(false, String.format("2 // %s", messageOut));

            if (messageIn[0].equals("2")) {

                if (messageIn.length > 2 && !doNotUpdate.contains(messageIn[1])) {
                    updatePokemons(Arrays.copyOfRange(messageIn, 3, messageIn.length));
                    if (this.playerPokemons.contains(messageIn[2])) {
                        this.playerSelectedPokemon = getPokemonString(messageIn[2], this.playerPokemons);
                    }
                }

                switch (messageIn[1]) {
                    case "Draw":
                        Interactive.delayTypeln(1,getPokemonString(messageIn[2], Main.allPokemon).getAscii());
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
                        messageOut = String.format("Action // %s", String.join(" // ", battle.getUserAction(this.playerPokemons, this.playerSelectedPokemon)));
                        break;
                    case "MakeChoose":
                        messageOut = String.format("Choose // %s", battle.playerChoosePokemon(this.playerPokemons).getName());
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

    public Pokemon getPokemonString(String pokemonName, ArrayList<Pokemon> pokemonArray) {
        for (Pokemon pokemon : pokemonArray) {
            if (pokemon.getName().equals(pokemonName)) {
                return pokemon;
            }
        }
        return null;
    }

    public void updatePokemons(String[] pokemonData) {
        Pokemon updatePokemon;
        int updateIndex;

        for (int i = this.playerPokemons.size() - 1; i >= 0; i--) {
            updatePokemon = this.playerPokemons.get(i);
            updateIndex   = Utilities.indexOf(pokemonData, updatePokemon.getName());

            if (updateIndex != -1) {
                if (Integer.parseInt(pokemonData[updateIndex + 1]) <= 0) {
                    this.playerPokemons.remove(i);
                    continue;
                }

                updatePokemon.setHp(Integer.parseInt(pokemonData[updateIndex + 1]));
                updatePokemon.setEnergy(Integer.parseInt(pokemonData[updateIndex + 2]));

                this.playerPokemons.set(i, updatePokemon);
            }
        }
    }
}
