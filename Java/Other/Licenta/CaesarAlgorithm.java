package Licenta;
public class CaesarAlgorithm{
    
    private static String ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //private static LanguageDetector languageDetector;
	
    //Criptare mesaj cu algoritm caesar si cheie
	public static String encrypt(String plainText, int key) {
		
		String cipherText = "";
		//transformam formatul textului pentru ca algoritmul sa nu mai fie case sensitive
		plainText = plainText.toUpperCase();
	
		for(int i=0;i<plainText.length();i++) {
			char character = plainText.charAt(i);                                               // extrage fiecare caracter din mesaj
			int charIndex = ALPHABET.indexOf(character);                                        // variabilei charindex ii este atribuit un caracter din alphabet
			int encryptedIndex = Math.floorMod(charIndex+key, ALPHABET.length());               //formula clasica pentru criptare/decriptare din codul lui caesar
			cipherText += ALPHABET.charAt(encryptedIndex);                                      //creaza un string cu fiecare caracter din alfabet in functie de index
		}
		
		return cipherText;	
	}

    //Decriptare mesaj cu algoritm Caesar si cheie
    public static String decrypt(String cipherText, int key) {

		String plainText = "";
		
		for(int i=0;i<cipherText.length();++i) {
			char character = cipherText.charAt(i);                                              // extrage fiecare caracter din mesaj
			int charIndex = ALPHABET.indexOf(character);                                        // variabilei charindex ii este atribuit un caracter din alphabet
			int decryptedIndex = Math.floorMod(charIndex-key, ALPHABET.length());               //formula clasica pentru criptare/decriptare din codul lui caesar
			plainText += ALPHABET.charAt(decryptedIndex);                                       //creaza un string cu fiecare caracter din alfabet in functie de index
		}
		
		return plainText;
	}  

    //Decriptare mesaj cu algoritmul Caesar fara cheie
    public static String crack(String cipherText) {
		
        String plainText;

		for(int key=0;key<ALPHABET.length();++key) {
			plainText = "";
		
            for(int i=0;i<cipherText.length();++i) {
				char character = cipherText.charAt(i);
				int charIndex = ALPHABET.indexOf(character);
				int decryptedIndex = Math.floorMod(charIndex-key, ALPHABET.length());
				plainText += ALPHABET.charAt(decryptedIndex);
			}
			System.out.format("Cheie utilizata %s posibil mesaj: %s%n", 
					key, plainText);
		}
        return ALPHABET;	
	}

    /*//Decriptare mesaj cu Dictionary method si brute-force
    public static String crackDictionary(String cipherText) {
		
        String plainText;

		for(int key=0;key<ALPHABET.length();++key) {
			plainText = "";
		
            for(int i=0;i<cipherText.length();++i) {
				char character = cipherText.charAt(i);
				int charIndex = ALPHABET.indexOf(character);
				int decryptedIndex = Math.floorMod(charIndex-key, ALPHABET.length());
				plainText += ALPHABET.charAt(decryptedIndex);
			}
            if(languageDetector.isRomanian(plainText))
			System.out.format("Cheie utilizata %s posibil mesaj: %s%n", 
					key, plainText);
		}
        return ALPHABET;	
	}*/

}                                                                                                                                                                                                                                                   