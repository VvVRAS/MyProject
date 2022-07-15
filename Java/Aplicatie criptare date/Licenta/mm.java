package Licenta;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.Checksum;

import Licenta.RSAalg;
import Licenta.key;

public class mm {
    public static void main(String[] args) {
        JFrame f = new JFrame("Main menu");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.setVisible(true);
        f.setSize(800,150);
        //f.setResizable(false);
        //f.setLayout(new FlowLayout());

        JPanel p1 = new JPanel();
        //Add text box in panel 1
        
        //Create panel 2
        JPanel p2 = new JPanel();
        //Create panel 3
        JPanel p3 = new JPanel();

        JTabbedPane tabs = new JTabbedPane();
        //Set tab container position
        tabs.setBounds(200,1500,300,300);
        //Associate each panel with the corresponding tab
        tabs.add("RSA", p1);
        tabs.add("AES", p2);
        tabs.add("HASH", p3);
        JTextArea consola = new JTextArea();

        f.add(tabs);
        //f.add(consola);
        //------RSA--------
        JLabel l1 = new JLabel("Numele cheii");
        p1.add(l1);
        JTextField t1 = new JTextField(20);
        p1.add(t1);

        JLabel l2 = new JLabel("Numele fisierului");
        p1.add(l2);
        JTextField t2 = new JTextField(20);
        p1.add(t2);


        JButton bc = new JButton("Criptare");
        bc.setBounds(20,20,30,40);
        bc.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                int pos = consola.getCaretPosition();
            try {
                
                RSAalg.cryptRSA(t1.getText(), t2.getText());
                consola.insert("Fisierul a fost criptat cu succes.\n", pos);

            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                    | IllegalBlockSizeException | BadPaddingException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                
                consola.insert("Verificati numele fisierelor\n", pos);


            }

        }
    });

    p1.add(bc);

    JButton bd = new JButton("Decriptare");
    bd.setBounds(20,20,30,40);
    bd.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int posd = consola.getCaretPosition();

            try {
                
                RSAalg.decryptRSA(t1.getText(), t2.getText());
                consola.insert("Fisierul a fost decriptat cu succes.\n", posd);

            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                    | IllegalBlockSizeException | BadPaddingException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                consola.insert("Verificati numele fisierelor\n", posd);

            }
        }
    });

        
        JButton bkgen = new JButton("Generare chei");
        bd.setBounds(20,20,30,40);
        bd.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int posg = consola.getCaretPosition();

            try {
                RSAalg.KeyGenRSA();
                RSAalg.PublicKeySaveRSA(t1.getText());
                RSAalg.PrivateKeySaveRSA(t1.getText());
                consola.insert("Cheile au fost generate cu succes.\n", posg);

            } catch (NoSuchAlgorithmException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                consola.insert("Cheile nu au fost generate\n", posg);

            }
        }
    });

        p1.add(bd);
        p1.add(bkgen);
        p1.add(consola, BorderLayout.SOUTH);

        //------AES--------
       JTextArea consola1 = new JTextArea();
       JLabel l3 = new JLabel("Numele cheii");
       p2.add(l3);
       JTextField t3 = new JTextField(20);
       p2.add(t3);
       
       JLabel l4 = new JLabel("Numele vectorului de initializare (IV)");
       p2.add(l4);
       JTextField t4 = new JTextField(20);
       p2.add(t4);

       JLabel l5 = new JLabel("Numele fisierului de intrare");
       p2.add(l5);
       JTextField t5 = new JTextField(20);
       p2.add(t5);


       
   
       JButton bc1 = new JButton("Criptare");
       bc1.setBounds(20,20,30,40);
       bc1.addActionListener(new ActionListener() {
   
       @Override
       public void actionPerformed(ActionEvent e) {
           int pos1 = consola1.getCaretPosition();
       try {
           
           //RSAalg.cryptRSA(t3.getText(), t4.getText() );

           SecretKey key = aes.loadKey(t3.getText());
        
           String algorithm = "AES/CBC/PKCS5Padding";
        
           //IvParameterSpec iv = aes.generateIv();
           IvParameterSpec iv = aes.loadIv(t4.getText());
   
           System.out.print("Numele fisierului: ");
           String fName = t5.getText();
   
           String inputFile = "Licenta//AES//Clar//";
           inputFile = inputFile+fName;
   
           String outputFile = "Licenta//AES//Criptat//"; 
           outputFile = outputFile+fName;
           String encryptedFile = outputFile;
           
           String decryptedFile = "Licenta//AES//Dec//";
           decryptedFile = decryptedFile+fName;
   
           aes.encryptFile(algorithm, key, iv, inputFile, outputFile);


           consola1.insert("Fisierul a fost criptat cu succes.\n", pos1);
   
       } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
               | IllegalBlockSizeException | BadPaddingException | IOException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
           
           consola1.insert("Verificati numele fisierelor\n", pos1);
   
       } catch (InvalidAlgorithmParameterException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
   
       }
       });
       p2.add(bc1);
   
       JButton bd1 = new JButton("Decriptare");
       bd1.setBounds(40,40,60,60);
       bd1.addActionListener(new ActionListener() {
   
       @Override
       public void actionPerformed(ActionEvent e) {
       int pos1 = consola1.getCaretPosition();
   
       try {
   
        SecretKey key = aes.loadKey(t3.getText());
        
        String algorithm = "AES/CBC/PKCS5Padding";
     
        //IvParameterSpec iv = aes.generateIv();
        IvParameterSpec iv = aes.loadIv(t4.getText());

        System.out.print("Numele fisierului: ");
        String fName = t5.getText();

        String inputFile = "Licenta//AES//Clar//";
        inputFile = inputFile+fName;

        String outputFile = "Licenta//AES//Criptat//"; 
        outputFile = outputFile+fName;
        String encryptedFile = outputFile;
        
        String decryptedFile = "Licenta//AES//Dec//";
        decryptedFile = decryptedFile+fName;

        aes.decryptFile(algorithm, key, iv, encryptedFile, decryptedFile);


       consola1.insert("Fisierul a fost decriptat cu succes.\n", pos1);
   
       } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
       | IllegalBlockSizeException | BadPaddingException | IOException e1) {
       // TODO Auto-generated catch block
       e1.printStackTrace();
       consola1.insert("Verificati numele fisierelor\n", pos1);
   
       } catch (InvalidAlgorithmParameterException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }}});
   
       p2.add(bd1);

       JButton bg1 = new JButton("Generare cheie + IV");
       bg1.setBounds(20,20,30,40);
       bg1.addActionListener(new ActionListener() {
   
       private IvParameterSpec generateIv;

    @Override
       public void actionPerformed(ActionEvent e) {
            int posiv = consola1.getCaretPosition();
            int keyByte = 128;
           
        try {
            generateIv = aes.generateIv(t4.getText());
            SecretKey key = aes.generateKey(keyByte, t3.getText());
            consola.insert("Fisierul IV este generat\n", posiv);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            consola1.insert("Fisierul IV nu este generat\n", posiv);

        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }   

       
    }});
   
       p2.add(bg1);

       p2.add(consola1, BorderLayout.SOUTH);
   
       //-------------------------------
       JTextArea consolaHash = new JTextArea();
       JTextArea consolaHashRSApr = new JTextArea();
       JTextArea consolaHashRSApu = new JTextArea();



    JLabel lhash = new JLabel("Numele cheii");
    p3.add(lhash);
    JTextField thash = new JTextField(20);
    p3.add(thash);

    JButton bHAES = new JButton("Hash cheie AES");
    bHAES.setBounds(20,20,30,40);
    bHAES.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int poshaes = consolaHash.getCaretPosition();
            System.out.println(thash.getText());
            String loc = "Licenta//AES//Cheie//";
            String fileName=thash.getText();
            loc=loc.concat(fileName);
            loc=loc.concat(".txt");
            //consolaHash.insert("-------Hash-------.\n", poshaes);
            System.out.println(thash.getText());
            String location = loc;
			File file = new File(location);
                try {
                //MD5 algorithm
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                
                //checksum
                var checksumMD5 = hashAlg.getFileChecksum(md5Digest, file);
                

                //SHA-1 algorithm
                MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
                
                //SHA-1 checksum 
                var checksumSHA256 = hashAlg.getFileChecksum(shaDigest, file);
                
                System.out.println(checksumMD5 + " " + checksumSHA256);
                
                consolaHash.insert(checksumMD5, poshaes);
                consolaHash.insert("\nVerificare HASH MD5: ", poshaes);
                
                consolaHash.insert(checksumSHA256, poshaes);
                consolaHash.insert("\nVerificare HASH SHA-256: ", poshaes);
                consolaHash.insert("\nAES - verificare cheie\n", poshaes);

                } catch (IOException | NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }
    });



    JButton bHRSApr = new JButton("Hash cheie Privata RSA");
    bHRSApr.setBounds(20,20,30,40);
    bHRSApr.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            int posrsapr = consolaHash.getCaretPosition();

            String loc = "Licenta//RSA//CheiePrivata//PrivateKey-";
            String fileName=thash.getText();
            loc=loc.concat(fileName);
            loc=loc.concat(".key");
            //consolaHash.insert("-------Hash-------.\n", posrsapr);

            String location = loc;
			File file = new File(location);
			
            try {
                //MD5 algorithm
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                
                //checksum
                var checksumMD5 = hashAlg.getFileChecksum(md5Digest, file);
                

                //SHA-1 algorithm
                MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
                
                //SHA-1 checksum 
                var checksumSHA256 = hashAlg.getFileChecksum(shaDigest, file);

                consolaHash.insert(checksumMD5, posrsapr);
                consolaHash.insert("\nVerificare HASH MD5: ", posrsapr);
                
                consolaHash.insert(checksumSHA256, posrsapr);
                consolaHash.insert("\nVerificare HASH SHA-256: ", posrsapr);
                consolaHash.insert("\nRSA - verificare cheie privata", posrsapr);

                } catch (IOException | NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }
        
    });
    
    JButton bHRSApu = new JButton("Hash cheie Publica RSA");
    bHRSApu.setBounds(20,20,30,40);
    bHRSApu.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int posrsapu = consolaHash.getCaretPosition();

            String loc = "Licenta//RSA//CheiePublica//PublicKey-";
            String fileName=thash.getText();
            loc=loc.concat(fileName);
            loc=loc.concat(".key");
            //consolaHash.insert("-------Hash-------.\n", posrsapu);

            String location = loc;
			File file = new File(location);
			
            try {
                //MD5 algorithm
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                
                //checksum
                var checksumMD5 = hashAlg.getFileChecksum(md5Digest, file);
                

                //SHA-1 algorithm
                MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
                
                //SHA-1 checksum 
                var checksumSHA256 = hashAlg.getFileChecksum(shaDigest, file);

                consolaHash.insert(checksumMD5, posrsapu);
                consolaHash.insert("\nVerificare HASH MD5: ", posrsapu);
                
                consolaHash.insert(checksumSHA256, posrsapu);
                consolaHash.insert("\nVerificare HASH SHA-256: ", posrsapu);
                consolaHash.insert("\nRSA - verificare cheie publica", posrsapu);

                } catch (IOException | NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }
        
    });

    p3.add(bHAES);
    p3.add(bHRSApr);
    p3.add(bHRSApu);
    p3.add(consolaHash, BorderLayout.SOUTH);



}    



}
