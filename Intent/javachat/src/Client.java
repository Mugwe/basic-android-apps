
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

    // The client socket
    public static Socket clientSocket = null;
    // The output stream
    public static PrintStream os = null;
    // The input stream
    public static DataInputStream is = null;

    public static BufferedReader inputLine = null;
    public static boolean closed = false;

    public static void main(String[] args) {

        // The default port.
        int portNumber = 2223;
        // The default host.
        String host = "localhost";

        if (args.length < 2) {
            System.out
                    .println("Now connected to host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();
        }

	/*
 	* Open a socket on a given host and port. Open input and output streams.
 	*/
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }


        if (clientSocket != null && os != null && is != null) {
            try {

                // Create a thread to read from the server. //
                new Thread(new Client()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }

                // Close the output stream, close the input stream, close the socket.//

                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }


    //Create a thread to read from the server. (non-Javadoc)//

    public void run() {
	/*
 	* Keep on reading from the socket till we receive "Bye" from the
 	* server. Once we received that then we want to break.
 	*/
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.startsWith("/exit"))
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}

