package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

class PMController {

	public void showDashboard(Scanner sc, String username) throws SQLException {	
		
		PMModel pmm = new PMModel(username);
        
        System.out.println("\n\t********* DASHBOARD *********");
        
        pmm.getPMName(username);
        
        displayOptions(sc, pmm);
	}
	
	private void displayOptions(Scanner sc, PMModel pmm) throws SQLException {
		
		boolean loop = true;
		
		System.out.println("\n\t\t***** MENU (Choose option) ****");
		System.out.println("\t1. View all projects\n"
						 + "\t2. View team members\n"
						 + "\t3. View all bugs\n"
						 + "\t4. Change project status\n"
						 + "\t5. Assign/change bug fixer\n"
						 + "\t6. Sign out\n");
		do {
			System.out.print("\n\tEnter your option : ");
			int option = sc.nextInt(); sc.nextLine();
			
			switch(option) {
			case 1:
				pmm.viewPMProjects();
				break;
			case 2:
				pmm.viewTeamMembers(sc);
				break;
			case 3:
				pmm.viewBugs(sc);
				break;
			case 4:
				pmm.changeProjectStatus();
				break;
			case 5:
				pmm.assignOrChangeBugFixer();
				break;
			case 6:
				loop = false;
				// TODO Handle sign out!
				break;
			default:
				System.out.println("\tInvalid option. Please retry.");
				break;
			}
		} while(loop);
	}
}