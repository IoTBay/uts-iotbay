/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.util.List;
import javax.servlet.ServletRequest;
import uts.isd.model.Address;
import uts.isd.model.Customer;
import uts.isd.model.User;

/**
 * The interface for a User repository.
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 */
public interface IAddress {
    
    /* Select queries */
    /**
     * Returns a single Address based on its ID
     * @param id The Address's primary key ID.
     * @return Address object containing the product record.
     */    
    public Address getAddressById(int id);
    
    /**
     * Returns all addresses in the addresses table
     * 
     * @return List of all addresses
     */
    public List<Address> getAllAddresses();
    
    /**
     * Returns all addresses in the addresses table for a particular customer
     * 
     * @param id The customer ID to get addresses for
     * 
     * @return List of addresses
     */
    public List<Address> getAllAddressesByCustomerId(int id);
    
    /* Update queries */
    
    /**
     * Updates a address in the database based on the passed in User model object.
     * 
     * @param a The address object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if address was updated, or false if no updates were performed.
     */
    public boolean updateAddress(Address a, Customer customer);
    
    /**
     * Adds a address in the database based on the passed in User model object.
     *
     * @param a The address object to insert values from
     * @param customer The user making the change
     * @return Returns true if address was added, or false if no insert was performed.
     */
    public boolean addAddress(Address a, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a address to delete
     * @return Returns true if address was deleted, or false if it failed
     */
    public boolean deleteAddressById(int id);
 
}

    
