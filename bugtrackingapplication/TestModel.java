package bugtrackingapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class TestModel {

	String fname, lname, username;
	
	Connection connection;
	
    public TestModel(String username) throws SQLException {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set the PM instance's variables
        getTesterName(username);
    }
    
    public void getTesterName(String username) throws SQLException {
    	
    	String fname, lname;
    	
    	this.username = username;
    	
    	PreparedStatement statement = null;
        ResultSet result = null;
        
    	String query;
        query = "SELECT * FROM testers where username = ?";
        
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
            	System.out.println("Error in TestModel: getDevName()");
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

    public int getGreatestBugID() throws SQLException {
    	
    	String query;
    	
        query = "SELECT bugID FROM bug ORDER BY bugID DESC LIMIT 1";
        
        PreparedStatement statement = null;
	    ResultSet result = null;
	    int newBug = 20;
	    
		try {
	        statement = connection.prepareStatement(query);
	        result = statement.executeQuery();
	        if (result.next()) {
	        	 String res = result.getString("bugID");
	        	 newBug = (Integer.parseInt(res) + 1);
	        }
	    } catch (SQLException e){
	    	e.printStackTrace();
	    } finally {
	        statement.close();
	        result.close();
	    }
		return newBug;
    }
    
    public void addNewBug(Scanner sc) throws SQLException {
    	
    	String insert, pID, descr;
    	int priority;
    	PreparedStatement statement = null;
        
    	viewProjects();
    	System.out.print("\tEnter the project in which bug should be created > ");
    	pID = sc.nextLine();
    	
    	System.out.print("\tEnter the priority of the new bug (LOW-1, MODERATE-2, HIGH-3) > ");
    	priority = sc.nextInt(); sc.nextLine();
    	
    	System.out.print("\tEnter the description of the new bug > ");
    	descr = sc.nextLine();
    	
        insert = "INSERT INTO bug (bugid, projectid, bugstatus, bugPriority, bugOwnerUName, Description) VALUES (?, ?, ?, ?, ?, ?)";
        int bugID = getGreatestBugID();
        
        try {
            statement = this.connection.prepareStatement(insert);
            statement.setString(1, Integer.toString(bugID));
            statement.setString(2, pID);
            statement.setString(3, "INITIATED");
            statement.setInt(4, priority);
            statement.setString(5, this.username);
            statement.setString(6, descr);
            
            statement.execute();
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            statement.close();
        }
    	
    }
}