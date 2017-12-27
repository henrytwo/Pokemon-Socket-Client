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
        Interactive.delayTypeln(game());
    }

    public String game() {
        this.playerSelectedPokemon = Battle.playerChoosePokemon(Main.selectedPokemon);
        this.connector.get(String.format("2 // %s // %s // InitPkmn // %s", this.gameCode, this.uuid, this.playerSelectedPokemon.getName()));

        String[] messageIn;
        String messageOut = "Ready";
        System.out.println("Waiting for opponent...");

        while (true) {
            messageIn = this.connector.get(true, String.format("2 // %s // %s // %s", this.gameCode, this.uuid, messageOut));

            if (messageIn[0].equals("2")) {

                if (messageIn.length > 2) {
                    updatePokemons(Arrays.copyOfRange(messageIn, 3, messageIn.length));
                    this.playerSelectedPokemon = getPokemonString(messageIn[2], this.playerPokemons);
                }

                switch (messageIn[1]) {
                    case "Result":
                        return messageIn[2];
                    case "MakeAction":
                        messageOut = String.format("Action // %s", String.join(" // ", battle.getUserAction(this.playerPokemons, this.playerSelectedPokemon)));
                        break;
                    case "MakeChoose":
                        messageOut = String.format("Choose // %s", battle.playerChoosePokemon(this.playerPokemons).getName());
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

        for (int i = this.playerPokemons.size(); i != 0; i--) {
            updatePokemon = this.playerPokemons.get(i);
            updateIndex   = Utilities.indexOf(pokemonData, updatePokemon.getName());

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
