package com.ensea;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    static int bufferSize = 1024;
    private final DatagramSocket udpDatagramSocket;
    private byte[] buffer = new byte[bufferSize];
    private final InetAddress inetAddress;

    public UDPClient(DatagramSocket udpDatagramSocket, InetAddress inetAddress) { //Constructor that initialises our datagram sockets
        this.udpDatagramSocket = udpDatagramSocket;
        this.inetAddress = inetAddress;
    }

    public void receiveSentPacket(InetAddress adClient, int port) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in); //text reading and writing
                String messageSending = scanner.nextLine();
                buffer = messageSending.getBytes();
                DatagramPacket udpDatagramPacket = new DatagramPacket(buffer, buffer.length, adClient, port);
                udpDatagramSocket.send(udpDatagramPacket);
                udpDatagramSocket.receive(udpDatagramPacket);
                String messageFromServer = new String(udpDatagramPacket.getData(), 0, udpDatagramPacket.getLength());
                System.out.println("From server : " + adClient + ":" + port);
                System.out.println("Message : " + messageFromServer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[1]);
        DatagramSocket udpDatagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName(args[0]);
        UDPClient udpClient = new UDPClient(udpDatagramSocket, inetAddress);
        udpClient.receiveSentPacket(inetAddress, port);
    }
}