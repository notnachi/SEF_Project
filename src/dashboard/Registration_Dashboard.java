package dashboard;

import java.util.Scanner;

import app_users.Applicant;
import app_users.Employer;
import app_users.Maintainance;
import app_users.UserDatabase;

public class Registration_Dashboard {
	
	private UserDatabase userDB;
	
	public Registration_Dashboard(UserDatabase userDB)
	{
		this.userDB = userDB;
	}
	
	public void registerApplicant()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter username ");
		String username = scan.nextLine();
		
		System.out.print("Enter password ");
		String password = scan.nextLine();

		System.out.print("Enter Email ID ");
		String email = scan.nextLine();
		
		System.out.print("Enter name ");
		String name = scan.nextLine();
		
		System.out.print("Enter contact details ");
		String contact = scan.nextLine();

		boolean isInternational;
		System.out.print("Are you an international applicant ? Press Y for yes and N for no ");
		String checkInternational =  scan.next();
		if(checkInternational.compareToIgnoreCase("y")==0)
		{
			isInternational = true;
		}
		else
		{
			isInternational = false;
		}

		boolean hasLicense;
		System.out.print("Do you have a driving license ? Press Y for yes and N for no ");
		String checkLicense =  scan.next();
		if(checkLicense.compareToIgnoreCase("y")==0)
		{
			hasLicense = true;
		}
		else
		{
			hasLicense = false;
		}
		
		this.userDB.addUsers(username, new Applicant(username, password, email, name, contact, isInternational, hasLicense));
		
//		System.out.println("Is this user an instance of applicant" + (userDB.fetchUser(username) instanceof Applicant));
	}
	
	public void registerEmployer()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter username ");
		String username = scan.nextLine();
		
		System.out.print("Enter password ");
		String password = scan.nextLine();
		
		System.out.print("Enter name ");
		String name = scan.nextLine();
		
		System.out.print("Enter emailID ");
		String email = scan.nextLine();
		
		System.out.print("Enter contact details ");
		String contact = scan.nextLine();
		
		System.out.print("Enter description about company ");
		String compDesc = scan.nextLine();
		
		this.userDB.addUsers(username, new Employer(username, password, name, email, contact, compDesc));
		
	}
	
	public void registerStaff()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter username ");
		String username = scan.nextLine();
		
		System.out.print("Enter password ");
		String password = scan.nextLine();

		System.out.print("Enter Email ");
		String email = scan.nextLine();
		
		
		this.userDB.addUsers(username, new Maintainance(username, password, email));
		
	}
}
