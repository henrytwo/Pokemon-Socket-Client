/**
 * Pokemon Arena
 * Main.java
 *
 * Main project class
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

import java.util.*;

public class Main {

    // Contains all Pokemon data and config
    public static Deck deck                           = new Deck("data/pokemon_data.txt");

    public static Scanner stdin                       = new Scanner(System.in);
    public static ArrayList<Pokemon> allPokemon       = Deck.getPokemonObjects(deck.getPokemonNames(), deck.getPokemonData());
    public static ArrayList<Pokemon> pokemonAvailable = Utilities.deepCopy(allPokemon);
    public static ArrayList<Pokemon> selectedPokemon  = new ArrayList<>();

    public static String name, gameCode, uuid, host;
    public static int port;

    /**
     * Main project method
     * Everything else is called from here
     *
     * @param args             Voodoo OS args
     */
    public static void main(String[] args){

        // Initial sequence
        Interactive.introScreen();
        Interactive.choosePokemon();

        // Menu loop
        while (true) {
            // Mode select
            switch (Interactive.singleSelectMenu(String.format("Welcome to the Pokemon universe %s!\nHow would you like to battle?", name), "Select Gamemode", new String[]{"1 vs Computer", "1 vs 1 [WAN Multiplayer]", "1 vs 1 [LAN Multiplayer]", "Change Pokemon Selection", "Credits", "Exit"})) {
                case 1:
                    // Default singleplayer
                    singleplayer();
                    break;
                case 2:
                    // Default multiplayer
                    multiplayer();
                    break;
                case 3:
                    // Gets custom server address
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
                    // Change Pokemon Selection
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
                    // Credit Screen
                    Interactive.credits();
                    break;
                case 6:
                    // Exit Game
                    System.exit(0);
            }
        }
    }

    /**
     * Creates opponent (CPU) and battle (HUMAN) objects and feeds into game engine
     */
    private static void singleplayer() {
        Opponent opponent = new Opponent();
        Player player = new Player();

        Engine engine = new Engine(player, opponent, selectedPokemon, name);
    }

    /**
     * Helper method to set default multiplayer address
     */
    private static void multiplayer() {
        multiplayer("development.rastera.xyz", 1337);
    }

    /**
     * Creates Communicator object and
     * Connects to remote POKEMON server
     *
     * @param host           String of server ip address
     * @param port           Integer of server port
     */
    private static void multiplayer(String host, int port) {

        Communicator connector = new Communicator(name, host, port);

        while (connector.isAlive) {
            // Multiplayer menu
            switch (Interactive.singleSelectMenu(String.format("Welcome %s\nConnected to server [%s:%d]", name, host, port), "RASTERA LAN MULTIPLAYER", new String[]{"Generate Game Code", "Join Game Room", "Exit WAN MULTIPLAYER"})) {
                case 1:
                    // Generate gamecode
                    gameCode = Operations.genCode(connector);

                    // Checks if code was successfully generated
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
                    // Join Game
                    Interactive.clearConsole();
                    Interactive.delayType("[Enter Game Code]> ");
                    gameCode = stdin.next();

                    setupGame(connector);

                    break;
                case  3:
                    // Return to main menu
                    return;
            }
        }
    }

    /**
     * Creates Player object (HUMAN) and LocalEngine (REMOTE ENGINE) object
     * LocalEngine is essentially a bridge between the game server and local battle
     *
     * @param connector        Communicator object for commucation with game server
     */
    private static void setupGame(Communicator connector) {
        if (Operations.joinGame(connector)) {
            Player player = new Player();
            LocalEngine engine = new LocalEngine(connector, Main.gameCode, Main.uuid, player, Main.selectedPokemon, name);
        }
    }
}