package app_items;

import java.util.*;

import app_exceptions.CannotFullyBlacklistException;
import app_users.User;

public class Blacklist {
	
	private static HashMap <String, User> provisionalBlacklistedUsers = new HashMap<>();
	private static HashMap <String, User> fullyBlacklistedUsers = new HashMap<>();
	
	public void provisionallyBlacklist(User user)
	{
		this.provisionalBlacklistedUsers.put(user.getUsername(), user);
	}

	public void fullyBlacklist(User user) throws CannotFullyBlacklistException
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
	
	public void removeFromFullyBlacklist(String username) throws NullPointerException
	{
		this.fullyBlacklistedUsers.remove(username);
	}
	
	public HashMap<String, User> getProvisionallyBlacklistedUserList()
	{
		return this.provisionalBlacklistedUsers;
	}
	
	public HashMap<String, User> getFullyBlacklistedUserList()
	{
		return this.fullyBlacklistedUsers;
	}

}
