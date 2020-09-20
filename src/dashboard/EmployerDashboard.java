package dashboard;

import java.util.Scanner;

import app_exceptions.InvalidComplaintHierarchyException;
import app_items.Interview;
import app_items.Job;
import app_users.Applicant;
import app_users.Employer;
import app_users.User;

public class EmployerDashboard {
	
	private Employer user;
	
	public EmployerDashboard(Employer user)
	{
		this.user = user;
	}
	
	private enum EmployerMenu {
		
		ViewProfile,
		EditProfile,
		CreateJob,
		ViewJobs,
		ShortlistCandidate,
		ViewInterviews,
		RecordResult,
		SendOffer,
		SendComplaint,
		Exit

	}
	
	public void displayMainMenu()
	{
		String exitProg = "n";
		do
		{
			EmployerMenu choice = getResponse();
			Scanner scan = new Scanner(System.in);
			
			switch(choice)
			{
				case ViewProfile:
					user.showEmployerProfile();
					break;
				case EditProfile:
					break;
				case CreateJob:
					user.createJob();
					break;
				case ViewJobs:
					user.showAllJobs();
					break;
				case ShortlistCandidate:
				try {
					user.shortListCandidates();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					break;
				case ViewInterviews:
					user.showInterview();
					break;
				case RecordResult:
				try {
					user.recordResult();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					break;
				case SendOffer:
					user.showAllJobs();
					System.out.print("Enter job ID for which you want to send offer ");
					String jobID = scan.nextLine();
					Job job = user.getJobDetails(jobID);
					user.sendJobOffers(job);
				case SendComplaint:
					try {
						user.sendComplaint();
					} catch (NullPointerException e) {
						System.err.println("Invalid username");
					} catch (InvalidComplaintHierarchyException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
					break;
				case Exit:
					break;
			}
		}while(!exitProg.equalsIgnoreCase("y"));
		
	}
	
	public EmployerMenu getResponse()
	{
		boolean runAgain = true;
		
		EmployerMenu choice = null;
		
		System.out.println("************Menu************");
		System.out.println("1. View Profile\n"
						 + "2. Edit Profile\n"
						 + "3. Create Job\n"
						 + "4. View Jobs\n"
						 + "5. Shortlist Candidate\n"
						 + "6. View Interviews\n"
						 + "7. Record Result\n"
						 + "8. Send Offer\n"
						 + "9. Send Complaint\n"
						 + "10. Exit");
		
		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = EmployerMenu.values()[userResponse];
				
				//System.out.println("This is choice in get response " + choice);
						
				if(userResponse >= 0 && userResponse < 10)
				{
					runAgain = false;
				}
				else
				{
					System.out.println("Please enter numbers in the specifed range");
					runAgain = true;
				}
			}
			catch(Exception ex)
			{
				System.err.println("Please enter a valid option or press 7 to exit");
				runAgain = true;
			}
			
			//menuScan.close();
			
		}while(runAgain);
		
		return choice;
	}

	


	

}
