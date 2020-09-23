package app_items;

import java.util.ArrayList;
import java.time.LocalDateTime;

import app_users.Applicant;

public class Interview {
	Job job;
	LocalDateTime interviewTime;

	public Interview(Job job, LocalDateTime interviewTime)
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
	
	public LocalDateTime getInterviewTime() {
		return interviewTime;
	}
	
}
