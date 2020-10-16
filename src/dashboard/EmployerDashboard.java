package dashboard;

import java.util.Scanner;

import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_items.Job;
import app_users.Employer;
import app_users.UserDatabase;

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
		ShortlistApplicant,
		ViewInterviews,
		RecordResult,
		SendComplaint,
		Exit

	}
//
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
					try {
						user.showEmployerProfile();
					} catch (InvalidAccessRightsException e) {
						System.out.println(e.getMessage());
					}
					break;
				case EditProfile:
					break;
				case CreateJob:
					try {
						user.createJob();
					} catch (InvalidAccessRightsException e) {
						System.err.println(e.getMessage());
					}
					break;
				case ViewJobs:
					user.showAllJobs();
					break;
				case ShortlistApplicant:
				try {
					user.shortlistApplicants();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.err.println(e1.getMessage());;
				}
					break;
				case ViewInterviews:
					user.showInterview();
					break;
				case RecordResult:
					if(user.getProvisionallyBlacklistStatus())
					{
						System.err.println("Invalid access rights for operation");
					}
					else
					{
						user.showAllJobs();
						System.out.print("Enter job ID for which you want to send offer ");
						String jobID = scan.nextLine();
						Job job = user.fetchJob(jobID);
						try {
							user.recordResult(job);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							System.err.println(e1.getMessage());
						}
					}

					break;
				case SendComplaint:
					if(user.getProvisionallyBlacklistStatus())
					{
						System.err.println("Invalid access rights");
					}
					else
					{
						System.out.print("Enter username of applicant ");
						String complaintAbout = scan.next();
						System.out.print("Enter your complaint ");
						String complaintDesc = scan.next();
						try {
							user.sendComplaint(UserDatabase.fetchUser(complaintAbout), complaintDesc);
						} catch (NullPointerException e) {
							System.err.println("Invalid username");
						} catch (InvalidComplaintHierarchyException e) {
							// TODO Auto-generated catch block
							System.err.println(e.getMessage());
						} catch (UserAlreadyBlacklistedException e) {
							System.err.println(e.getMessage());
						} catch (InvalidAccessRightsException e) {
							System.err.println(e.getMessage());
						}
					}

					break;
				case Exit:
					System.out.print("Exit Prog? Press any key for no & Y for yes ");
					exitProg = scan.next();
					break;
			}
		}while(!exitProg.equalsIgnoreCase("y"));

	}
//
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
						 + "8. Send Complaint\n"
						 + "9. Exit");

		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = EmployerMenu.values()[userResponse];

				//System.out.println("This is choice in get response " + choice);

				if(userResponse >= 0 && userResponse < 9)
				{
					runAgain = false;
				}
				else
				{
					System.out.println("Please enter numbers in the specified range");
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
