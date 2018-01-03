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
        this.playerName              = playerName;
        this.opponentName            = opponentName;
        this.playerPokemons          = Utilities.deepCopy(playerPokemons);
        this.opponentPokemons        = Utilities.deepCopy(opponentPokemons);
        this.battle                  = battle;
        this.opponent                = opponent;
        this.playerTurn              = random.nextBoolean();

        this.opponentSelectedPokemon = opponent.getSelectedPokemon();
        this.playerSelectedPokemon   = Battle.playerChoosePokemon(this.playerPokemons);

        Interactive.clearConsole();
        Interactive.winScreen(game());
    }

    public ArrayList<Pokemon> regen(ArrayList<Pokemon> party) {
        for (Pokemon pokemon : party) {
            pokemon.setEnergy((pokemon.getEnergy() + 10 > 50) ? 50 : pokemon.getEnergy() + 10);
        }
        return party;
    }

    public boolean game() {

        String[] action;

        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.playerName, this.playerSelectedPokemon.getName()));
        Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!", this.opponentName, this.opponentSelectedPokemon.getName()));

        while (true) {

            while (this.playerTurn) {
                Interactive.delayTypeln(String.format("----- %s's TURN! -----", this.playerName));

                if (this.playerSelectedPokemon.getHp() <= 0) {
                    this.playerPokemons.remove(this.playerSelectedPokemon);

                    if (this.playerPokemons.size() == 0) {
                        return false;
                    }

                    Interactive.delayTypeln("Your Pokemon has fainted! You must pick a replacement to continue fighting!");

                    action = new String[] {"Retreat", battle.playerChoosePokemon(this.playerPokemons, false).getName()};
                }
                else if (this.playerSelectedPokemon.getStunned()) {
                    this.playerSelectedPokemon.setStunned(false);
                    Interactive.delayTypeln(String.format("%s IS STUNNED!", this.playerSelectedPokemon.getName()));
                    action = new String[] {"Pass"};
                }
                else {
                    action = this.battle.getUserAction(this.playerPokemons, this.playerSelectedPokemon);
                }

                if (action[0] == "Pass") {
                    Interactive.delayTypeln(String.format("%s passed their turn\n", this.playerName));

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
                    Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!\n", this.playerName, this.playerSelectedPokemon.getName()));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else if (action[0] == "Info") {
                    Interactive.clearConsole();
                    Interactive.delayTypeln("Pokemon Statistics");
                    Interactive.displayPokemonCards(this.playerPokemons);
                }
                else if (action[0] != "Back") {

                    if (this.playerSelectedPokemon.getEnergy() - this.playerSelectedPokemon.getAttacks().get(Integer.parseInt(action[0])).getEnergyCost() >= 0) {
                        this.playerSelectedPokemon.setEnergy(this.playerSelectedPokemon.getEnergy() - this.playerSelectedPokemon.getAttacks().get(Integer.parseInt(action[0])).getEnergyCost());

                        //Interactive.clearConsole();
                        Interactive.delayTypeln(1, this.playerSelectedPokemon.getAscii());
                        Interactive.delayTypeln(String.format("%s: %s, USE %s!", this.playerName, this.playerSelectedPokemon.getName(), this.playerSelectedPokemon.getAttacks().get(Integer.parseInt(action[0])).getName()));

                        this.opponentSelectedPokemon = action(this.opponentSelectedPokemon, this.playerSelectedPokemon, this.playerSelectedPokemon.getAttacks().get(Integer.parseInt(action[0])));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                    else{
                        Interactive.confirmBoxClear("You do not have enough energy to use this attack!");
                    }
                }
            }

            this.playerPokemons = regen(this.playerPokemons);

            while (!this.playerTurn) {
                Interactive.delayTypeln(String.format("----- %s's TURN! -----", this.opponentName));

                if (this.opponentSelectedPokemon.getHp() <= 0) {
                    this.opponentPokemons.remove(this.opponentSelectedPokemon);

                    if (this.opponentPokemons.size() == 0) {
                        return true;
                    }

                    action = new String[] {"Retreat", this.opponent.pickPokemon(this.opponentPokemons).getName()};
                }
                else if (this.opponentSelectedPokemon.getStunned()) {
                    this.opponentSelectedPokemon.setStunned(false);
                    Interactive.delayTypeln(String.format("%s IS STUNNED!", this.opponentSelectedPokemon.getName()));
                    action = new String[] {"Pass"};
                }
                else {
                    action = opponent.computerTurn(this.opponentName, this.opponentSelectedPokemon.getEnergy(), this.opponentSelectedPokemon.getAttacks());
                }

                if (action[0] == "Pass") {
                    Interactive.delayTypeln(String.format("%s passed their turn\n", this.opponentName));

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
                    Interactive.delayTypeln(String.format("%s: %s I CHOOSE YOU!\n", this.opponentName, this.opponentSelectedPokemon.getName()));

                    this.playerTurn = !this.playerTurn;
                    break;
                }
                else {
                    ArrayList<Attack> attackArrayList = new ArrayList<>(this.opponentSelectedPokemon.getAttacks());

                    if (this.opponentSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost() >= 0) {
                        this.opponentSelectedPokemon.setEnergy(this.opponentSelectedPokemon.getEnergy() - attackArrayList.get(Integer.parseInt(action[0])).getEnergyCost());

                        //Interactive.clearConsole();
                        Interactive.delayTypeln(1, this.opponentSelectedPokemon.getAscii());
                        Interactive.delayTypeln(String.format("%s: %s, USE %s!", this.opponentName, this.opponentSelectedPokemon.getName(), attackArrayList.get(Integer.parseInt(action[0])).getName()));

                        this.playerSelectedPokemon = action(this.playerSelectedPokemon, this.opponentSelectedPokemon, attackArrayList.get(Integer.parseInt(action[0])));

                        this.playerTurn = !this.playerTurn;
                        break;
                    }
                }
            }

            this.opponentPokemons = regen(this.opponentPokemons);
        }
    }

    public Pokemon action(Pokemon target, Pokemon attacker, Attack attack) {

        int baseDamage = attack.getDamage();
        int finalDamage;
        String messageBuffer = "";

        if (attacker.getDisabled()) {
            baseDamage = baseDamage - 10 > 0 ? baseDamage - 10 : 0;
            messageBuffer += String.format("DAMAGE REDUCED TO %d DUE TO DISABLE!\n", baseDamage);
        }

        if (baseDamage > 0) {
            if (attacker.getType().equals(target.getResistance())) {
                baseDamage *= 0.5;
                messageBuffer += "IT'S NOT VERY EFFECTIVE!\n";
            } else if (attacker.getType().equals(target.getWeakness())) {
                baseDamage *= 2;
                messageBuffer += "IT'S SUPER EFFECTIVE!\n";
            }
        }

        finalDamage = baseDamage;

        if (attack.getSpecial() != "N/A") {
            switch (attack.getSpecial()) {
                case "Stun":
                    if (random.nextBoolean()) {
                        target.setStunned(true);
                        messageBuffer += String.format("%s HAS BEEN STUNNED!\n", target.getName());
                    }
                    else {
                        messageBuffer += String.format("%s DODGED THE STUN!\n", target.getName());
                    }
                    break;
                case "Wild card":
                    if (random.nextBoolean()) {
                        finalDamage = 0;
                        messageBuffer = String.format("%s MISSED! NO DAMAGE INFLICTED!\n", attacker.getName());
                    }
                    break;
                case "Wild storm":
                    while (true) {
                        if (random.nextBoolean()) {
                            finalDamage += baseDamage;
                            messageBuffer += "Wild Storm succeeded! Attack repeated!\n";
                        }
                        else {
                            messageBuffer += (finalDamage == baseDamage) ? "Wild Storm missed!\n" : "";
                            break;
                        }
                    }
                    break;
                case "Disable":
                    if (!target.getDisabled()) {
                        messageBuffer += String.format("%s HAS BEEN DISABLED!\n", target.getName());
                        target.setDisabled(true);
                    }
                    else {
                        messageBuffer += String.format("%s DODGED THE DISABLE!\n", target.getName());
                    }
                    break;
                case "Recharge":
                    attacker.setHp((attacker.getHp() + 20 > attacker.getHpTotal()) ? attacker.getHpTotal() : attacker.getHp() + 20);
                    messageBuffer += String.format("RECHARGE APPLIED TO %s!\n", attacker.getName());
                    break;
            }
        }


        messageBuffer += String.format("%s INFLICTED %d DAMAGE ON %S!\n", attacker.getName(), finalDamage, target.getName());
        target.setHp((target.getHp() - finalDamage < 0) ? 0 : target.getHp() - finalDamage);

        if (target.getHp() <= 0) {
            messageBuffer += String.format("%s HAS FAINTED!\n", target.getName());
        }

        Interactive.delayTypeln(messageBuffer);

        return target;
    }
}
