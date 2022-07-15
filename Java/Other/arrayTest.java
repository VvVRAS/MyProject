import java.util.*;
public class arrayTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();  
        String name = null;
        String Surname = null;
        String fullName = null;
        for(int i = 0; i < 100; i++){
            System.out.print("Scrieti-va numele de familie: ");
            name = sc.nextLine();
            System.out.print("Scrieti-va prenumele: ");
            Surname = sc.nextLine();
                
            if (name.isEmpty() || Surname.isEmpty()) {
                break;
            } else {            
                fullName = name + "\t" + Surname;
            }
            list.add(Integer.valueOf(i), fullName);
            name = null;
            Surname = null;
              
        }
        
        System.out.println("Traversing List...");
        Integer ID = 0;
        System.out.println("ID  Name\tSurname");
        for(String i:list){  
            System.out.println(ID + "   " + i);
            ID++;  
        }  
    }
}
