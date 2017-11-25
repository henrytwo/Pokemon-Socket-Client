package rastera.henry;

import java.util.ArrayList;
import java.util.Scanner;

public class Interactive {

    public static Scanner stdin = new Scanner(System.in);

    private static int selection;

    public static int singleSelectMenu(String caption, String[] options) {

        while (true) {

            System.out.println("╔════════════════════════════════╗");
            System.out.println(String.format("║ %30s ║", caption));
            System.out.println("╠════╦═══════════════════════════╣");

            for(int i = 0; i < options.length; i++) {
                System.out.println(String.format("║ %2d ║ %25s ║", i + 1, options[i]));
            }

            System.out.println("╚════╩═══════════════════════════╝");
            System.out.print("[Enter Selection] > ");

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
}
