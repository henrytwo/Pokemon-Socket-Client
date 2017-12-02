import java.util.ArrayList;
import java.util.Random;

public class Engine {

    private static Random random = new Random();
    
    public ArrayList<Pokemon> playerPokemons, opponentPokemons;
    private String playerName, opponentName;
    private Pokemon playerSelectedPokemon, opponentSelectedPokemon;
    private boolean playerTurn;
    private Opponent opponent;
    private Battle battle;

    public Engine(Battle battle, Opponent opponent, ArrayList<Pokemon> playerPokemons, String playerName, ArrayList<Pokemon> opponentPokemons, String opponentName) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.playerPokemons = Utilities.deepCopy(playerPokemons);
        this.opponentPokemons = Utilities.deepCopy(opponentPokemons);
        this.battle = battle;
        this.opponent = opponent;
        this.playerTurn = random.nextBoolean();

        this.opponentSelectedPokemon = opponent.getSelectedPokemon();
        this.playerSelectedPokemon = battle.playerChoosePokemon(this.playerPokemons);

        game();
    }

    public void game() {

        String[] action;

        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.playerName, this.playerSelectedPokemon.getName()));
        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.opponentName, this.opponentSelectedPokemon.getName()));

        while (true) {
            while (this.playerTurn) {
                action = this.battle.getUserAction(this.playerPokemons, this.playerSelectedPokemon.getName(), this.playerSelectedPokemon.getEnergy(), this.playerSelectedPokemon.getAttacks());

                if (action[0] == "Pass") {
                    Interactive.delayTypeln(String.format("%s passed their turn", this.playerName));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else if(action[0] == "Retreat") {

                    for (Pokemon pokemon : this.playerPokemons) {
                        if (pokemon.getName() == action[1]) {
                            this.playerSelectedPokemon = pokemon;
                            break;
                        }
                    }

                    Interactive.delayTypeln(String.format("%s %s", this.playerName, String.format("retreated and switched to %s", this.playerSelectedPokemon.getName())));
                    Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.playerName, this.playerSelectedPokemon.getName()));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else if (action[0] != "Back") {
                    ArrayList<Attack> attackArrayList = new ArrayList<>(this.playerSelectedPokemon.getAttacks());

                    if (this.playerSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost() >= 0) {
                        this.playerSelectedPokemon.setEnergy(this.playerSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost());

                        Interactive.clearConsole();
                        Interactive.delayTypeln(1, this.playerSelectedPokemon.getAscii());
                        Interactive.delayTypeln(String.format("%s: %s, USE %s!", this.playerName, this.playerSelectedPokemon.getName(), attackArrayList.get(Integer.parseInt(action[0])).getName()));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                    else{
                        Interactive.confirmBoxClear("You do not have enough energy to use this attack!");
                    }
                }
            }

            while (!this.playerTurn) {
                action = opponent.computerTurn(this.opponentName, this.opponentSelectedPokemon.getEnergy(), this.opponentSelectedPokemon.getAttacks());

                if (action[0] == "Pass") {
                    Interactive.delayTypeln(String.format("%s passed their turn", this.opponentName));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else if (action[0] == "Retreat") {
                    for (Pokemon pokemon : this.opponentPokemons) {
                        if (pokemon.getName() == action[1]) {
                            this.opponentSelectedPokemon = pokemon;
                            break;
                        }
                    }

                    Interactive.delayTypeln(String.format("%s %s", this.opponent, String.format("retreated and switched to %s", this.opponentSelectedPokemon.getName())));
                    Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.opponentName, this.opponentSelectedPokemon.getName()));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else {
                    ArrayList<Attack> attackArrayList = new ArrayList<>(this.opponentSelectedPokemon.getAttacks());

                    if (this.opponentSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost() >= 0) {
                        this.opponentSelectedPokemon.setEnergy(this.opponentSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost());

                        Interactive.clearConsole();
                        Interactive.delayTypeln(1, this.opponentSelectedPokemon.getAscii());
                        Interactive.delayTypeln(String.format("%s: %s, USE %s!", this.opponentName, this.opponentSelectedPokemon.getName(), attackArrayList.get(Integer.parseInt(action[0])).getName()));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                }
            }
        }
    }
}
