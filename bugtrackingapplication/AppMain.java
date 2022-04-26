package bugtrackingapplication;

import java.sql.SQLException;

/**
 * Driver class of the entire CLI application 
 * Loads the singleton class : Startup 
 * @authors Anushka Hebbar, Anusha Kabber, Arushi Kumar
 *
 */
class AppMain {
	
	public static void main(String[] args) {
		try {
			Startup instance = Startup.getInstance();
			instance.startUpApp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}