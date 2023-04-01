import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;

public class RSA {
    public static void main(String[] args) throws Exception {
        // Generam cheile pentru a si b
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048, new SecureRandom());
        KeyPair keyPairA = keyPairGen.generateKeyPair();
        KeyPair keyPairB = keyPairGen.generateKeyPair();

        // Obtinem cheile publice si private pentru a si b
        PublicKey publicKeyA = keyPairA.getPublic();
        PrivateKey privateKeyA = keyPairA.getPrivate();
        PublicKey publicKeyB = keyPairB.getPublic();
        PrivateKey privateKeyB = keyPairB.getPrivate();

        // Textul pe care dorim sa-l criptam
        String plaintext = "Hello World!";

        // Criptam textul folosind cheia privata a lui a si cheia publica a lui b
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKeyA);
        byte[] encryptedText = cipher.doFinal(plaintext.getBytes());

        // Decriptam textul folosind cheia privata a lui b si cheia publica a lui a
        cipher.init(Cipher.DECRYPT_MODE, privateKeyB);
        byte[] decryptedText = cipher.doFinal(encryptedText);

        // Afisam textul original si textul decriptat
        System.out.println("Textul original: " + plaintext);
        System.out.println("Textul criptat: " + new String(encryptedText));
        System.out.println("Textul decriptat: " + new String(decryptedText));
    }
}