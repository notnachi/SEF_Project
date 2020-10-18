package app_items;

import java.util.*;

import app_exceptions.CannotFullyBlacklistException;
import app_users.EndUser;
import app_users.GlobalUser;

public class Blacklist {
	
	private static HashMap <String, EndUser> provisionalBlacklistedUsers = new HashMap<>();
	private static HashMap <String, EndUser> fullyBlacklistedUsers = new HashMap<>();
	
	public void provisionallyBlacklist(EndUser user)
	{
		this.provisionalBlacklistedUsers.put(user.getUsername(), user);
	}

	public void fullyBlacklist(EndUser user) throws CannotFullyBlacklistException
	{
		//first verify that the user has been provisionally blacklisted
		if(this.provisionalBlacklistedUsers.get(user.getUsername()) == null)
		{
			throw new CannotFullyBlacklistException("Cannot fully blacklist user who is not in provisionally blacklisted");
		}
		//remove from provisional blacklist
		this.provisionalBlacklistedUsers.remove(user.getUsername());
		//add to fully blacklist
		this.fullyBlacklistedUsers.put(user.getUsername(), user);
	}
	
	public void removeFromProvisionalBlacklist(String username) throws NullPointerException
	{

		this.provisionalBlacklistedUsers.remove(username);
	}
	
	public void removeFromFullyBlacklist(String username)  {

		this.fullyBlacklistedUsers.remove(username);
	}
	
	public static HashMap<String, EndUser> getProvisionallyBlacklistedUserList()
	{
		return provisionalBlacklistedUsers;
	}
	
	public static HashMap<String, EndUser> getFullyBlacklistedUserList()
	{
		return fullyBlacklistedUsers;
	}

	public static void showProvisionallyBlacklistedUsers()
	{
		for(String userID : provisionalBlacklistedUsers.keySet())
		{
			System.out.println(userID);
		}
	}

	public static void showFullyBlacklistedUsers()
	{
		for(String userID : fullyBlacklistedUsers.keySet())
		{
			System.out.println(userID);
		}
	}
}
