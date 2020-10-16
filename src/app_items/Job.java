package app_items;

import java.time.LocalDateTime;
import java.util.*;

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

	/*
	 * Job should have a list of applications
	 */

	private Map<String, JobApplication> applicationList;

	private LocalDateTime startingTimeSlot;
	private LocalDateTime endingTimeSlot;

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
			   double minYearsOfExperience)
	{
		this.jobID = "J" + jobCount;
		this.internationalApply = internationalApply;
		this.jobType = jobType;
		this.workingHours = workingHours;
		this.jobCategory = jobCategory;
		this.licenseRequired = licenseRequired;
		this.minYearsOfExperience = minYearsOfExperience;

		this.jobAvailable = true;

		this.applicationList = new HashMap<>();
		this.shortlistedApplicantList = new HashMap<>();

		this.jobResult = new Result();
		this.offerList = new ArrayList<>();

		jobCount+=1;

	}


	public void addJobApplication(Applicant applicant, JobApplication application)
	{
		this.applicationList.put(applicant.getUsername(), application);
	}

	public JobApplication fetchJobApplication(String applicantUsername)
	{
		return this.applicationList.get(applicantUsername);
	}

	public void setStartingTimeSlot(LocalDateTime time)
	{
		this.startingTimeSlot = time;
	}

	public void setEndingTimeSlot(LocalDateTime time)
	{
		this.endingTimeSlot = time;
	}

	public Map<String,JobApplication> getRankedApplications()
	{
		Map<String, JobApplication> rankedList = new HashMap<>();

		for(String appUsername : applicationList.keySet())
		{
			if(applicationList.get(appUsername).getStatusOfApplication().compareToIgnoreCase("RANKED")==0)
			{
				rankedList.put(appUsername , applicationList.get(appUsername));
			}
		}

		return rankedList;
	}

	public HashMap<String, Applicant> getShortlistedApplicantList() {
		return shortlistedApplicantList;
	}

	public void setJobResult(Result jobResult) {
		this.jobResult = jobResult;
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

	public void setOfferList(ArrayList<Offer> offerList) {
		this.offerList = offerList;
	}

	@Override
	public String toString() {
		StringBuilder jobDetails = new StringBuilder();

		jobDetails.append("Job ID -> " + jobID + "\n");
		jobDetails.append("Job Type -> " + jobType + "\n");
		jobDetails.append("Job Category -> " + jobCategory + "\n");
		jobDetails.append("Job Working hours -> " + workingHours + "\n");

		return jobDetails.toString();
	}
}
	
	

	
	



