package app_items;

import java.util.ArrayList;

public class EmploymentRecord {
	
	private String companyName;
	private String domain;
	private double yearsOfExperience;
	private String role;
	
	
	public EmploymentRecord(String companyName, String domain, double yearsOfExperience, String role)
	{
		this.companyName = companyName;
		this.domain = domain;
		this.yearsOfExperience = yearsOfExperience;
		this.role = role;
	}
	
	public String getcompanyName()
	{
		return companyName;
	}

	public String getdomain()
	{
		return domain;
	}
	public String getrole()
	{
		return role;
	}
	
	public double getExperience()
	{
		return yearsOfExperience;
	}

}
