package com.ensea;

import java.net.*;
import java.io.*;

public class TCPClient {
    private int port;
    private String hostname;

    public TCPClient(String destinationAddr, int port) throws IOException {
        this.port = port;
        this.hostname = destinationAddr;
    }

    private void start() throws IOException {

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();
            String text;

            do {
                text = console.readLine("Enter text: ");

                writer.println(text);

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String time = reader.readLine();

                System.out.println(time);

            } while (!text.equals("Quit"));

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        if (args.length < 2) return;
        System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");

        TCPClient sender = new TCPClient(args[0], Integer.parseInt(args[1]));
        sender.start();
    }

}