package pang.online;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Klasa implementująca klienta
 */
public class Client {
    /**
     * socket
     */
    private static Socket socket;
    /**
     * status połączenia z serweram
     */
    private String connectionStatus;

    /**
     * Tworzy klienta
     */
    public Client(){
        connectToServer();
    }

    /**
     * Sprawdza status połączenia
     * @return status połączenia z serwerem
     */
    public String getConnectionStatus(){
        return connectionStatus;
    }

    /**
     * Pobiera dane, które przychodzą z serwera
     * @param dataTypePath nazwa folderu, w którym mają zostać zapisane dane
     */
    public void getData(String dataTypePath){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int files = dataInputStream.readInt();
            for(int i = 0; i<files; i++){
                int fileNameLength = dataInputStream.readInt();
                if(fileNameLength>0){
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes,0,fileNameLength);
                    String fileName = new String(fileNameBytes);

                    int fileDataLength = dataInputStream.readInt();

                    if(fileDataLength > 0){
                        byte[] fileBytes = new byte[fileDataLength];
                        dataInputStream.readFully(fileBytes,0,fileDataLength);
                        File filePath = Path.of("Downloads", dataTypePath).toFile();
                        File dataFromServer = new File(filePath, fileName);

                        FileOutputStream fileOutputStream = new FileOutputStream(dataFromServer);
                        fileOutputStream.write(fileBytes);
                        fileOutputStream.close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tworzy nowy socket, ustawia status połączenia z serwerem. Informuje o błędach połączenia, gdy
     * host jest nieznany, albo serwer jest niedostępny.
     */
    public void connectToServer(){
        try{
            socket = new Socket("localhost",44444);
            connectionStatus = "Connected to server";
        } catch (UnknownHostException e){
            System.err.println("Unknown host.");
        } catch (Exception e){
            System.err.println("Server unavailable");
            connectionStatus = "Server unavailable";
        }
    }

    /**
     * Wysyła żądanie do serwera
     * @param commandName treść żądania
     */
    public static void sendCommand(String commandName){
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(commandName);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
