// Main.java

package rastera.henry;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Scanner stdin = new Scanner(System.in);
    public static String name;
    public static String gameCode;
    public static String uuid;
    public static String[] pikachuNames = new String[] {"Pikachu", "Pikachu", "Pikachu", "Pikachu"};

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
                    multiplayer();
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
        // Socket server
        Communicator connector = new Communicator("127.0.0.1", 3160);

        while (true) {
            switch (Interactive.singleSelectMenu("RASTERA WAN MULTIPLAYER", new String[]{"Generate Game Code", "Join Game Room", "Debug console", "Exit WAN MULTIPLAYER"})) {
                case 1:
                    gameCode = Operations.genCode(connector);

                    System.out.println(String.format(" |Code Successfully Generated: %s", gameCode));
                    System.out.println(" |Ensure both players enter the EXACT");
                    System.out.println(" |to join the game. (Case sensitive)");

                    while (true) {
                        try {
                            System.out.println(String.format("\nJoin this game? <%s> [Y/n]", gameCode));
                            System.out.print("[Enter Selection] > ");

                            String selection = stdin.nextLine().toLowerCase().substring(0, 1);

                            if (selection.equals("n")) {
                                break;
                            }
                            else if (selection.equals("y")) {
                                Operations.joinGame(connector, name, gameCode, pikachuNames);
                                break;
                            }
                            else {
                                System.out.println("\nError: Please enter a valid choice [Y/n]");
                            }
                        }
                        catch (Exception e) {
                            stdin.nextLine();
                        }
                    }

                    break;
                case 2:

                    //Operations.joinGame(connector, name, gameCode, pikachuNames);
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