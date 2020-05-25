/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Customer;

/**
 * The interface for a User repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan - 11000801
 */
public interface IProduct {
    
    /* Select queries */

    /**
     * 
     * Returns a single product based on their ID
     *
     * @param id The product's primary key ID.
     * @return Product object containing the user record.
     */

    public Product getProductById(int id);

    /**
     * Returns a single user based on their unique email
     * 
     * @param email The user's unique email address.
     * @return  User object containing the user record.
     */
    public User getCustomerByEmail(String email);
    
    /**
     * 
     * 
     * @param firstName
     * @return 
     */
    public Iterable<Product> getProductsByName(String name);
    
    
    /**
     * 
     * 
     * @param firstName
     * @return 
     */
    public Iterable<Product> getProductsByDescription(String description);
    
    /**
     * Returns all users in the users table
     * 
     * @return List of all users
     */
    public Iterable<User> getAllUsers();
    
    /* Update queries */
    
    /**
     * Updates a user in the database based on the passed in User model object.
     * 
     * @param u The user object to take updated values from.
     * @return Returns true if user was updated, or false if no updates were performed.
     */
    public boolean updateUser(Customer u);
    
    /**
     * Adds a user in the database based on the passed in User model object.
     *
     * @param u The user object to insert values from
     * @return Returns true if user was added, or false if no insert was performed.
     */
    public boolean addUser(User u);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a user to delete
     * @return Returns true if user was deleted, or false if it failed
     */

    public boolean deleteUserById(int id);
}
