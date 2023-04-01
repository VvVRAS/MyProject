import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAalg {

    private static Scanner sc = new Scanner(System.in);

    public static void KeyGenRSA() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        keyGeneratorForRSA.keyGenRSA();
    }

    public static void PublicKeySaveRSA(String keyname)
            throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        keyGeneratorForRSA.CheiePublica(keyname);
    }

    public static void PrivateKeySaveRSA(String keyname)
            throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        keyGeneratorForRSA.CheiePrivata(keyname);
    }

    static void cryptRSA() throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String plaintextfile = "fisier.txt";
        String plaintextfile1c = "fisier.txt-criptat";
        String plaintextfile2c = "fisier.txt-criptat2";
        // CRIPTARE
        FileInputStream inputPublicKeyA = new FileInputStream("RSA//CheiePublica//PublicKey-B.key");
        byte auxEncA[] = new byte[inputPublicKeyA.available()];
        inputPublicKeyA.read(auxEncA);
        inputPublicKeyA.close();

        KeyFactory keyfA = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec cheiePublicaA = new X509EncodedKeySpec(auxEncA);
        PublicKey cheiPublicaPK = keyfA.generatePublic(cheiePublicaA);

        Cipher cifruRSAEncA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSAEncA.init(Cipher.ENCRYPT_MODE, cheiPublicaPK);

        FileInputStream plainTextFileInputStreamA = new FileInputStream("RSA//Clar//" + plaintextfile);
        FileOutputStream plainTextFileOutputStreamA = new FileOutputStream("RSA//Criptat//" + plaintextfile1c);

        byte[] bufferA = new byte[117];
        int nA;

        while ((nA = plainTextFileInputStreamA.read(bufferA)) != -1) {
            auxEncA = new byte[nA];
            System.arraycopy(bufferA, 0, auxEncA, 0, nA);
            auxEncA = cifruRSAEncA.doFinal(auxEncA);
            plainTextFileOutputStreamA.write(auxEncA);
        }

        plainTextFileInputStreamA.close();
        plainTextFileOutputStreamA.close();

        // ----- criptare 2
        FileInputStream inputPrivateKeyB = new FileInputStream(
                "RSA//CheiePrivata//PrivateKey-A.key");

        byte auxB[] = new byte[inputPrivateKeyB.available()];
        inputPrivateKeyB.read(auxB);
        inputPrivateKeyB.close();

        KeyFactory keyfacB = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec cheiePrivataB = new PKCS8EncodedKeySpec(auxB);
        PrivateKey cheiePrivataPKB = keyfacB.generatePrivate(cheiePrivataB);

        Cipher cifruRSADecB = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSADecB.init(Cipher.ENCRYPT_MODE, cheiePrivataPKB);

        FileInputStream encriptedFileInputStreamB = new FileInputStream(
                "RSA//Criptat//" + plaintextfile1c);
        FileOutputStream encryptedFileOutputStreamB = new FileOutputStream("RSA//Criptat//" + plaintextfile2c);

        byte[] bufferB = new byte[117];
        int nB;
        byte bytereadB[];

        while ((nB = encriptedFileInputStreamB.read(bufferB)) != -1) {
            bytereadB = new byte[nB];
            System.arraycopy(bufferB, 0, bytereadB, 0, nB);
            bytereadB = cifruRSADecB.doFinal(bytereadB);
            encryptedFileOutputStreamB.write(bytereadB);
        }

        encriptedFileInputStreamB.close();
        encryptedFileOutputStreamB.close();
    }

    // ------------------------------------
    static void decryptRSA()
            throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String plaintextfile = "fisier.txt-criptat2";
        String plaintextfile1c = "fisier.txt-criptat";
        String plaintextfile2c = "fisier.txt";
        // CRIPTARE
        FileInputStream inputPublicKeyA = new FileInputStream("RSA//CheiePublica//PublicKey-A.key");
        byte auxEncA[] = new byte[inputPublicKeyA.available()];
        inputPublicKeyA.read(auxEncA);
        inputPublicKeyA.close();

        KeyFactory keyfA = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec cheiePublicaA = new X509EncodedKeySpec(auxEncA);
        PublicKey cheiPublicaPK = keyfA.generatePublic(cheiePublicaA);

        Cipher cifruRSAEncA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSAEncA.init(Cipher.DECRYPT_MODE, cheiPublicaPK);

        FileInputStream plainTextFileInputStreamA = new FileInputStream("RSA//Criptat//" + plaintextfile);
        FileOutputStream plainTextFileOutputStreamA = new FileOutputStream("RSA//Decriptat//" + plaintextfile1c);

        byte[] bufferA = new byte[128];
        int nA;

        while ((nA = plainTextFileInputStreamA.read(bufferA)) != -1) {
            auxEncA = new byte[nA];
            System.arraycopy(bufferA, 0, auxEncA, 0, nA);
            auxEncA = cifruRSAEncA.doFinal(auxEncA);
            plainTextFileOutputStreamA.write(auxEncA);
        }

        plainTextFileInputStreamA.close();
        plainTextFileOutputStreamA.close();

        // ----- criptare 2
        FileInputStream inputPrivateKeyB = new FileInputStream(
                "RSA//CheiePrivata//PrivateKey-B.key");

        byte auxB[] = new byte[inputPrivateKeyB.available()];
        inputPrivateKeyB.read(auxB);
        inputPrivateKeyB.close();

        KeyFactory keyfacB = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec cheiePrivataB = new PKCS8EncodedKeySpec(auxB);
        PrivateKey cheiePrivataPKB = keyfacB.generatePrivate(cheiePrivataB);

        Cipher cifruRSADecB = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSADecB.init(Cipher.DECRYPT_MODE, cheiePrivataPKB);

        FileInputStream encriptedFileInputStreamB = new FileInputStream(
                "RSA//Decriptat//" + plaintextfile1c);
        FileOutputStream encryptedFileOutputStreamB = new FileOutputStream("RSA//Decriptat//" + plaintextfile2c);

        byte[] bufferB = new byte[128];
        int nB;
        byte bytereadB[];

        while ((nB = encriptedFileInputStreamB.read(bufferB)) != -1) {
            bytereadB = new byte[nB];
            System.arraycopy(bufferB, 0, bytereadB, 0, nB);
            bytereadB = cifruRSADecB.doFinal(bytereadB);
            encryptedFileOutputStreamB.write(bytereadB);
        }

        encriptedFileInputStreamB.close();
        encryptedFileOutputStreamB.close();
    }

    public static void main(String[] args)
            throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        /*
         * Scanner sc = new Scanner(System.in);
         * System.out.print("Scrieti numele cheilor: ");
         * String NAMEKEY = sc.nextLine();
         * System.out.print("\nScrieti numele fisierului: ");
         * String FILENAME = sc.nextLine();
         * 
         * System.out.print("\n 1) Generare cheie + CD\n 2) PK+CD\n");
         * 
         * int sel = sc.nextInt();
         * 
         * if (sel == 1) {
         * KeyGenRSA(); // Genereaza cheile private si publice
         * PublicKeySaveRSA(NAMEKEY); // Salveaza cheia publica intr-un fisier
         * PrivateKeySaveRSA(NAMEKEY); // Salveaza cheia privata intr-un fisier
         * cryptRSA(NAMEKEY, FILENAME);
         * decryptRSA(NAMEKEY, FILENAME);
         * } else {
         * cryptRSA(NAMEKEY, FILENAME);
         * decryptRSA(NAMEKEY, FILENAME);
         * }
         */

        /*
         * System.out.println("Generare pentru cristi");
         * KeyGenRSA();
         * PublicKeySaveRSA("Cristi");
         * PrivateKeySaveRSA("Cristi");
         * 
         * System.out.println("Generare pentru mihai");
         * KeyGenRSA();
         * PrivateKeySaveRSA("Mihai");
         * PublicKeySaveRSA("Mihai");
         * 
         * System.out.println("Done");
         */

        cryptRSA();
        System.out.println("Decriptare");
        decryptRSA();
        // decryptRSA();

    }
}