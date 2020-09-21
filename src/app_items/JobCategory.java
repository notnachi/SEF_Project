package app_items;


import app_exceptions.DuplicateCategoryException;

import java.util.HashMap;

public class JobCategory {

    private final String categoryID;
    private String categoryName;

    private static int categoryCount = 1;

    private static HashMap<String,String> categoryList = new HashMap<>();

    public JobCategory(String categoryName) throws DuplicateCategoryException {
        if(this.validateCategory(categoryName))
        {
            throw new DuplicateCategoryException("Category already present");
        }
        this.categoryID = "C" + categoryCount;
        this.categoryName = categoryName;

        categoryList.put(categoryID,categoryName);
        categoryCount++;
    }

    public void showAllCategories()
    {
        for(String categoryID: categoryList.keySet())
        {
            System.out.print(categoryList.get(categoryID) + "\n");
        }
    }



    public Boolean validateCategory(String categoryName)
    {
        return categoryList.containsValue(categoryName);
    }


}
