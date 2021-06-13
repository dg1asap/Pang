package pang.online;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private String connectionStatus;

    public Client(){
        connectToServer();
    }

    public String getConnectionStatus(){
        return connectionStatus;
    }

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

    public void connectToServer(){
        try{
            socket = new Socket("localhost",53911);
            connectionStatus = "Connected to server";
        } catch (UnknownHostException e){
            System.err.println("Unknown host.");
        } catch (Exception e){
            System.err.println("Server unavailable");
            connectionStatus = "Server unavailable";
        }
    }

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
