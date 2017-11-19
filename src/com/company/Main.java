// Main.java

package com.company;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args){
        multiplayer();
    }

    public static void multiplayer() {
        // Socket server
        Communicator connector = new Communicator("127.0.0.1", 3157);

        // Client UUID
        while (true) {
            System.out.print("> ");
            String[] data = connector.get(stdin.nextLine());
            for (String line : data) {
                System.out.println(line);
            }
        }
    }
}