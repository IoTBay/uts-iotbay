/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.util.List;
import uts.isd.model.PaymentMethod;
import uts.isd.model.Customer;

/**
 * The interface for a User repository.
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 */
public interface IPaymentMethod {
    
    /* Select queries */
    /**
     * Returns a single PaymentMethod based on its ID
     * @param id The PaymentMethod's primary key ID.
     * @return PaymentMethod object containing the product record.
     */    
    public PaymentMethod getPaymentMethodById(int id);
    
    /**
     * Returns all payment methods in the payment methods table
     * 
     * @return List of all payment methods
     */
    public List<PaymentMethod> getAllPaymentMethods();
    
    /**
     * Returns all payment methods in the payment methods table for a particular customer
     * 
     * @param id The customer ID to get payment methods for
     * 
     * @return List of payment methods
     */
    public List<PaymentMethod> getAllPaymentMethodsByCustomerId(int id);
    
    /* Update queries */
    
    /**
     * Updates a payment method in the database based on the passed in User model object.
     * 
     * @param a The payment method object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if payment method was updated, or false if no updates were performed.
     */
    public boolean updatePaymentMethod(PaymentMethod a, Customer customer);
    
    /**
     * Adds a payment method in the database based on the passed in User model object.
     *
     * @param a The payment method object to insert values from
     * @param customer The user making the change
     * @return Returns true if payment method was added, or false if no insert was performed.
     */
    public boolean addPaymentMethod(PaymentMethod a, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a payment method to delete
     * @return Returns true if payment method was deleted, or false if it failed
     */
    public boolean deletePaymentMethodById(int id);
 
}