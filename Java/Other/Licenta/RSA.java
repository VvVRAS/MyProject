package Licenta;


import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.SecureRandom;

public class RSA {

	// this is the 'e' parameter (encryption)
	private BigInteger publicKey;
	// this is the 'd' parameter (decryption)
	private BigInteger privateKey;
	// this is n=p*q
	private BigInteger n;
	// need a random number generator
	private SecureRandom random;
	
	public RSA() {
		this.random = new SecureRandom();
	}
	
	public void generateKeys(int keyDigits) {
		
		// p is a large prime number
		BigInteger p = BigInteger.probablePrime(keyDigits, random);
		// q is a large prime number
		BigInteger q = BigInteger.probablePrime(keyDigits, random);
		// n=q*p
		// this is the trap-door function
		n = p.multiply(q);
		
		// Euler's totient phi function (p-1)*(q-1)
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		// we have to use GCD to find e and gcd(phi,e)=1 
		// so e is coprime to phi
		BigInteger e = generatePublicFactor(phi);
		
		// modular inverse of e - mod phi (extended Euclidean algorithm)
		BigInteger d = e.modInverse(phi);
		
		// this is how we can decrypt messages
		this.privateKey = d;
		// this is how we can encrypt messages
		this.publicKey = e;
    }

    protected BigInteger privateKeyRSA(){
        return privateKey;
    }
	

    protected BigInteger publicKeyRSA(){
        return publicKey;
    }

	private BigInteger generatePublicFactor(BigInteger phi) {
		
		BigInteger e = new BigInteger(phi.bitLength(), random);
		
		// we are after a coprime where gcd(e, phi)=1
		while(!e.gcd(phi).equals(BigInteger.ONE))
			e = new BigInteger(phi.bitLength(), random);
		
		return e;
	}

	public BigInteger encryptMessage(String message) {
		return encrypt(publicKey, n, message);
	}

	public String decryptMessage(BigInteger message) {
		return decrypt(privateKey, n, message);
	}
	
	// the cipher text is a huge integer
	private BigInteger encrypt(BigInteger e, BigInteger n, String message) {
		
		byte[] messageByte = message.getBytes();
		BigInteger messageInt = new BigInteger(messageByte);
		
		// we have to use modular exponentiation 
		// so the cipher text = message ^ e mod n
		return messageInt.modPow(e, n);
	}

	private String decrypt(BigInteger d, BigInteger n, BigInteger cipherText) {
		
		// we use modular exponentiation for decryption as well
		// cipher ^ d mod n = plain text
		BigInteger messageInt = cipherText.modPow(d, n);
		
		// we want to end up with a string
		return new String(messageInt.toByteArray());
	}
}