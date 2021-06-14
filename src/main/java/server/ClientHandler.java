package server;


import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable,Serializable{

    private Socket socket;
    private boolean isRunning = true;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                while(isRunning) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    DataOutputStream dataOutputStream =  new DataOutputStream(socket.getOutputStream());
                    String clientCommand = bufferedReader.readLine();

                    if ((clientCommand) != null) {
                        if(clientCommand.contains("saveScore")){
                            saveScore(clientCommand);
                            closeConnection();
                        }
                        else{
                            handleClientMessage(clientCommand, dataOutputStream);

                            if (clientCommand.equals("logout")) {
                                bufferedReader.close();
                                dataOutputStream.close();
                                closeConnection();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("ClientHandler error");
            }
    }

    private void handleClientMessage(String clientCommand, DataOutputStream dataOutputStream){
        MessageHandler messageHandler = new MessageHandler(clientCommand);
        messageHandler.handleCommand(dataOutputStream);
    }

    private void saveScore(String clientCommand){
        String[] userData = clientCommand.split("\\s+");

        String nick = userData[1];
        String levelName = userData[2];
        Double score = Double.parseDouble(userData[3]);
        new DataSaver(levelName,score,nick).save();
    }

    private void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.removeClientNumber();
        isRunning = false;
    }
}