package testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app_users.Applicant;
import app_users.UserDatabase;

class TestUsers {
	
	static UserDatabase us;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		us = new UserDatabase();
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserLogin() {
		String userID = "S0";
		Applicant ap = us.getApplicant("S0");
		assertEquals("S0",ap.getUserID());
	}

}
