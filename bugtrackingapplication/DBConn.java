package bugtrackingapplication;

import java.sql.*;  

/**
 * Connect the Java application with the MySQL database
 * Prerequisite : Load the mysqlconnector.jar file
 * @authors Anushka Hebbar, Anusha Kabber, Arushi Kumar
 * 
 */
class DBConn {  
    
	private static final String URL = "jdbc:mysql://localhost:3306/bugtracker";
    private static String USERNAME = "root";
    private static String PASSWORD = "password";
    
    public static void main(String args[]) throws SQLException {  
    
    }  

    public static Connection getConnection() throws SQLException {
        try {
        	// Driver class for MySQL 
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Pass in connection URL, username and password
            // Connection URL - API:DB://server-IP-and-port-where-SQL-runs/db-name
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            return con;
        } catch (Exception e) {
        	System.out.println("Exception occurred during DB connectivity: ");
        	System.out.println(e);
            e.printStackTrace();
        }
        
        return null;
    }
}  