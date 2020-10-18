package app_items;

import app_users.Applicant;
import org.junit.platform.engine.support.descriptor.FileSystemSource;

import java.time.LocalDateTime;

public class JobApplication {

    private Job job;

    private Applicant shortlistedApplicant;

    private String statusOfApplication;

    private int applicantRank;

    private LocalDateTime interviewTime;

    public JobApplication(Job job, Applicant shortlistedApplicant)
    {
        this.job = job;
        this.shortlistedApplicant = shortlistedApplicant;

        this.statusOfApplication = "SHORTLISTED";

        this.interviewTime = null;
    }

    public void setApplicantRank(int rank)
    {
        this.applicantRank = rank;
        this.statusOfApplication = "RANKED";
    }

    public void setInterviewTime(LocalDateTime time)
    {
        this.interviewTime = time;
    }

    public LocalDateTime getInterviewTime()
    {
        return interviewTime;
    }

    public void finalizeApplicant()
    {
        this.statusOfApplication = "FINALIZED";
    }

    public void acceptOffer()
    {
        this.statusOfApplication = "OFFER_ACCEPTED";
    }

    public void rejectOffer()
    {
        this.statusOfApplication = "OFFER_REJECTED";
    }

    public String getStatusOfApplication()
    {
        return this.statusOfApplication;
    }

    public Applicant getApplicant()
    {
        return this.shortlistedApplicant;
    }

    public String getJobID()
    {
        return job.getJobID();
    }


    public boolean interviewScheduled()
    {
        if(this.interviewTime !=  null)
            return true;
        else
            return false;
    }

    public void showInterviewTimeSlot()
    {
        System.out.println("Starting time -> " + job.getStartingTimeSlot());
        System.out.println("Ending time -> " + job.getEndingTimeSlot());
    }

    public boolean validateInterviewTime(LocalDateTime interviewTime)
    {
        if(interviewTime.isBefore(job.getStartingTimeSlot()) || interviewTime.isAfter(job.getEndingTimeSlot()))
            return false;

        return true;
    }


    @Override
    public String toString() {

        StringBuilder appDetails = new StringBuilder();

        appDetails.append("******Application Details********\n");
        appDetails.append("Job ID -> " + job.getJobID() + "\n");
        appDetails.append("Job Type -> " + job.getJobType() + "\n");
        appDetails.append("Job Category -> " + job.getJobCategory() + "\n");
        appDetails.append("Years of Experience -> " + job.getMinYearsOfExperience() + "\n");
        appDetails.append("Status of Application -> " + statusOfApplication + "\n");

        if(interviewTime != null)
            appDetails.append("Scheduled Interview Date -> " + interviewTime + "\n");
        else
            appDetails.append("No interview scheduled yet. Please schedule at the earliest");

        return appDetails.toString();
    }
}
