package app_users;

import app_exceptions.InvalidPasswordException;

public abstract class GlobalUser {
	
	//username for user
	private String username;
	//password for 
	private String password;

	private String emailID;

	public GlobalUser(String username, String password, String emailID)
	{
		this.username = username;
		this.password = password;
		this.emailID = emailID;

	}
	
	public String getUsername() {
		return username;
	}

	public boolean validateCredentials(String loginPassword) throws InvalidPasswordException
	{
		if(loginPassword.compareTo(this.password)!=0)
		{
			throw new InvalidPasswordException("Password is invalid");
		}
		return true;
	}

	public String getEmailID()
	{
		return emailID;
	}
	
	
	
	


}
