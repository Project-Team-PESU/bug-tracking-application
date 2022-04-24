package bugtrackingapplication;

class Employee {
	
	public String employee_id;
	public String username;
	private String password;
	
	public Employee(String emp_id, String username, String password) {
		this.employee_id = emp_id;
		this.username = username;
		this.setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}
	
	public void viewAssignedTasks() {
		
	}
	
	public void viewTeam() {
		
	}
	
	public void viewAssignedBugDetails() {
		
	}
	
	public void viewAssignedProjectStatus() {
		
	}
	
	public void viewAllProjects() {
		
	}
}