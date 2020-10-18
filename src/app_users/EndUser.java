package app_users;

import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_items.Blacklist;
import app_items.Complaint;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class EndUser extends GlobalUser {

    //arraylist of complaints
    private ArrayList<Complaint> complaintList;

    private boolean isProvisionallyBlacklisted;

    private boolean isFullyBlacklisted;

    private LocalDateTime fullyBlacklistDate;

    public EndUser(String username, String password, String emailID) {

        super(username, password, emailID);

        this.complaintList = new ArrayList<>();

        this.isFullyBlacklisted = false;
        this.isProvisionallyBlacklisted = false;

        this.fullyBlacklistDate = null;
    }

    public boolean getProvisionallyBlacklistStatus()
    {
        return this.isProvisionallyBlacklisted;
    }

    public boolean getFullyBlacklistStatus()
    {
        return this.isFullyBlacklisted;
    }

    public void setFullyBlacklistDate(LocalDateTime fullyBlacklistDate)
    {
        this.fullyBlacklistDate = fullyBlacklistDate;
    }

    public LocalDateTime getFullyBlacklistDate()
    {
        return this.fullyBlacklistDate;
    }


    public void sendComplaint(EndUser user, String complaintDesc) throws UserAlreadyBlacklistedException, InvalidAccessRightsException, InvalidComplaintHierarchyException {

        //avoid accessing this method if user is blacklisted
        if(this.getFullyBlacklistStatus() || this.getProvisionallyBlacklistStatus())
        {
            throw new InvalidAccessRightsException("You have been blacklisted. Cannot perform operation.");
        }

        if((this instanceof Applicant && user instanceof Applicant) || (this instanceof Employer && user instanceof Employer))
        {
            throw new InvalidComplaintHierarchyException("Cannot complaint about user of same type");
        }

        if(user.getFullyBlacklistStatus() || user.getProvisionallyBlacklistStatus())
        {
            throw new UserAlreadyBlacklistedException("This user has already been blacklisted");
        }


        Complaint newComplaint = new Complaint(this,user, complaintDesc);

        user.getComplaint(newComplaint);
    }

    public void getComplaint(Complaint complaint)
    {
        this.complaintList.add(complaint);

        if(this.complaintList.size()==3)
        {
            Blacklist newBlacklist = new Blacklist();
            newBlacklist.provisionallyBlacklist(this);
            this.setProvisionallyBlacklistStatus(true);
        }
    }

    public void clearAllComplaints()
    {
        this.complaintList.clear();
    }

    public void setFullyBlacklistStatus(boolean isFullyBlacklisted)
    {
        this.isFullyBlacklisted = isFullyBlacklisted;
    }

    public void setProvisionallyBlacklistStatus(boolean isProvisionallyBlacklisted)
    {
        this.isProvisionallyBlacklisted = isProvisionallyBlacklisted;
    }
}
