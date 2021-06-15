package server;

import java.io.*;

/**
 * Klasa odpowiedzialna za obsługę żądań klienta
 */
public class MessageHandler {
    /**
     * Komenda jaką wysłał klient
     */
    private String clientCommand;

    /**
     * Ustawia treść żądania od klienta
     * @param clientCommand żądanie klienta
     */
    public MessageHandler(String clientCommand){
        this.clientCommand = clientCommand;
    }

    /**
     * Obsługuje żądania klienta
     * @param dataOutputStream strumień wyjściowy
     */
    public void handleCommand(DataOutputStream dataOutputStream){
        switch (clientCommand){
            case "getHighScores" -> DataSender.sendData(dataOutputStream,"highScores");
            case "getMaps" -> DataSender.sendData(dataOutputStream,"levels");
            case "getConfigs" -> DataSender.sendData(dataOutputStream, "config");
            case "logout" -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            default -> System.out.println("Command not available [" + clientCommand + "]" );
        }

    }
}
