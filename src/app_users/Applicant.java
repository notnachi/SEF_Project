package app_users;

import java.time.LocalDateTime;
import java.util.*;

import app_exceptions.DuplicateCategoryException;
import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_items.*;

public class Applicant extends User {

	/*
	 * Login Details - username and password (handled by user class)
	 * Personal Details - name, contact number
	 * Profile Specifics - Employment Records, Qualifications, References
	 * Logic Fields required for shortlisting - nationality, availability, hasLicense, status
	 * Fields updated by other classes at runtime - interviewList, complaintList, jobOffer
	 * Blacklist Related Fields - isProvisionallyBlacklisted, isFullyBlacklisted
	 */

	/*********** Personal Details ***********/
	private String name;

	private String contactNumber;

	/*********** Profile Specifics ***********/

	//list of past employment records
	private ArrayList<EmploymentRecord> employmentRecords;

	//list of qualification
	private ArrayList<Qualifications> studentQualifications;

	private ArrayList<String> references;

	/*********** Logic Fields ***********/

	//international or local
	private boolean isInternational;

	//collection of availabilities stored against type as key
	private HashMap<String, Availability> availabilityList;

	//license
	private boolean hasLicense;

	//available, pending, unknown, employed
	private String status;

	private double totalYearsOfExperience;


	/*********** Runtime updated ***********/

	private Map<String, JobApplication> applicationList;

	//stores the interview times
	private ArrayList <Interview> interviewList;

	private JobApplication jobOffer;

	private LocalDateTime fullyBlacklistDate;

	/*********** Blacklist Related ***********/


	public Applicant(String username, String password, String name, String contactNumber, boolean isInternational, boolean hasLicense)
	{
		super(username, password);
		this.name = name;
		this.contactNumber = contactNumber;
		this.isInternational = isInternational;
		this.hasLicense = hasLicense;
		this.totalYearsOfExperience = 0;
		this.status = "available";
		this.references = new ArrayList<>();
		
		this.employmentRecords =  new ArrayList<>();
		this.studentQualifications = new ArrayList<>();
		this.availabilityList  = new HashMap<>();

		this.applicationList = new HashMap<>();
		this.interviewList = new ArrayList<>();
		
		this.jobOffer = null;


		this.fullyBlacklistDate = null;

	}

	public void receiveJobApplication(Job job, JobApplication application)
	{
		this.applicationList.put(job.getJobID(),application);
	}

	public String getName() {
		return name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void showStudentProfile() throws InvalidAccessRightsException //Nachi edit - removed applicant argument from function
	{
		if(super.getFullyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You are fully Blacklisted");
		}
		System.out.println("Status: "+status);
		
		if(!studentQualifications.isEmpty()) 
		{
		for(int i =0; i< studentQualifications.size() ; i++)
		{
		  Qualifications q = studentQualifications.get(i);
		  System.out.println(" Qualification "+(i+1)+" :"+ q.getdegree() +"    "  +q.getspecialization());
		}
		}
		else 
		{
			System.out.println("No Qualification is added to the profile");
		}
		
		if(!employmentRecords.isEmpty())
		{
		for(int i = 0; i< employmentRecords.size() ; i++)
		{
			 EmploymentRecord jb = employmentRecords.get(i);
			 System.out.println("Employment Record "+(i+1)+ ":\n "+
			 " Company Name :"+jb.getcompanyName()+"\n"+
					 "Domain/skill  :"+jb.getdomain()+"\n"+"Experience  :"+jb.getExperience()+"\n"+" Role  :"+jb.getrole()+" \n");
			 
		}
		}
		else
		{
			System.out.println("No Employment Records is added to the profile");
		}
		
		if(!references.isEmpty())
		{
		System.out.println("References : " +references);
		}
		else
		{
			System.out.println("No References added to the profile");
		}
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public ArrayList<Interview> getInterviewList() {
		return interviewList;
	}

	public int checkNationalityRequirement(Job job)
	{
		return Boolean.compare(job.isInternationalApply(), this.isInternational);
	}

	public int checkLicenseRequirement(Job job)
	{
		return Boolean.compare(job.isLicenseRequired(), this.hasLicense);
	}

	public boolean checkJobType(Job job)
	{
		return this.availabilityList.containsKey(job.getJobType());
	}

	public boolean checkWorkingHours(Job job)
	{
		if(this.checkJobType(job))
		{
			if(this.availabilityList.get(job.getJobType()).getAvailableWorkingHours() >= job.getWorkingHours())
				return true;
			else
				return false;

		}
		return false;
	}

	public void addAvailability() throws InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Scanner sc = new Scanner(System.in);
		String jobType;
		double workingHours;
		String jobCategory;
		System.out.println("Enter the job type");
		jobType = sc.next();
		System.out.println("Enter the number of working hours");
		workingHours = sc.nextDouble();
		System.out.println("Enter the job category");
		jobCategory = sc.next();

		Availability newAvailability = new Availability(jobType, workingHours, jobCategory);
		this.availabilityList.put(newAvailability.getJobType(), newAvailability);
	}

	public void addEmploymentRecord() throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Scanner sc = new Scanner(System.in);
		String companyName;
		String domain;
		double yearsOfExperience;
		String role;
		System.out.println("Enter the comapnay name");
		companyName = sc.next();
		System.out.println("Enter the domain of work");
		domain = sc.next();
		System.out.println("Enter the years of Experience");
		yearsOfExperience = sc.nextDouble();
		System.out.println("Enter the role in the company");
		role = sc.next();


		EmploymentRecord newEmploymentRecord = new EmploymentRecord(companyName, domain, yearsOfExperience, role);
		for (EmploymentRecord emp : this.employmentRecords) {
			if((emp.getcompanyName().equals(companyName)) && (emp.getdomain().equals(domain)) && (emp.getrole().equals(role)))
			//if (this.employmentRecords.contains(newEmploymentRecord))
			{
				throw new DuplicateCategoryException ("The employer records already exist");
			}
		}

		this.employmentRecords.add(newEmploymentRecord);
		this.totalYearsOfExperience += yearsOfExperience;
	}

	public void addQualifications() throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Scanner sc = new Scanner(System.in);
		String degree;
		String specialization;
		System.out.println("Enter the degree");
		degree = sc.next();
		System.out.println("Enter the field of specialization");
		specialization = sc.next();

		Qualifications newQualifications = new Qualifications(degree, specialization);
		for (Qualifications qua : this.studentQualifications) {
			if((qua.getdegree().equals(degree)) && (qua.getspecialization().equals(specialization)))
			{
				throw new DuplicateCategoryException ("The student qualification already exist");
			}
		}
		this.studentQualifications.add(newQualifications);
	}

	public void addReferences() throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Scanner sc = new Scanner(System.in);
		String references;
		System.out.println("Enter the reference you wish to update");
		references = sc.next();
		if (this.references.contains(references))
		{
			throw new DuplicateCategoryException ("The student qualification already exist");
		}
		this.references.add(references);
	}

	public void addAvailability(String jobType, double workingHours, String jobCategory) throws InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Availability newAvailability = new Availability(jobType, workingHours, jobCategory);
		this.availabilityList.put(newAvailability.getJobType(), newAvailability);
	}

	public void addEmploymentRecord(String companyName, String domain, double yearsOfExperience, String role) throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		EmploymentRecord newEmploymentRecord = new EmploymentRecord(companyName, domain, yearsOfExperience, role);
		for (EmploymentRecord emp : this.employmentRecords) {
			if((emp.getcompanyName().equals(companyName)) && (emp.getdomain().equals(domain)) && (emp.getrole().equals(role)))
			//if (this.employmentRecords.contains(newEmploymentRecord))
			{
				throw new DuplicateCategoryException ("The employer records already exist");
			}
		}

		this.employmentRecords.add(newEmploymentRecord);
	}

	public void addQualifications(String degree, String specialization) throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		Qualifications newQualifications = new Qualifications(degree, specialization);
		for (Qualifications qua : this.studentQualifications) {
			if((qua.getdegree().equals(degree)) && (qua.getspecialization().equals(specialization)))
			{
				throw new DuplicateCategoryException ("The student qualification already exist");
			}
		}
		this.studentQualifications.add(newQualifications);
	}

	public void addReferences(String references) throws DuplicateCategoryException, InvalidAccessRightsException {
		if(super.getFullyBlacklistStatus() || super.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("Cannot perform operation. You have been blacklisted");
		}
		if (this.references.contains(references))
		{
			throw new DuplicateCategoryException("The student qualification already exist");
		}
		this.references.add(references);
	}

	public void addInterview(Interview interview)
	{
		/*
		 * Before adding check whether the interview time clashes
		 * with other interview objects in the interview list
		 */
		this.interviewList.add(interview);
	}

	public void viewInterview()
	{
		if(interviewList.isEmpty())
		{
			System.out.println("You have no interviews scheduled");
		}
		for(Interview iw : interviewList)
		{
			System.out.println(iw);
		}
	}


	public void receiveJobOffer(JobApplication application)
	{
		this.jobOffer = application;
		this.setStatus("Pending");
	}

	public void rejectOffer()
	{
		this.jobOffer = null;
	}

	public JobApplication checkOffer()
	{
		return this.jobOffer;
	}

	public void acceptJobOffer(boolean acceptOffer)
	{
		this.jobOffer.acceptOffer();
	}

	public void acceptJobOffer()
	{
		if(this.jobOffer == null)
		{
			System.out.println("You have no active job offers");
		}
		else
		{
			Scanner scan =  new Scanner(System.in);
			System.out.println("You have been sent the following offer");
			System.out.println(this.jobOffer);
			System.out.print("Do you want to accept it? Press Y for yes and N for no ");
			String response =  scan.next();
			if(response.compareToIgnoreCase("y")==0)
			{
				jobOffer.acceptOffer();
				this.status = "EMPLOYED";
			}
			else
			{
				jobOffer.rejectOffer();
				this.status = "available";
			}
		}

	}






	
	

	
		
}
