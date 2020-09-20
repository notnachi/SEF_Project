package app_items;

import app_users.Applicant;
import app_users.Employer;

public class Offer {
	Employer employer;
	Job job;
	Applicant applicantInfo;
	
	public Employer getEmployer() {
		return employer;
	}
	
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	
	public Job getJob() {
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public Applicant getApplicantInfo() {
		return applicantInfo;
	}
	
	public void setApplicantInfo(Applicant applicantInfo) {
		this.applicantInfo = applicantInfo;
	}
	
	public Offer(Employer employer, Job job, Applicant applicantInfo) {
		this.employer = employer;
		this.job = job;
		this.applicantInfo = applicantInfo;
	}
	
	
}
