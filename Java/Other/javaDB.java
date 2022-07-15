import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class javaDB {

    public static void main(String[] args) {

        String pushEnter;
        Scanner in = new Scanner(System.in);

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
}