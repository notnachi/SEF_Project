package app_users;

import java.time.LocalDateTime;
import java.util.ArrayList;

import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.InvalidPasswordException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_items.Blacklist;
import app_items.Complaint;

public abstract class User {
	
	//username for user
	private String username;
	//password for 
	private String password;

	//arraylist of complaints
	private ArrayList<Complaint> complaintList;

	private boolean isProvisionallyBlacklisted;

	private boolean isFullyBlacklisted;

	private LocalDateTime fullyBlacklistDate;
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;

		this.complaintList = new ArrayList<>();

		this.isFullyBlacklisted = false;
		this.isProvisionallyBlacklisted = false;

		this.fullyBlacklistDate = null;
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

	public boolean getProvisionallyBlacklistStatus()
	{
		return this.isProvisionallyBlacklisted;
	}

	public boolean getFullyBlacklistStatus()
	{
		return this.isFullyBlacklisted;
	}

	public void setFullyBlacklistDate(LocalDateTime fullyBlacklistDate)
	{
		this.fullyBlacklistDate = fullyBlacklistDate;
	}

	public LocalDateTime getFullyBlacklistDate()
	{
		return this.fullyBlacklistDate;
	}


	public void sendComplaint(User user, String complaintDesc) throws UserAlreadyBlacklistedException, InvalidAccessRightsException, InvalidComplaintHierarchyException {

		//avoid accessing this method if user is blacklisted
		if(this.getFullyBlacklistStatus() || this.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("You have been blacklisted. Cannot perform operation.");
		}

		if((this instanceof Applicant && user instanceof Applicant) || (this instanceof Employer && user instanceof Employer))
		{
			throw new InvalidComplaintHierarchyException("Cannot complaint about user of same type");
		}

		if(user.getFullyBlacklistStatus() || user.getProvisionallyBlacklistStatus())
		{
			throw new UserAlreadyBlacklistedException("This user has already been blacklisted");
		}


		Complaint newComplaint = new Complaint(this,user, complaintDesc);

		user.getComplaint(newComplaint);
	}

	public void getComplaint(Complaint complaint)
	{
		this.complaintList.add(complaint);

		if(this.complaintList.size()==3)
		{
			Blacklist newBlacklist = new Blacklist();
			newBlacklist.provisionallyBlacklist(this);
			this.setProvisionallyBlacklistStatus(true);
		}
	}

	public void clearAllComplaints()
	{
		this.complaintList.clear();
	}

	public void setFullyBlacklistStatus(boolean isFullyBlacklisted)
	{
		this.isFullyBlacklisted = isFullyBlacklisted;
	}

	public void setProvisionallyBlacklistStatus(boolean isProvisionallyBlacklisted)
	{
		this.isProvisionallyBlacklisted = isProvisionallyBlacklisted;
	}
	
	
	
	


}
