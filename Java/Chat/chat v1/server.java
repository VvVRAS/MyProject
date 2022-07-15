package Chat;

import java.util.Scanner;

import javax.sound.sampled.Port;


//import java.util.concurrent.TimeUnit;
//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
//import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.io.InputStreamReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.lang.Thread;

public class server {

    private final static Scanner sc = new Scanner(System.in);
    private static boolean runningServer = true;                                    //Server run - true/ stop run = false
    private static String msgReceived = null;                                       //receve messages from other clients
    private static String clientName = null;
    private static String clientIP = null;
    private static ServerSocket server = null;
    private static DataInputStream dis = null;
    private static PrintWriter out;
    private static BufferedReader in;
    private static int port = 2568;
    private static int maxUserConnect = 10;
    private static int userConnected = 0;
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void setPort(int port) {
        System.out.println("Port set on: <" + port + ">");
        System.out.print("Port number [1000-65535]: ");
        port = sc.nextInt();
        System.out.println("Port set on: <" + port + ">");
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void serverBanner(int port){                                     //SERVER START BANNER
        System.out.println("\n\n\n\n\n\n\n\n\n");
        System.out.println("Server: ON \nPort server: <" + port + ">" + "\nIP: "
         + "<192.168.0.106>" );
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void userDisconected(String Name) {                               //USER DISCONNECTED
        System.out.println(Name + " => disconected.");
        maxUserConnect++;
        userConnected--;
        System.out.println("User connected " + userConnected + " user can connect " + maxUserConnect + "\n");
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void userConnected() {                                           //USER CONNECTED
        maxUserConnect--;
        userConnected++;
        System.out.println("User connected " + userConnected + " user can connect " + maxUserConnect + "\n");
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void receiveMessages(int port){

        try {
            server = new ServerSocket(port);
            Socket socket = server.accept();                                            // socket accept new client connection
            in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            
            msgReceived = in.readLine();                                                // message receved from client
            switch(msgReceived) {
                case "/disconnect":
                    if(clientName != null) {
                        clientName = in.readLine();
                        userDisconected(clientName);                                         // banner - lost connection with the client
                        runningServer = false;                                               // stop server  
                    }
                    break;
                case "/Are_you_on?":
                    break;
                case "/connect":
                    clientName = in.readLine();
                    clientIP  = in.readLine();                                          // message receved from client
                    System.out.println("User: [" + clientName + "] with <" + clientIP + ">\n");
                    userConnected();
                    break;
                default:
                    System.out.println("[" + clientName + "]: " + msgReceived);         // print message from client 
            }

            in.close();                                                                 // buffer read close
            socket.close();                                                             // Socket close
            server.close();
        } catch (Exception e) {
            System.out.println(">>>SERVER FATAL ERROR<<<");        }                                                             
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        //setPort(port);
        serverBanner(port);                                                         //Banner - Servers start
        
        while (runningServer){
            System.out.println(runningServer);
            receiveMessages(port);
        }
        System.exit(0);                                                             // close program
    }    
}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
