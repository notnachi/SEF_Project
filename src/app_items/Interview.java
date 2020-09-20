package app_items;

import java.util.ArrayList;

import app_users.Applicant;

public class Interview {
	ArrayList<Applicant> appList = new ArrayList<Applicant>();
	Job job;
	String interviewTime;
	
	public ArrayList<Applicant> getAppList() {
		return appList;
	}
	
	public void setAppList(ArrayList<Applicant> appList) {
		this.appList = appList;
	}
	
	public Job getJob() {
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public String getInterviewTime() {
		return interviewTime;
	}
	
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
}
