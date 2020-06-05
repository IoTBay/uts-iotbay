/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.util.List;
import uts.isd.model.PaymentTransaction;
import uts.isd.model.Customer;

/**
 * The interface for a User repository.
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 */
public interface IPaymentTransaction {
    
    /* Select queries */
    /**
     * Returns a single PaymentTransaction based on its ID
     * @param id The PaymentTransaction's primary key ID.
     * @return PaymentTransaction object containing the product record.
     */    
    public PaymentTransaction getPaymentTransactionById(int id);
    
    /**
     * Returns all payment transactions in the payment transactions table
     * 
     * @return List of all payment transactions
     */
    public List<PaymentTransaction> getAllPaymentTransactions();
    
    /**
     * Returns all payment transactions in the payment transactions table for a particular customer
     * 
     * @param id The customer ID to get payment transactions for
     * 
     * @return List of payment transactions
     */
    public List<PaymentTransaction> getAllPaymentTransactionsByCustomerId(int id);
    
    /* Update queries */
    
    /**
     * Updates a payment transaction in the database based on the passed in User model object.
     * 
     * @param a The payment transaction object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if payment transaction was updated, or false if no updates were performed.
     */
    public boolean updatePaymentTransaction(PaymentTransaction a, Customer customer);
    
    /**
     * Adds a payment transaction in the database based on the passed in User model object.
     *
     * @param a The payment transaction object to insert values from
     * @param customer The user making the change
     * @return Returns true if payment transaction was added, or false if no insert was performed.
     */
    public boolean addPaymentTransaction(PaymentTransaction a, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a payment transaction to delete
     * @return Returns true if payment transaction was deleted, or false if it failed
     */
    public boolean deletePaymentTransactionById(int id);
 
}