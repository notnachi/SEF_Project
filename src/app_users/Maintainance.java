package app_users;

import java.time.LocalDateTime;
import java.util.Scanner;

import app_exceptions.*;
import app_items.Blacklist;
import app_items.JobCategory;

public class Maintainance extends GlobalUser {
	
	Blacklist blacklist = new Blacklist();
	
	UserDatabase userDB = new UserDatabase();
	
	public Maintainance(String username, String password, String emailID)
	{
		super(username,password, emailID);
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
	
	public void removeProvisionalBlacklist(EndUser user) throws UserNotPresentException {

		if(!blacklist.getProvisionallyBlacklistedUserList().containsKey(user.getUsername()))
		{
			throw new UserNotPresentException("User is not in provisionally blacklisted hashmap");
		}

		user.setProvisionallyBlacklistStatus(false);
		user.clearAllComplaints();
		blacklist.removeFromProvisionalBlacklist(user.getUsername());


	}
		
	public void removeFullyBlacklist(EndUser user) throws UserNotPresentException, CannotReactivateUserException {

		if(!blacklist.getFullyBlacklistedUserList().containsKey(user.getUsername()))
		{
			throw new UserNotPresentException("User is not in fully blacklisted hashmap");
		}

		if(LocalDateTime.now().isBefore(user.getFullyBlacklistDate().plusMonths(3)))
		{
			throw new CannotReactivateUserException("Cannot reactivate fully blacklisted user before 3 months");
		}

		user.setFullyBlacklistStatus(false);
		user.setFullyBlacklistDate(null);

		blacklist.removeFromFullyBlacklist(user.getUsername());

	}

	public void fullyBlacklistUser(EndUser user) throws CannotFullyBlacklistException
	{
		user.setProvisionallyBlacklistStatus(false);
		user.setFullyBlacklistStatus(true);
		user.setFullyBlacklistDate(LocalDateTime.now());
		blacklist.fullyBlacklist(user);

	}

	public void addJobCategory(String categoryName) throws DuplicateCategoryException
	{
		JobCategory.getInstance().addJobCategory(categoryName);
	}
}

