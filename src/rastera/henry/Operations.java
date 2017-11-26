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
        return data[0].equals("0") ? data[1] : "-1";
    }

    public static void joinGame(Communicator connector) {

        // Join room
        String[] data = connector.get(String.format("1 // %s // %s // %s", Main.gameCode, Main.name, String.join(" // ", Main.pokemonNames)));

        if (data[0].equals("1")) {
            Main.uuid = data[2];

            System.out.println(String.format(" |%s", data[1]));
            System.out.println(String.format(" |Assigned UUID: %s", data[2]));
        }
        else {
            System.out.println(String.format(" |%s", data[1]));
            return;
        }

        while (true) {
            // Acknowledgement
            data = connector.get(String.format(" 2 // %s // %s", Main.gameCode, Main.uuid));
            for(String line:data) {
                System.out.println(line);
            }
        }

    }
}
