import java.util.Scanner;

public class Operations {
    public static Scanner stdin = new Scanner(System.in);

    public static void debugConsole(Communicator connector) {
        while (true) {
            System.out.print("> ");
            String[] data = connector.get(stdin.nextLine());
            for (String line : data) {
                Interactive.delayTypeln(" |" + line);
            }
        }
    }

    public static String genCode(Communicator connector) {
        String[] data = connector.get("0 // ");
        return data[0].equals("0") ? data[1] : "-1";
    }

    public static boolean joinGame(Communicator connector) {
        // Join room
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
