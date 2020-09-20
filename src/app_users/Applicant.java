package app_users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.InvalidPasswordException;
import app_items.Availability;
import app_items.Blacklist;
import app_items.Complaint;
import app_items.EmploymentRecord;
import app_items.Job;
import app_items.Offer;
import app_items.Qualifications;

public class Applicant extends User {
	
	//international or local
	private String nationality;
	
	private String name;
	
	private String contactNumber;
	
	Scanner scan = new Scanner(System.in);
	//list of past employment records
	private ArrayList<EmploymentRecord> employmentRecords;
	
	//list of qualification 
	private ArrayList<Qualifications> studentQualifications;
	
	//available, pending, unknown, employed
	private String status;
	
	
	private Offer newJobOffer;
	//collection of availabilities stored against type as key
	private HashMap<String, Availability> availabilityList;
	
	//license
	private boolean hasLicense;
	
	private ArrayList<String> references;
	
	private Job jobOffer;
	
	private Job acceptedJob;
	
	private HashMap <Job, String> interviewList;
	
	//arraylist of complaints
	private ArrayList<Complaint> complaintList;


	public Applicant(String username, String password, String name, String contactNumber, String nationality)
	{
		super(username, password);
		this.name = name;
		this.contactNumber = contactNumber;
		this.nationality = nationality;
		this.hasLicense = false;
		this.status = "available";
		this.references = new ArrayList<>();
		
		this.employmentRecords =  new ArrayList<>();
		this.studentQualifications = new ArrayList<>();
		this.availabilityList  = new HashMap<>();
		this.interviewList = new HashMap<>();
		this.complaintList = new ArrayList<>();
		
		this.jobOffer = null;
		this.acceptedJob = null;
	}
	
	public void sendComplaint() throws NullPointerException, InvalidComplaintHierarchyException
	{
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
		}
	}

	public void showStudentProfile() //Nachi edit - removed applicant argument from function
	{
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
//	
//	public void searchemployer(UserDatabase u)
//	{
//		System.out.println("Enter the employer ID:");
//		String ID = scan.next();
//		Employer e = u.getEmployer(ID);
//		System.out.println("Employer Name: "+e.getname()+"\nAbout:"+e.getabout());
//		
//	}
	
	public void updateAvailability()
	{
		
		System.out.println("1. Add Availability\n 2. Remove Availability\n Enter the option:");
		int option = Integer.parseInt(scan.next());
		switch(option)
		{
		case 1:
		 HashMap<String, ArrayList<String>> period = new  HashMap<String, ArrayList<String>>();
		 int a;char val;
		 ArrayList<String> value = new  ArrayList<String>();
		 ArrayList<String> jobCategories = new  ArrayList<String>();
		System.out.println("Enter the availability Type: ");
		 String type = scan.next();
		 do
		 {
		 System.out.println("Enter the job Category:");
		 String temp = scan.next();
		 
		 jobCategories.add(temp);
		 
		 System.out.println("Do you want to add another job category: Yes /No");
		 char t = scan.next().charAt(0);
		 if(t == 'Y' || t== 'y')
		  a=0;
		 else a=1;
		 }while(a==0);
		 
		 for(int i =0; i <7 ; i++)
		 {   
			 value.clear();
			 System.out.println("Enter the avialability of day "+ (i+1));
			 System.out.println("Available in Morning? yes /No");
			  val = scan.next().charAt(0);
			  
			  
			 if(val == 'Y' || val == 'y')
				value.add("Morning");
			 
			 
			 System.out.println("Available in Afternoon? yes /No");
			  val = scan.next().charAt(0);
			  
			 if(val == 'Y' || val == 'y')
				value.add("Afternoon");
			 
			 System.out.println("Available in Evening? yes /No");
			  val = scan.next().charAt(0);
			  
			 if(val == 'Y' || val == 'y')
				value.add("Evening");
			 
			 String key = "Day"+(i+1);
			 period.put(key, new ArrayList<String>(value));
			 System.out.println(period);
		 }
		 Availability available = new Availability(type, period, jobCategories);
		 availabilityList.put(type, available);
		 break;
		 
		case 2:
			int i =0;
		for(String en : availabilityList.keySet())
		{
			
			Availability ab = availabilityList.get(en);
			System.out.println("\nAvailability List "+ (i+1) );
			System.out.println("Type: "+ab.getType());
			System.out.println("Category :"+ab.getCategoryList());
			System.out.println("period:"+ ab.getperiod());
			i++;
		}
		System.out.println("Enter the type to delete the record: ");
		String temp = scan.next();
		this.availabilityList.remove(temp);
		
		}
	}
	
	public void addrefernce()
	{
		System.out.println("Enter the reference name:");
		String name = scan.next();
		System.out.println("Enter the role:");
		String role = scan.next();
		String temp = name+"  "+role;
		this.references.add(temp);
	}

	public void updateEmploymentRecords()
	{
		System.out.println("1. Add a Employment Record\n 2. Remove a Employment Record\n Enter the option:");
		int option = Integer.parseInt(scan.next());
		switch(option)
		{
		case 1:
		
		System.out.println("Enter the Company Name :");
		 String companyName = scan.next();
		 
		 System.out.println("Enter the Skill/Language/Tool :");
		 String domain = scan.next();
		 
		 System.out.println("Enter the years of experience :");
		 double yearsOfExperience =scan.nextDouble();

		 System.out.println("Enter the role :");
		 String role = scan.next();
	    	
		 EmploymentRecord jb = new EmploymentRecord(companyName,domain,yearsOfExperience,role);
		 this.employmentRecords.add(jb);
		 break;
		 
		case 2:

			try
			{
				for(int i = 0; i< employmentRecords.size() ; i++)
				{
					 EmploymentRecord job = employmentRecords.get(i);
					 System.out.println("Employment Record "+(i+1)+ ":\n "+
					 " Company Name :"+job.getcompanyName()+"\n"+
							 "Domain/skill  :"+job.getdomain()+"\n"+"Experience  :"+job.getExperience()+"\n"+" Role  :"+job.getrole()+" \n");
					 
				}
				System.out.println("Enter the option to delete the record: ");
				int temp = Integer.parseInt(scan.next());
				this.employmentRecords.remove(temp-1);
			}
			catch(Exception e)
			{
				System.out.println(" Error :"+e);
			}
			break;
		 
		}
		 
		 
      //  this.employmentRecords.add(s);
	}
	
	
		
	
	public void updatestatus(String s)
	{
		this.status = s;
	}
	
	public void updateQualification()
	{
		
		System.out.println("1. Add a qualification\n 2. Remove Qualification\n Enter the option:");
		int option = Integer.parseInt(scan.next());
		switch(option)
		{
		case 1:
		String degree, specialization;
		System.out.println(" Enter the degree:");
		degree = scan.next();
		System.out.println(" Enter the specialization:");
		specialization = scan.next();
		Qualifications q = new Qualifications(degree, specialization);
		this.studentQualifications.add(q);
		break;
		
		case 2:
			try {
				
				
				for(int i =0; i< studentQualifications.size() ; i++)
				{
				  Qualifications qual = studentQualifications.get(i);
				  System.out.println(" Qualification "+(i+1)+" :"+ qual.getdegree() +"    "  +qual.getspecialization());
				}
				System.out.println("Enter the option to remove the qualification:");
				int temp = Integer.parseInt(scan.next());  
				this.studentQualifications.remove(temp-1);
				}
				
				catch(Exception e )
				{
					System.out.println("error");
				}
			break;
			
			default :
				System.out.println("Enter the valid option:");
				break;
		}
	}
	

	
	public void updateLicenseInfo(boolean update)
	{
		this.hasLicense = update;
	}
	
	
	public String getNationality()
	{
		return this.nationality;
	}
	
	public String getname()
	{
		return this.name;
	}
	
	public String getstatus()
	{
		return this.status;
	}
	
	public void responsetoJobOffer()
	{
		System.out.println("Job ID :"+jobOffer.getJobID()+"\nDescription:" +jobOffer.getJobDesc());
		System.out.println(" Do you want to accept the job: yes/no");
		char t = scan.next().charAt(0);
		if(t == 'y'  || t == 'Y')
		{
			acceptedJob = jobOffer;
			status = "Employed";
		}
		else 
		{
			jobOffer = null;
			status = "Available";
		}
	}
	
	
	public void viewinterview()
	{
		for(Job j : interviewList.keySet())
		{
			System.out.println("Job Id :"+ j.getJobID()  + "Timing :" + interviewList.get(j));
		}
	
	}
	
	//In case you already have a class variable of type Offer, let me know what youve called it and ill modify it accordingly in my class
	

	public Offer getNewJobOffer() 
	{
			return this.newJobOffer;
	}

	public void setNewJobOffer(Offer newJobOffer) 
	{
		this.newJobOffer = newJobOffer;
	}



	//I think you should be able to add this directly without having any variable name mismatch
	public void addInterview(Job job, String time)
	{
		this.interviewList.put(job, time);
	}
	
	
	

	
		
}
