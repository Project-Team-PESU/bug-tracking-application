package bugtrackingapplication;

import java.sql.SQLException;

class AppMain {
	public static void main(String[] args) {
		
		Startup startup = new Startup();
		
		try {
			startup.startUpApp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}