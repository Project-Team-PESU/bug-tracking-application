package bugtrackingapplication;


import java.sql.SQLException;
import java.util.Scanner;

//import org.apache.commons.lang3.RandomStringUtils;

class Startup {
    
	public void showDashboard(Scanner sc, String[] handler) throws SQLException {

		switch(handler[1]) {
			case "Admin":
				AdminModule.showDashboard(handler[0]);
				break;
			case "Project Manager":
				PMController pmc = new PMController();
				pmc.showDashboard(sc, handler[0]);
				break;
			case "Developer":
				DevModule.showDashboard(handler[0]);
				break;
			case "Tester":
				TesterModule.showDashboard(handler[0]);
				break;
		}
	}
	
	public void startUpApp() throws SQLException {
		
		char new_user; 
		boolean loop = true;
		String[] handler;
		
		// Predefined standard input object
		Scanner sc = new Scanner(System.in);
		
		System.out.println("***************** BUG TRACKER ********************");
		
		while(loop) {
			System.out.print("\tNew User? [Y/n] ");
			new_user = sc.next().charAt(0);
			sc.nextLine();
			
			switch(new_user) {
				case 'Y' : case 'y':
					
					loop = false;
					SignupModule sm = new SignupModule();
					LoginModule lm = new LoginModule();
					sm.createNewUser(sc);
					handler = lm.handleLogin(sc);
					
					if (handler[1].equals("Project Manager"))
						showDashboard(sc, handler);
					break;
					
				case 'n' : case 'N' :
					
					loop = false;
					
					LoginModule nlm = new LoginModule();
					handler = nlm.handleLogin(sc);
					showDashboard(sc, handler);
					break;
					
				default:
					break;
			}
		}
		
		sc.close();
	}
}