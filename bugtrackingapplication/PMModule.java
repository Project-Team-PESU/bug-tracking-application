package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class PMModule {
	
	String fname, lname, empID;
	
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
    
    public void getName(String username) throws SQLException {
    	
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
            	
            	this.fname = fname;
            	this.lname = lname;
            	this.empID = empID;
            }
            else
            	System.out.println("Error in PMController: getName()");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
    }

	public void viewProjects() {
		PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query;
        query = "SELECT * FROM projects where username = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            result = statement.executeQuery();
            if(result.next()) {
                fname = result.getString("fname");
            	lname = result.getString("lname");
            	empID = result.getString("empID");
            	
            	System.out.println("Welcome " + fname + " " + lname + "!");
            	
            	this.fname = fname;
            	this.lname = lname;
            	this.empID = empID;
            }
            else
            	System.out.println("Error in PMController: getName()");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
	}

	public void viewTeam() {
		// TODO Auto-generated method stub
		
	}

	public void viewBugs() {
		// TODO Auto-generated method stub
		
	}

	public void assignBugFixer() {
		// TODO Auto-generated method stub
		
	}

	public void changeProjectStatus() {
		// TODO Auto-generated method stub
		
	}
}