
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

    // key
    // generator-----------------------------------------------------------------------------------------
    public static void keyGenRSA() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        publicKeyRSA = keyPair.getPublic();
        // as putea incerca sa adaug un while pt nr de chei private
        privateKeyRSA = keyPair.getPrivate();
    }

    // salvare cheie publica si apoi encodarea
    // cheii-------------------------------------------------------
    public static void CheiePublica(String publicKeyName) throws FileNotFoundException, IOException {

        // se genereaza o cheie publica folosind standard-ul X509
        X509EncodedKeySpec publicKeyX509 = new X509EncodedKeySpec(publicKeyRSA.getEncoded());

        // citire fisier cheie publica
        FileOutputStream fout = new FileOutputStream("RSA//CheiePublica//PublicKey-" + publicKeyName + ".key");
        // citire fisier cheie publica si apoi scriere continut fisier + encodare
        // continut fisier
        fout.write(publicKeyX509.getEncoded());

        fout.close();

    }

    // salvare cheie privata
    // generata--------------------------------------------------------------------------
    public static void CheiePrivata(String privateKeyName) throws FileNotFoundException, IOException {
        // se genereaza o cheie privata folosind standardul PKCS8
        PKCS8EncodedKeySpec privateKeyPKCS8 = new PKCS8EncodedKeySpec(privateKeyRSA.getEncoded());
        FileOutputStream fout = new FileOutputStream("RSA//CheiePrivata//PrivateKey-" + privateKeyName + ".key");

        fout.write(privateKeyPKCS8.getEncoded());
        fout.close();
    }

}
