package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class DevModel {

	String fname, lname, username;
	
	Connection connection;
	
    public DevModel(String username) throws SQLException {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set the PM instance's variables
        getDevName(username);
    }
    
    public void getDevName(String username) throws SQLException {
    	
    	String fname, lname;
    	
    	this.username = username;
    	
    	PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query;
        query = "SELECT * FROM developers where username = ?";
        
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
            	System.out.println("Error in DevModel: getDevName()");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }
    
    public void viewProjects() throws SQLException {
		PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query, pID, pName, pDesc;
    	
    	java.sql.Date pDeadlineInit;
    	java.util.Date pDeadline;
    	
        query = "SELECT TeamMembers.projectID, projectName, projectDesc, projectDeadline FROM TeamMembers join project on TeamMembers.projectID = project.projectID where empUName = ?";
        
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
    
    public void executeTeamQueries(String pID, String query, String role) throws SQLException {
		
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
	         	            	
	            	System.out.println("\t   * " + username + " - " + fname + ' ' + lname + " - " + role);
	            	
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
        viewProjects();
        System.out.print("\tEnter the project ID of the team you want to view > ");
        
        pID = sc.nextLine();
        
        System.out.println("\n\t    Team Members of Project " + pID);
        System.out.println("\t-----------------------------------");
        
        executeTeamQueries(pID, query1, "Developer");
        executeTeamQueries(pID, query2, "Project Manager");
        executeTeamQueries(pID, query3, "Admin");
        executeTeamQueries(pID, query4, "Tester");
	}

	public void getAssignedBugsWithProjectID(String pID) throws SQLException {
		
		String query;
		
		String bugID, bugStatus, bugPriority, bugOwnerUName, bugFixerUName, createdDate,
		resolvedDate, lastUpdatedDate, description, resolutionDesc;

		PreparedStatement statement = null;
		ResultSet result = null;

		System.out.println("\tDetails of bugs registered in Project " + pID);
        
        query = "SELECT * FROM bug WHERE bugFixerUName = ? and projectID = ?";
        
        try {
	        statement = connection.prepareStatement(query);
	        statement.setString(1, this.username);
	        statement.setString(2, pID);
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
	
	public String viewAssignedBugs(Scanner sc) throws SQLException {
		
		String pID;
		
		System.out.println("\tThe following are your projects and their IDs. ");
        viewProjects();
        System.out.print("\n\tEnter the project ID of the team you want to view > ");
        pID = sc.nextLine();
        
        getAssignedBugsWithProjectID(pID);
        
        return pID;
	}
	
	public void changeAssignedBugStatus(Scanner sc) throws SQLException {
		
		int status;
		String pID, bID, update;
		
		PreparedStatement statement = null;
        
        pID = viewAssignedBugs(sc);
        
        System.out.print("\n\tEnter the bug ID of the bug to be altered > ");
        bID = sc.nextLine();
        
        System.out.print("\n\tEnter status of the bug ('INITIATED' - 1, 'RESOLVED' - 2, 'OVERDUE' - 3) to be changed into > ");
        status = sc.nextInt(); sc.nextLine();
        
        update = "UPDATE bug SET bugStatus=? WHERE bugID=? AND projectID=?";
        
        try {
            statement = this.connection.prepareStatement(update);
            statement.setInt(1, status);
            statement.setString(2, bID);
            statement.setString(3, pID);
            statement.execute();
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            statement.close();
        }
        
	}
}