package com.company;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static void main(String[] args){
        multiplayer();
    }

    public static void multiplayer() {
        // Socket server
        Communicator connector = new Communicator("127.0.0.1", 3149);

        // Client UUID
        String uuid = connector.get("0 // ")[1];
        System.out.println("UUID: " + initData);
        System.out.println(connector.get("1 // " + uuid + " // Hdenry // Pikachu\n")[1]);
    }
}