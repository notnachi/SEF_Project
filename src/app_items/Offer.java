package app_items;

import app_users.Applicant;
import app_users.Employer;

public class Offer {

	//job for which the offer is sent
	private Job job;
	//applicant to whom the offer is sent to
	private Applicant applicant;
	//status of the offer
	private String offerStatus;

	public Offer(Job job, Applicant applicant)
	{
		this.job = job;
		this.applicant = applicant;
	}

	public String getOfferStatus()
	{
		return offerStatus;
	}

	public void updateOfferStatus(boolean acceptOffer)
	{
		if(acceptOffer)
		{
			this.offerStatus = "Job Offer Accepted";
			applicant.setStatus("Employed");
		}
		else
		{
			this.offerStatus = "Job Offer Rejected";
			applicant.rejectOffer();
			applicant.setStatus("Available");
		}
	}

	@Override
	public String toString() {
		return "Offer{" +
				"job=" + job +
				'}';
	}
}
