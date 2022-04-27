package bugtrackingapplication;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

class LoginController {
	
    public String[] handleLogin(Scanner sc) throws SQLException {
    	
    	int role; 
    	boolean loop = true;
    	
    	String username, password, query;
    	String[] handles = new String[2];
        
    	System.out.println("\n\t************ LOGIN ************");
    	
    	System.out.println("\tAdmin : 1\n\tProject Manager : 2\n\tDeveloper : 3\n\tTester : 4");
    	
    	while (loop) {
	    	System.out.print("\n\tEnter your role > ");
			
	    	try {
	    		role = sc.nextInt(); sc.nextLine();
	    	} catch (InputMismatchException e) {
	    		System.out.println("Invalid role entry. Please retry.");
	    		continue;
	    	}
	    	
	    	System.out.print("\tUsername > ");
			username = sc.nextLine();
			
			System.out.print("\tPassword > ");
			password = sc.nextLine();
			
			switch(role) {
				case 1:
					handles[1] = "Admin";
		            query = "SELECT * FROM admins where username = ? and password = ?";
		            break;
				case 2:
					handles[1] = "Project Manager";
		            query = "SELECT * FROM projectmanagers where username = ? and password = ?";
		            break;
				case 3:
					handles[1] = "Developer";
		            query = "SELECT * FROM developers where username = ? and password = ?";
		            break;
				case 4:
					handles[1] = "Tester";
		            query = "SELECT * FROM testers where username = ? and password = ?";
		            break;
				default:
					System.out.println("\tRole does not exist. Please retry.");
					continue;
			}
			
			LoginModel lm = new LoginModel();
			int exists = lm.doesUserExist(username, password, query);
			
			switch(exists) {
			case 0:
				System.out.println("\tUser not found or password incorrect. Please retry.");
				continue;
			case -1:
				System.out.println("\tRole does not exist. Please retry.");
				continue;
			case 1:
				loop = false;
				handles[0] = username;
				break;
	        }
    	}
		return handles;
    }
}