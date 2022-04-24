package bugtrackingapplication;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class PMModule {
	
	String fname, lname, empID, username;
	
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
    	
    	this.username = username;
    	
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

	public void viewProjects() throws SQLException {
		PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query, pID, pName, pDesc;
    	
    	java.sql.Date pDeadlineInit;
    	java.util.Date pDeadline;
    	
        query = "SELECT * FROM project where projectManagerUName = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, this.username);
            result = statement.executeQuery();
            if (!result.next())
            	System.out.println("Error in PMController: getName()");
            else {
	            do {
	                pID = result.getString("projectID");
	            	pName = result.getString("projectName");
	            	pDesc = result.getString("projectDesc");
	            	pDeadlineInit = result.getDate("projectDeadline");
	            	
	            	pDeadline = new java.util.Date(pDeadlineInit.getTime());
	            	
	            	System.out.println("\tProject Details");
	            	System.out.println("ID : " + pID);
	            	System.out.println("Name : " + pName);
	            	System.out.println("Description : " + pDesc);
	            	System.out.println("Deadline : " + pDeadline);
	            	
	            } while (result.next());
            }
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
	}

	public void viewTeam() {
		
		
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