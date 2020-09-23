package app_items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import app_users.Applicant;
import app_users.Employer;

public class Job{

	//stores the unique id for the job
	private final String jobID;

	private static int jobCount;

	//employer who is offering the job
	private Employer emp;
	//is job applicable for international students
	private boolean internationalApply;

	/*
	 * Keeping the job separate from availability and Employment Record classes
	 * New added variables -
	 * 1. Job Type (String) part time, full time or internship
	 * 2. Working hours (Double) - working hours that job requires
	 * 3. Job Category (JobCategory) - job category of the job
	 * 4. License Required (Boolean) - is license required or not
	 * 5. minYearsOfExperience (double) - if min experience required
	 */

	private String jobType;
	private double workingHours;
	private String jobCategory;
	private boolean licenseRequired;
	private double minYearsOfExperience;


	//count of free spots available
	private int noOfSpotsAvailable;

	//number of students required
	private int noOfStudentsRequired;

	/*
	 * Job should have a list of all shortlisted applicants
	 */

	private HashMap<String, Applicant> shortlistedApplicantList;

	//store the list of final and rejected applicants
	private Result jobResult;

	private ArrayList<Offer> offerList;
	
	//job available or not
	private boolean jobAvailable;


	public Job(Employer emp,
			   boolean internationalApply,
			   String jobType,
			   double workingHours,
			   String jobCategory,
			   boolean licenseRequired,
			   double minYearsOfExperience,
			   int noOfStudentsRequired)
	{
		this.jobID = "J" + jobCount;
		this.internationalApply = internationalApply;
		this.jobType = jobType;
		this.workingHours = workingHours;
		this.jobCategory = jobCategory;
		this.licenseRequired = licenseRequired;
		this.minYearsOfExperience = minYearsOfExperience;
		this.noOfStudentsRequired = noOfStudentsRequired;

		this.jobAvailable = true;
		this.noOfSpotsAvailable = noOfStudentsRequired;

		this.shortlistedApplicantList = new HashMap<>();
		this.jobResult = new Result();
		this.offerList = new ArrayList<>();

		jobCount+=1;

	}

	public String getJobID() {
		return jobID;
	}

	public boolean isInternationalApply() {
		return internationalApply;
	}

	public String getJobType() {
		return jobType;
	}

	public double getWorkingHours() {
		return workingHours;
	}

	public boolean isLicenseRequired() {
		return licenseRequired;
	}

	public double getMinYearsOfExperience() {
		return minYearsOfExperience;
	}

	public int getNoOfSpotsAvailable() {
		return noOfSpotsAvailable;
	}

	public int getNoOfStudentsRequired() {
		return noOfStudentsRequired;
	}

	public boolean isJobAvailable() {
		return jobAvailable;
	}

	public void addShortlistedApplicant(Applicant applicant)
	{
		if(this.shortlistedApplicantList.containsKey(applicant.getUsername()))
		{
			//throw exception here
		}
		else
		{
			this.shortlistedApplicantList.put(applicant.getUsername(), applicant);
		}
	}



}
	
	

	
	



