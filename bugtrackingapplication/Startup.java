package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Singleton class that drives the application
 * @author nushkash
 *
 */
class Startup {
    
	private static Startup instance = null;
	
	public static Startup getInstance() {
		if (instance == null)
			instance = new Startup();
		
		return instance;
	}
	
	public void showDashboard(Scanner sc, String[] handler) throws SQLException {

		switch(handler[1]) {
			case "Admin":
//				AdminController amc = new AdminController();
//				amc.showDashboard(sc, handler[0]);
			case "Project Manager":
				PMController pmc = new PMController();
				pmc.showDashboard(sc, handler[0]);
				break;
			case "Developer":
				DevController dvc = new DevController();
				dvc.showDashboard(sc, handler[0]);
				break;
			case "Tester":
				TestController tsc = new TestController();
				tsc.showDashboard(sc, handler[0]);
				break;
		}
	}
	
	public void startUpApp() throws SQLException {
		
		char new_user; 
		boolean loop = true;
		String[] handler;
		
		// Predefined standard input object
		Scanner sc = new Scanner(System.in);
		
		while(loop) {
			System.out.println("\n***************** BUG TRACKER ********************");
			System.out.print("\tNew User? [Y/n] ");
			new_user = sc.next().charAt(0);
			sc.nextLine();
			
			switch(new_user) {
				case 'Y' : case 'y':
					SignupController sm = new SignupController();
					LoginController lc = new LoginController();
					
					sm.createNewUser(sc);
					System.out.println("\n\tPlease login with your credentials.");
					handler = lc.handleLogin(sc);
					
					
					showDashboard(sc, handler);
					break;
					
				case 'n' : case 'N' :

					LoginController nlc = new LoginController();
					handler = nlc.handleLogin(sc);
					
					showDashboard(sc, handler);
					
					break;
					
				default:
					break;
			}
		}
		
		sc.close();
	}
}