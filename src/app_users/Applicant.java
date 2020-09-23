package app_users;

import java.util.*;

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

	//stores the interview times
	private ArrayList <Interview> interviewList;

	private Offer jobOffer;

	//arraylist of complaints
	private ArrayList<Complaint> complaintList;

	private Date fullyBlacklistDate;

	/*********** Blacklist Related ***********/

	private boolean isProvisionallyBlacklisted;

	private boolean isFullyBlacklisted;


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
		this.interviewList = new ArrayList<>();
		this.complaintList = new ArrayList<>();
		
		this.jobOffer = null;

		this.isFullyBlacklisted = false;
		this.isProvisionallyBlacklisted = false;

		this.fullyBlacklistDate = null;

	}
	public boolean getProvisionallyBlacklistStatus()
	{
		return this.isProvisionallyBlacklisted;
	}

	public boolean getFullyBlacklistStatus()
	{
		return this.isFullyBlacklisted;
	}

	public void setFullyBlacklistDate(Date fullyBlacklistDate)
	{
		this.fullyBlacklistDate = fullyBlacklistDate;
	}

	public Date getFullyBlacklistDate()
	{
		return this.fullyBlacklistDate;
	}

	
	public void sendComplaint() throws NullPointerException, InvalidComplaintHierarchyException, UserAlreadyBlacklistedException, InvalidAccessRightsException {

		//avoid accessing this method if user is blacklisted
		if(this.getFullyBlacklistStatus() || this.getProvisionallyBlacklistStatus())
		{
			throw new InvalidAccessRightsException("You have been blacklisted. Cannot perform operation.");
		}

		UserDatabase userDB = new UserDatabase();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username ");
		String username = scan.nextLine();
		
		//check if user is of an applicant type
		if(!(userDB.fetchUser(username) instanceof Employer))
		{
			throw new InvalidComplaintHierarchyException("Username is not of type Employer");
		}
		Employer emp = (Employer) userDB.fetchUser(username);

		if(emp.getFullyBlacklistStatus() || emp.getProvisionallyBlacklistStatus())
		{
			throw new UserAlreadyBlacklistedException("This user has already been blacklisted");
		}
		
		System.out.print("Enter your complaint ");
		String complaintDesc = scan.nextLine();
		
		Complaint newComplaint = new Complaint(this, emp, complaintDesc);
		
		emp.getComplaint(newComplaint);
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

	public void setFullyBlacklistStatus(boolean isFullyBlacklisted)
	{
		this.isFullyBlacklisted = isFullyBlacklisted;
	}

	public void setProvisionallyBlacklistStatus(boolean isProvisionallyBlacklisted)
	{
		this.isProvisionallyBlacklisted = isProvisionallyBlacklisted;
	}

	public void showStudentProfile() throws InvalidAccessRightsException //Nachi edit - removed applicant argument from function
	{
		if(isFullyBlacklisted)
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

	public void addAvailability(String jobType, double workingHours, String jobCategory)
	{
		Availability newAvailability = new Availability(jobType, workingHours, jobCategory);
		this.availabilityList.put(newAvailability.getJobType(), newAvailability);
	}

	public void addInterview(Interview interview)
	{
		/*
		 * Before adding check whether the interview time clashes
		 * with other interview objects in the interview list
		 */
		this.interviewList.add(interview);
	}

	public void receiveJobOffer(Offer jobOffer)
	{
		this.jobOffer = jobOffer;
		this.setStatus("Pending");
	}

	public void acceptJobOffer(boolean acceptOffer)
	{
		this.jobOffer.updateOfferStatus(acceptOffer);
	}






	
	

	
		
}
