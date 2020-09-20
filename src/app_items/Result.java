package app_items;

import java.util.ArrayList;

import app_users.Applicant;

public class Result {
	
	private ArrayList<Applicant> finalStudents;
	private ArrayList<Applicant> rejectedStudents;
	Interview interview;
	
	
			//the successful applicant is removed from the list
			public void removeFinalStudent(Applicant st) 
			{
				finalStudents.remove(st);
			}
			
			// the rejected applicant is added from jobOfferAccepted method and appended in the list
			public void addRejectedStudent(Applicant st) 
			{
			rejectedStudents.remove(st);
			}
			public void removeRejectedStudent(Applicant st) 
			{
			rejectedStudents.remove(st);
			}
		
			//finalStudents are added to the list
			public void addFinalStudent(Applicant st) 
			{	
			finalStudents.add(st);
			}
			//rejectedStudents index would start with 0
			public Applicant index(int i) 
			{
			return rejectedStudents.remove(i);
			
			}
			public ArrayList<Applicant> getFinalStudents() {
				return finalStudents;
			}

			public void setFinalStudents(ArrayList<Applicant> finalStudents) {
				this.finalStudents = finalStudents;
			}

			public ArrayList<Applicant> getRejectedStudents() {
				return rejectedStudents;
			}

			public void setRejectedStudents(ArrayList<Applicant> rejectedStudents) {
				this.rejectedStudents = rejectedStudents;
			}

			public Interview getInterview() {
				return interview;
			}

			public void setInterview(Interview interview) {
				this.interview = interview;
			}

			public int getsize() 
			{
			return this.rejectedStudents.size();
			
			}
			
}

