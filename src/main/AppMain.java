package main;

import java.io.FileNotFoundException;
import java.util.Scanner;

import app_exceptions.DuplicateCategoryException;
import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidPasswordException;
import app_items.JobCategory;
import app_users.Applicant;
import app_users.Employer;
import app_users.Maintainance;
import app_users.UserDatabase;
import dashboard.ApplicantDashboard;
import dashboard.EmployerDashboard;
import dashboard.Registration_Dashboard;
import dashboard.StaffDashboard;

import javax.mail.MessagingException;

public class AppMain {
	
	private static UserDatabase userDB = new UserDatabase();

	private static void loadDummyData() throws InvalidAccessRightsException, DuplicateCategoryException {

		JobCategory.getInstance().addJobCategory("IT");
		JobCategory.getInstance().addJobCategory("HR");
		JobCategory.getInstance().addJobCategory("Finance");
		JobCategory.getInstance().addJobCategory("Design");

		Applicant app1 = new Applicant("app1", "abc123", "app1@gmail.com" , "Nachiket Rao", "test", false, true);
		app1.addAvailability("part-time",40,"IT");

		Applicant app2 = new Applicant("app2", "abc123","app2@gmail.com",  "anurag", "test", true, true);
		app2.addAvailability("part-time",40,"IT");

		Applicant app3 = new Applicant("app3", "abc123","app3@gmail.com" , "joel", "test", false, true);
		app3.addAvailability("part-time",40,"IT");
		app3.setProvisionallyBlacklistStatus(true);

		Applicant app4 = new Applicant("app4", "abc123","app4@gmail.com" , "test", "test", false, true);
		app4.addAvailability("part-time",40,"IT");

		Applicant app5 = new Applicant("app5", "abc123","app5@gmail.com" , "test", "test", true, true);
		app5.addAvailability("full-time",40,"IT");

		Applicant app6 = new Applicant("app6", "abc123","app6@gmail.com" , "test", "test", false, false);
		app6.addAvailability("full-time",40,"IT");

		Applicant app7 = new Applicant("app7", "abc123", "app7@gmail.com" ,"test", "test", false, true);
		app7.addAvailability("full-time",40,"IT");

		Applicant app8 = new Applicant("app8", "abc123","app8@gmail.com" , "test", "test", false, true);
		app8.addAvailability("full-time",40,"IT");

		Employer emp1 = new Employer("emp1", "abc123", "google", "emp2@gmail.com", "1234", "test");
		emp1.createJob(true,"part-time",20,"IT",true,0);
		emp1.setProvisionallyBlacklistStatus(true);

		Employer emp2 = new Employer("emp2", "abc123", "amazon", "emp2@gmail.com", "1234", "test");
		emp2.createJob(false,"full-time",40,"IT",true,0);

		Maintainance staff1 = new Maintainance("staff1", "abc123", "staff1@gmail.com");

		userDB.addUsers(app1.getUsername(),app1);
		userDB.addUsers(app2.getUsername(),app2);
		userDB.addUsers(app3.getUsername(),app3);
		userDB.addUsers(app4.getUsername(),app4);
		userDB.addUsers(app5.getUsername(),app5);
		userDB.addUsers(app6.getUsername(),app6);
		userDB.addUsers(app7.getUsername(),app7);
		userDB.addUsers(app8.getUsername(),app8);

		userDB.addUsers(emp1.getUsername(), emp1);
		userDB.addUsers(emp2.getUsername(), emp2);

		userDB.addUsers(staff1.getUsername(),staff1);
	}
	
	private enum MainMenuOption
	{
		RegisterAsNewUser,
		ExistingUserLogin,
		Exit
	}
	
	public static void displayMainMenu() throws InvalidAccessRightsException, DuplicateCategoryException, FileNotFoundException {
		loadDummyData();
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
	
	public static void displayLoginMenu() throws FileNotFoundException {
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
	
	public static void main(String[] args) throws InvalidAccessRightsException, DuplicateCategoryException, FileNotFoundException {
		

		displayMainMenu();
		
	}

}
