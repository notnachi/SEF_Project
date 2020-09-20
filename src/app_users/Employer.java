package app_users;
import app_items.*;
import app_exceptions.CriteriaIncompleteException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.InvalidInterviewException;
import app_exceptions.InvalidJobException;
import app_exceptions.JobNotAvailableException;

import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Employer extends User {
	
	private String employerName;
	private String emailID;
	private String contactNumber;
	private String aboutEmployer;
	
	private ArrayList<Job> jobList = new ArrayList<Job>();
	private ArrayList<Interview> interviewList = new ArrayList<Interview>();
	
	//arraylist of complaints
	private ArrayList<Complaint> complaintList;
	
	public Employer(String username, String password, String empName, String emailID, String contactNumber, String empDesc) {
		
		super(username,password);
		this.employerName = empName;
		this.emailID = emailID;
		this.contactNumber = contactNumber;
		this.aboutEmployer = empDesc;
		
		this.complaintList = new ArrayList<>(); 
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
		}
	}
	
	public void showEmployerProfile()
	{
		System.out.println("Name " + this.employerName);
	}
	

	public void createJob()
	{
		String jobID; 
		boolean internationalApply; 
		ArrayList<String> jobCategories = new ArrayList<String>();
		String jobType = null; 
		HashMap<String, ArrayList<String>> jobPeriod = new HashMap<String, ArrayList<String>>();
		String continueLoop = null;
		int numberOfStudents;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your jobID");
		jobID = sc.next();
		System.out.println("Enter if the job is applicable to international students");
		internationalApply = sc.nextBoolean();
		do {
			System.out.println("Enter all the job categories to which this job belongs");
			jobCategories.add(sc.next());
			System.out.println("Do you wish to continue? Enter Y or N");
			continueLoop = sc.next().toUpperCase();
			if(!continueLoop.equals("Y") || !continueLoop.equals("N")) {
				System.out.println("Invalid Input! Enter again");
				continue;
			}
		}while(continueLoop.equals("Y"));
		
		System.out.println("Please enter the shifts for the job");
		String day, time;
		ArrayList<String> jobTime = new ArrayList<String>();
		String conLoop;
		do {
			System.out.println("Enter the day");
			day = sc.next();
			System.out.println("Enter the time");
			time = sc.next();
			if(jobPeriod.isEmpty()) {
				jobTime.add(time);
				jobPeriod.put(day, jobTime);
				jobTime.clear();
			}
			else if (jobPeriod.containsKey(day)){
				jobTime = jobPeriod.get(day);
				jobTime.add(time);
				jobPeriod.replace(day, jobTime);
				jobTime.clear();
			}
			else {
				jobTime.add(time);
				jobPeriod.put(day, jobTime);
				jobTime.clear();
			}
			System.out.println("Please enter Y to continue and N to stop");
			conLoop = sc.next().toUpperCase();
		} while(!conLoop.equals("N"));
		
		System.out.println("Enter the job type");
		jobType = sc.next();
		System.out.println("Enter the maximum number of students required for the job");
		numberOfStudents = sc.nextInt();
		Availability details = new Availability(jobType, jobPeriod, jobCategories);
		Job newJob = new Job(jobID, this, internationalApply, details, numberOfStudents);
		this.jobList.add(newJob);
	}
	
	public Job getJobDetails(String jobID)
	{
		for(Job j : jobList) {
			if(j.getJobID().equals(jobID)) {
				return j;
			}
		}
		return null;
	}
	
	public ArrayList<Job> getJobHasResult()
	{
		ArrayList<Job> jobResult = new ArrayList<Job>();
		for(Job j : jobList) {
			if(!j.getResult().equals(null)) {
				jobResult.add(j);
			}
		}
		return jobResult;
	}
	
	public void showAllJobs()
	{
		for(Job j : jobList) {
			System.out.println(j.toString());
		}
	}
	
	public ArrayList<Applicant> retrieveEligibleApplicants(Job job)
	{
		//search in user database for applicants who have the require availability for the job specified
		
		return null;
	}
	
	public void createInterview(ArrayList<Applicant> applicants, Job job, String interviewTime) {
		Interview interview = new Interview();
		interview.setAppList(applicants);
		interview.setInterviewTime(interviewTime);
		interview.setJob(job);
	}
	
	public void showInterview() {
		if(this.interviewList.isEmpty()) {
			System.out.println("The employer has no scheduled interviews");
		}
		else {
			for(Interview i : this.interviewList) {
				System.out.println("This interview is scheduled for Job: " + i.getJob().getJobID());
				System.out.println("The interview is scheduled to be held at time: " + i.getInterviewTime());
			}
		}
	}
	
	public void shortListCandidates() throws Exception
	{
		/**
		 * Call the retrieve applicant method here
		 * Check if number of candidates required is less than total number of eligible candidates (throw exception)
		 * run a loop for number of times and add shortlisted candidates 
		 * when executed, 
		 * 1- add the job to the student job offers hashmap
		 * 2 - 
		 */
		
		
		System.out.println("Enter the job ID for which you want to shortlist candidates");
		Scanner sc = new Scanner(System.in);
		String id = sc.next();
		Job job = null;
		for(Job j : this.jobList) {
			if(j.getJobID().equals(id)) {
				job = j;
			}
		}
		if(job == null) {
			throw new InvalidJobException("Given job id is invalid");
		}
		
		String s = null;
		String appUsername = null;
		boolean flag = false;
		if(!job.isJobAvailable()) {
			throw new JobNotAvailableException("This Job is not available anymore");
		}
		else {
			//ArrayList<Applicant> eligibleCandidates = retrieveEligibleApplicants(job);
			
			ArrayList<Applicant> shortlistedCandidates = new ArrayList<Applicant>();
			Applicant applicant;
			UserDatabase user = new UserDatabase();
			
			//Dummy list of eligible candidates that has all candidates
			ArrayList<Applicant> eligibleCandidates = new ArrayList<>();
			for(String username : user.getUserData().keySet())
			{
				if(user.fetchUser(username) instanceof Applicant)
				{
					eligibleCandidates.add((Applicant) user.fetchUser(username));
				}
			}
			int i = 1;
			String j;
			
			//Prints everyone in eligible candidates
			for(Applicant a : eligibleCandidates) {
				System.out.println(a.toString());
			}
			
			//checks if eligible candidates is more than the number of students required
			if(eligibleCandidates.size() > job.getNumberOfStudents()) {
				
				//checks if interview time is unique
				do {
					System.out.println("Enter the Interview Time ");
					s = sc.next();
					for(Interview k : interviewList) {
						if (k.getInterviewTime().equals(s)) {
							
							System.out.println("Interview Time already exists, enter again!");
							flag = true;
							break;
						}
						else {
							flag = false;
						}	
					}
					
				} while(flag == true);
				
				//gets the list of shortlisted candidates
				do {
					System.out.println("Enter the Applicant with rank " + i);
					appUsername = sc.next();
					applicant = (Applicant) user.fetchUser(appUsername);//add a condition to check if the db contains the value first later
					shortlistedCandidates.add(applicant);
					/**
					 * 		add method in applicant
					 * 		public void addInterview(Job job, String time) {
								this.interviewList.put(job, time);
							}
					 */
					applicant.addInterview(job, s);
					System.out.println("Do you want to continue?");
					j = sc.next().toUpperCase();
					if(!j.equals("Y") || !j.equals("N")) {
						System.out.println("Invalid Input! Enter again");
						continue;
					}
				}while(j.equals("Y"));
				createInterview(shortlistedCandidates, job, s);
			}
			else {
				throw new CriteriaIncompleteException("Not enough eligible candidates for this job");
			}
		}
	}
	
	//Records result
	public void recordResult() throws Exception {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the interview time for which you wish to record the result");
		String time = sc.next();
		Interview interview = null;
		for(Interview i : this.interviewList) {
			if(i.getInterviewTime().equals(time)) {
				interview = i;
			}
		}
		if(interview == null) {
			throw new InvalidInterviewException("No such interview time exists");
		}
		
		System.out.println("List of applicants who attended the interview");
		for(Applicant applicant : interview.getAppList()) {
			System.out.println("Applicant Username: " + applicant.getUsername());
		}
		ArrayList<Applicant> successfulCandidates = new ArrayList<Applicant>();
		ArrayList<Applicant> unsuccessfulCandidates = new ArrayList<Applicant>();
		Result result = new Result();
		String j;
		Applicant applicant;
		String appUsername;
		UserDatabase user = new UserDatabase();
		int count = 0;
		
		//gets list of successful candidates
		do {
			System.out.println("Enter the Applicants who were successful in their interview ");
			appUsername = sc.next();
			applicant = (Applicant) user.fetchUser(appUsername);//add a condition to check if the db contains the value first later
			successfulCandidates.add(applicant);
			count += 1;
			
			//once user has  entered number of candidates required, automatically breaks the loop
			if(count == interview.getJob().getNumberOfStudents() ) {
				System.out.println("You have entered the maximum number of students allowed for the job");
				break;
			}
			System.out.println("Enter Y to continue and N to stop");
			j = sc.next();
			
			//if user wishes to exit the loop, first checks if user has entered total number of candidates required, if not then throws exception
			if(j.equals("N")) {
				if(successfulCandidates.size() < interview.getJob().getNumberOfStudents()) {
					throw new CriteriaIncompleteException("The number of applicants required for the job has not been fulfilled");
				}
			}
		} while (!j.equals("N"));
		
		//creates list of unsuccessful candidates
		for(Applicant a : interview.getAppList()) {
			if(!successfulCandidates.contains(a)) {
				unsuccessfulCandidates.add(a);
			}
		}
		
		//creates and records result
		result.setFinalStudents(successfulCandidates);
		result.setRejectedStudents(unsuccessfulCandidates);
		result.setInterview(interview);
		interview.getJob().setResult(result);
		
		//sends job offer to every applicant in the successful list
		sendJobOffers(interview.getJob());
	}
	
	//sends job offer to the applicant specified
	//Question: is the employer keeping track of applicants he has sent an offer to?
	public void sendJobOffers(Job job)
	{
		/**
		 * Get the student ID's who have cleared the interview
		 * Send job offers to these students - set values in the student job offer hashmap
		 * Set status of those students to pending	
		 */
		for(Applicant a : job.getResult().getFinalStudents()) {
			if(!a.getNewJobOffer().getJob().getJobID().equals(job.getJobID())){
				Offer offer = new Offer(this, job, a);
				a.setNewJobOffer(offer);
			}
		}
	}
}
