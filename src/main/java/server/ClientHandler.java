package server;


import java.io.*;
import java.net.Socket;

/**
 * Klasa odpowiedzialna za obsługę poszczególnych klientów
 */
public class ClientHandler implements Runnable{

    /**
     * Socket
     */
    private Socket socket;
    /**
     * Informuje, czy klient nadal ma być obsługiwany
     */
    private boolean isRunning = true;

    /**
     * Ustawia socket
     * @param socket socket
     */
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    /**
     * Otwiera strumienie, obsługujące żądania klienta. Po zakończonej sesji wylogowuje klienta
     */
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

    /**
     * Przekazuje obsługę żądania MessageHandler
     * @param clientCommand treść żądania klienta
     * @param dataOutputStream strumień wyjściowy
     */
    private void handleClientMessage(String clientCommand, DataOutputStream dataOutputStream){
        MessageHandler messageHandler = new MessageHandler(clientCommand);
        messageHandler.handleCommand(dataOutputStream);
    }

    /**
     * Obsługuje zapisywanie wyników do plików serwera
     * @param dataToSave żadanie klienta o zapis z nazwą mapy, gracza i jego wynikiem
     */
    private void saveScore(String dataToSave){
        String[] userData = dataToSave.split("\\s+");

        String nick = userData[1];
        String levelName = userData[2];
        Double score = Double.parseDouble(userData[3]);
        new DataSaver(levelName,score,nick).save();
    }

    /**
     * Kończy połączenie z klientem. Zamyka socket.
     */
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