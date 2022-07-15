package Licenta;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

//---------------------Key_Generator_For_RSA--------------------------------------

class keyGeneratorForRSA {

    private static PrivateKey privateKeyRSA;
    private static PublicKey publicKeyRSA;
    
    //key generator-----------------------------------------------------------------------------------------
    public static void keyGenRSA() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen=KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        
        KeyPair keyPair=keyPairGen.generateKeyPair();
        publicKeyRSA=keyPair.getPublic();
        // as putea incerca sa adaug un while pt nr de chei private
        privateKeyRSA=keyPair.getPrivate();
    }
    
    //salvare cheie publica si apoi encodarea cheii-------------------------------------------------------
    public static void CheiePublica(String publicKeyName) throws FileNotFoundException, IOException {
        
        //se genereaza o cheie publica folosind standard-ul X509
        X509EncodedKeySpec publicKeyX509 = new X509EncodedKeySpec(publicKeyRSA.getEncoded());
        
        // citire fisier cheie publica
        FileOutputStream fout = new FileOutputStream("Licenta//RSA//CheiePublica//PublicKey-" + publicKeyName + ".key");     
        //citire fisier cheie publica si apoi scriere continut fisier + encodare continut fisier
        fout.write(publicKeyX509.getEncoded());
        
        fout.close();
    
    }

    //salvare cheie privata generata--------------------------------------------------------------------------
    public static void CheiePrivata(String privateKeyName) throws FileNotFoundException, IOException
    {
        //se genereaza o cheie privata folosind standardul PKCS8
        PKCS8EncodedKeySpec privateKeyPKCS8 = new PKCS8EncodedKeySpec(privateKeyRSA.getEncoded());
        FileOutputStream fout =new FileOutputStream("Licenta//RSA//CheiePrivata//PrivateKey-" + privateKeyName + ".key");
        
        fout.write(privateKeyPKCS8.getEncoded());
        fout.close();
    }
}

//------------------------------Key_Generator_For_AES----------------------------------------

class keyGeneratorForAES{
	//cu generare de cheie cu ajutorul unui numar generat la intamplare
    public static SecretKey generateKey(int n, String keyname) throws NoSuchAlgorithmException, IOException {
        String loc="Licenta//AES//Cheie//";
        loc=loc.concat(keyname);
        loc=loc.concat(".key");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();   ////////

        FileOutputStream fos = new FileOutputStream(loc);
        fos.write(key.getEncoded());
        fos.close();
        return key;
    }

     //cu generare de cheie cu ajutorul unui numar generat la intamplare
         
     public static SecretKey loadKey(String keyname) throws IOException {

        String loc="Licenta//AES//Cheie//";
        loc=loc.concat(keyname);
        loc=loc.concat(".txt");

        byte []keybyte = new byte[16];
        FileInputStream fin = new FileInputStream(loc);
        fin.read(keybyte);
        SecretKey key = new SecretKeySpec(keybyte, 0, 16, "AES");
        fin.close();;
        return key;
     }
     
    //cu cheie definita
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    //convert secretKey to string
    public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    //convert string to secretkey
    public static SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);    /////////
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");  ////////
        return originalKey;
    }

    // paramertul IV
    public static IvParameterSpec generateIv(String ivName) throws IOException {
        
        String loc="Licenta//AES//Cheie//";
        loc=loc.concat(ivName);
        loc=loc.concat(".txt");
        
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);  

        OutputStream os = new FileOutputStream(loc);
        os.write(iv); //byte
        os.close();
        
        return new IvParameterSpec(iv);
    }

    public static IvParameterSpec loadIv(String ivName) throws IOException {
        
        String loc="Licenta//AES//Cheie//";
        loc=loc.concat(ivName);
        loc=loc.concat(".txt");

        File file = new File(loc);
        FileInputStream fileInputStream = null;
        
        byte[] iv = new byte[(int) file.length()];
        new SecureRandom().nextBytes(iv);  
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(iv);
        fileInputStream.close();
        
        return new IvParameterSpec(iv);
    }

}

//-----------------------------Simple_Key_For_Classic_Alghorithms---------------------------

public class key {

    private static Scanner scanner = new Scanner(System.in);

    protected static String stringKey(/*String sKey*/){
        Scanner scanner = new Scanner(System.in);
        String sKey = scanner.nextLine();
        return sKey;
    }
     
   protected static int intKey(/*int iKey*/){
        
        System.out.print("Scrieti cheia: ");
        int iKey = scanner.nextInt();
        return iKey;
    }

    protected static char xorkey ( char xorKey) {
        xorKey = scanner.next().charAt(0);
        return xorKey;
    }

   /* protected static BigInteger DH() {
    // it should be a huge prime number
		BigInteger n = new BigInteger(Integer.toString(31)); //37
		
		// g is the primitive root of n
		BigInteger g = new BigInteger(Integer.toString(19)); //13
		
		DiffieHellman algorithm = new DiffieHellman();
		algorithm.generatePrivateKeys(n, g);
        System.out.println("\n" + n + "\n" + g + "\n" + algorithm);
        return g;
    }*/


}

