package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server implements Runnable {

    private static int clients = 0;
    private ServerSocket serverSocket;

    public static void removeClientNumber() {
        clients += -1;
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Starting server");

        while (true) {
            try {
                System.out.printf("Waiting for clients... ");
                System.out.println("[Active clients: " + clients + "]");

                Socket socket = serverSocket.accept();

                clients++;
                //System.out.println("\u001B[32m" + "Client connected" + "\u001B[0m");
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();


            } catch (SocketTimeoutException e) {
                //System.err.println("timeout");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
