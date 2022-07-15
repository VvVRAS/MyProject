package Licenta;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {

	// SecureRandom class instead
	private Random random;
	
	public DiffieHellman() {
		this.random = new Random();
	}
	
	// we want to make sure that both Alice and Bob
	// will use the same private key (AES)
	public void generatePrivateKeys(BigInteger n, BigInteger g) {
		
		// THESE VARIABLES ARE PRIVATE
		// random number for Alice where x<n-1
		int rand1 = random.nextInt(n.intValue()-2) + 1;
		BigInteger x = new BigInteger(Integer.toString(rand1));
		
		// random number for Bob where x<n-1
		int rand2 = random.nextInt(n.intValue()-2) + 1;
		BigInteger y = new BigInteger(Integer.toString(rand2));
		
		// THESE VARIABLES ARE PUBLIC !!!
		// calculate g^x mod n which is Alice's k1
		BigInteger k1 = g.modPow(x, n);
		
		// calculate g^y mod n which is Bob's k2
		BigInteger k2 = g.modPow(y, n);
		
		// they can calculate the same private key !!!
		// Alice private key
		BigInteger key1 = k2.modPow(x, n);
		
		// Bob private key
		BigInteger key2 = k1.modPow(y, n);
		
		// if an attacker wants to get x and y (the private variables) which is the discrete
		// logarithm problem it is an exponentially slow process !!!
		System.out.println("Prima cheie: " + key1.intValue());
		System.out.println("A doua cheie: " + key2.intValue());
	}
}
