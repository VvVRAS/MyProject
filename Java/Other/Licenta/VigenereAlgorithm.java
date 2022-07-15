package Licenta;

public class VigenereAlgorithm {

    private static final String ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encrypt(String message, String key) {
		
		message = message.toUpperCase();
		// transformarea algoritmului in case insensitive
		key = key.toUpperCase();
		String cipherText = "";
		
		// index cheie criptare
		int keyIndex = 0;
		
		//cripteaza fiecare caracter din mesajul necriptat 
		for(int i=0; i<message.length(); ++i) {
			char charMessage = message.charAt(i);
			// numarul de schimburi = (index litera din mesaj + index litera din cheie)%27
			int index = Math.floorMod(ALPHABET.indexOf(charMessage) + ALPHABET.indexOf(key.charAt(keyIndex)), ALPHABET.length());
			
			cipherText += ALPHABET.charAt(index);
			
            keyIndex++;
			if(keyIndex == key.length()) {
				keyIndex = 0;
            }
		}
		return cipherText;
	}
	
	public String decrypt(String cipherText, String key) {
		
        // transformarea algoritmului in case insensitive
		cipherText = cipherText.toUpperCase();
		key = key.toUpperCase();
		
		String message = "";
		int keyIndex = 0;
		
        //decripteaza fiecare caracter din mesajul criptat 
		for(int i=0;i<cipherText.length();++i) {
			
            char charCipher = cipherText.charAt(i);
			
            // numarul de schimburi = (index litera alfabet + index litera din cheie)%27
            int index = Math.floorMod(ALPHABET.indexOf(charCipher) - ALPHABET.indexOf(key.charAt(keyIndex)), ALPHABET.length());
			
            message += ALPHABET.charAt(index);
			
            keyIndex++;	
            if(keyIndex == key.length()){
				keyIndex = 0;
            }
		}
		return message;
	}
}

