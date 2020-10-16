package app_items;

import java.util.ArrayList;
import java.util.List;

import app_users.Applicant;

public class Result {
	
	private ArrayList<Applicant> finalApplicantList;
	private ArrayList<Applicant> rejectedApplicantList;

	public Result()
	{
		finalApplicantList = new ArrayList<>();
		rejectedApplicantList = new ArrayList<>();
	}
	
	public void addFinalApplicant(Applicant applicant)
	{
		this.finalApplicantList.add(applicant);
	}

	public void addRejectedApplicants(List<Applicant> rejectedApplicants)
	{
		this.rejectedApplicantList.addAll(rejectedApplicants);
	}

	public ArrayList<Applicant> getFinalApplicantList() {
		return finalApplicantList;
	}

	public ArrayList<Applicant> getRejectedApplicantList() {
		return rejectedApplicantList;
	}
}

