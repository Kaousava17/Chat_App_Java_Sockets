package com.ensea;

import java.io.*;
import java.net.*;

public class TCPMultiServer {
    public static void main(String[] args) throws IOException {

        int portNumber = 5000;

        // Create a server socket to listen for client connections
        ServerSocket serverSocket = new ServerSocket(portNumber);


        while (true) {

            Socket clientSocket = serverSocket.accept();
            InetAddress clientIP = clientSocket.getInetAddress();
            Thread thread = new Thread(new ClientHandler(clientSocket, clientIP));
            // Start the new thread
            thread.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private InetAddress clientIP;

    public ClientHandler(Socket clientSocket, InetAddress clientIP) {
        this.clientSocket = clientSocket;
        this.clientIP = clientIP;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine = in.readLine();
            // Write the line back to the client, preceded by the client's IP address
            out.println(clientIP + ": " + inputLine);
            // Close the socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
