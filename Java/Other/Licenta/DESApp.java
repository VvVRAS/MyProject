package Licenta;

public class DESApp {
    public static void main(String[] args) {
    
    DESAlgorithm des = new DESAlgorithm();
		//Textul introdus
		String message = "Testatre algoritm DES";
		System.out.println("Textul initial: \t" + message);
        //Criptare
		String encryptText = des.encrypt(message);
		System.out.println("Textul criptat: \t" + encryptText);
        //Decriptare
        String decryptText = des.decrypt(encryptText);
		System.out.println("Textul decriptat:\t" + decryptText);
    }
}