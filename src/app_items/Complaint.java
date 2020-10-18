package app_items;

import app_users.EndUser;
import app_users.GlobalUser;

public class Complaint {
	
	private EndUser complaintBy;
	private EndUser complaintAbout;
	private String complaintDesc;
	
	public Complaint(EndUser complaintBy, EndUser complaintAbout, String complaintDesc)
	{
		this.complaintBy = complaintBy;
		this.complaintAbout = complaintAbout;
		this.complaintDesc = complaintDesc;
	}
	
}
