package app_items;

import app_users.Applicant;

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





}
