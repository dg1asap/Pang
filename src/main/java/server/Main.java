package server;

public class Main {
    public static void main(String[] args){
        new Thread(new Server(44444)).start();
    }
}
