package app_items;

public class Availability{
	
	private String jobType;
	
	private double availableWorkingHours;

	
	private String jobCategories;
	
	public Availability(String jobType, double availableWorkingHours, String category)
	{
		this.jobType = jobType;
		this.jobCategories = category;
		this.availableWorkingHours = availableWorkingHours;
	}

	public String getJobType()
	{
		return this.jobType;
	}

	public double getAvailableWorkingHours()
	{
		return this.availableWorkingHours;
	}



}
