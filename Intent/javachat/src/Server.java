/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


    // The server socket.
    public static ServerSocket serverSocket = null;
    // The client socket.
    public static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    public static final int maxClientsCount = 10;
    public static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {

        // The default port number.
        int portNumber = 2223;
        if (args.length < 1) {
            System.out
                    .println("Connection established");
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }


        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

	/*
 	* Create a client socket for each connection and pass it to a new client
 	* thread.
 	*/
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}


class clientThread extends Thread {

    public DataInputStream rin;
    public PrintStream os;
    public Socket clientSocket;
    public final clientThread[] threads;
    public int maxClientsCount;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
  	/*
   	* Create input and output streams for this client.
   	*/
            rin = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            os.println("Enter your name.");
            String name = rin.readLine().trim();
            os.println("Hello " + name  + " To quit type /exit");
            while (true) {
                String line = rin.readLine();
                if (line.startsWith("/exit")) {
                    break;
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
                        threads[i].os.println( name + ":" + line);
                    }
                }
            }

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

  	/*
   	* Close the output stream, close the input stream, close the socket.
   	*/
            rin.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}

