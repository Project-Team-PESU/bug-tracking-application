package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class LoginModel {
	
	Connection connection;
	
    public LoginModel() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    public int doesUserExist(String username, String password, String query) throws SQLException {
    	
    	boolean loop = true;
    	boolean wrong = false;
    	
    	PreparedStatement statement = null;
    	ResultSet result = null;
        
    	while (loop) {
	        try {
        	    statement = this.connection.prepareStatement(query);
	            statement.setString(1, username);
	            statement.setString(2, password);
	            
	            result = statement.executeQuery();
	        	
            	if (result.next())
	            	return 1;
            	else
            		return 0;
	        } catch (SQLException e) {
	        	System.out.println("Exception occurred in LoginModel.");
	        	e.printStackTrace();
	        } finally {
	        	if (!wrong) {
		        	statement.close();
		            result.close();
	        	}
	        }
    	}
		return -1;
    }
}