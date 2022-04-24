package bugtrackingapplication;

import java.sql.*;
import java.util.*;

class Startup {
	
	Connection connection;
	
    public void Login() {
        try {
            this.connection = DBConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        return this.connection != null;
    }
    
	public static void main(String[] args) {
		
		System.out.println("Login as : \n1. Admin \n2. Project Manager\n3.Developer\n4.Tester");
		
		// Predefined standard input object
        Scanner sc = new Scanner(System.in);
        
        int inp = sc.nextInt();
        
        System.out.println(inp);
	}
	
	public boolean isLogin(String user, String pass,String option) throws  Exception{
        PreparedStatement statement = null;
        ResultSet result = null;
        String query;
        
        if(option.equals("Admin"))
            query = "SELECT * FROM admins where user = ? and pass = ?";
        else if(option.equals("ProjectManager"))
            query = "SELECT * FROM projectmanagers where user = ? and pass = ?";
        else
            query = "SELECT * FROM developers where user=? and pass = ?";
        try{
            statement = this.connection.prepareStatement(query);
            statement.setString(1,user);
            statement.setString(2,pass);
            result = statement.executeQuery();
            if(result.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            return false;
        }finally {
            statement.close();
            result.close();
	}
}
}