package DATABASE;
import java.util.Scanner;

import javax.xml.namespace.QName;

public class jdb {
	public static void main(String[] args) {
		
		//Variabile
		int i = 1;

		//inserare clase
		cls clear = new cls();
		menu men = new menu();
		Scanner in = new Scanner(System.in);

		// cod sursa
		while (i < 2) {// Iesire din loop [do while]
			//clear.cls();
			men.menu();
		
			System.out.print("Selectati intrarea din meniu: ");
			int selectEntry = in.nextInt();
	
			switch (selectEntry) {
				case 1:
					//clear.cls();
					System.out.println("------------------\n|Adaugare angajat|\n------------------"); 
					dbquerry.addData(); //adaugare angajat
					break;
				case 2:
					//clear.cls();
					System.out.println("------------------\n|Stergere angajat|\n------------------"); 
					dbquerry.rem(); //stergere angajat
					break;
				case 3:
					//clear.cls();
					System.out.println("------------------\n|Afisare angajati|\n------------------"); 
					dbquerry.show();
					break;
				case 4:
					//clear.cls();
					System.out.println("------------------\n|Afisare departamente|\n------------------"); 
					dbquerry.showdep();
					break;
				case 5:
					//clear.cls();
					break;
				case 99:
				//	clear.cls();
					i = 3;
					break;
				default:
					System.out.println("Ati selectat o intrare inexistenta");
			
			}
	
		}
		
	
	}
}


//export CLASSPATH=/home/usr/Documents/Java/lib/mysql-connector-java-8.0.25.jar:$CLASSPATH
