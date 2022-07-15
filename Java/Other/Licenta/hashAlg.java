package Licenta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class hashAlg {

	public static String getFileChecksum(MessageDigest digest, File file) throws IOException{

		FileInputStream fis = new FileInputStream(file);
	
		byte[] byteArray = new byte[1024];
		int bytesCount = 0; 

		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		};
		

		fis.close();

		byte[] bytes = digest.digest();

		StringBuilder sb = new StringBuilder();
		for(int i=0; i< bytes.length ;i++)
		{
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		//return complete hash
		return sb.toString();
		}

		public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
			Scanner selSc=new Scanner(System.in);
			Scanner wrStrg=new Scanner(System.in);
			Scanner FlName = new Scanner(System.in);

			String fileName;
			String loc="";
			System.out.print("Verificare chei: \n1) AES\n2) RSA - cheie privata\n3) RSA - cheie publica$: ");
			int sel=selSc.nextInt();
			//String WS=wrStrg.nextLine();
			
			switch (sel) {
				case 1: loc="Licenta//AES//Cheie//";
						System.out.print("Scrieti numele fisierului: ");
						fileName=FlName.nextLine();
						loc=loc.concat(fileName);
						loc=loc.concat(".txt");
						break;
				
				case 2: loc="Licenta//RSA//CheiePrivata//PrivateKey-";
						System.out.print("Scrieti numele fisierului: ");
						fileName=FlName.nextLine();
						loc=loc.concat(fileName);
						loc=loc.concat(".key");
						break;
				case 3: loc="Licenta//RSA//CheiePublica//PublicKey-";
						System.out.print("Scrieti numele fisierului: ");
						fileName=FlName.nextLine();
						loc=loc.concat(fileName);
						loc=loc.concat(".key");
						break;
				default: 
						 System.out.println("Ati ales gresit");
						 break;
			}
			String location = loc;
			File file = new File(location);
			
			//MD5 algorithm
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			
			//checksum
			var checksumMD5 = getFileChecksum(md5Digest, file);
			

			//SHA-1 algorithm
			MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
			
			//SHA-1 checksum 
			var checksumSHA256 = getFileChecksum(shaDigest, file);

			//checksum
			System.out.println("\nChecksum MD5 - " + checksumMD5);
			System.out.println("\nChecksum SHA256 - " + checksumSHA256);
		}
}