package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

class SignupController {

    public void createNewUser(Scanner sc) throws SQLException {
    	
    	String username, password, fname, lname, role;
    	
    	System.out.println("\n\t************ SIGN UP ************");
		System.out.print("\tFirst Name : ");
		fname = sc.nextLine();
		System.out.print("\tLast Name : ");
		lname = sc.nextLine();
		System.out.print("\tUsername : ");
		username = sc.nextLine();
		System.out.print("\tPassword : ");
		password = sc.nextLine();
		System.out.print("\tRole (Admin/PM/Dev/Tester) : ");
		role = sc.nextLine();
		
		SignupModel sm = new SignupModel();
		
		sm.makeNewUser(fname, lname, username, password, role);	
    }
}