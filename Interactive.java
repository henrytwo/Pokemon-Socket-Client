import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static int singleSelectMenu(String caption, String[] options) {
        return singleSelectMenu("", caption, options);
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
    public static int singleSelectMenu(String preCaption, String caption, String[] options) {

        int selection;

        while (true) {

            clearConsole();

            if (preCaption.length() > 0) {
                delayTypeln(preCaption);
            }

            /* Displays table with options
            *  along with corresponding value
            */
            delayTypeln(5,"╔════════════════════════════════╗");
            delayTypeln(5,String.format("║ %-30s ║", caption));
            delayTypeln(5,"╠════╦═══════════════════════════╣");

            for (int i = 0; i < options.length; i++) {
                delayTypeln(5,String.format("║ %-2d ║ %-25s ║", i + 1, options[i]));
            }

            delayTypeln(5,"╚════╩═══════════════════════════╝");
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
        delayType(20, line + "\n");
    }

    public static void delayType(String line) {
        delayType(20, line);
    }

    public static void delayTypeln(int time, String line) {
        delayType(time, line + "\n");
    }

    public static void delayType(int time, String line) {
        char[] charArray = (line).toCharArray();

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

    public static void introScreen() {
        // Do the nice intro stuff
        clearConsole();
        delayTypeln("Pokemon stuff goes here");
    }

}
