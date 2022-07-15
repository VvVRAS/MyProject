package Chat;

import java.util.Scanner;

//import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;

public class client {

    //Settings
    private static String IP  = "localhost";
    private static String bufferIP = null;
    private static int port = 2568;
    private static Socket s = null;
    private static String userName = null;
    private static boolean connect = true;
    private static String nullMsg = "_______";
    private static Boolean messageSendedTF = false;                                    //False(F) for "Message not send" True(T) for "Message send"
    final private static Scanner sc = new Scanner(System.in);

//-----------------------------------------------------------------------------------------------------------------------------------------------------------
private static boolean testServer(){
    try {
        Socket s = new Socket(IP, port);    
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.println("/Are_you_on?");
        out.close();
        s.close();
        connect = true;
    } catch (Exception e) {
        System.out.println("Server maybe offline.");
        connect = false;
    }
    return connect;
}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void clientLogIn(){
    
    
        String command = null;                                                  //Server status(true - server on | false - server off)
        System.out.println("Command: ");
        System.out.println("\"/connect\"     => LogIn");
        System.out.println("\"/disconnect\"  => LogOut");
        command = sc.nextLine();
        testServer();
        System.out.println(connect);
        
        try{
            s = new Socket(IP, port);      
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
            switch (command) {
                case "/connect":
                    System.out.print("UserNamer: ");
                    userName = sc.nextLine();
                    if (userName.isEmpty() || userName.isBlank()) {
                        System.out.println("\n\n\nUser Name can NOT be empty.\n\n\n\n");
                        connect = false;
                    }
                    out.println("/connect");
        
                    out.println(userName);
        
                    String ip=(((InetSocketAddress) s.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
                    bufferIP = ip;
                    out.println(bufferIP);
        
                    System.out.println("Client IP: " + ip);
                    messageSendedTF = true;
                    
                    connect = true;
                    messageSendedTF = connect;
                    break;
                case "/disconnect":
                    System.out.println(">>>Disconnected<<<");
                    out.println(userName);
                    break;
                default:
                    System.out.println("ERROR: DEFAULT");
            }
            in.close();
            out.close();
            s.close();
        } catch(Exception e) {
            System.out.println("Error => Connection refused. \nServer maybe offline. \nTry again later.");
            connect = false;
            messageSendedTF = connect;
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private static void clientSendMessage(String message){
        try {
            s = new Socket(IP, port);      
            
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader((s.getInputStream())));
                
            switch (message) {
                case "":
                    out.println(nullMsg);
                    break;
                case "/disconnect":
                    out.println(message);
                    System.out.println(">>>Disconnected<<<");
                    out.println(userName);
                    messageSendedTF = false;
                    connect = false;
                    break;
                default:
                out.println(message);
            }

            in.close();
            out.close();
            s.close();
            messageSendedTF = true;                                             // Message send
        } catch (Exception e){
            messageSendedTF = false;                                            //Messages not send \n I try again
            System.out.println("Try again");
        }
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws UnknownHostException, IOException {
        String message = null;                                                  //print client message;
        //testServer();                                                         //test connection with server if it is online or offline
        clientLogIn();                                                          //Client start program 
        while(connect && userName != null){                                     //client loop for send messages
            System.out.print("[" + userName + "]: ");                           //[ClientName]: message
            if(messageSendedTF == true) {                                       //send new message if last messge get sendesd with succes
                message = sc.nextLine();
                clientSendMessage(message);                                     
            } else {            
                clientSendMessage(message);                                     //resend last message
            }
        }
    }
}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
