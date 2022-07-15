package Licenta;
import java.util.Scanner;
public class InputMessage {
    public static String plaintext(){
        Scanner inputText=new Scanner(System.in);
        String plainText;
        System.out.print("Scrieti mesajul: ");   
        return plainText = inputText.nextLine();
    }
}
