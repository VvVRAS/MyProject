package Licenta;

class XOREncryption
{
    // In acest cod este folosita aceeasi clasa pentru criptare,
    // dar si pentru decriptare
    static String encryptDecrypt(String inputString)
    {
        // Definirea cheii XOR. Fucntioneaza cu orice caracter
        char xorKey = 'P';
 
        // Definirea variabilei de tip String, ce are drept
        // scop contruirea propozitiei sau a frazei decriptate
        // sau criptate
        String outputString = "";
 
        // calculul numarului de caractere din string
        int len = inputString.length();
 
        // criptarea sau decriptarea
        // propiu-zisa a fiecarui caracter in parte
        for (int i = 0; i < len; i++)
        {
            outputString = outputString + Character.toString((char) (inputString.charAt(i) ^ xorKey));
        }
 
        System.out.println(outputString);
        return outputString;
    }
 
    // Codul
    public static void main(String[] args)
    {
        String sampleString = "ABCabc123";
 
        // Variabila de tip String criptata
        System.out.println("Encrypted String");
        String encryptedString = encryptDecrypt(sampleString);
 
        // Variabila de tip string decriptata
        System.out.println("Decrypted String");
        encryptDecrypt(encryptedString);
    }
}
 