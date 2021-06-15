package server;

import java.io.*;
import java.nio.file.Path;

/**
 * Klasa odpowiedzialna za wysyłanie danych do klienta
 */
public class DataSender {

    /**
     * Wysyła dane, o które prosi klient
     * @param dataOutputStream strumień wyjściowy
     * @param dataName nazwa pliku konfiguracyjnego
     */
    public static void sendData(DataOutputStream dataOutputStream, String dataName) {
        try {
            File serverFiles = Path.of( "./ServerData", dataName).toFile();
            int numberOfFilesToSend = serverFiles.listFiles().length;

            File[] files = serverFiles.listFiles();

            dataOutputStream.writeInt(numberOfFilesToSend);

            for (File file : files) {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());

                byte[] fileData = new byte[(int) file.length()];
                fileInputStream.read(fileData);

                dataOutputStream.writeInt(file.getName().getBytes().length);
                dataOutputStream.write(file.getName().getBytes());

                dataOutputStream.writeInt(fileData.length);
                dataOutputStream.write(fileData);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}