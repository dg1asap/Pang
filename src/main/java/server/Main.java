package server;

public class Main {
    public static void main(String[] args){
            Thread server = new Thread(new Server(53911));
            server.start();
    }
}
