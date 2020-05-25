/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Order;
import uts.isd.model.OrderLine;

/**
 * The interface for a Order repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan - 11000801
 */
public interface IOrder {
    
    /* Select queries */

    /**
     * 
     * Returns a single order based on their ID
     *
     * @param id The order's primary key ID.
     * @return Order object containing the order record.
     */

    public Order getOrderById(int id);

    /**
     * Returns a list of orders based on the linked customer Id
     * 
     * @param customerId The customer's unique primary key
     * @return  Order object containing the order record.
     */
    public Iterable<Order> getOrdersByCustomerId(int customerId);
    
    /**
     * Returns all orders in the orders table
     * 
     * @return List of all orders
     */
    public Iterable<Order> getAllOrders();
    
    /* Update queries */
    
    /**
     * Updates a order in the database based on the passed in Order model object.
     * 
     * @param o The order object to take updated values from.
     * @return Returns true if order was updated, or false if no updates were performed.
     */
    public boolean updateOrder(Order o);
    
    /**
     * Adds a order in the database based on the passed in Order model object.
     *
     * @param o The order object to insert values from
     * @return Returns true if order was added, or false if no insert was performed.
     */
    public boolean addOrder(Order o);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a order to delete
     * @return Returns true if order was deleted, or false if it failed
     */

    public boolean deleteOrderById(int id);
}
