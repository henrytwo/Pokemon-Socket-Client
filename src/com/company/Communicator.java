package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Communicator {
        private String host;
        private int port;
        private Socket socketConnection;
        BufferedReader inFromServer;
        PrintWriter outToServer;
        String inData;

        public Communicator(String ihost, int iport) {
            host = ihost;
            port = iport;
        }

        public String[] get(String data) {
            try {
                socketConnection = new Socket(host, port);
                inFromServer = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
                outToServer = new PrintWriter(socketConnection.getOutputStream(), true);

                System.out.println("Communicating...");
                outToServer.write(data);
                outToServer.flush();

                inData = inFromServer.readLine();
                if (inData != null) {
                    return inData.split(" // ");
                }

                socketConnection.close();
            } catch (IOException e) {
                System.out.println("Shit it broke");
                System.out.println(e.getMessage());
                System.exit(0);
            }
            return new String[]{};
        }
}