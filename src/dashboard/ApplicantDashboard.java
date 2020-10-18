package dashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import app_exceptions.DuplicateCategoryException;
import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_users.Applicant;
import app_users.EndUser;
import app_users.UserDatabase;

public class ApplicantDashboard {
	
	private Applicant user;
	
	public ApplicantDashboard(Applicant user)
	{
		this.user = user;
	}
	
	public void displayMainMenu() throws FileNotFoundException {
		if(user.checkOffer() != null)
		{
			System.out.println("***************YOU HAVE RECEIVED AN OFFER****************");
		}
		String exitProg = "n";
		do
		{
			MainMenu choice = this.getMainMenuResponse();
			Scanner scan = new Scanner(System.in);
			switch(choice)
			{
				case ViewProfile:
					try {
						user.showStudentProfile();
					} catch (InvalidAccessRightsException e) {
						System.err.println(e.getMessage());
					}
					break;
				case EditProfile:
					String exitSubMenu = "n";
					do
					{
						UpdateProfileMenu updateProfile = this.getUpdateProfileOption();
						switch(updateProfile)
						{
							case UploadCV:
								System.out.println("Enter file path of your cv");
								String filePath = scan.next();
								File cv = new File(filePath);
								user.setCv(cv);
								break;
							case AddAvailability:
								try {
									user.addAvailability();
								} catch (InvalidAccessRightsException e) {
									System.err.println(e.getMessage());
								}
								break;
							case AddEmploymentRecords:
								try {
									user.addEmploymentRecord();
								} catch (DuplicateCategoryException | InvalidAccessRightsException e) {
									System.err.println(e.getMessage());
								}
								break;
							case AddQualifications:
								try {
									user.addQualifications();
								} catch (DuplicateCategoryException | InvalidAccessRightsException e) {
									System.err.println(e.getMessage());
								}
								break;
							case AddReferences:
								try {
									user.addReferences();
								} catch (DuplicateCategoryException | InvalidAccessRightsException e) {
									System.err.println(e.getMessage());
								}
								break;
							case GoBack:
								System.out.print("Exit Prog? Press any key for no & Y for yes ");
								exitSubMenu = scan.next();
								break;
							default:
								System.out.println("Please enter valid response");
								break;
						}
					}while(!exitSubMenu.equalsIgnoreCase("n"));

					break;
				case ScheduleInterview:
					user.scheduleInterview();
					break;
				case ViewInterview:
					user.viewInterview();
					break;
				case RespondToOffer:
					user.acceptJobOffer();
					break;
				case SendComplaint:
					if(user.getProvisionallyBlacklistStatus() || user.getFullyBlacklistStatus())
					{
						System.err.println("Invalid access rights. Cannot perform operation");
					}
					System.out.print("Enter username of employer ");
					String complaintAbout = scan.next();
					System.out.print("Enter your complaint ");
					String complaintDesc = scan.next();
					try {
						user.sendComplaint((EndUser) UserDatabase.fetchUser(complaintAbout), complaintDesc);
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
					break;
				case Exit:
					System.out.print("Exit Prog? Press any key for no & Y for yes ");
					exitProg = scan.next();
					break;
				default:
					System.out.println("Please enter valid response");
					break;
			}
		}while(!exitProg.equalsIgnoreCase("y"));

	}
//

	private enum MainMenu {

		ViewProfile,
		EditProfile,
		ScheduleInterview,
		ViewInterview,
		RespondToOffer,
		SendComplaint,
		Exit

	}

	public MainMenu getMainMenuResponse()
	{
		boolean runAgain = true;

		MainMenu choice = null;

		System.out.println("************Menu************");
		System.out.println("1. View Profile\n"
						 + "2. Edit Profile\n"
						 + "3. Schedule an Interview\n"
						 + "4. View Scheduled Interviews\n"
						 + "5. Respond to Offer\n"
						 + "6. Send complaint\n"
						 + "7. Exit");

		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = MainMenu.values()[userResponse];

				//System.out.println("This is choice in get response " + choice);

				if(userResponse >= 0 && userResponse < 7)
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
//
	private enum UpdateProfileMenu {

		UploadCV,
		AddAvailability,
		AddEmploymentRecords,
		AddQualifications,
		AddReferences,
		GoBack
	}

//
	public UpdateProfileMenu getUpdateProfileOption()
	{
		boolean runAgain = true;

		UpdateProfileMenu choice = null;

		System.out.println("************Menu************");
		System.out.println( "1. Upload CV"
						 + "2. Add Availability\n"
						 + "3. Add Employment Record\n"
						 + "4. Add Qualification\n"
						 + "5. Add References\n"
						 + "6. Go Back");

		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = UpdateProfileMenu.values()[userResponse];

				//System.out.println("This is choice in get response " + choice);

				if(userResponse >= 0 && userResponse < 6)
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
