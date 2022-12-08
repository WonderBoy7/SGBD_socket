package main;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * Client
 */
public class Client {

    public static void main(String[] args) throws IOException {
        while (true){
            Socket za = new Socket("localhost",1897);
            DataOutputStream message = new DataOutputStream(za.getOutputStream());
            Scanner myObj = new Scanner(System.in);
            String ds=myObj.nextLine();
            String m = ds;
            message.writeUTF(m);
            DataInputStream response = new DataInputStream(za.getInputStream());
            String vaminy= response.readUTF();
            System.out.println(vaminy);
        }
    }
        
}
