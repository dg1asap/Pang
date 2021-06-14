import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable,Serializable{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                boolean isRunning = true;
                while(isRunning) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    DataOutputStream dataOutputStream =  new DataOutputStream(socket.getOutputStream());
                    String clientCommand = bufferedReader.readLine();

                    if ((clientCommand) != null) {
                        if(clientCommand.contains("saveScore")){
                            String[] userData = clientCommand.split("\\s+");

                            String nick = userData[1];
                            String levelName = userData[2];
                            Double score = Double.parseDouble(userData[3]);
                            new DataSaver(levelName,score,nick).save();
                            socket.close();
                            Server.removeClientNumber();
                            isRunning = false;
                        }
                        else{
                            MessageHandler messageHandler = new MessageHandler(clientCommand);
                            messageHandler.handleCommand(dataOutputStream);

                            if(clientCommand.equals("wait")){
                                socket.close();
                                Server.removeClientNumber();
                                isRunning = false;
                            }
                            else if (clientCommand.equals("logout")) {
                                bufferedReader.close();
                                dataOutputStream.close();
                                socket.close();
                                Server.removeClientNumber();
                                isRunning = false;
                            }
                            else {
                                System.out.println("\u001B[36m" + "Request handled" + "\u001B[0m");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Client error");
            }

    }
}