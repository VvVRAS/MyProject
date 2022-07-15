package Licenta;

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
import Licenta.key;


public class RSAalg {
    
    private static Scanner sc = new Scanner(System.in);
    public static PrivateKey privateKeyRSA;
    public static PublicKey publicKeyRSA;

    public static void KeyGenRSA() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen=KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        
        KeyPair keyPair=keyPairGen.generateKeyPair();
        publicKeyRSA=keyPair.getPublic();
        privateKeyRSA=keyPair.getPrivate();
    }

    public static void PublicKeySaveRSA(String keyname) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
      //se genereaza o cheie publica folosind standard-ul X509
      X509EncodedKeySpec publicKeyX509 = new X509EncodedKeySpec(publicKeyRSA.getEncoded());
        
      // citire fisier cheie publica
      FileOutputStream fout = new FileOutputStream("Licenta//RSA//CheiePublica//PublicKey-" + keyname + ".key");     
      //citire fisier cheie publica si apoi scriere continut fisier + encodare continut fisier
      fout.write(publicKeyX509.getEncoded());
      
      fout.close();
    }
    public static void PrivateKeySaveRSA(String keyname) throws FileNotFoundException, IOException, NoSuchAlgorithmException {    
        //se genereaza o cheie privata folosind standardul PKCS8
        PKCS8EncodedKeySpec privateKeyPKCS8 = new PKCS8EncodedKeySpec(privateKeyRSA.getEncoded());
        FileOutputStream fout =new FileOutputStream("Licenta//RSA//CheiePrivata//PrivateKey-" + keyname + ".key");
        
        fout.write(privateKeyPKCS8.getEncoded());
        fout.close();
    }

    public static void cryptRSA(String namePublicKey, String plaintextfile) throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        // CRIPTARE
        FileInputStream inputPublicKey = new FileInputStream("Licenta//RSA//CheiePublica//PublicKey-" + namePublicKey + ".key");
        byte auxEnc[] = new byte[inputPublicKey.available()];
        inputPublicKey.read(auxEnc);
        inputPublicKey.close();

        KeyFactory keyf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec cheiePublica = new X509EncodedKeySpec(auxEnc);
        PublicKey cheiPublicaB = keyf.generatePublic(cheiePublica);

        Cipher cifruRSAEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSAEnc.init(Cipher.ENCRYPT_MODE, cheiPublicaB);

        FileInputStream plainTextFileInputStream = new FileInputStream("Licenta//RSA//Clar//" + plaintextfile);
        FileOutputStream plainTextFileOutputStream = new FileOutputStream("Licenta//RSA//Criptat//" + plaintextfile + "-criptat");

        byte[] buffer = new byte[117];
        int n;

        while ((n = plainTextFileInputStream.read(buffer)) != -1)
        {
            auxEnc = new byte[n];
            System.arraycopy(buffer, 0, auxEnc, 0, n);
            auxEnc = cifruRSAEnc.doFinal(auxEnc);
            plainTextFileOutputStream.write(auxEnc);
        }

        plainTextFileInputStream.close();
        plainTextFileOutputStream.close();
    }

    public static void decryptRSA(String namePrivateKey, String encryptedfile)  throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        // DECRITPARE

        FileInputStream inputPrivateKey = new FileInputStream("Licenta//RSA//CheiePrivata//PrivateKey-" + namePrivateKey + ".key");
        
        byte aux[] = new byte[inputPrivateKey.available()];
        inputPrivateKey.read(aux);
        inputPrivateKey.close();

        KeyFactory keyfac = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec cheiePrivata = new PKCS8EncodedKeySpec(aux);
        PrivateKey cheiePrivataB = keyfac.generatePrivate(cheiePrivata);

        Cipher cifruRSADec = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cifruRSADec.init(Cipher.DECRYPT_MODE, cheiePrivataB);

        FileInputStream encriptedFileInputStream = new FileInputStream("Licenta//RSA//Criptat//" + encryptedfile + "-criptat");
        FileOutputStream encryptedFileOutputStream = new FileOutputStream("Licenta//RSA//Decriptat//" + encryptedfile);

        byte[] buffer = new byte[128];
        int n;
        byte byteread[];

        while ((n = encriptedFileInputStream.read(buffer)) != -1)
        {
            byteread = new byte[n];
            System.arraycopy(buffer, 0, byteread, 0, n);
            byteread = cifruRSADec.doFinal(byteread);
            encryptedFileOutputStream.write(byteread);
        }
        
        encriptedFileInputStream.close();
        encryptedFileOutputStream.close();
 
    }
    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Scrieti numele cheilor: ");
        String NAMEKEY = sc.nextLine();
        System.out.print("\nScrieti numele fisierului: ");
        String FILENAME = sc.nextLine();
        
        System.out.print("\n 1) Generare cheie + CD\n 2) PK+CD\n");

        int sel = sc.nextInt();
        
        if(sel == 1) {
            KeyGenRSA();                        //Genereaza cheile private si publice 
            PublicKeySaveRSA(NAMEKEY);          //Salveaza cheia publica intr-un fisier
            PrivateKeySaveRSA(NAMEKEY);         //Salveaza cheia privata intr-un fisier
            cryptRSA(NAMEKEY, FILENAME);
            decryptRSA(NAMEKEY, FILENAME);
        } else {
            cryptRSA(NAMEKEY, FILENAME);
            decryptRSA(NAMEKEY, FILENAME);
        }

    }
}