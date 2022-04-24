package bugtrackingapplication;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//import org.apache.commons.lang3.RandomStringUtils;

class Startup {
	
	Connection connection;
	
    public Startup() {
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
    
    public boolean handleLogin(Scanner sc) throws SQLException {
    	
    	int role; boolean loop = true;
    	String empID, username, password, fname, lname, query;
    	PreparedStatement statement = null;
    	ResultSet result = null;
        
    	System.out.println("\n\t************ LOGIN ************");
    	
    	while (loop) {
	    	System.out.print("\tAdmin-1\n\tProject Manager-2\nDeveloper-3\nTester-4\nEnter Number of your Role: ");
			
	    	role = sc.nextInt(); sc.nextLine();
	    	
	    	System.out.print("\tUsername : ");
			username = sc.nextLine();
			
			System.out.print("\tPassword : ");
			password = sc.nextLine();
			
			if (role == 1)
	            query = "SELECT * FROM admins where username = ? and password = ?";
			
	        else if (role == 2)
	            query = "SELECT * FROM projectmanagers where username = ? and password = ?";
			
	        else if (role == 3)
	        	query = "SELECT * FROM developers where username = ? and password = ?";
			
	        else
	            query = "SELECT * FROM testers where username = ? and password = ?";
			
	        try {
	            statement = this.connection.prepareStatement(query);
	            statement.setString(1, username);
	            statement.setString(2, password);
	            
	            result = statement.executeQuery();
	            
	            if(result.next())
	                return true;
	            else 
	                System.out.println("User not found. Please retry.");
	        } 
	        catch (SQLException e) {
	            return false;
	        } 
	        finally {
	        	statement.close();
	            result.close();
	        }
    	}
		return false;
    }
    
	public void startUpApp() throws SQLException {
		
		char new_user; 
		boolean loop = true;
		
		// Predefined standard input object
		Scanner sc = new Scanner(System.in);
		
		System.out.println("***************** BUG TRACKER ********************");
		
		while(loop) {
			System.out.print("\tNew User? [Y/n] ");
			new_user = sc.next().charAt(0);
			sc.nextLine();
			
			switch(new_user) {
				case 'Y' : case 'y':
					loop = false;
					createNewUser(sc);
					handleLogin(sc);
					break;
				case 'n' : case 'N' :
					handleLogin(sc);
				default:
					break;
			}
		}
		
		sc.close();
	}
}