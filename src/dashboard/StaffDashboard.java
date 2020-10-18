package dashboard;

import java.util.Scanner;

import app_exceptions.CannotFullyBlacklistException;
import app_exceptions.CannotReactivateUserException;
import app_exceptions.DuplicateCategoryException;
import app_exceptions.UserNotPresentException;
import app_items.Blacklist;
import app_users.EndUser;
import app_users.Maintainance;
import app_users.UserDatabase;
import jdk.jfr.Category;


public class StaffDashboard  {
	
	private Maintainance user;

	public StaffDashboard(Maintainance user)
	{
		this.user = user;
	}
//

//
	public void displayMainMenu() {
		String exitProg = "n";
		do
		{
			MainMenu choice = this.getMainMenuResponse();
			Scanner scan = new Scanner(System.in);
			switch(choice)
			{
				case ViewApplicantRecords:
					user.getApplicantRecords();
					break;
				case ViewEmployerRecords:
					user.getEmployerRecords();
					break;
				case RemoveFromProvBlacklist:
				    if(Blacklist.getProvisionallyBlacklistedUserList().isEmpty())
                    {
                        System.out.println("No users blacklisted yet");
                    }
				    else
                    {
                        Blacklist.showProvisionallyBlacklistedUsers();

                        System.out.print("Enter username of user to remove from provisionally blacklist");
                        String restoreUser = scan.next();
                        try {
                            user.removeProvisionalBlacklist((EndUser) UserDatabase.fetchUser(restoreUser));
                        } catch (UserNotPresentException e) {
                            System.err.println(e.getMessage());
                        }
                    }

                    break;
				case AddToFullyBlacklist:

                    if(Blacklist.getFullyBlacklistedUserList().isEmpty())
                    {
                        System.out.println("No users blacklisted yet");
                    }
				    else
                    {
                        Blacklist.showProvisionallyBlacklistedUsers();
                        System.out.print("Enter username of user to fully blacklist  ");
                        String fullyBlacklistUser = scan.next();
                        try {
                            user.fullyBlacklistUser((EndUser) UserDatabase.fetchUser(fullyBlacklistUser));
                        } catch (CannotFullyBlacklistException e) {
                            System.out.println(e.getMessage());
                        }
                    }

					break;
                case RemoveFromFullyBlacklist:

                    if(Blacklist.getFullyBlacklistedUserList().isEmpty())
                    {
                        System.out.println("No users blacklisted yet");
                    }
                    else
                    {
                        Blacklist.showFullyBlacklistedUsers();
                        System.out.print("Enter username of user to remove from fully blacklist  ");
                        String newUser = scan.next();
                        try {
                            user.removeFullyBlacklist((EndUser) UserDatabase.fetchUser(newUser));
                        } catch (CannotReactivateUserException | UserNotPresentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
				case AddJobCategories:
					System.out.print ("Please enter new job category ");
					boolean noError = false;
					do {
						String category = scan.next();
						try {
							user.addJobCategory(category);
							noError = true;
							System.out.println("Category added successfully");
						} catch (DuplicateCategoryException e) {
							System.out.print("Category already present. Please enter different Category ");
						}
					}while(!noError);
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
    private enum MainMenu
    {
        ViewApplicantRecords,
        ViewEmployerRecords,
        RemoveFromProvBlacklist,
        AddToFullyBlacklist,
        RemoveFromFullyBlacklist,
        AddJobCategories,
        Exit
}
	public MainMenu getMainMenuResponse()
	{
		boolean runAgain = true;

		MainMenu choice = null;

		System.out.println("************Menu************");
		System.out.println("1. View Applicant Records\n"
						 + "2. View Employer Records\n"
						 + "3. Remove from Provisional Blacklist\n"
						 + "4. Add to fully blacklist\n"
                         + "5. Remove from fully blacklist\n"
						 + "6. Add job categories\n"
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
//
//

}
