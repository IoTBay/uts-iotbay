/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.util.List;
import uts.isd.model.Category;
import uts.isd.model.Customer;

/**
 * The interface for a User repository.
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 */
public interface ICategory {
    
    /* Select queries */
    /**
     * Returns a single Category based on its ID
     * @param id The Category's primary key ID.
     * @return Category object containing the product record.
     */    
    public Category getCategoryById(int id);
    
    /**
     * Returns all categories in the categories table
     * 
     * @return List of all categories
     */
    public List<Category> getAllCategories();
    
    /* Update queries */
    
    /**
     * Updates a category in the database based on the passed in User model object.
     * 
     * @param a The category object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if category was updated, or false if no updates were performed.
     */
    public boolean updateCategory(Category a, Customer customer);
    
    /**
     * Adds a category in the database based on the passed in User model object.
     *
     * @param a The category object to insert values from
     * @param customer The customer creating the record
     * @return Returns true if category was added, or false if no insert was performed.
     */
    public boolean addCategory(Category a, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a category to delete
     * @return Returns true if category was deleted, or false if it failed
     */
    public boolean deleteCategoryById(int id);
 
}

    
