package app_items;


import app_exceptions.DuplicateCategoryException;

import java.util.HashMap;

public class JobCategory {

    private static JobCategory jc;

    private String categoryID;
    private String categoryName;

    private static int categoryCount = 1;

    private HashMap<String,String> categoryList = new HashMap<>();

//    public JobCategory(String categoryName) throws DuplicateCategoryException {
//        if(this.validateCategory(categoryName))
//        {
//            throw new DuplicateCategoryException("Category already present");
//        }
//        this.categoryID = "C" + categoryCount;
//        this.categoryName = categoryName;
//
//        categoryList.put(categoryID,categoryName);
//        categoryCount++;
//    }

    public JobCategory(){}

    public static JobCategory getInstance()
    {
        if (jc == null)
            jc = new JobCategory();

        return jc;
    }

    public void showAllCategories()
    {
        for(String categoryID: categoryList.keySet())
        {
            System.out.print(categoryID + " " + categoryList.get(categoryID) + "\n");
        }
    }

    public String getJobCategory(String categoryID)
    {
        return categoryList.get(categoryID);
    }

    public void addJobCategory(String categoryName) throws DuplicateCategoryException {

        if(this.validateCategory(categoryName))
        {
            throw new DuplicateCategoryException("Category already present");
        }
        this.categoryID = "C" + categoryCount;
        this.categoryName = categoryName;

        categoryList.put(categoryID,categoryName);
        categoryCount++;
    }


    public Boolean validateCategory(String categoryName)
    {
        return categoryList.containsValue(categoryName);
    }


}
