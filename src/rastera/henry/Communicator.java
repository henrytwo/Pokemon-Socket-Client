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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Communicator {
    private Socket socketConnection;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;
    boolean isAlive = true;
    String inData;

    public Communicator(String host, int port) {
        try {
            socketConnection = new Socket();
            socketConnection.connect(new InetSocketAddress(host, port), 5000);


            inFromServer = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            outToServer = new PrintWriter(socketConnection.getOutputStream(), true);

            String[] verificationResponse = this.get("3000 // ");

            if (verificationResponse.length == 2 && verificationResponse[1].equals("DOCTYPE!")) {
                System.out.println(String.format("\nSuccessfully connected to server at: %s:%d", host, port));
            }
            else  {
                isAlive = false;
                System.out.println(String.format("\nUnable to connect to: %s:%d", host, port));
                socketConnection.close();
            }

        } catch (IOException e) {
            isAlive = false;
            System.out.println(String.format("\nUnable to connect to: %s:%d", host, port));
        }
    }

    public String[] get(String data) {
        if (isAlive) {
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
            }
        }
        return new String[]{"-1", "Error: Unable to Connect"};
    }
}