package Licenta;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DESAlgorithm {
    private SecretKey secretKey;
	private SecureRandom random;
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	private IvParameterSpec ivParams;
	
    public DESAlgorithm() {
		try {
			secretKey = KeyGenerator.getInstance("DES").generateKey();
			random = new SecureRandom();
			encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// initializare vector (IV)
			byte[] initializatonVector = new byte[encryptCipher.getBlockSize()];
			random.nextBytes(initializatonVector);
			ivParams = new IvParameterSpec(initializatonVector);
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {	
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}	
	}
	
	public String encrypt(String message) {
		
		byte[] bytes = message.getBytes();
		byte[] cipherText = null;
		
		try {
			cipherText = encryptCipher.doFinal(bytes);			
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(cipherText);
	}
	
	public String decrypt(String cipherText) {
		
		byte[] message = null;
		
		try {
			message = decryptCipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes()));
			return new String(message, "UTF8");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
