package Licenta;

import Licenta.CryptoUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESgcmFILE {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static byte[] encrypt(byte[] pText, String password) throws Exception {

        // 16 bytes pt salt
        byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);
        //12 bytes pt gcm
        byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);

        //secretkey
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] cipherText = cipher.doFinal(pText);

        // prefix IV si Salt
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv).put(salt).put(cipherText).array();
        return cipherTextWithIvSalt;
    }

    //este nevoie de aceeasi parola, salt, iv pentru decript
    private static byte[] decrypt(byte[] cText, String password) throws Exception {

        ByteBuffer bb = ByteBuffer.wrap(cText);
        byte[] iv = new byte[12];
        bb.get(iv);
        byte[] salt = new byte[16];
        bb.get(salt);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] plainText = cipher.doFinal(cipherText);
        return plainText;

    }

    public static void encryptFile(String fromFile, String toFile, String password) throws Exception {

        //citire
        byte[] fileContent = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(fromFile).toURI()));
        byte[] encryptedText = AESgcmFILE.encrypt(fileContent, password);
        Path path = Paths.get(toFile);
        Files.write(path, encryptedText);
    }

    public static byte[] decryptFile(String fromEncryptedFile, String password) throws Exception {

        byte[] fileContent = Files.readAllBytes(Paths.get(fromEncryptedFile));
        return AESgcmFILE.decrypt(fileContent, password);
    }

  
    public static void main(String[] args) throws Exception {

        String password = "123456789";
        String fromFile = "Licenta//AES//1.jpeg"; 
        String toFile = "Licenta//AES//encrypted.ccs";

        // encrypt file
        AESgcmFILE.encryptFile(fromFile, toFile, password);

        // decrypt file
        byte[] decryptedText = AESgcmFILE.decryptFile(toFile, password);
        String pText = new String(decryptedText, UTF_8);
        System.out.println(pText);
        Files.write(Paths.get("Licenta//AES//12.jpeg"), Arrays.asList(pText), UTF_8);
    }

}