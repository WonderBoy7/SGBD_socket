package main;

import request.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread{
    Socket client;
    public static void main(String[] args) throws IOException {
        ServerSocket s= new ServerSocket(1897);
        System.out.println("wait some client");
        for (int i = 1; i > -1; i++) {
            System.out.println("Client tafiditra");
            Socket client = s.accept();
            Serveur ss=new Serveur(client);
            ss.start();
        }
        }
        /*while (true){
            }

    }*/

    public Serveur (Socket client){
        this.client=client;
    }

    public void run(){
        try {
            Request request = new Request();
            DataInputStream message = new DataInputStream(client.getInputStream());
            String mess = message.readUTF();
            System.out.println(mess);
            String sss = request.request(mess).toString();
            DataOutputStream renvoie = new DataOutputStream(client.getOutputStream());
            renvoie.writeUTF(sss);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
