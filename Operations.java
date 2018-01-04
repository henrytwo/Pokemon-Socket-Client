/**
 * Pokemon Arena
 * Operations.java
 *
 * Class of major socket operations
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

public class Operations {

    /**
     * Sands Communicator code to generate gameCode
     *
     * @param connector        Connector Object
     * @return                 String with gameCode
     */
    public static String genCode(Communicator connector) {
        String[] data = connector.get("0 // ");
        return data[0].equals("0") ? data[1] : "-1";
    }

    /**
     * Sends communicator code to join room
     *
     * @param connector        Connector object
     * @return                 Boolean with status of request
     */
    public static boolean joinGame(Communicator connector) {
        String[] data = connector.get(String.format("1 // %s // %s", Main.gameCode, String.join(" // ", Deck.getPokemonName(Main.selectedPokemon))));

        if (data[0].equals("1")) {
            Interactive.delayln(String.format("%s", data[1]));
            return true;
        } else {
            Interactive.confirmBoxClear(String.format("%s", data[1]));
            return false;
        }
    }
}
