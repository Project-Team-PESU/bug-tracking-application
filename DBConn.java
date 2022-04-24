import java.sql.*;  

/**
 * Connect the Java application with the MySQL database
 * Prerequisite : Load the mysqlconnector.jar file
 * @authors Anushka Hebbar, Anusha Kabber, Arushi Kumar
 * 
 */
class DBConn {  
    
    public static void main(String args[]) {  

        try{  
            // Driver class
            Class.forName("com.mysql.jdbc.Driver");  

            // Pass in connection URL, username and password
            // Connection URL - API:DB://server-IP-and-port-where-SQL-runs/db-name
            Connection con = DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/bugtracker", "root", "password");  
            
            Statement stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery("select * from emp");  
            
            while(rs.next())  {
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
            }
            con.close();  
        } catch(Exception e) { 
            System.out.println("Exception occurred during DB connection : " + e);
        }  
    }  
}  