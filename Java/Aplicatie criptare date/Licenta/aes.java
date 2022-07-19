package Licenta;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class aes {
    private static String algorithm = "AES/CBC/PKCS5Padding";
    
    //ciphertext_size (bytes) = cleartext_size + (16 - (cleartext_size % 16))

    // Random key generator with random big integer number
    public static SecretKey generateKey(int n, String keyname) throws NoSuchAlgorithmException, IOException {
        String loc="Licenta//AES//Cheie//";
        loc=loc.concat(keyname);
        loc=loc.concat(".txt");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();  

        FileOutputStream fos = new FileOutputStream(loc);
        fos.write(key.getEncoded());
        fos.close();
        return key;
    }

     //Save in memory random key generated
         
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
     
    // save key file in memory
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

    //  Generate Random Initialised vector (IV)
    public static IvParameterSpec generateIv(String ivName) throws IOException {
        
        String loc="Licenta//AES//IV//";
        loc=loc.concat(ivName);
        loc=loc.concat(".txt");
        
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);  

        OutputStream os = new FileOutputStream(loc);
        os.write(iv); //byte
        os.close();
        
        return new IvParameterSpec(iv);
    }
    
    //Save random generated IV into memory
    public static IvParameterSpec loadIv(String ivName) throws IOException {
        
        String loc="Licenta//AES//IV//";
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

    // criptare string
    /*  public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) 
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
    InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    */
    //decriptare string
    /*  public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) 
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
    InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
    */

    //------------------------------------------------------------------------------CBC--------------------------------------------------------------------------------
    public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
    String inputFile, String outputFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            inputStream.close();
            outputStream.close();
        }

    public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
    String encryptedFile, String decryptedFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(encryptedFile);
        FileOutputStream outputStream = new FileOutputStream(decryptedFile);
        byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
        }
    
    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, 
    InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {   
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        String keyName = "AESKey";
        String ivName = "iv";

        System.out.print("\n Numarul de bytes alocat cheii: \n1) 128 Byte\n2) 256 Byte\n$: ");
        int sel = sc.nextInt();
        int keyByte;
        switch (sel) {
            case 1: keyByte = 128;
                    System.out.println("Lungimea cheii = 128 Bytes");
                    break;
            case 2: keyByte = 256;
                    System.out.println("Lungimea cheii = 256 Bytes");
                    break;
            default: keyByte = 128;
                     System.out.println("Lungimea cheii = 128 Bytes");
                     break;
        }

        
        SecretKey key;
        //key = aes.generateKey(keyByte);
        key = aes.loadKey(keyName);
        
        //IvParameterSpec iv = aes.generateIv();
        IvParameterSpec iv = aes.loadIv(ivName);

        System.out.print("Numele fisierului: ");
        String fName = sc2.nextLine();

        String inputFile = "Licenta//AES//Clar//";
        inputFile = inputFile+fName;

        String outputFile = "Licenta//AES//Criptat//"; 
        outputFile = outputFile+fName;
        String encryptedFile = outputFile;
        
        String decryptedFile = "Licenta//AES//Dec//";
        decryptedFile = decryptedFile+fName;

        aes.encryptFile(algorithm, key, iv, inputFile, outputFile);
        
        aes.decryptFile(algorithm, key, iv, encryptedFile, decryptedFile);
    
        String cKey = convertSecretKeyToString(key);

        System.out.println(cKey);
    }
}
