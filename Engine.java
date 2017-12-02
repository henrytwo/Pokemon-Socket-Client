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

        Interactive.winScreen(game());
    }

    public void regen() {
        for (Pokemon pokemon : this.playerPokemons) {
            pokemon.setEnergy((pokemon.getEnergy() + 10 > 50) ? 50 : pokemon.getEnergy() + 10);
        }

        for (Pokemon pokemon : this.opponentPokemons) {
            pokemon.setEnergy((pokemon.getEnergy() + 10 > 50) ? 50 : pokemon.getEnergy() + 10);
        }
    }

    public boolean game() {

        String[] action;

        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.playerName, this.playerSelectedPokemon.getName()));
        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.opponentName, this.opponentSelectedPokemon.getName()));

        while (true) {

            while (this.playerTurn) {

                if (this.playerPokemons.size() == 0) {
                    return false;
                }

                if (this.playerSelectedPokemon.getHp() <= 0) {
                    Interactive.delayTypeln("Your Pokemon has fainted! You must pick a replacement to continue fighting!");

                    this.playerPokemons.remove(this.playerSelectedPokemon);
                    action = new String[] {"Retreat", battle.playerChoosePokemon(this.playerPokemons).getName()};
                }
                else {
                    action = this.battle.getUserAction(this.playerPokemons, this.playerSelectedPokemon.getName(), this.playerSelectedPokemon.getEnergy(), this.playerSelectedPokemon.getAttacks());
                }

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
                else if (action[0] == "Info") {
                    Interactive.clearConsole();
                    Interactive.delayTypeln("Pokemon Statistics");
                    Interactive.displayPokemonCards(playerPokemons);
                }
                else if (action[0] != "Back") {
                    ArrayList<Attack> attackArrayList = new ArrayList<>(this.playerSelectedPokemon.getAttacks());

                    if (this.playerSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost() >= 0) {
                        this.playerSelectedPokemon.setEnergy(this.playerSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost());

                        Interactive.clearConsole();
                        Interactive.delayTypeln(1, this.playerSelectedPokemon.getAscii());
                        Interactive.delayTypeln(String.format("%s: %s, USE %s!", this.playerName, this.playerSelectedPokemon.getName(), attackArrayList.get(Integer.parseInt(action[0])).getName()));

                        this.opponentSelectedPokemon = action(this.opponentSelectedPokemon, this.playerSelectedPokemon, attackArrayList.get(Integer.parseInt(action[0])));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                    else{
                        Interactive.confirmBoxClear("You do not have enough energy to use this attack!");
                    }
                }
            }

            regen();

            while (!this.playerTurn) {

                if (this.opponentPokemons.size() == 0) {
                    return true;
                }

                if (this.opponentSelectedPokemon.getHp() <= 0) {
                    this.opponentPokemons.remove(this.opponentSelectedPokemon);
                    action = new String[] {"Retreat", opponent.pickPokemon().getName()};
                }
                else {
                    action = opponent.computerTurn(this.opponentName, this.opponentSelectedPokemon.getEnergy(), this.opponentSelectedPokemon.getAttacks());
                }

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

                    Interactive.delayTypeln(String.format("%s retreated and switched to %s", this.opponentName, this.opponentSelectedPokemon.getName()));
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

                        this.playerSelectedPokemon = action(this.playerSelectedPokemon, this.opponentSelectedPokemon, attackArrayList.get(Integer.parseInt(action[0])));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                }
            }

            regen();
        }
    }

    public Pokemon action(Pokemon target, Pokemon attacker, Attack attack) {

        int damage = attack.getDamage();

        if (attacker.getType() == target.getResistance()) {
            damage *= 0.5;
            Interactive.delayTypeln("IT'S NOT VERY EFFECTIVE!");
        }
        else if (attacker.getType() == target.getWeakness()) {
            damage *= 2;
            Interactive.delayTypeln("IT'S SUPER EFFECTIVE!");
        }

        if (attack.getSpecial() != "N/A" && random.nextBoolean()) {
            switch (attack.getSpecial()) {
                case "Stun":
                    // Stun
                    break;
                case "Wild Card":
                    // Wild Card
                    break;
                case "Wild Storm":
                    // Wild Storm
                    break;
                case "Disable":
                    // Disable
                    break;
                case "Recharge":
                    // Recharge
                    break;
            }
        }

        Interactive.delayTypeln(String.format("%s INFLICTED %d DAMAGE ON %S!", attacker.getName(), damage, target.getName()));
        target.setHp((target.getHp() - damage < 0) ? 0 : target.getHp() - damage);

        if (target.getHp() <= 0) {
            Interactive.delayTypeln(String.format("%s HAS FAINTED!", target.getName()));
        }

        return target;
    }
}
