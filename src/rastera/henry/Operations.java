package rastera.henry;

import java.util.Scanner;

public class Operations {
    public static Scanner stdin = new Scanner(System.in);

    public static void debugConsole(Communicator connector) {
        while (true) {
            System.out.print("> ");
            String[] data = connector.get(stdin.nextLine());
            for (String line : data) {
                System.out.println(" |" + line);
            }
        }
    }

    public static String genCode(Communicator connector) {
        String[] data = connector.get("0 // ");
        return data[1];
    }

    public static void joinGame(Communicator connector, String name, String gameCode, String[] pokemonNames) {

        String[] data = connector.get(String.format("1 // %s // %s // %s", gameCode, name, String.join(" // ", pokemonNames)));

        System.out.println(String.format(" |%s", data[1]));
        System.out.println(String.format(" |Assigned UUID: %s", data[2]));

    }
}
