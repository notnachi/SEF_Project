package app_items;

import java.util.ArrayList;
import java.util.Date;

import app_users.Applicant;

public class Interview {
	Job job;
	Date interviewTime;
	
	public Interview(Job job, Date interviewTime)
	{
		this.job = job;
		this.interviewTime = interviewTime;
	}

	public Job getJob() {
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public Date getInterviewTime() {
		return interviewTime;
	}
	
	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}
}
