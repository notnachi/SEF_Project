package app_users;
import app_exceptions.*;
import app_items.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Employer extends User {
	
	private String employerName;
	private String emailID;
	private String contactNumber;
	private String aboutEmployer;
	
	private HashMap<String, Job> jobList = new HashMap<>();
	private ArrayList<Interview> interviewList = new ArrayList<Interview>();

	
	public Employer(String username, String password, String empName, String emailID, String contactNumber, String empDesc) {
		
		super(username,password);
		this.employerName = empName;
		this.emailID = emailID;
		this.contactNumber = contactNumber;
		this.aboutEmployer = empDesc;

	}

	
	public void showEmployerProfile() throws InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You are fully Blacklisted");
		}
		System.out.println("Name " + this.employerName);
		System.out.println("Email " + this.emailID);
		System.out.println("Contact " + this.contactNumber);
		System.out.println("About " + this.aboutEmployer);
	}

	public Job fetchJob(String jobID)
	{
		return jobList.get(jobID);
	}

	public void showInterview()
	{
		if(interviewList.isEmpty())
		{
			System.out.println("No interviews scheduled");
		}
		else
		{
			for(Interview iw : interviewList)
			{
				System.out.println(iw);
			}
		}

	}

	

	public void createJob(boolean internationalApply,
						  String jobType,
						  double workingHours,
						  String jobCategory,
						  boolean licenseRequired,
						  double minYearsOfExperience) throws InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot create jobs. You have been blacklisted");
		}

		Job newJob = new Job(this, internationalApply, jobType, workingHours, jobCategory, licenseRequired, minYearsOfExperience);
		System.out.println(newJob.getJobID());
		this.jobList.put(newJob.getJobID(), newJob);

	}

	public void createJob() throws InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot create jobs. You have been blacklisted");
		}

		Scanner scan = new Scanner(System.in);

		boolean internationalApply;
		String jobType;
		double workingHours;
		String jobCategory;
		boolean licenseRequired;
		double minYearsOfExperience;

		System.out.print("Is job open to international applicants? Press Y for yes and N for no ");
		String intResponse =  scan.next();
		if(intResponse.compareToIgnoreCase("y")==0)
		{
			internationalApply = true;
		}
		else {
			internationalApply = false;
		}

		System.out.print("Enter the job type ");
		jobType =  scan.next();

		System.out.print("Enter number of working hours ");
		workingHours =  Double.parseDouble(scan.next());

		System.out.print("Enter the job category ");
		jobCategory =  scan.next();

		System.out.print("Is a driving license required? Press Y for yes and N for no ");
		String licenseResponse =  scan.next();
		if(licenseResponse.compareToIgnoreCase("y")==0)
		{
			licenseRequired = true;
		}
		else {
			licenseRequired = false;
		}

		System.out.print("Enter years of experience required ");
		minYearsOfExperience =  Double.parseDouble(scan.next());

		Job newJob = new Job(this, internationalApply, jobType, workingHours, jobCategory, licenseRequired, minYearsOfExperience);
		System.out.println(newJob.getJobID());
		this.jobList.put(newJob.getJobID(), newJob);

	}



	public void shortlistApplicants() throws ParseException, InvalidInterviewException, InvalidJobException, CriteriaIncompleteException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot shortlist applicants. You have been blacklisted");
		}
		if(jobList.isEmpty())
		{
			System.out.println("You haven't created any jobs");
		}
		else
		{
			try
			{
				Scanner scan = new Scanner(System.in);

				System.out.print("Enter the Job ID:");
				String jobid = scan.next();

				if(this.jobList.get(jobid) == null)
				{
					throw new InvalidJobException("Job is not available");
				}
				Job job =  this.jobList.get(jobid);
				List<Applicant> appID = filterApplicantByJob(jobid);
				System.out.println("Suitable Applicants Applicant:");
				for(Applicant ap :  appID )
				{
					//create a default job application for all shortlisted applicants
					JobApplication application = new JobApplication(job, ap);
					job.addJobApplication(ap,application);
					System.out.println("Applicant ID : "+ ap.getUsername() + "  Name : " +ap.getName() +"  contact: "+ap.getContactNumber());
					System.out.println("\n");
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm");

				System.out.print("Enter starting interview timeslot in yyyy-MMM-dd HH:mm format ");
				scan.nextLine();
				String startTime = scan.nextLine();

				LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);


				System.out.print("Enter ending interview timeslot in yyyy-MMM-dd HH:mm format ");
				//scan.nextLine();
				String endingTime = scan.nextLine();

				LocalDateTime endDateTime = LocalDateTime.parse(endingTime, formatter);

				job.setStartingTimeSlot(startDateTime);
				job.setEndingTimeSlot(endDateTime);


				System.out.println("Enter the number of candidates required for the job:");
				int count = scan.nextInt();

				if(count >  appID.size())
				{
					throw new CriteriaIncompleteException("Required candidate count exceeds the shortlisted candidate");
				}
				for(int i = 0 ; i < count ; i++)
				{
					int rank = i+1;
					System.out.print("Enter the applicant username with rank " + rank + " :");
					String appUsername = scan.next();

					JobApplication application = job.fetchJobApplication(appUsername);

					application.setApplicantRank(rank);

					Applicant app = (Applicant) UserDatabase.fetchUser(appUsername);

					//send applicant a job application
					app.receiveJobApplication(job, application);

				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}


	}

	public void recordResult(Job job) throws ApplicantNotShortlistedException, ApplicantNotAvailableException, InvalidAccessRightsException {
		/*
		 * 1. Display the list of shortlisted applicants
		 * 2. Prompt the employer to enter the number of final applicant
		 * 3. Prompt the employer to select the final applicant (check whether this applicant is still available)
		 * 4. Add this applicant to the finalized list
		 * 5. Send this applicant a job offer
		 * 6. Add the offer to the offer list in the job class
		 * 7. Add the remaining applicants to the rejected student list
		 */
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot create jobs. You have been blacklisted");
		}

		//first fetch all the applications which have been ranked
		Map<String, JobApplication> rankedList = job.getRankedApplications();

		//display the list
		for(String appUsername : rankedList.keySet())
		{
			System.out.println(rankedList.get(appUsername));
		}
		String appUsername;
		int finalNumber;
		Scanner sc = new Scanner(System.in);

//		// Throws Exception if there are no shortlisted applicants
//		if(shortlistedApplicantList == null) {
//			throw new ApplicantNotShortlistedException("No applicants were shortlisted for job: " + job.getJobID());
//		}
//
//		// Displays all shortlisted applicants
//		for(String key : shortlistedApplicantList.keySet()) {
//			System.out.println("Applicant: " + shortlistedApplicantList.get(key).getUsername());
//		}

		System.out.println("Enter the number of final applicants");
		finalNumber = sc.nextInt();

		for(int i = 0; i < finalNumber; i++) {

			System.out.println("Enter the Applicant username");

			appUsername = sc.next();

			Applicant app = rankedList.get(appUsername).getApplicant();

			// checks whether the applicant already has an offer
			if(!app.getStatus().equals("available")) {
				throw new ApplicantNotAvailableException("This applicant is not available anymore");
			}

			//sends offer to applicant
			app.receiveJobOffer(rankedList.get(appUsername));

			//update the status of the application
			rankedList.get(appUsername).finalizeApplicant();

			//set applicant status to pending
			app.setStatus("PENDING");

		}

	}



	public List<Applicant> filterApplicantByJob(String jobID)
	{
		Job job = this.jobList.get(jobID);
		List<Applicant> applicantList = UserDatabase.getAllApplicants();

		List<Applicant> filteredAppList = applicantList.stream()
				.filter(applicant -> applicant.getStatus().compareToIgnoreCase("available")==0)
				.filter(applicant -> applicant.checkNationalityRequirement(job) <=0)
				.filter(applicant -> applicant.checkLicenseRequirement(job) <= 0)
				.filter(applicant -> applicant.checkJobType(job))
				.filter(applicant -> applicant.checkWorkingHours(job))
				.collect(Collectors.toList());

		return filteredAppList;
	}

	public void showAllJobs()
	{
		if(jobList.isEmpty())
		{
			System.out.println("You haven't created any jobs");
		}
		else
		{
			for(String jobID : jobList.keySet())
			{
				System.out.println(jobList.get(jobID));
			}
		}

	}


}
