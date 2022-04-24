package bugtrackingapplication;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

class PMModule {

	Connection connection;
	
    public PMModule() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    public String[] getName(String username) throws SQLException {
    	
    	String fname, lname, empID;
    	
    	PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query;
        query = "SELECT * FROM projectmanagers where username = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            result = statement.executeQuery();
            if(result.next()) {
                fname = result.getString("fname");
            	lname = result.getString("lname");
            	empID = result.getString("empID");
            	
            	System.out.println("Welcome " + fname + " " + lname + "!");
            	
            	String[] empinfo = {fname, lname, empID}; 
            	return empinfo;
            }
            else
            	System.out.println("Oh no!");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
        
        return new String[3];
    }
    
	public void showDashboard(String username) throws SQLException {	
		String fname, lname, empID;
        
        System.out.println("********* DASHBOARD *********");
        
        String[] empinfo = getName(username);
        fname = empinfo[0];
        lname = empinfo[1];
        empID = empinfo[2];
	}
	
}