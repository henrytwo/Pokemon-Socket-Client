// RASTERA SOCKET COMMUNICATOR
// COPYRIGHT 2017 (C) RASTERA DEVELOPMENT
// rastera.xyz
// DEVELOPED BY HENRY TU

// Communicator.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Communicator {
    private Socket socketConnection;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;
    private String inData;
    public  boolean isAlive = true;

    /* Constructor for Communicator class.
    *  Establishes TCP connection and
    *  verifies connection with server.
    *
    *  @param host     String of IP Address for Socket to bind to
    *  @param port     Integer of Port at Host for socket to bind to
    */
    public Communicator(String host, int port) {
        try {
            socketConnection = new Socket();
            socketConnection.connect(new InetSocketAddress(host, port), 5000);

            inFromServer     = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            outToServer      = new PrintWriter(socketConnection.getOutputStream(), true);

            String[] verificationResponse = this.get("3000 // ");

            /* Sends CODE 3000 to server to verify
            *  that connection is to proper game
            *  server. (It's possible to connect
            *  to any TCP server successfully)
            */
            if (verificationResponse.length == 2 && verificationResponse[1].equals("DOCTYPE!")) {
                Interactive.confirmBoxClear(String.format("Successfully connected to server at: %s:%d", host, port));
            }
            else  {
                isAlive = false;
                Interactive.confirmBoxClear(String.format("Unable to connect to: %s:%d", host, port));
                socketConnection.close();
            }
        } catch (IOException e) {
            isAlive = false;
            e.printStackTrace();
            Interactive.confirmBoxClear(String.format("Unable to connect to: %s:%d", host, port));
        }
    }

    /* Sends string with encoded data to
    *  Socket server and splits returned
    *  data as String Array.
    *
    *  @param data String of data to be sent
    */
    public String[] get(String data) {
        return get(true, data);
    }
    public String[] get(boolean verbose, String data) {

        /* Verifies that Socket is still active
        *  before sending data to avoid crashes.
        */
        if (isAlive) {
            try {
                if (verbose) {
                    Interactive.clearConsole();
                    Interactive.delayTypeln("Communicating...");
                }

                outToServer.write(data);
                outToServer.flush();

                inData = inFromServer.readLine();

                if (inData != null) {
                    return inData.split(" // ");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new String[]{"-1", "Error: Unable to Connect"};
    }
}