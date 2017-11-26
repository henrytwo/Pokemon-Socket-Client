// Main.java

package rastera.henry;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Scanner stdin = new Scanner(System.in);
    public static String name, gameCode, uuid, host;
    public static int port;
    public static String[] pokemonNames = new String[] {"Pikachu", "Pikachu", "Pikachu", "Pikachu"};
    public static Deck deck = new Deck("data/pokemon_data.txt");

    public static void main(String[] args){
        int gameMode;

        System.out.print("\033[H\033[2J");
        System.out.print("Enter name: ");
        name = stdin.nextLine();

        while (true) {
            // Mode select
            switch (Interactive.singleSelectMenu("Select Gamemode", new String[]{"1 vs Computer", "1 vs 1 [WAN Multiplayer]", "1 vs 1 [LAN Multiplayer]", "Exit"})) {
                case 1:
                    singleplayer();
                    break;
                case 2:
                    multiplayer();
                    break;
                case 3:
                    while (true) {
                        System.out.println("Enter the address and port of the RASTERA LAN server");

                        try {
                            System.out.print("Host: ");
                            host = stdin.nextLine();

                            System.out.print("Port: ");
                            port = stdin.nextInt();

                            break;
                        }
                        catch (Exception e) {
                            System.out.print("Error: Invalid Entry");
                            stdin.next();
                        }
                    }

                    multiplayer(host, port);

                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    public static void singleplayer() {
        System.out.println("Single player");
    }

    public static void multiplayer() {
        multiplayer("127.0.0.1", 3160);
    }

    public static void multiplayer(String host, int port) {

        Communicator connector = new Communicator(host, port);

        while (connector.isAlive) {
            switch (Interactive.singleSelectMenu("RASTERA WAN MULTIPLAYER", new String[]{"Generate Game Code", "Join Game Room", "Debug console", "Exit WAN MULTIPLAYER"})) {
                case 1:
                    gameCode = Operations.genCode(connector);

                    if (!gameCode.equals("-1")) {
                        System.out.println(String.format(" |Code Successfully Generated: %s", gameCode));
                        System.out.println(" |Ensure both players enter the EXACT");
                        System.out.println(" |to join the game. (Case sensitive)");

                        if (Interactive.booleanSelectMenu(String.format("\nJoin this game? <%s> [Y/n]", gameCode))) {
                            Operations.joinGame(connector);
                            break;
                        } else {
                            break;
                        }
                    }
                    else {
                        System.out.println("Error: Unable to connect to server");
                    }

                case 2:
                    System.out.print("[Enter Game Code]> ");
                    gameCode = stdin.next();

                    Operations.joinGame(connector);
                    break;
                case 3:
                    Operations.debugConsole(connector);
                    break;
                case  4:
                    return;
            }
        }
    }
}