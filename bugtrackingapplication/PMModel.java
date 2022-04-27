package bugtrackingapplication;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("unused")
class PMModel {
	
	String fname, lname, username;
	
	Connection connection;
	
    public PMModel(String username) throws SQLException {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Set the PM instance's variables
        getPMName(username);
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
    /**
     * Retrieve the project manager's first and last names
     * and sets as the object's instance variables
     * @param username : PM's username
     * @throws SQLException
     */
    public void getPMName(String username) throws SQLException {
    	
    	String fname, lname;
    	
    	this.username = username;
    	
    	PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query;
        query = "SELECT * FROM projectmanagers where username = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            result = statement.executeQuery();
            
            // Returns: a ResultSet object that contains the data produced by the given query; never null
            
            if(result.next()) {
                fname = result.getString("fname");
            	lname = result.getString("lname");
            	
            	this.fname = fname;
            	this.lname = lname;
            }
            else
            	System.out.println("Error in PMController: getPMName()");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
    }

    /**
     * Get details of all the projects handled by the project manager
     * @throws SQLException
     */
	public void viewPMProjects() throws SQLException {
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
            	System.out.println("Error in PMModule: viewPMProjects()");
            else {
            	System.out.println("\n\t\tYour Projects");
            	System.out.println("\t-----------------------------------");
            	int i = 1;
	            do {
	                pID = result.getString("projectID");
	            	pName = result.getString("projectName");
	            	pDesc = result.getString("projectDesc");
	            	pDeadlineInit = result.getDate("projectDeadline");
	            	
	            	pDeadline = new java.util.Date(pDeadlineInit.getTime());
	            	
	            	System.out.println("\tProject " + Integer.toString(i++));
	            	System.out.println("\t   * " + pName + " | ID " + pID);
	            	System.out.println("\t   * " + pDesc);
	            	System.out.println("\t   * Due on " + pDeadline);
	            	System.out.println();
	            } while (result.next());
            }
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
	}

	public void executeTeamQueries(String pID, String query) throws SQLException {
		
		String fname, username, lname;
		
		PreparedStatement statement = null;
	    ResultSet result = null;
	    
		try {
	        statement = connection.prepareStatement(query);
	        statement.setString(1, pID);
	        result = statement.executeQuery();
	        if (result.next())
	        	do {
	                username = result.getString("username");
	            	fname = result.getString("fname");
	            	lname = result.getString("lname");
	         	            	
	            	System.out.println("\t   * " + username + " - " + fname + ' ' + lname);
	            	
	            } while (result.next());
	    } catch (SQLException e){
	    	e.printStackTrace();
	    } finally {
	        statement.close();
	        result.close();
	    }
		
	}

	public void viewTeamMembers(Scanner sc) throws SQLException {
        
    	String query1, query2, query3, query4, pID;
    	
        query1 = "SELECT d.username, d.fname, d.lname "
        	  + "FROM developers d JOIN TeamMembers tm "
        	  + "ON d.username = tm.empUName "
        	  + "WHERE projectID = ?";
        query2 = "SELECT d.username, d.fname, d.lname "
          	  + "FROM projectmanagers d JOIN TeamMembers tm "
          	  + "ON d.username = tm.empUName "
          	  + "WHERE projectID = ?";
        query3 = "SELECT d.username, d.fname, d.lname "
            	  + "FROM admins d JOIN TeamMembers tm "
            	  + "ON d.username = tm.empUName "
            	  + "WHERE projectID = ?";
        query4 = "SELECT d.username, d.fname, d.lname "
          	  + "FROM testers d JOIN TeamMembers tm "
          	  + "ON d.username = tm.empUName "
          	  + "WHERE projectID = ?";
        
        System.out.println("\tThe following are your projects and their IDs. ");
        viewPMProjects();
        System.out.print("\tEnter the project ID of the team you want to view > ");
        
        pID = sc.nextLine();
        
        System.out.println("\n\t    Team Members of Project " + pID);
        System.out.println("\t-----------------------------------");
        
        executeTeamQueries(pID, query1);
        executeTeamQueries(pID, query2);
        executeTeamQueries(pID, query3);
        executeTeamQueries(pID, query4);
	}

	public void getBugsWithProjectID(String pID) throws SQLException {
		
		String query;
		
		String bugID, bugStatus, bugPriority, bugOwnerUName, bugFixerUName, createdDate,
		resolvedDate, lastUpdatedDate, description, resolutionDesc;

		PreparedStatement statement = null;
		ResultSet result = null;

		System.out.println("\tDetails of bugs registered in Project " + pID);
        
        query = "SELECT * FROM bug WHERE projectID = ?";
        
        try {
	        statement = connection.prepareStatement(query);
	        statement.setString(1, pID);
	        result = statement.executeQuery();
	        if (result.next()) {
	        	do {
	                bugID = result.getString("bugID");
	                bugStatus = result.getString("bugStatus");
	                bugPriority = result.getString("bugPriority");
	                bugOwnerUName = result.getString("bugOwnerUName");
	                bugFixerUName = result.getString("bugFixerUName");
	                createdDate = result.getString("createdDate");
	                resolvedDate = result.getString("resolvedDate");
	                lastUpdatedDate = result.getString("lastUpdatedDate");
	                description = result.getString("description");
	                resolutionDesc = result.getString("resolutionDesc");
	         	            	
	            	System.out.println("\n\t\tBug ID " + bugID + " Details");
	            	System.out.println("\t-----------------------------------");
	            	System.out.println("\t   * Status - " + bugStatus);
	            	System.out.println("\t   * Priority - " + bugPriority);
	            	System.out.println("\t   * Owner (Dev) - " + bugOwnerUName);
	            	System.out.println("\t   * Assigned to (Tester) - " + bugFixerUName);
	            	System.out.println("\t   * Created on - " + createdDate);
	            	System.out.println("\t   * Resolved on - " + resolvedDate);
	            	System.out.println("\t   * Last updated on - " + lastUpdatedDate);
	            	System.out.println("\t   * Description - " + description);
	            	System.out.println("\t   * Resolution Description - " + resolutionDesc);
	            	
	            } while (result.next());
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        statement.close();
	        result.close();
	    }
	}
	
	public void viewAllBugs(Scanner sc) throws SQLException {
		
		String pID;
		
		System.out.println("\tThe following are your projects and their IDs. ");
        viewPMProjects();
        System.out.print("\n\tEnter the project ID of the team you want to view > ");
        pID = sc.nextLine();
        
        getBugsWithProjectID(pID);
	}

	public void viewDevsOnTeam(String pID) throws SQLException {
        
    	String query;
    	
        query = "SELECT d.username, d.fname, d.lname "
        	  + "FROM developers d JOIN TeamMembers tm "
        	  + "ON d.username = tm.empUName "
        	  + "WHERE projectID = ?";
        
        System.out.println("\n\t\tDevelopers of Project " + pID);
        System.out.println("\t-----------------------------------");
        executeTeamQueries(pID, query);
	}

	public void assignOrChangeBugFixer(Scanner sc) throws SQLException {
		
		String pID, bID, devID, update;
		
		PreparedStatement statement = null;
		
		System.out.println("\tThe following are your projects and their IDs. ");
        viewPMProjects();
        System.out.print("\n\tEnter the project ID with the bugs to be altered > ");
        pID = sc.nextLine();
        
        getBugsWithProjectID(pID);
        
        System.out.print("\n\tEnter the bug ID of the bug to be altered > ");
        bID = sc.nextLine();
        
        System.out.print("\n\tThe following are the usernames of the developers on the team.");
        viewDevsOnTeam(pID);
        
        System.out.print("\tEnter the username of the new developer >");
        devID = sc.nextLine();
        
        update = "UPDATE bug SET bugFixerUName=? WHERE bugID=? AND projectID=?";
        
        try {
            statement = this.connection.prepareStatement(update);
            statement.setString(1, bID);
            statement.setString(2, devID);
            statement.setString(3, pID);
            statement.execute();
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            statement.close();
        }
        
	}
}