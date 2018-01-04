/**
 * Pokemon Arena
 * Interactive.java
 *
 * Class containing all interactive components
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Interactive {

    public static Scanner stdin = new Scanner(System.in);

    /**
     * Makes first letter of String Uppercase
     *
     *  @param word       String with initial word to be capitalized
     */
    public static String correctCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * Helper method for singleSelectMenu
     * Generates numerical menu based on String[] of options
     *
     * @param precaption   Caption displayed before menu
     * @param caption      Main Caption displayed on menu
     * @param options      String array with options
     * @return             Integer with array index of selected item
     */
    public static int singleSelectMenu(String precaption, String caption, String[] options) {
        return singleSelectMenu(precaption, caption, options,true);
    }


    /**
     * Helper method for singleSelectMenu
     * Generates numerical menu based on String[] of options
     *
     * @param caption      Main Caption displayed on menu
     * @param options      String array with options
     * @param clear        Whether to clear screen before menu is displayed
     * @return             Integer with array index of selected item
     */
    public static int singleSelectMenu(String caption, String[] options, boolean clear) {
        return singleSelectMenu("", caption, options, clear);
    }

    /**
     * Helper method for singleSelectMenu
     * Generates numerical menu based on String[] of options
     *
     * @param caption      Main Caption displayed on menu
     * @param options      String array with options
     * @return             Integer with array index of selected item
     */
    public static int singleSelectMenu(String caption, String[] options) {
        return singleSelectMenu("", caption, options, true);
    }

    /**
     * Displays a menu with n items
     * and returns the index of
     * selected item. Verifies that
     * selection is found on menu.
     *
     * @param preCaption
     * @param caption      String to be used as the title
     * @param options      String Array with all possible options order of items is maintained with index
     * @param clear        Whether to clear screen before menu is displayed
     * @return             Integer with array index of selected item
     */
    public static int singleSelectMenu(String preCaption, String caption, String[] options, boolean clear) {

        int selection;

        while (true) {

            if (clear) {
                clearConsole();
            }

            if (preCaption.length() > 0) {
                delayTypeln(preCaption);
            }

            /*
             * Displays table with options
             * along with corresponding value
             */
            delayTypeln(3,"╔═════════════════════════════════════════════════════════╗");
            delayTypeln(3,String.format("║ %-55s ║", caption));
            delayTypeln(3,"╠════╦════════════════════════════════════════════════════╣");

            for (int i = 0; i < options.length; i++) {
                delayTypeln(3,String.format("║ %-2d ║ %-50s ║", i + 1, options[i]));
            }

            delayTypeln(3,"╚════╩════════════════════════════════════════════════════╝");
            delayType("[Enter Selection]> ");

            /*
             * Reads from Scanner to get
             * Input specified by user and
             * validates that item
             * is found on menu.
             */
            try {
                selection = stdin.nextInt();

                if (selection > 0 && selection <= options.length) {
                    return selection;
                } else {
                    confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, options.length));
                }
            } catch (Exception e) {
                stdin.nextLine();
                confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, options.length));
            }

        }

    }

    /**
     * Displays a caption and presents a [Y/n] prompt
     *
     * @param caption      String to be used as Main Caption
     * @return             Boolean response to caption
     */
    public static boolean booleanSelectMenu(String caption) {
        while (true) {

            delayTypeln(caption);
            delayType("[Enter Selection]> ");

            try {
                char selection = stdin.next().toLowerCase().charAt(0);
                if (selection == 'y' || selection == 'n') {
                    return selection == 'y';
                } else {
                    confirmBoxClear("Error: Please enter a valid choice [Y/n]");
                }
            } catch (Exception e) {
                confirmBoxClear("Error: Please enter a valid choice [Y/n]");
                stdin.nextLine();
            }
        }
    }

    /**
     * Helper Method for confirmBox
     * Clears Screen before prompt
     *
     * @param message      String with message
     */
    public static void confirmBoxClear(String message) {
        clearConsole();
        confirmBox(message);
    }

    /**
     * Displays message and prompts for confirmation [Enter]
     *
     * @param message      String with message
     */
    public static void confirmBox(String message) {
        delayTypeln(message);
        delayTypeln("\nPress [Enter] to continue");
        try {
            System.in.read();
        }
        catch (IOException e) {

        }
        clearConsole();
    }

    /**
     * Clears the console by calling some magic woodoo
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * Helper Method to delayType
     * Adds newline character and default delay of 10ms
     *
     * @param line         String to be typed
     */
    public static void delayTypeln(String line) {
        delayType(10, line + "\n");
    }

    /**
     * Helper Method to delayType
     * Adds default delay of 10ms
     *
     * @param line         String to be typed
     */
    public static void delayType(String line) {
        delayType(10, line);
    }

    /**
     * Helper Method to delayType
     * Adds newline character
     *
     * @param time         Integer with delay between chars (ms)
     * @param line         String to be typed
     */
    public static void delayTypeln(long time, String line) {
        delayType(time, line + "\n");
    }

    /**
     * Delays printing String to console by specified time
     *
     * @param time         Integer with delay between chars (ms)
     * @param line         String to be typed
     */
    public static void delayType(long time, String line) {
        char[] charArray = (line.replaceAll("null", "")).toCharArray();

        for (char character : charArray) {
            try {
                if (character != ' ') {
                    Thread.sleep(time);
                }
                System.out.print(character);
            } catch (Exception e) {

            }
        }
    }

    /**
     * Delayed printing, line by line
     * With default delay of 5ms
     *
     * @param line         String to be typed
     */
    public static void delayln(String line) {
        delayln(5, line);
    }

    /**
     * Delayed printing, line by line
     *
     * @param time         Integer with delay between lines (ms)
     * @param line         String to be typed
     */
    public static void delayln(long time, String line) {
        try {
            String[] lines = line.replaceAll("null", "").split("\n");
            for (String ln : lines) {
                if (ln.length() > 0) {
                    Thread.sleep(time);
                }
                System.out.println(ln);
            }
        } catch (Exception e) {}
    }

    /**
     * Game intro sequence
     * Displays logo and initial dialog with Professor Henning
     */
    public static void introScreen() {
        String data;
        clearConsole();

        // Reads Pokemon logo from file
        try {
            BufferedReader pokemonLogoFile = new BufferedReader(new FileReader(new File("ascii/pokemonlogo.txt")));

            while (true){
                data = pokemonLogoFile.readLine();
                if (data != null) {
                    delayTypeln(1, data);
                }
                else {
                    break;
                }
            }

        }
        catch (IOException e) {
            delayTypeln("Error: FileIO Error");
        }

        confirmBox("\nWelcome to Pokemon Arena!\n\n" +
                   "Developed by: Henry Tu\n" +
                   "github.com/henrytwo\n" +
                   "henrytu.me\n" +
                   "Winter 2017 ICS4U\n\n" +
                   "Data file courtesy of Aaron Li [github.com/dumfing]");

        clearConsole();

        confirmBoxClear("Henning: Hello there! Welcome to the world of POKEMON! My name is HENNING!\n" +
                        "         People call me the POKEMON PROF!");

        clearConsole();

        for (Pokemon pokemon : Main.pokemonAvailable) {
            if (pokemon.getName().toLowerCase().equals("nidorino")) {
                delayln(15, pokemon.getAscii());
                break;
            }
        }

        confirmBox("Henning: This world is inhabited by creatures called POKEMON! For some\n" +
                   "         people, POKEMON are pets. Others use them for fights. Myself...\n" +
                   "         I study POKEMON as a profession.");


        delayType("Henning: First, What is your name?: ");
        Main.name = stdin.nextLine();

        if (Main.name.length() < 1) {
            Main.name = "Pokemon Trainer";
        }

        confirmBoxClear(String.format("Henning: Right! So your name is %s!", Main.name));
        confirmBoxClear("Henning: Your very own POKEMON legend is about to unfold! A world of dreams and adventures with POKEMON awaits! Let's go!");

    }

    /**
     * Initial Pokemon selection screen
     */
    public static void choosePokemon() {

        confirmBoxClear("Henning: It's time for you to pick your Pokemon!");

        pokemonPicker();

        clearConsole();

        delayTypeln("Henning: Excellent! You have selected your 6 Pokemon!\n" +
                "What a great start on your journey to master Pokemon!\n");
        displayPokemonCards(Main.selectedPokemon);

        confirmBox("");
    }

    /**
     * End game screen
     *
     * @param victory      Boolean with outcome of the game (Player perspective)
     */
    public static void winScreen(boolean victory) {
        clearConsole();

        if (victory) {
            delayTypeln("YOU WIN!");
        }
        else {
            delayTypeln("YOU LOST!");
        }

        confirmBox("");
    }

    /**
     * Credit Screen
     */
    public static void credits() {
        confirmBoxClear("Developed by: Henry Tu\n" +
                "github.com/henrytwo\n" +
                "henrytu.me\n" +
                "Winter 2017 ICS4U\n\n" +
                "Data file courtesy of Aaron Li [github.com/dumfing]");
    }

    /**
     * Formats Pokemon cards in 3 columns
     *
     * @param pokemonAvailable ArrayList of Pokemon to be displayed
     */
    public static void displayPokemonCards(ArrayList<Pokemon> pokemonAvailable) {
        String line;

        for (int i = 0; i < pokemonAvailable.size(); i += 3) {

            ArrayList<String[]> cardArray = new ArrayList<>();

            cardArray.add(pokemonAvailable.get(i).toCard(i).split("\n"));

            if (i + 1 < pokemonAvailable.size()) {
                cardArray.add(pokemonAvailable.get(i + 1).toCard(i + 1).split("\n"));
            }

            if (i + 2 < pokemonAvailable.size()) {
                cardArray.add(pokemonAvailable.get(i + 2).toCard(i + 2).split("\n"));
            }

            // Stacks 3 cards side by side
            for (int y = 0; y < 8; y++) {

                line = "";

                for (String[] card : cardArray) {
                    line += (line.length() > 0) ? " " + card[y] : card[y];
                }

                delayln(3, line);
            }
        }
    }

    /**
     * Pokemon Selection sequence
     * Writes output directly to global Pokemon records
     */
    public static void pokemonPicker() {
        int selection;
        String entry;
        boolean found, selected;

        for (int round = 0; round < Deck.NUMPOKEMON; round++) {
            while (true) {

                clearConsole();

                delayTypeln(String.format("Choosing Pokemon [%d/6]", round + 1));
                delayTypeln("Enter Pokemon ID to make selection");

                displayPokemonCards(Main.pokemonAvailable);

                delayTypeln(String.format("Choosing Pokemon [%d/6]", round + 1));
                delayTypeln("Enter Pokemon ID to make selection");
                delayType("[Enter Selection or Name]> ");

                try {
                    entry = stdin.next();

                    // Numerical Input
                    if (entry.matches("^[0-9]+$")) {

                        selection = Integer.parseInt(entry) - 1;

                        // Standard Numerical Input
                        if (selection >= 0 && selection <= Main.pokemonAvailable.size()) {

                            clearConsole();

                            delayln(15, Main.pokemonAvailable.get(selection).getAscii());
                            delayln(15, Main.pokemonAvailable.get(selection).toCard(-1));

                            if (booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", Main.pokemonAvailable.get(selection).getName()))) {
                                Main.selectedPokemon.add(Main.pokemonAvailable.get(selection));
                                Main.pokemonAvailable.remove(selection);
                                break;
                            }

                        }
                        // Automatic Selection
                        else if (selection == 665) {
                            clearConsole();
                            delayTypeln("Automatic Pokemon Selection");

                            // Randomly picks pokemon
                            ArrayList<Pokemon> pokemonAvailable = Utilities.deepCopy(Main.pokemonAvailable);
                            Collections.shuffle(pokemonAvailable);

                            int left = Deck.NUMPOKEMON - Main.selectedPokemon.size();

                            for (int i = 0; i < left; i++) {
                                delayTypeln(String.format("Selected %s", pokemonAvailable.get(i).getName()));
                                Main.selectedPokemon.add(pokemonAvailable.get(i));
                                Main.pokemonAvailable.remove(Main.pokemonAvailable.indexOf(pokemonAvailable.get(i)));
                            }

                            return;
                        } else {
                            confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, Main.pokemonAvailable.size()));
                        }
                    }

                    // Name based input
                    else {
                        found = false;
                        selected = false;

                        // Searches for Pokemon Object matching name
                        for (Pokemon pokemon : Main.pokemonAvailable) {
                            if (pokemon.getName().toLowerCase().equals(entry.toLowerCase())) {

                                found = true;
                                clearConsole();

                                if (booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", pokemon.getName()))) {
                                    Main.selectedPokemon.add(pokemon);
                                    Main.pokemonAvailable.remove(pokemon);
                                    selected = true;
                                    break;
                                }
                            }
                        }

                        if (selected) {
                            break;
                        }

                        if (!found) {
                            confirmBoxClear("Error: Please enter a valid Pokemon name!");
                        }
                    }
                } catch (Exception e) {
                    stdin.nextLine();
                    confirmBoxClear(String.format("Error: Please enter a valid item from the list <%d-%d>", 1, Main.pokemonAvailable.size()));
                }
            }
        }
    }

}
