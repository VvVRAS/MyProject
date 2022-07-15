package Licenta;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

	// 256 bits - 64 hexadecimal characters (output hash)
	// that Bitcoin miners 
	public String digest(String message) {
		
		MessageDigest messageDigest = null;
		byte[] messageDigestByte = null;
		String hexaHash = "";
		
		try {
			// we are using the SHA-256 hash algorithm
			messageDigest = MessageDigest.getInstance("SHA-256");
			// we need the byte array when dealing with cryptography
			messageDigestByte = messageDigest.digest(message.getBytes());
			// we convert the message hash into hexadecimal (16 characters)
			// problem is that it may omit leading zeros
			hexaHash = new BigInteger(1, messageDigestByte).toString(16);
			
			while(hexaHash.length() < 64)
				hexaHash = "0" + hexaHash;		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hexaHash;
	}
}