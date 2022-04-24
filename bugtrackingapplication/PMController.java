package bugtrackingapplication;

import java.sql.SQLException;
import java.util.Scanner;

class PMController {

	public void showDashboard(Scanner sc, String username) throws SQLException {	
		
		PMModule pmm = new PMModule();
        
        System.out.println("\t********* DASHBOARD *********");
        
        pmm.getName(username);
        
        displayOptions(sc, pmm);
	}
	
	private void displayOptions(Scanner sc, PMModule pmm) throws SQLException {
		
		boolean loop = false;
		
		System.out.println("\t\t***** MENU (Choose option) ****");
		System.out.println("\t1. View all projects\n"
						 + "\t2. View team members\n"
						 + "\t3. View all bugs\n"
						 + "\t4. Change project status\n"
						 + "\t5. Assign/change bug fixer");
		do {
			System.out.print("Enter your option : ");
			int option = sc.nextInt(); sc.nextLine();
			
			switch(option) {
			case 1:
				pmm.viewProjects();
				break;
			case 2:
				pmm.viewTeam();
				break;
			case 3:
				pmm.viewBugs();
				break;
			case 4:
				pmm.changeProjectStatus();
				break;
			case 5:
				pmm.assignBugFixer();
				break;
			default:
				System.out.print("Invalid option.");
				loop = true;
				break;
			}
		} while(loop);
	}
}