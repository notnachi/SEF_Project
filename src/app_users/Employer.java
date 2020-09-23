package app_users;
import app_exceptions.*;
import app_items.*;

import java.util.*;
import java.util.stream.Collectors;

public class Employer extends User {
	
	private String employerName;
	private String emailID;
	private String contactNumber;
	private String aboutEmployer;
	
	private HashMap<String, Job> jobList = new HashMap<>();
	private ArrayList<Interview> interviewList = new ArrayList<Interview>();
	
	//arraylist of complaints
	private ArrayList<Complaint> complaintList;

	private boolean isProvisionallyBlacklisted;

	private boolean isFullyBlacklisted;
	
	public Employer(String username, String password, String empName, String emailID, String contactNumber, String empDesc) {
		
		super(username,password);
		this.employerName = empName;
		this.emailID = emailID;
		this.contactNumber = contactNumber;
		this.aboutEmployer = empDesc;
		
		this.complaintList = new ArrayList<>();

		this.isFullyBlacklisted = false;
		this.isProvisionallyBlacklisted = false;
	}
	
	public void sendComplaint() throws NullPointerException, InvalidComplaintHierarchyException
	{
		UserDatabase userDB = new UserDatabase();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username ");
		String username = scan.nextLine();
		
		//check if user is of an applicant type
		if(!(userDB.fetchUser(username) instanceof Applicant))
		{
			throw new InvalidComplaintHierarchyException("Username is not of type Applicant");
		}
		Applicant app = (Applicant) userDB.fetchUser(username);
		
		System.out.print("Enter your complaint ");
		String complaintDesc = scan.nextLine();
		
		Complaint newComplaint = new Complaint(this,app, complaintDesc);
		
		app.getComplaint(newComplaint);
	}
	
	public void getComplaint(Complaint complaint)
	{
		this.complaintList.add(complaint);
		
		if(this.complaintList.size()==3)
		{
			Blacklist newBlacklist = new Blacklist();
			newBlacklist.provisionallyBlacklist(this);
			this.isProvisionallyBlacklisted = true;
		}
	}
	
	public void showEmployerProfile() throws InvalidAccessRightsException {
		if(isFullyBlacklisted)
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You are fully Blacklisted");
		}
		System.out.println("Name " + this.employerName);
		System.out.println("Email " + this.emailID);
		System.out.println("Contact " + this.contactNumber);
		System.out.println("About " + this.aboutEmployer);
	}
	

	public void createJob(boolean internationalApply,
						  String jobType,
						  double workingHours,
						  String jobCategory,
						  boolean licenseRequired,
						  double minYearsOfExperience,
						  int noOfStudentsRequired) throws InvalidAccessRightsException {
		if(isFullyBlacklisted || isProvisionallyBlacklisted)
		{
			throw new InvalidAccessRightsException("Cannot create jobs. You have been blacklisted");
		}

		Job newJob = new Job(this, internationalApply, jobType, workingHours, jobCategory, licenseRequired, minYearsOfExperience, noOfStudentsRequired);
		System.out.println(newJob.getJobID());
		this.jobList.put(newJob.getJobID(), newJob);

	}
	
	public void shortlistApplicants(Job job)
	{
		/*
		 * 1. Display the list of eligible candidates for the job
		 * 2. Let employer choose the number of applicants and run a loop for that many times
		 * 3. Enter the applicant ID
		 * 4. Enter the interview time for that applicant
		 * 5. Create an interview object. Add this object to the employer list and applicant list
		 * 6. Add the applicant to a shortlisted applicant list in the job
		 */
	}

	public void recordResult(Job job)
	{
		/*
		 * 1. Display the list of shortlisted applicants
		 * 2. Prompt the employer to enter the number of final applicant
		 * 3. Prompt the employer to select the final applicant (check whether this applicant is still available)
		 * 4. Add this applicant to the finalized list
		 * 5. Send this applicant a job offer
		 * 6. Add the remaining applicants to the rejected student list
		 */
	}



	public List<Applicant> filerApplicantByJob(String jobID)
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


}
