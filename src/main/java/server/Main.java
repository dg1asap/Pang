package server;

public class Main {
    public static void main(String[] args){
        Thread server = new Thread(new Server(44444));
        server.start();
    }
}
