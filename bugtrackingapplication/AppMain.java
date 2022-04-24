package bugtrackingapplication;

import java.sql.SQLException;

class AppMain {
	public static void main(String[] args) {
		Startup startup = new Startup();
		try {
			startup.handleLogin();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}