package Licenta;

public class AESApp {

	public static void main(String[] args) {
		
		AES aes = new AES();
		
		String text = "What a beautiful day for cryptography";
		String cipherText = aes.encrypt(text);
		System.out.println("Mesaj criptat: " + cipherText);
		System.out.println("Mesaj decriptat: " + aes.decrypt(cipherText));
	}
}