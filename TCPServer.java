package com.ensea;

import java.io.*;
import java.net.*;

public class TCPServer {
    private static int port;

    public TCPServer(int port) throws SocketException {
        this.port = port;
    }

    public void launch() throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Waiting for the client : " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String text;

                do {
                    text = reader.readLine();
                    System.console().printf(text + "\n");
                    String echo = new StringBuilder(text).toString();
                    writer.println("Server: " + echo);

                } while (!text.equals("bye"));

                socket.close();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) return;
        port = Integer.parseInt(args[0]);
        TCPServer client = new TCPServer(port);
        client.launch();
    }
}
