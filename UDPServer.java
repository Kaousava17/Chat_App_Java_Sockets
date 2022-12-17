package com.ensea;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    static int bufferSize = 1024;
    static int defaultPort = 8080;
    private final DatagramSocket udpDatagramSocket;
    private final byte[] buffer = new byte[bufferSize];

    public UDPServer(DatagramSocket udpDatagramSocket) {  //Constructor that initialises our datagram sockets
        this.udpDatagramSocket = udpDatagramSocket;
    }

    int port;
    public void receiveSendPacket() {
        System.out.println("Waiting for client ...");
        DatagramPacket udpDatagramPacket = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                udpDatagramSocket.receive(udpDatagramPacket);
                udpDatagramSocket.send(udpDatagramPacket);
                InetAddress inetAddress = udpDatagramPacket.getAddress();
                port = udpDatagramPacket.getPort();
                udpDatagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                String messageFromClient = new String(udpDatagramPacket.getData(), 0, udpDatagramPacket.getLength());
                System.out.println("From client : " + inetAddress + ":" + port);
                System.out.println("Message : " + messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        DatagramSocket udpDatagramSocket = new DatagramSocket(8080);
        UDPServer udpServer = new UDPServer(udpDatagramSocket);
        udpServer.receiveSendPacket();
    }
}