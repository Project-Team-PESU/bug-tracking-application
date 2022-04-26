package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class SignupModel {

	Connection connection;
	
    public SignupModel() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    public void makeNewUser(String fname, String lname, String username, String password, String role) throws SQLException {
    	
    	String insert;
    	PreparedStatement statement = null;
        
        if (role.equals("Admin"))
        	insert = "INSERT INTO admins VALUES (?, ?, ?, ?)";
        
        else if (role.equals("PM"))
        	insert = "INSERT INTO projectmanagers VALUES (?, ?, ?, ?)";
        
        else if (role.equals("Dev"))
        	insert = "INSERT INTO developers VALUES (?, ?, ?, ?)";
        
        else 
        	insert = "INSERT INTO testers VALUES (?, ?, ?, ?)";
        
        try {
            statement = this.connection.prepareStatement(insert);
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.execute();
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            statement.close();
        }
    }
}