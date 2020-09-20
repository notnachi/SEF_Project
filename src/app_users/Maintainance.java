package app_users;

import java.util.Scanner;

import app_exceptions.CannotFullyBlacklistException;
import app_exceptions.DuplicateCategoryException;
import app_items.Blacklist;
import app_items.JobCategory;

public class Maintainance extends User {
	
	Blacklist blacklist = new Blacklist();
	
	UserDatabase userDB = new UserDatabase();
	
	public Maintainance(String username, String password)
	{
		super(username,password);
	}
	
	public void getApplicantRecords()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("*********List of Applicants*********");
		for(String username : userDB.getUserData().keySet())
		{
			if(userDB.fetchUser(username) instanceof Applicant)
			{
				System.out.println(username);
			}
		}
		System.out.print("Enter username to view details ");
		String username = scan.nextLine();
		
		Applicant app = (Applicant) userDB.fetchUser(username);
		
		app.showStudentProfile();
	}
	
	public void getEmployerRecords()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("*********List of Applicants*********");
		for(String username : userDB.getUserData().keySet())
		{
			if(userDB.fetchUser(username) instanceof Employer)
			{
				System.out.println(username);
			}
		}
		System.out.print("Enter username to view details ");
		String username = scan.nextLine();
		
		Employer app = (Employer) userDB.fetchUser(username);
		
		//add the show profile functionality here
	}
	
	public void removeProvisionalBlacklist() throws NullPointerException
	{
		Scanner scan = new Scanner(System.in);
		if(blacklist.showProvisionallyBlacklistedUser().size() == 0)
		{
			System.out.println("No users in provisional blacklist hashmap");
		}
		else
		{
			//display list of provisionally blacklisted users
			System.out.println("*********List of Provisionally Blacklisted Users*********");
			
			for(String username : blacklist.showProvisionallyBlacklistedUser().keySet())
			{
				System.out.println(username);
			}
			
			System.out.print("Enter usename to remove from provisional blacklist");
			String username = scan.nextLine();
			
			blacklist.removeFromProvisionalBlacklist(username);
		}
	}
		
		public void removeFullyBlacklist()
		{
			Scanner scan = new Scanner(System.in);
			if(blacklist.showFullyBlacklistedUser().size() == 0)
			{
				System.out.println("No users in fully blacklist hashmap");
			}
			else
			{
				//display list of provisionally blacklisted users
				System.out.println("*********List of Fully Blacklisted Users*********");
				
				for(String username : blacklist.showFullyBlacklistedUser().keySet())
				{
					System.out.println(username); //in the future also display date when fully blacklisted
				}
				
				System.out.print("Enter usename to remove from provisional blacklist");
				String username = scan.nextLine();
				
				blacklist.removeFromFullyBlacklist(username);
			}
		}
		
		public void fullyBlacklistUser() throws CannotFullyBlacklistException
		{
			Scanner scan = new Scanner(System.in);
			if(blacklist.showProvisionallyBlacklistedUser().size() == 0)
			{
				System.out.println("Provisional Blacklist hashmap is empty. Nobody to fully blacklist");
			}
			else
			{
				//display list of provisionally blacklisted users
				System.out.println("*********List of Provisionally Blacklisted Users*********");
				
				for(String username : blacklist.showProvisionallyBlacklistedUser().keySet())
				{
					System.out.println(username); //in the future also display date when fully blacklisted
				}
				
				System.out.print("Enter usename to fully blacklist");
				String username = scan.nextLine();
				
				blacklist.fullyBlacklist(blacklist.showProvisionallyBlacklistedUser().get(username));
			}
		}

		public void addJobCategory(String categoryName) throws DuplicateCategoryException
		{
			JobCategory newCategory = new JobCategory(categoryName);
		}
	}

