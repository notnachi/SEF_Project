package dashboard;

import java.util.Scanner;

import app_exceptions.CannotFullyBlacklistException;
import app_users.Maintainance;


public class StaffDashboard  {
	
//	private Maintainance user;
//
//	public StaffDashboard(Maintainance user)
//	{
//		this.user = user;
//	}
//
//	private enum MainMenu
//	{
//		ViewApplicantRecords,
//		ViewEmployerRecords,
//		RemoveFromProvBlacklist,
//		AddToFullyBlacklist,
//		AddJobCategories,
//		Exit
//	}
//
//	public void displayMainMenu()
//	{
//		String exitProg = "n";
//		do
//		{
//			MainMenu choice = this.getMainMenuResponse();
//			Scanner scan = new Scanner(System.in);
//			switch(choice)
//			{
//				case ViewApplicantRecords:
//					user.getApplicantRecords();
//					break;
//				case ViewEmployerRecords:
//					user.getEmployerRecords();
//					break;
//				case RemoveFromProvBlacklist:
//					user.removeProvisionalBlacklist();
//					break;
//				case AddToFullyBlacklist:
//				try {
//					user.fullyBlacklistUser();
//				} catch (CannotFullyBlacklistException e) {
//					System.out.println(e.getMessage());
//				}
//					break;
//				case AddJobCategories:
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
//		System.out.println("1. View Applicant Records\n"
//						 + "2. View Employer Records\n"
//						 + "3. Remove from Provisional Blacklist\n"
//						 + "4. Add to fully blacklist\n"
//						 + "5. Add job categories\n"
//						 + "6. Exit");
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
//
//

}
