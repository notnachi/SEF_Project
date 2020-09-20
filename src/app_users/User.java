package app_users;

import java.util.ArrayList;

import app_exceptions.InvalidPasswordException;
import app_items.Blacklist;
import app_items.Complaint;

public abstract class User {
	
	//username for user
	private String username;
	//password for 
	private String password;
	
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}


	/**
	 * Validates applicant credentials during login
	 * @param loginPassword
	 * @return true if password matches applicantPassword
	 * @throws InvalidPasswordException if password does not match applicantPassword
	 */
	public boolean validateCredentials(String loginPassword) throws InvalidPasswordException
	{
		if(loginPassword.compareTo(this.password)!=0)
		{
			throw new InvalidPasswordException("Password is invalid");
		}
		return true;
	}
	
	
	
	


}
