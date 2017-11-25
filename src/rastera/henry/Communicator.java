// RASTERA SOCKET COMMUNICATOR
// COPYRIGHT 2017 (C) RASTERA DEVELOPMENT
// rastera.xyz
// DEVELOPED BY HENRY TU

// Communicator.java

package rastera.henry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Communicator {
    private Socket socketConnection;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;
    String inData;

    public Communicator(String host, int port) {
        try {
            socketConnection = new Socket(host, port);
            inFromServer = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            outToServer = new PrintWriter(socketConnection.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public String[] get(String data) {
        try {
            System.out.println("Communicating...");
            outToServer.write(data);
            outToServer.flush();

            inData = inFromServer.readLine();

            if (inData != null) {
                return inData.split(" // ");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return new String[]{};
    }
}