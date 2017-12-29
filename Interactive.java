import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.Delayed;

public class Interactive {

    public static Scanner stdin = new Scanner(System.in);

    /* Makes first letter of String
    *  Uppercase
    *
    *  @param word     String with initial word to be capitalized
    */
    public static String correctCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


    public static int singleSelectMenu(String precaption, String caption, String[] options) {
        return singleSelectMenu(precaption, caption, options,true);
    }

    public static int singleSelectMenu(String caption, String[] options, boolean clear) {
        return singleSelectMenu("", caption, options, clear);
    }

    public static int singleSelectMenu(String caption, String[] options) {
        return singleSelectMenu("", caption, options, true);
    }

    /* Displays a menu with n items
    *  and returns the index of
    *  selected item. Verifies that
    *  selection is found on menu.
    *
    *  @param caption     String to be used as the title
    *  @param options     String Array with all possible options
    *                     order of items is maintained with index
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

            /* Displays table with options
            *  along with corresponding value
            */
            delayTypeln(3,"╔═════════════════════════════════════════════════════════╗");
            delayTypeln(3,String.format("║ %-55s ║", caption));
            delayTypeln(3,"╠════╦════════════════════════════════════════════════════╣");

            for (int i = 0; i < options.length; i++) {
                delayTypeln(3,String.format("║ %-2d ║ %-50s ║", i + 1, options[i]));
            }

            delayTypeln(3,"╚════╩════════════════════════════════════════════════════╝");
            delayType("[Enter Selection]> ");

            /* Reads from Scanner to get
            *  Input specified by user and
            *  validates that item
            *  is found on menu.
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

    public static void confirmBoxClear(String message) {
        clearConsole();
        confirmBox(message);
    }

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

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    public static void delayTypeln(String line) {
        delayType(10, line + "\n");
    }

    public static void delayType(String line) {
        delayType(10, line);
    }

    public static void delayTypeln(long time, String line) {
        delayType(time, line + "\n");
    }

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

    public static void delayln(String line) {
        delayln(5, line);
    }

    public static void delayln(long time, String line) {
        try {
            if (line.length() > 0) {
                Thread.sleep(time);
            }
            System.out.println(line);
        } catch (Exception e) {

        }
    }

    public static void introScreen() {
        String data;
        clearConsole();

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
        delayType("What is your name?: ");
        Main.name = stdin.nextLine();

        if (Main.name.length() < 1) {
            Main.name = "Pokemon Trainer";
        }

    }

    public static void credits() {
        confirmBoxClear("Developed by: Henry Tu\n" +
                "github.com/henrytwo\n" +
                "henrytu.me\n" +
                "Winter 2017 ICS4U\n\n" +
                "Data file courtesy of Aaron Li [github.com/dumfing]");
    }

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

            for (int y = 0; y < 8; y++) {

                line = "";

                for (String[] card : cardArray) {
                    line += (line.length() > 0) ? " " + card[y] : card[y];
                }

                delayln(3, line);
            }
        }
    }

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
                    if (entry.matches("^[0-9]+$")) {

                        selection = Integer.parseInt(entry) - 1;

                        if (selection >= 0 && selection <= Main.pokemonAvailable.size()) {

                            clearConsole();

                            if (booleanSelectMenu(String.format("Are you sure you want to select %s [Y/n]?", Main.pokemonAvailable.get(selection).getName()))) {
                                Main.selectedPokemon.add(Main.pokemonAvailable.get(selection));
                                Main.pokemonAvailable.remove(selection);
                                break;
                            }

                        } else if (selection == 665) {
                            clearConsole();
                            delayTypeln("Automatic Pokemon Selection");

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
                    else {
                        found = false;
                        selected = false;

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

    public static void choosePokemon() {
        confirmBoxClear(String.format("Professor: Hello there %s! Welcome to the world of POKEMON! My name is Professor Henguin Jiang!", Main.name));
        confirmBoxClear("Professor: I see that you have begun your\njourney to become a Pokemon master!\nBefore you can battle, you must choose your Pokemon!");

        pokemonPicker();

        clearConsole();

        delayTypeln("Professor: Excellent! You have selected your 6 Pokemon!\n" +
                "What a great start on your journey to master Pokemon!\n");
        displayPokemonCards(Main.selectedPokemon);

        confirmBox("");
    }

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
}
