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
		
		System.out.print("Enter name ");
		String name = scan.nextLine();
		
		System.out.print("Enter contact details ");
		String contact = scan.nextLine();
		
		System.out.print("Enter nationality ");
		String nationality = scan.nextLine();
		
		this.userDB.addUsers(username, new Applicant(username, password, name, contact, nationality));
		
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
		
		
		this.userDB.addUsers(username, new Maintainance(username, password));
		
	}
}
