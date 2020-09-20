package app_items;

import java.util.*;

public class Availability{
	
	private String type;
	
	private HashMap<String, ArrayList<String>> period ;

	
	private ArrayList<String> jobCategories;
	
	public Availability(String type, HashMap<String, ArrayList<String>> period, ArrayList<String> category)
	{
		this.type = type;
		this.jobCategories = category;
		this.period = period;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	
	public ArrayList<String> getCategoryList()
	{
		return this.jobCategories;
	}                          

	public HashMap<String, ArrayList<String>>  getperiod()
	{
		return period;
	}

}
