// Main.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Deck deck                           = new Deck("data/pokemon_data.txt");
    public static Scanner stdin                       = new Scanner(System.in);
    public static ArrayList<Pokemon> allPokemon       = Deck.getPokemonObjects(deck.getPokemonNames(), deck.getPokemonData());
    public static ArrayList<Pokemon> pokemonAvailable = Utilities.deepCopy(allPokemon);
    public static ArrayList<Pokemon> selectedPokemon  = new ArrayList<>();

    public static String name, gameCode, uuid, host;
    public static int port;

    public static void main(String[] args){
        Interactive.introScreen();
        Interactive.choosePokemon();

        while (true) {
            // Mode select
            switch (Interactive.singleSelectMenu(String.format("Welcome to the Pokemon universe %s!\nHow would you like to battle?", name), "Select Gamemode", new String[]{"1 vs Computer", "1 vs 1 [WAN Multiplayer]", "1 vs 1 [LAN Multiplayer]", "Change Pokemon Selection", "Credits", "Exit"})) {
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
                    Interactive.clearConsole();

                    if (Interactive.booleanSelectMenu("Are you sure you want to change Pokemon selection? [Y/n]")) {

                        pokemonAvailable = Deck.getPokemonObjects(deck.getPokemonNames(), deck.getPokemonData());
                        selectedPokemon  = new ArrayList<>();

                        Interactive.pokemonPicker();

                        Interactive.clearConsole();

                        Interactive.delayTypeln("Pokemon selection has been changed successfully\n");
                        Interactive.displayPokemonCards(Main.selectedPokemon);

                        Interactive.confirmBox("");
                    }

                    break;
                case 5:
                    Interactive.credits();
                    break;
                case 6:
                    System.exit(0);
            }
        }
    }

    public static void singleplayer() {
        Opponent opponent = new Opponent();
        Battle battle = new Battle();

        Engine engine = new Engine(battle, opponent, selectedPokemon, name, opponent.deck, opponent.getName());
    }

    public static void multiplayer() {
        multiplayer("development.rastera.xyz", 3001);
    }

    public static void multiplayer(String host, int port) {

        Communicator connector = new Communicator(name, host, port);

        while (connector.isAlive) {
            switch (Interactive.singleSelectMenu(String.format("Welcome %s\nConnected to server [%s:%d]", name, host, port), "RASTERA LAN MULTIPLAYER", new String[]{"Generate Game Code", "Join Game Room", "Debug console", "Exit WAN MULTIPLAYER"})) {
                case 1:
                    gameCode = Operations.genCode(connector);

                    if (!gameCode.equals("-1")) {
                        Interactive.confirmBoxClear(String.format("Code Successfully Generated: %s\n" +
                                                                  "Ensure both players enter the EXACT SAME\n" +
                                                                  "CODE to join the game. (Case sensitive)", gameCode));

                        if (Interactive.booleanSelectMenu(String.format("Join this game? <%s> [Y/n]", gameCode))) {
                            setupGame(connector);
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

                    setupGame(connector);

                    break;
                case 3:
                    Operations.debugConsole(connector);
                    break;
                case  4:
                    return;
            }
        }
    }

    public static void setupGame(Communicator connector) {
        if (Operations.joinGame(connector)) {
            Battle battle = new Battle();
            LocalEngine engine = new LocalEngine(connector, Main.gameCode, Main.uuid, battle, Main.selectedPokemon, name);
        }
    }
}