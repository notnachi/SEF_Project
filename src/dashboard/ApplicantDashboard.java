package dashboard;

import java.util.*;

import app_exceptions.InvalidComplaintHierarchyException;
import app_users.Applicant;

public class ApplicantDashboard {
	
	private Applicant user;
	
	public ApplicantDashboard(Applicant user)
	{
		this.user = user;
	}
	
//	public void displayMainMenu()
//	{
//		String exitProg = "n";
//		do
//		{
//			MainMenu choice = this.getMainMenuResponse();
//			Scanner scan = new Scanner(System.in);
//			switch(choice)
//			{
//				case ViewProfile:
//					user.showStudentProfile();
//					break;
//				case EditProfile:
//					String exitSubMenu = "n";
//					do
//					{
//						UpdateProfileMenu updateProfile = this.getUpdateProfileOption();
//						switch(updateProfile)
//						{
//						case AddEmploymentRecords:
//							user.updateEmploymentRecords();
//							break;
//						case AddQualifications:
//							user.updateQualification();
//							break;
//						case AddReferences:
//							user.addrefernce();
//							break;
//						case GoBack:
//							System.out.print("Exit Prog? Press any key for no & Y for yes ");
//							exitSubMenu = scan.next();
//							break;
//						default:
//							System.out.println("Please enter valid response");
//							break;
//						}
//					}while(!exitSubMenu.equalsIgnoreCase("n"));
//
//					break;
//				case ViewInterviews:
//					user.viewinterview();
//					break;
//				case AcceptJob:
//					user.responsetoJobOffer();
//					break;
//				case RejectJob:
//					user.responsetoJobOffer();
//					break;
//				case SendComplaint:
//				try {
//					user.sendComplaint();
//				} catch (NullPointerException e) {
//					System.err.println("Invalid username");
//				} catch (InvalidComplaintHierarchyException e) {
//					// TODO Auto-generated catch block
//					System.err.println(e.getMessage());
//				}
//					break;
//				case Exit:
//					System.out.print("Exit Prog? Press any key for no & Y for yes ");
//					exitProg = scan.next();
//					break;
//				default:
//					System.out.println("Please enter valid response");
//					break;
//			}
//		}while(!exitProg.equalsIgnoreCase("y"));
//
//	}
//
//	public MainMenu getMainMenuResponse()
//	{
//		boolean runAgain = true;
//
//		MainMenu choice = null;
//
//		System.out.println("************Menu************");
//		System.out.println("1. View Profile\n"
//						 + "2. Edit Profile\n"
//						 + "3. View Scheduled Interviews\n"
//						 + "4. Accept Job\n"
//						 + "5. Reject Job\n"
//						 + "6. Send complaint\n"
//						 + "7. Exit");
//
//		do
//		{
//			Scanner menuScan = new Scanner(System.in);
//			System.out.println("Please select an option");
//			try
//			{
//				int userResponse = menuScan.nextInt()-1;
//				choice = MainMenu.values()[userResponse];
//
//				//System.out.println("This is choice in get response " + choice);
//
//				if(userResponse >= 0 && userResponse < 7)
//				{
//					runAgain = false;
//				}
//				else
//				{
//					System.out.println("Please enter numbers in the specifed range");
//					runAgain = true;
//				}
//			}
//			catch(Exception ex)
//			{
//				System.err.println("Please enter a valid option or press 7 to exit");
//				runAgain = true;
//			}
//
//			//menuScan.close();
//
//		}while(runAgain);
//
//		return choice;
//	}
//
//	private enum MainMenu {
//
//		ViewProfile,
//		EditProfile,
//		ViewInterviews,
//		AcceptJob,
//		RejectJob,
//		SendComplaint,
//		Exit
//
//	}
//
//	public UpdateProfileMenu getUpdateProfileOption()
//	{
//		boolean runAgain = true;
//
//		UpdateProfileMenu choice = null;
//
//		System.out.println("************Menu************");
//		System.out.println("1. Add Employment Record\n"
//						 + "2. Add Qualification\n"
//						 + "3. Add References\n"
//						 + "4. Go Back");
//
//		do
//		{
//			Scanner menuScan = new Scanner(System.in);
//			System.out.println("Please select an option");
//			try
//			{
//				int userResponse = menuScan.nextInt()-1;
//				choice = UpdateProfileMenu.values()[userResponse];
//
//				//System.out.println("This is choice in get response " + choice);
//
//				if(userResponse >= 0 && userResponse < 4)
//				{
//					runAgain = false;
//				}
//				else
//				{
//					System.out.println("Please enter numbers in the specifed range");
//					runAgain = true;
//				}
//			}
//			catch(Exception ex)
//			{
//				System.err.println("Please enter a valid option or press 7 to exit");
//				runAgain = true;
//			}
//
//			//menuScan.close();
//
//		}while(runAgain);
//
//		return choice;
//	}
//
//	private enum UpdateProfileMenu {
//
//		AddEmploymentRecords,
//		AddQualifications,
//		AddReferences,
//		GoBack
//	}
	

}
