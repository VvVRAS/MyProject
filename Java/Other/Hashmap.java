import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class Hashmap {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("exemplu.txt"));
        HashMap<String, Integer> fcuv = new HashMap();

        while (in.hasNextLine()) {
            String linie = in.nextLine();
            String[] cuvinte = linie.split("[ ,.;:?!]+");
            for (String cuvant : cuvinte)
                if (fcuv.containsKey(cuvant))
                    fcuv.put(cuvant, fcuv.get(cuvant) + 1);
                else
                    fcuv.put(cuvant, 1);
        }

        System.out.println("Frecventele cuvintelor din fisier: ");
        for (Map.Entry<String, Integer> aux : fcuv.entrySet())
            System.out.println(aux.getKey() + " -> " + aux.getValue());

        in.close();

    }
}
