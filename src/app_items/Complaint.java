package app_items;

import app_users.User;

public class Complaint {
	
	private User complaintBy;
	private User complaintAbout;
	private String complaintDesc;
	
	public Complaint(User complaintBy, User complaintAbout, String complaintDesc)
	{
		this.complaintBy = complaintBy;
		this.complaintAbout = complaintAbout;
		this.complaintDesc = complaintDesc;
	}
	
}
