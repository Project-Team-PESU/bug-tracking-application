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
    
	public void showDashboard(String username) throws SQLException {
		// TODO Auto-generated method stub
		
		PreparedStatement statement = null;
        ResultSet result = null;
        
        String query;
        query = "SELECT * FROM projectmanagers where username = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            result = statement.executeQuery();
            if(result.next())
                System.out.println(result);
            else
            	System.out.println("Oh no!");
        } catch (SQLException e){
        	e.printStackTrace();
        } finally {
            statement.close();
            result.close();
        }
        
		System.out.println("Welcome");
	}
	
}