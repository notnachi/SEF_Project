package main;

import java.util.Scanner;

import app_exceptions.InvalidPasswordException;
import app_users.Applicant;
import app_users.Employer;
import app_users.Maintainance;
import app_users.UserDatabase;
import dashboard.ApplicantDashboard;
import dashboard.EmployerDashboard;
import dashboard.Registration_Dashboard;
import dashboard.StaffDashboard;

public class AppMain {
	
	private static UserDatabase userDB = new UserDatabase();
	
	private enum MainMenuOption
	{
		RegisterAsNewUser,
		ExistingUserLogin,
		Exit
	}
	
	public static void displayMainMenu()
	{
		String exitProg = "n";
		
		do
		{
			MainMenuOption choice = chooseMenuOption();
			Scanner scan = new Scanner(System.in);
			
			switch(choice)
			{
				case RegisterAsNewUser:
					displayRegistrationMenu();
					break;
				case ExistingUserLogin:
					displayLoginMenu();
					break;
				case Exit:
					System.out.print("Exit Prog? Press any key for no & Y for yes ");
					exitProg = scan.next();	
					break;
				default:
					System.out.println("Please enter valid response");
					break;
			}
		}while(!exitProg.equalsIgnoreCase("Y"));
	}
	
	public static MainMenuOption chooseMenuOption()
	{
		boolean runAgain = true;
		
		MainMenuOption choice = null;
		
		System.out.println("************Menu************");
		System.out.println("1. Register as new user\n"
						 + "2. Login to System\n"
						 + "3. Exit");
		
		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = MainMenuOption.values()[userResponse];
				
				//System.out.println("This is choice in get response " + choice);
						
				if(userResponse >= 0 && userResponse < 3)
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
				System.err.println("Please enter a valid option or press 3 to exit");
				runAgain = true;
			}
			
			//menuScan.close();
			
		}while(runAgain);
		
		return choice;
	}
	
	private enum LoginOption
	{
		ApplicantLogin,
		EmployerLogin,
		StaffLogin
	}
	
	public static void displayLoginMenu()
	{
		LoginOption choice = chooseLoginOption();
		Scanner scan = new Scanner(System.in);
		
		//take username and password from user
		System.out.print("Enter your username ");
		String userID = scan.nextLine();
		System.out.print("Enter your password ");
		String password = scan.nextLine();
		
		switch(choice)
		{
			case ApplicantLogin:
				try {
					Applicant app = userDB.getApplicant(userID, password);
					ApplicantDashboard appDash = new ApplicantDashboard(app);
					appDash.displayMainMenu();
				} 
				catch (NullPointerException e) {
					System.err.println("Username not found in database. Please try again");
				} 
				catch (InvalidPasswordException e) {
					System.err.println(e.getMessage());
				}
				break;
			case EmployerLogin:
				try {
					Employer emp = userDB.getEmployer(userID, password);
					EmployerDashboard empDash = new EmployerDashboard(emp);
					empDash.displayMainMenu();
				} 
				catch (NullPointerException e) {
					System.err.println("Username not found in database. Please try again");
				} 
				catch (InvalidPasswordException e) {
					System.err.println(e.getMessage());
				}
				break;
			case StaffLogin:
				try {
					Maintainance staff = userDB.getStaff(userID, password);
					StaffDashboard staffDash = new StaffDashboard(staff);
					staffDash.displayMainMenu();
				} 
				catch (NullPointerException e) {
					System.err.println("Username not found in database. Please try again");
				} 
				catch (InvalidPasswordException e) {
					System.err.println(e.getMessage());
				}
				break;
		}
	}
	
	public static LoginOption chooseLoginOption()
	{
		boolean runAgain = true;
		
		LoginOption choice = null;
		
		System.out.println("************Menu************");
		System.out.println("1. Login as Applicant\n"
						 + "2. Login as Employer\n"
						 + "3. Login as Maintainance Staff");
		
		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = LoginOption.values()[userResponse];
				
				//System.out.println("This is choice in get response " + choice);
						
				if(userResponse >= 0 && userResponse < 3)
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
				System.err.println("Please enter a valid option or press 4 to go back");
				runAgain = true;
			}
			
			//menuScan.close();
			
		}while(runAgain);
		
		return choice;
	}
	
	private enum RegisterOption
	{
		ApplicantRegister,
		EmployerRegister,
		StaffRegister
	}
	
	public static void displayRegistrationMenu()
	{
		RegisterOption choice = chooseRegisterOption();
		Scanner scan = new Scanner(System.in);
		Registration_Dashboard rd = new Registration_Dashboard(userDB);
		switch(choice)
		{
			case ApplicantRegister:		
				rd.registerApplicant();
				break;
			case EmployerRegister:		
				rd.registerEmployer();
				break;
			case StaffRegister:
				rd.registerStaff();
				break;
		}
	}
	
	public static RegisterOption chooseRegisterOption()
	{
		boolean runAgain = true;
		
		RegisterOption choice = null;
		
		System.out.println("************Menu************");
		System.out.println("1. Register as Applicant\n"
						 + "2. Register as Employer\n"
						 + "3. Register as Maintainance Staff");
		
		do
		{
			Scanner menuScan = new Scanner(System.in);
			System.out.println("Please select an option");
			try
			{
				int userResponse = menuScan.nextInt()-1;
				choice = RegisterOption.values()[userResponse];
				
				//System.out.println("This is choice in get response " + choice);
						
				if(userResponse >= 0 && userResponse < 3)
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
				System.err.println("Please enter a valid option or press 4 to go back");
				runAgain = true;
			}
			
			//menuScan.close();
			
		}while(runAgain);
		
		return choice;
	}
	
	public static void main(String[] args) {
		
		displayMainMenu();
		
	}

}
