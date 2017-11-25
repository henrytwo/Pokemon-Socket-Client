// Main.java

package rastera.henry;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args){
        int gameMode;

        // Mode select
        switch (Interactive.singleSelectMenu("Select Gamemode", new String[] {"1 vs Computer", "1 vs 1 [WAN Multiplayer]"})) {
            case 1:
                singleplayer();
            case 2:
                multiplayer();
        }

    }

    public static void singleplayer() {

    }

    public static void multiplayer() {
        // Socket server
        Communicator connector = new Communicator("127.0.0.1", 3160);

        // Client UUID
        while (true) {
            System.out.print("> ");
            String[] data = connector.get(stdin.nextLine());
            for (String line : data) {
                System.out.println(" |" + line);
            }
        }
    }
}