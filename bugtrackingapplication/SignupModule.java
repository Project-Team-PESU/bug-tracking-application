package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

class SignupModule {

	Connection connection;
	
    public SignupModule() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    public void createNewUser(Scanner sc) throws SQLException {
    	
    	String empID, username, password, fname, lname, role, insert;
    	PreparedStatement statement = null;
        
    	// TODO Implement random string creation for IDs
    	
//        empID = RandomStringUtils.randomAlphabetic(10);
        empID = "Emp123";
        
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
		
        if (role.equals("Admin"))
        	insert = "INSERT INTO admins VALUES (?, ?, ?, ?, ?)";
        
        else if (role.equals("PM"))
        	insert = "INSERT INTO projectmanagers VALUES (?, ?, ?, ?, ?)";
        
        else if (role.equals("Dev"))
        	insert = "INSERT INTO developers VALUES (?, ?, ?, ?, ?)";
        
        else 
        	insert = "INSERT INTO testers VALUES (?, ?, ?, ?, ?)";
        
        try {
            statement = this.connection.prepareStatement(insert);
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, empID);
            statement.execute();
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            statement.close();
        }
    }
}