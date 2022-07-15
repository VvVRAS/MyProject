/* Cifrul Vigener este foarte asemanator cu codul lui Caesar, diferenta
* este data de faptul ca in cifrul Vigener sunt folosite mai multe chei de criptarea
* Sistemul de criptare Vigener este o metoda de criptare a textului ce are la baza
* codul lui Caesar
* - este o metoda polialfabetica de subtiotuie
* - foarte usor de implementat si de inteles
* - a fost considerat "cifrul indescifrabil" pana in secolul 16 cand a fost "spart"
* Problema codului lui Caesar era ca avea cateva chei pentru criptarea textului, iar codul 
* Vigener folosea cuvinte pe post de chei private
* cuvantul folosit pe post de cheie arata cate litere erau mitate in mesaj
*/
package Licenta;

public class VigenereApp {
    public static void main(String[] args) {
        VigenereAlgorithm cipher = new VigenereAlgorithm();
        
        String message = "Testare Algoritm Vigenere";
        String key = "Secret";

        //Criptare
        String cipherText = cipher.encrypt(message, key);
        System.out.println(cipherText);
        
        //Decriptare
        System.out.println(cipher.decrypt(cipherText, key));
    }
}
