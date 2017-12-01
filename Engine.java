import java.util.Random;

public class Engine {

    private static Random random = new Random();
    private boolean playerTurn;
    private Opponent opponent;

    public Engine(Opponent opponent) {
        this.opponent = opponent;
    }

    public void startBattle(Battle battle) {
        this.playerTurn = random.nextBoolean();

        battle.opponentChoosePokemon(opponent.getSelectedPokemon());

        while (true) {
            battle.scene(String.format("%s: %s I CHOOSE YOU!\n%s: %s I CHOOSE YOU!", battle.getPlayerName(), battle.getPlayerSelectedPokemon().getName(), battle.getOpponentName(), battle.getOpponentSelectedPokemon().getName()));

            if (true){//this.playerTurn) {
                battle.getUserAction(battle.getPlayerSelectedPokemon().getName(), battle.getPlayerSelectedPokemon().getEnergy(), battle.getPlayerSelectedPokemon().getAttacks());
            }
        }
    }

}
