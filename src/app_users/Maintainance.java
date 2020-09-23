package app_users;

import java.util.Scanner;

import app_exceptions.CannotFullyBlacklistException;
import app_exceptions.DuplicateCategoryException;
import app_exceptions.InvalidAccessRightsException;
import app_exceptions.UserNotPresentException;
import app_items.Blacklist;
import app_items.JobCategory;

public class Maintainance extends User {
	
	Blacklist blacklist = new Blacklist();
	
	UserDatabase userDB = new UserDatabase();
	
	public Maintainance(String username, String password)
	{
		super(username,password);
	}
	
	public void getApplicantRecords() {
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
		
		//app.showStudentProfile();
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
	
	public void removeProvisionalBlacklist(User user) throws UserNotPresentException {
		if(!blacklist.getProvisionallyBlacklistedUserList().containsKey(user.getUsername()))
		{
			throw new UserNotPresentException("User is not in provisionally blacklisted hashmap");
		}
		else
		{
			user.setProvisionallyBlacklistStatus(false);
			user.clearAllComplaints();
			blacklist.removeFromProvisionalBlacklist(user.getUsername());
		}

	}
		
	public void removeFullyBlacklist()
	{

	}

	public void fullyBlacklistUser() throws CannotFullyBlacklistException
	{

	}

	public void addJobCategory(String categoryName) throws DuplicateCategoryException
	{
		JobCategory newCategory = new JobCategory(categoryName);
	}
	}

