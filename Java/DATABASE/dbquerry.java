package DATABASE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class dbquerry {

	static String pushEnter;
	static Scanner in = new Scanner(System.in);

	// Adaugare Angajat -----------------------------------------------------------------------
	public static void addData() {	
        System.out.println("ADAUGARE ANGAJAT");

    	//Scanner in = new Scanner(System.in);
    	System.out.print("ID angajat: ");
    	String id = in.nextLine();

		if ( id == "0" ) {
			System.out.println("ID must be greaten than 0");
			id = in.nextLine();
		}

    	System.out.print("Nume angajat: ");
    	String name = in.nextLine();

    	System.out.print("Prenume angajat: ");
    	String surname = in.nextLine();

    	System.out.print("Nr. telef. angajat: ");
	    String phone = in.nextLine();

	    System.out.print("E-mail angajat: ");
	    String email = in.nextLine();

	    System.out.print("Departament: ");
	    String departament = in.nextLine();

        System.out.print("Domiciliu: ");
	    String domiciliu = in.nextLine();

        System.out.print("Data angajarii: ");
	    String hireDate = in.nextLine();
        
        System.out.print("Salariu: ");
	    int salary = in.nextInt();
	
	    try {	
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/db", "root", "javapass");

    		Statement statement = connection.createStatement();

	    	statement.executeUpdate("INSERT INTO Angajati VALUES ('" + id + "'" + ", " + "'" + name +
             "'" + ", " + "'" + surname + "'" + ", " + "'" + salary + "'" + ", " + "'" + hireDate +
              "'" + ", " + "'" + phone + "'" + ", " + "'" + email + "'" + ", " + "'" + departament +
               "'" + ", " + "'" + domiciliu + "'" + ");");

		} catch (Exception e) {
		    e.printStackTrace();
	    }
    }

	// Stergere angajat ----------------------------------------------------------------------------------------------
	protected static void rem() {

        System.out.println("Stergere angajat dupa id");

    	
		try {	
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/db", "root", "javapass");

    	    Statement statement = connection.createStatement();
            
			//Interogare db ID angajati
            ResultSet rs = statement.executeQuery("select id, name, surname from Angajati;"); 
	    	System.out.println("--------------------------------------------\nID\tNume\tPrenume\t\t\n--------------------------------------------");
            while (rs.next()){      //Afisare id angajati
                System.out.println("" + rs.getString(1) + "\t" + rs.getString(2) + " " + rs.getString(3) + " ");
                System.out.println("--------------------------------------------");
            }
            
            System.out.print("ID angajat: "); //scrie ID angajat
            String id = in.nextLine();

			if ( id == "0") {
				System.out.println("Exiting...");
			} 
            else {
            	//executare stergere angajat dupa ID
            	statement.executeUpdate("delete from Angajati where id=" + id + ";");
			}

        } catch (Exception e) {
		    e.printStackTrace();
	    }
        
    }

	// afisare angajati dupa nume ---------------------------------------------------------------------------------------
	protected static void show() {	
		
        System.out.println("Afisare angajati dupa nume");

    	
		try {	
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/db", "root", "javapass");

    	    Statement statement = connection.createStatement();
            
			//Interogare db ID angajati
            ResultSet rs = statement.executeQuery("select id, name, surname from Angajati;"); 
	    	System.out.println("--------------------------------------------\nID\tNume\tPrenume\t\t\n--------------------------------------------");
            while (rs.next()){      //Afisare id angajati
                System.out.println("" + rs.getString(1) + "\t" + rs.getString(2) + " " + rs.getString(3) + " ");
                System.out.println("--------------------------------------------");
            }
			
			System.out.println("Press \"Enter\" to exit.");
			pushEnter=in.nextLine();

        } catch (Exception e) {
		    e.printStackTrace();
	    }
        
    }

	// Afisare angajat departamente ---------------------------------------------------------------------------------------
	protected static void showdep() {

		 System.out.println("Afisare angajati dupa departament");
 
		 
		 try {	
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/db", "root", "javapass");
 
			 Statement statement = connection.createStatement();
			 
			 //Interogare db ID angajati
			 ResultSet rs = statement.executeQuery("select id, name, surname, departament from Angajati;"); 
			 System.out.println("--------------------------------------------\nID\tNume\tPrenume\tdepartament\t\n--------------------------------------------");
			 while (rs.next()){      //Afisare id angajati
				 System.out.println("" + rs.getString(1) + "\t" + rs.getString(2) + " " + rs.getString(3) + "\t" + rs.getString(4) + " ");
				 System.out.println("--------------------------------------------");
			 }
			 
			 System.out.print("\nPress \"Enter\" to exit.");
			 pushEnter=in.nextLine();
 
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 
	 }
}