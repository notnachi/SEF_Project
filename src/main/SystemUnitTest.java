package main;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import app_exceptions.InvalidComplaintHierarchyException;
import app_exceptions.InvalidPasswordException;
import app_items.Blacklist;
import app_users.Applicant;
import app_users.Employer;
import app_users.Maintainance;
import app_users.UserDatabase;
import dashboard.Registration_Dashboard;

public class SystemUnitTest {

	private Blacklist blacklist = new Blacklist();
	
	private static UserDatabase userDB = new UserDatabase();
	
	private static Maintainance staff = new Maintainance("nachi","abcd1234");
	
	private static Applicant app1  = new Applicant("sunny","abcd1234","Sonu Sunny","04634213","international");
	private static Applicant app2 = new Applicant("adkar","abcd1234","Anurag Karmarkar","34354675","local");
	
	private static Employer emp1 = new Employer("emp101","abcd1234","Harsh Rao","harsh@google.com","93244223","HR for google");
	private static Employer emp2 = new Employer("emp102","abcd1234","Avinash More","avi@amazon.com","43234234","HR for amazon");


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		 userDB.addUsers("nachi", staff);
		 userDB.addUsers("sunny", app1);
		 userDB.addUsers("adkar", app2);
		 userDB.addUsers("emp101", emp1);
		 userDB.addUsers("emp102", emp2);
	}
	
	@Test (expected = InvalidPasswordException.class)
	public void loginFailTest() throws InvalidPasswordException  {
		
		app1.validateCredentials("wrongPassword");
	}
	
	@Test
	public void registerApplicantTest() throws InvalidPasswordException  {
		
		Registration_Dashboard register = new Registration_Dashboard(userDB);
		
		register.registerApplicant();
		
		assertTrue(userDB.fetchUser("testApp") instanceof Applicant);
		assertEquals("iamapp", ((Applicant)userDB.fetchUser("testApp")).getname());
	}
	
	@Test
	public void registerEmployerTest() throws InvalidPasswordException  {
		
		Registration_Dashboard register = new Registration_Dashboard(userDB);
		
		register.registerEmployer();
		
		assertTrue(userDB.fetchUser("testEmp") instanceof Employer);
		assertEquals("testEmp", ((Employer)userDB.fetchUser("testEmp")).getUsername());
	}
	
	@Test 
	public void loginSuccessTest() throws InvalidPasswordException  {
		
		assertTrue(app1.validateCredentials("abcd1234"));
	}
	
	
	@Test (expected = InvalidComplaintHierarchyException.class)
	public void wrongUserBlacklistTest() throws NullPointerException, InvalidComplaintHierarchyException {
		
		app1.sendComplaint();
	}

	@Test
	public void provisionalBlacklistTest() throws NullPointerException, InvalidComplaintHierarchyException {
		
		//initially zero members should be present in provisionalBlacklistHashmap
		assertEquals(0,blacklist.showProvisionallyBlacklistedUser().size());
		
		app1.sendComplaint();
		app1.sendComplaint();
		app1.sendComplaint();
		
		assertEquals(1,blacklist.showProvisionallyBlacklistedUser().size());
		assertEquals("emp101",blacklist.showProvisionallyBlacklistedUser().get("emp101").getUsername());
	}
	
	@Test (expected = InvalidComplaintHierarchyException.class)
	public void incorrectBlacklistTest() throws NullPointerException, InvalidComplaintHierarchyException {
		
		app1.sendComplaint();	
	}

}
