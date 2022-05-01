package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

class TestController {

	
	public void showDashboard(Scanner sc, String username) throws SQLException {	
		
		TestModel tsm = new TestModel(username);
        
        System.out.println("\n\t*********** DASHBOARD ***********");
        
        System.out.println("\tWelcome " + tsm.fname + " " + tsm.lname + "!");
        
        displayOptions(sc, tsm);
	}
	
	private void displayOptions(Scanner sc, TestModel tsm) throws SQLException {
		
		boolean loop = true;
		
		System.out.println("\n\t****** MENU (Choose option) ****");
		System.out.println("\t1. View all projects\n"
						 + "\t2. View team members\n"
						 + "\t3. Add new bug to project\n"
						 + "\t4. Sign out");
		do {
			System.out.print("\n\tEnter your option > ");
			int option = sc.nextInt(); sc.nextLine();
			
			switch(option) {
			case 1:
				tsm.viewProjects();
				break;
			case 2:
				tsm.viewTeamMembers(sc);
				break;
			case 3:
				tsm.addNewBug(sc);
				break;
			case 4:
				loop = false;
				break;
			default:
				System.out.println("\tInvalid option. Please retry.");
				break;
			}
		} while(loop);
	}
}