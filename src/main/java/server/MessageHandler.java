package server;

import java.io.*;
import java.net.Socket;

public class MessageHandler {
    private String clientCommand;

    public MessageHandler(String clientCommand){
        this.clientCommand = clientCommand;
    }

    public void handleCommand(DataOutputStream dataOutputStream){
        switch (clientCommand){
            case "getHighScores" -> DataSender.sendData(dataOutputStream,"highScores");
            case "getMaps" -> DataSender.sendData(dataOutputStream,"levels");
            case "getConfigs" -> DataSender.sendData(dataOutputStream, "config");
            case "logout" -> {
                try {
                    Thread.sleep(1000);
                    //System.err.println("Client disconnected");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            default -> System.out.println("Command not available [" + clientCommand + "]" );
        }

    }
}
