package app_items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import app_users.Applicant;
import app_users.Employer;

public class Job{
	
	private String jobID;
	//employer who is offering the job
	private Employer emp;
	//is job applicable for international students
	private boolean internationalApply;

	/*
	 * Keeping the job separate from availability and Employment Record classes
	 * New added variables -
	 * 1. Job Type (String) part time, full time or internship
	 * 2. Working hours (Double) - working hours that job requires
	 * 3. Job Cateogory (JobCategory) - job category of the job
	 * 4. License Required (Boolean) - is license required or not
	 * 5. minYearsOfExperience (double) - if min experience required
	 */

	private String jobType;
	private double workingHours;
	private JobCategory jobCategory;
	private boolean licenseRequired;
	private double minYearsOfExperience;

	
	//job description
	private String jobDesc;

	//count the successful applicants
	private int count;
	
	//number of students required 
	private int numberOfStudents;
	
	//job available or not
	private boolean jobAvailable;
	
	//stores the list of finalized candidates along with interview times
	private HashMap<Applicant, String> applicantInterviewTimes;
	
	
	
	Result result;


	

	@Override
	public String toString() {
		return "jobID=" + jobID + "\n"+
				"emp=" + emp + "\n"+ 
				"internationalApply=" + internationalApply +"\n"+
				"jobRequirements=" + jobRequirements +"\n"+ 
				"jobDesc=" + jobDesc +"\n"+
				"count=" + count +"\n"+
				"numberOfStudents=" + numberOfStudents +"\n"+
				"jobAvailable=" + jobAvailable + "\n"+
				"applicantInterviewTimes=" + applicantInterviewTimes +"\n"+
				"result=" + result ;
	}
	
	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
	}


	public String getJobID() {
		return jobID;
	}


	public void setJobID(String jobID) {
		this.jobID = jobID;
	}


	public Employer getEmp() {
		return emp;
	}


	public void setEmp(Employer emp) {
		this.emp = emp;
	}


	public boolean isInternationalApply() {
		return internationalApply;
	}


	public void setInternationalApply(boolean internationalApply) {
		this.internationalApply = internationalApply;
	}


//	public EmploymentRecord getJobRequirements() {
//		return jobRequirements;
//	}
//
//
//	public void setJobRequirements(EmploymentRecord jobRequirements) {
//		this.jobRequirements = jobRequirements;
//	}


	public String getJobDesc() {
		return jobDesc;
	}


	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getNumberOfStudents() {
		return numberOfStudents;
	}


	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}


	public boolean isJobAvailable() {
		return jobAvailable;
	}


	public void setJobAvailable(boolean jobAvailable) {
		this.jobAvailable = jobAvailable;
	}


	public HashMap<Applicant, String> getApplicantInterviewTimes() {
		return applicantInterviewTimes;
	}


	public void setApplicantInterviewTimes(HashMap<Applicant, String> applicantInterviewTimes) {
		this.applicantInterviewTimes = applicantInterviewTimes;
	}



	public Job(String jobID, Employer emp, boolean internationalApply, Availability detail,int numberOfStudents )
	{
		this.jobID = jobID;
		this.emp = emp;
		this.internationalApply = internationalApply;
		this.jobAvailable = true;
		this.numberOfStudents = numberOfStudents;
		

		
	}
	
	
	
	
	public void jobOfferRejected(Applicant st)
	{
		
		//the successful applicant is removed from the list
		result.removeFinalStudent(st);
		
		// the rejected applicant is added from jobOfferAccepted method and appended in the list
		result.addRejectedStudent(st);
		
		Applicant applicant;
		
//		Employer e1 = new Employer(jobDesc);
		//rejectedStudents index would start with 0

		applicant = result.index(0);
		this.emp.sendJobOffers(this);
		result.removeRejectedStudent(applicant);
		
		count--;
		
		/**
		 * remove student from successful applicant and add to rejected applicant
		 * (send the offer to the next student from the rejected stack)
		 */
	}
	
	public void jobOfferAccepted(Applicant st)
	{
		// declaring the instances for count and number of students 
		int c = count;
		int n = numberOfStudents;
		
		count++;
		
		
		//finalStudents are added to the list
		result.addFinalStudent(st);
		
		System.out.println("You are accepted for the job. ");
		int i= result.getsize();
		if (c == n && i == n)
		{   
			//checks if the size for finalStudents exceeds then jobAvailable becomes false 
			
			jobAvailable = false;	
		}
		
	
		/**
		 * Change job availability
		 * (add student to final student data structure)
		 */
    }
}
	
	

	
	



