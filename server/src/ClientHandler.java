import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                String clientCommand = bufferedReader.readLine();

                if(clientCommand != null) {
                    System.out.println(clientCommand);
                }

                bufferedReader.close();
                printStream.close();
                socket.close();
                Server.removeClientNumber();
            }
        } catch (Exception e) {
            System.err.println("Client disconnected");
        }
    }
}