package rastera.henry;

import java.util.ArrayList;
import java.util.Scanner;

public class Interactive {

    public static Scanner stdin = new Scanner(System.in);

    public static int singleSelectMenu(String caption, String[] options) {

        int selection;

        while (true) {

            //System.out.print("\033[H\033[2J");

            System.out.println("╔════════════════════════════════╗");
            System.out.println(String.format("║ %-30s ║", caption));
            System.out.println("╠════╦═══════════════════════════╣");

            for(int i = 0; i < options.length; i++) {
                System.out.println(String.format("║ %-2d ║ %-25s ║", i + 1, options[i]));
            }

            System.out.println("╚════╩═══════════════════════════╝");
            System.out.print("[Enter Selection]> ");

            try {
                selection = stdin.nextInt();

                if (selection > 0 && selection <= options.length) {
                    return selection;
                }
                else {
                    System.out.println(String.format("\n\nError: Please enter a valid item from the list <%d-%d>", 1, options.length));
                }
            }
            catch (Exception e) {
                stdin.nextLine();
                System.out.println(String.format("\n\nError: Please enter a valid item from the list <%d-%d>", 1, options.length));
            }

        }

    }

    public static boolean booleanSelectMenu(String caption) {
        while (true) {

            System.out.println(caption);
            System.out.print("[Enter Selection]> ");

            try {
                char selection = stdin.next().toLowerCase().charAt(0);
                if (selection == 'y' || selection == 'n') {
                    return selection == 'y';
                }
                else {
                    System.out.println("\nError: Please enter a valid choice [Y/n]");
                }
            }
            catch (Exception e) {
                System.out.println("\nError: Please enter a valid choice [Y/n]");
                stdin.nextLine();
            }
        }

    }
}
