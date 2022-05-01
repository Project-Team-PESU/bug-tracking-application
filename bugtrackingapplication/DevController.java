package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

class DevController {

	
	public void showDashboard(Scanner sc, String username) throws SQLException {	
		
		DevModel dvm = new DevModel(username);
        
        System.out.println("\n\t*********** DASHBOARD ***********");
        
        System.out.println("\tWelcome " + dvm.fname + " " + dvm.lname + "!");
        
        displayOptions(sc, dvm);
	}
	
	private void displayOptions(Scanner sc, DevModel dvm) throws SQLException {
		
		boolean loop = true;
		
		System.out.println("\n\t****** MENU (Choose option) ****");
		System.out.println("\t1. View all projects\n"
						 + "\t2. View team members\n"
						 + "\t3. View assigned bugs\n"
						 + "\t4. Change assigned bug status\n"
						 + "\t5. Sign out");
		do {
			System.out.print("\n\tEnter your option > ");
			int option = sc.nextInt(); sc.nextLine();
			
			switch(option) {
			case 1:
				dvm.viewProjects();
				break;
			case 2:
				dvm.viewTeamMembers(sc);
				break;
			case 3:
				dvm.viewAssignedBugs(sc);
				break;
			case 4:
				dvm.changeAssignedBugStatus(sc);
				break;
			case 5:
				loop = false;
				break;
			default:
				System.out.println("\tInvalid option. Please retry.");
				break;
			}
		} while(loop);
	}
}