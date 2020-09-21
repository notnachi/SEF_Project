package app_users;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EmployerTest {

    private static UserDatabase userDB = new UserDatabase();
    static Applicant app1 = new Applicant("app1", "abc123", "nachi", "1234", false, true);
    static Applicant app2 = new Applicant("app2", "abc123", "anurag", "1234", true, true);
    static Applicant app3 = new Applicant("app3", "abc123", "ishaa", "1234", false, false);
   static  Applicant app4 = new Applicant("app4", "abc123", "joel", "1234", false, true);
   static  Applicant app5 = new Applicant("app5", "abc123", "nadeem", "1234", false, true);



    static Employer emp1 = new Employer("emp1", "abc123", "google", "google.com", "1234", "test");


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {


        userDB.addUsers(app1.getUsername(),app1);
        userDB.addUsers(app2.getUsername(),app2);
        userDB.addUsers(app3.getUsername(),app3);
        userDB.addUsers(app4.getUsername(),app4);
        userDB.addUsers(app5.getUsername(),app5);

        userDB.addUsers(emp1.getUsername(), emp1);

        app1.addAvailability("part-time",10,"IT");
        app2.addAvailability("part-time",40,"Accounts");
        app3.addAvailability("part-time",20,"Finance");
        app4.addAvailability("full-time",40,"IT");
        app5.addAvailability("part-time",10,"IT");

        emp1.createJob(false,"part-time",10,"IT",true,0,5);
    }

    @Test
    public void filerApplicantByJob() {



       List<Applicant> applicantList =  emp1.filerApplicantByJob("J0");

       System.out.println(applicantList.size());

       for(Applicant app : applicantList)
       {
           System.out.println(app.getUsername());
       }

    }
}
