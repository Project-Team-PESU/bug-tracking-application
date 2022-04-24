package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class LoginModule {
	
	Connection connection;
	
    public LoginModule() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    public String[] handleLogin(Scanner sc) throws SQLException {
    	
    	int role; 
    	boolean loop = true;
    	boolean wrong = false;
    	
    	String username, password, query;
    	String[] handles = new String[2];
    	
    	PreparedStatement statement = null;
    	ResultSet result = null;
        
    	System.out.println("\n\t************ LOGIN ************");
    	
    	System.out.println("\tAdmin : 1\n\tProject Manager : 2\n\tDeveloper : 3\n\tTester : 4");
    	while (loop) {
	    	System.out.print("\tEnter your role : ");
			
	    	role = sc.nextInt(); sc.nextLine();
	    	
	    	// TODO: Do not allow duplicate usernames!
	    	System.out.print("\tUsername : ");
			username = sc.nextLine();
			
			System.out.print("\tPassword : ");
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
					query = "";
					break;
			}
	        try {
	        	if (query.equals("")) 
	        		wrong = true;
	        	else {
		            statement = this.connection.prepareStatement(query);
		            statement.setString(1, username);
		            statement.setString(2, password);
		            
		            result = statement.executeQuery();
	        	}
	        	
	            if (!wrong) {
	            	if (result.next()) {
		            	handles[0] = username;
		            	return handles;
	            	} else
	            		System.out.println("\tUser not found or password incorrect. Please retry.");
	            } else 
	            	System.out.println("\tRole does not exist. Please retry.");
	        } catch (SQLException e) {
	        	System.out.println("Exception occurred in LoginModule.");
	        	e.printStackTrace();
	        } finally {
	        	if (!wrong) {
		        	statement.close();
		            result.close();
	        	}
	        }
    	}
		return handles;
    }
}