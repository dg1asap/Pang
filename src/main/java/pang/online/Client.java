package pang.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    Socket socket;

    public Client(){
        connectToServer();
    }

    private void connectToServer(){
        try{
            socket = new Socket("localhost",53911);
        } catch (UnknownHostException e){
            System.err.println("Unknown host.");
        } catch (Exception e){
            System.err.println("Server unavailable");
        }
    }


}
