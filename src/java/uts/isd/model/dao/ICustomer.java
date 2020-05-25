/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Customer;

/**
 * The interface for a Customer repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan - 11000801
 */
public interface ICustomer {
    
    /* Select queries */

    /**
     * 
     * Returns a single customer based on their ID
     *
     * @param id The customer's primary key ID.
     * @return Customer object containing the customer record.
     */

    public Customer getCusomerById(int id);

    /**
     * Returns a single customer based on their unique email
     * 
     * @param email The customer's unique email address.
     * @return  Customer object containing the customer record.
     */
    public Customer getCustomerByEmail(String email);
    
    
    /**
     * Returns all customers in the customers table
     * 
     * @return List of all customers
     */
    public Iterable<Customer> getAllCustomers();
    
    /* Update queries */
    
    /**
     * Updates a customer in the database based on the passed in Customer model object.
     * 
     * @param c The customer object to take updated values from.
     * @return Returns true if customer was updated, or false if no updates were performed.
     */
    public boolean updateCustomer(Customer c);
    
    /**
     * Adds a customer in the database based on the passed in Customer model object.
     *
     * @param c The customer object to insert values from
     * @return Returns true if customer was added, or false if no insert was performed.
     */
    public boolean addCustomer(Customer c);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a customer to delete
     * @return Returns true if customer was deleted, or false if it failed
     */

    public boolean deleteCustomerById(int id);
}
