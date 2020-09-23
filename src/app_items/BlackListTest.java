package app_items;

import static org.junit.Assert.*;

import app_exceptions.InvalidAccessRightsException;
import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.UserAlreadyBlacklistedException;
import app_exceptions.UserNotPresentException;
import app_users.Applicant;
import app_users.Employer;
import app_users.Maintainance;
import app_users.UserDatabase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


public class BlackListTest {

    private static UserDatabase userDB = new UserDatabase();
    static Applicant app1 = new Applicant("app1", "abc123", "nachi", "1234", false, true);
    static Applicant app2 = new Applicant("app2", "abc123", "anurag", "1234", true, true);

    static Employer emp1 = new Employer("emp1", "abc123", "google", "google.com", "1234", "test");
    static Maintainance staff1 = new Maintainance("staff1", "abc123");

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        userDB.addUsers(app1.getUsername(),app1);
        userDB.addUsers(app2.getUsername(),app2);
        userDB.addUsers(emp1.getUsername(),emp1);
        userDB.addUsers(staff1.getUsername(),staff1);
    }

    @Test
    public void provisionallyBlacklistTest() {

        Blacklist blacklist = new Blacklist();

        //initially user is not blacklisted
        assertFalse(app1.getProvisionallyBlacklistStatus());
        assertFalse(blacklist.getFullyBlacklistedUserList().containsKey(app1.getUsername()));

        try {
            emp1.sendComplaint(app1,"test complaint 1");
            emp1.sendComplaint(app1,"test complaint 2");
            emp1.sendComplaint(app1,"test complaint 3");
        } catch (UserAlreadyBlacklistedException e) {
            e.printStackTrace();
        } catch (InvalidAccessRightsException e) {
            e.printStackTrace();
        } catch (InvalidComplaintHierarchyException e) {
            e.printStackTrace();
        }

        //user is provsionally blacklisted after 3 compplaints
        assertTrue(app1.getProvisionallyBlacklistStatus());

        assertTrue(blacklist.getProvisionallyBlacklistedUserList().containsKey(app1.getUsername()));

    }

    @Test
    public void successfullReactivationTest()
    {
        Blacklist blacklist = new Blacklist();
        //user is not initially blacklisted
        assertFalse(app2.getProvisionallyBlacklistStatus());
        assertFalse(blacklist.getFullyBlacklistedUserList().containsKey(app2.getUsername()));

        try {
            emp1.sendComplaint(app2,"test complaint 1");
            emp1.sendComplaint(app2,"test complaint 2");
            emp1.sendComplaint(app2,"test complaint 3");
        } catch (UserAlreadyBlacklistedException e) {
            e.printStackTrace();
        } catch (InvalidAccessRightsException e) {
            e.printStackTrace();
        } catch (InvalidComplaintHierarchyException e) {
            e.printStackTrace();
        }

        //user is now provisionally blacklisted
        assertTrue(app2.getProvisionallyBlacklistStatus());
        assertTrue(blacklist.getProvisionallyBlacklistedUserList().containsKey(app2.getUsername()));

        try {
            staff1.removeProvisionalBlacklist(app2);
        } catch (UserNotPresentException e) {
            e.printStackTrace();
        }

        assertFalse(app2.getProvisionallyBlacklistStatus());
        assertFalse(blacklist.getFullyBlacklistedUserList().containsKey(app2.getUsername()));
    }


}
