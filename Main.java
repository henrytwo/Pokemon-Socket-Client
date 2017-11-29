// Main.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Deck deck                           = new Deck("data/pokemon_data.txt");
    public static Scanner stdin                       = new Scanner(System.in);
    public static ArrayList<Pokemon> pokemonAvailable = Deck.getPokemonObjects(deck.getPokemonNames(), deck.getPokemonData());
    public static ArrayList<Pokemon> selectedPokemon  = new ArrayList<>();

    public static String name, gameCode, uuid, host;
    public static int port;

    public static void main(String[] args){
        Interactive.delayType("\033[H\033[2J");

        Interactive.introScreen();
        Interactive.delayType("What is your name?: ");
        name = stdin.nextLine();

        Interactive.choosePokemon();

        selectedPokemon.add(deck.getPokemonObject("Pikachu"));
        selectedPokemon.add(deck.getPokemonObject("Lapras"));
        selectedPokemon.add(deck.getPokemonObject("Jolteon"));
        selectedPokemon.add(deck.getPokemonObject("Flareon"));

        Interactive.delayTypeln(String.format("║ %-15s ║ %-9s ║ %-15s ║", "Name", "HP", "Type"));
        for (Pokemon poke : selectedPokemon) {
           Interactive.delayTypeln(1, poke.toStringSimple());
        }

        while (true) {
            // Mode select
            switch (Interactive.singleSelectMenu(String.format("Welcome to the Pokemon universe %s!\nHow would you like to battle?", name), "Select Gamemode", new String[]{"1 vs Computer", "1 vs 1 [WAN Multiplayer]", "1 vs 1 [LAN Multiplayer]", "Credits","Exit"})) {
                case 1:
                    singleplayer();
                    break;
                case 2:
                    multiplayer();
                    break;
                case 3:
                    while (true) {

                        Interactive.clearConsole();
                        Interactive.delayTypeln("Enter the address and port of the RASTERA LAN server");
                        
                        try {
                            Interactive.delayType("Host: ");
                            host = stdin.nextLine();

                            Interactive.delayType("Port: ");
                            port = Integer.parseInt(stdin.nextLine());

                            break;
                        }
                        catch (Exception e) {
                            Interactive.confirmBoxClear("Error: Invalid Entry");
                        }
                    }

                    multiplayer(host, port);

                    break;
                case 4:
                    Interactive.credits();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    public static void singleplayer() {
        Interactive.delayTypeln("Single player");
    }

    public static void multiplayer() {
        multiplayer("127.0.0.1", 3162);
    }

    public static void multiplayer(String host, int port) {

        Communicator connector = new Communicator(host, port);

        while (connector.isAlive) {
            switch (Interactive.singleSelectMenu(String.format("Welcome %s", name), "RASTERA WAN MULTIPLAYER", new String[]{"Generate Game Code", "Join Game Room", "Debug console", "Exit WAN MULTIPLAYER"})) {
                case 1:
                    gameCode = Operations.genCode(connector);

                    if (!gameCode.equals("-1")) {
                        Interactive.confirmBoxClear(String.format("Code Successfully Generated: %s\n" +
                                                                  "Ensure both players enter the EXACT SAME\n" +
                                                                  "CODE to join the game. (Case sensitive)", gameCode));

                        if (Interactive.booleanSelectMenu(String.format("Join this game? <%s> [Y/n]", gameCode))) {
                            Operations.joinGame(connector);
                            break;
                        } else {
                            break;
                        }
                    }
                    else {
                        Interactive.confirmBoxClear("Error: Unable to connect to server");
                    }

                case 2:
                    Interactive.clearConsole();
                    Interactive.delayType("[Enter Game Code]> ");
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