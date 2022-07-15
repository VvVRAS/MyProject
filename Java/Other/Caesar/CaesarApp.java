package Licenta;

public class CaesarApp{
    public static void linetitle(String title){
        System.out.print("\n--------------------------------------------------------------------\n");
        System.out.println("\t" + title);
        System.out.print("--------------------------------------------------------------------\n");
    }

    public static void line() {
        System.out.print("--------------------------------------------------------------------\n");
    }

    public static void caesar(String text, int key){
        linetitle("Caesar - criptare");
		
        String encrypt = CaesarAlgorithm.encrypt(text, key);
        System.out.println("Mesajul standard: " + text + "\nCheia de criptare: " + key + "\nMesaj criptat: " + encrypt);
		
        linetitle("Caesar - decriptare cu cheie");
        String decrypt = CaesarAlgorithm.decrypt(encrypt, key);
        System.out.println("Mesaj criptat: " + encrypt + "\nCheia de decriptare: " + key + "\nMesaj decriptat: " + decrypt);
        
        linetitle("Decriptare algoritm Caesar fara cheie");
        CaesarAlgorithm.crack(encrypt);
        line();

        /*
        linetitle("Decriptare cod Caesar cu ajutorul dictionarului");
        String crackDictionary = CaesarAlgorithm.crackDictionary(encrypt);
        line(); */
    }
    //##################################################################################################################################
	public static void main(String[] args) {
		
        // Caesar
        String text = "Acesta este mesajul in clar";
        int key = 3;
        caesar(text, key);

    }
}

