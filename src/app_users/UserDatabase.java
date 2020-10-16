package app_users;

import java.applet.AppletContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import app_exceptions.InvalidPasswordException;

public class UserDatabase {
	
	public static HashMap<String,User> userData = new HashMap<>();

	
	public UserDatabase()
	{
		
	}
	
	public HashMap<String,User> getUserData()
	{
		return this.userData;
	}
	
	public void addUsers(String username, User user)
	{
		this.userData.put(username, user);
	}
	
	public static User fetchUser(String username)
	{
		return userData.get(username);
	}
	
	/**
	 * 
	 * @param userID
	 * @param password
	 * @return Object of applicant class of the specified username 
	 * @throws NullPointerException if invalid username is entered as key will not be present in hashmap
	 * @throws InvalidPasswordException
	 */
	public Applicant getApplicant(String userID, String password) throws NullPointerException, InvalidPasswordException
	{
		Applicant app = (Applicant) this.userData.get(userID);
		//validate password
		app.validateCredentials(password);
		
		return app;
	}
	
	/**
	 * 
	 * @param userID
	 * @param password
	 * @return Object of employer class of the specified username 
	 * @throws NullPointerException if invalid username is entered as key will not be present in hashmap
	 * @throws InvalidPasswordException
	 */
	public Employer getEmployer(String userID, String password) throws NullPointerException, InvalidPasswordException
	{
		Employer emp = (Employer) this.userData.get(userID);
		
		//validate password
		emp.validateCredentials(password);
		
		return emp;
	}
	
	/**
	 * 
	 * @param userID
	 * @param password
	 * @return Object of maintainance class of the specified username 
	 * @throws NullPointerException if invalid username is entered as key will not be present in hashmap
	 * @throws InvalidPasswordException
	 */
	public Maintainance getStaff(String userID, String password) throws NullPointerException, InvalidPasswordException
	{
		Maintainance staff = (Maintainance) this.userData.get(userID);
		
		staff.validateCredentials(password);
		
		return staff;
	}

	public static List<Applicant> getAllApplicants()
	{
		List<Applicant> applicantList = new ArrayList<>();
		for(String userID : userData.keySet())
		{
			if(userData.get(userID) instanceof Applicant)
			{
				applicantList.add((Applicant) userData.get(userID));
			}
		}
		return applicantList;
	}
	
//	public void readApplicantFile() throws IOException
//	{
//		BufferedReader bufferRead = new BufferedReader(new FileReader("applicant.txt"));
//
//		String studentInfo = "";
//
//		while((studentInfo = bufferRead.readLine())!=null && !studentInfo.isBlank())
//		{
//			//first split the information on basis of semicolon to seperate the different fields
//			StringTokenizer infoSplit = new StringTokenizer(studentInfo,";");
//
//			String username = infoSplit.nextToken();
//			String password = infoSplit.nextToken();
//
//			String name = infoSplit.nextToken();
//			String contact = infoSplit.nextToken();
//			String nationality = infoSplit.nextToken();
//
//			Applicant app = new Applicant(username, password, name, contact, nationality);
//
//			//add user to hashmap
//			this.userData.put("username", app);
//		}
//	}
	
	

}
