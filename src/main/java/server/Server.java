package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Klasa reprezentująca serwer
 */
public class Server implements Runnable {

    /**
     * Informuje o liczbie klientów połączonych z serwerem
     */
    private static int clients = 0;
    /**
     * Serwerowy socket
     */
    private ServerSocket serverSocket;

    /**
     * Usuwa klienta z listy klientów, gdy klient się wyloguje
     */
    public static void removeClientNumber() {
        clients += -1;
    }

    /**
     * Tworzy serwer, z przydzielonym numerem portu
     * Ustawia timeout na 5000ms
     * @param port numer portu
     */
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

    /**
     * Oczekuje na logowanie się nowych klientów. Gdy klient się połączy tworzony jest nowy wątek, który ma za
     * zadanie obsłużyć jego żądania
     */
    @Override
    public void run() {
        System.out.println("Starting server");

        while (true) {
            try {
                System.out.printf("Waiting for clients... ");
                System.out.println("[Active clients: " + clients + "]");

                Socket socket = serverSocket.accept();

                clients++;
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
