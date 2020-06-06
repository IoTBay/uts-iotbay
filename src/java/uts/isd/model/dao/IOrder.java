/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.util.List;
import uts.isd.model.Customer;
import uts.isd.model.Order;
import uts.isd.model.OrderLine;

/**
 * The interface for a Order repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-25
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
     * 
     * Returns a single orderLine based on their ID
     *
     * @param id The orderLine's primary key ID.
     * @return OrderLine object containing the order line record.
     */
    public OrderLine getOrderLineById(int id);
    
    /**
     * 
     * Returns the current draft order for the customer.
     *
     * @param customer The customer object to get the draft for.
     * @return Order object containing the order record.
     */
    public Order getCartOrderByCustomer(Customer customer);

    /**
     * Returns a list of orders based on their customer 
     * 
     * @param customerId The order's related customer ID
     * @return  List of orders related to the customer.
     */
    public Iterable<Order> getOrdersByCustomerId(int customerId);
    
    
    /**
     * Returns all orders in the orders table
     * 
     * @return List of all orders
     */
    public Iterable<Order> getAllOrders();
    
    /**
     * Returns a list of order lines based for the order
     * 
     * @param orderId The order's ID
     * @return  List of order lines related to the order.
     */
    public List<OrderLine> getOrderLines(int orderId);
    
    /* Update queries */
    
    /**
     * Updates a order in the database based on the passed in Order model object.
     * 
     * @param o The order object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if order was updated, or false if no updates were performed.
     */
    public boolean updateOrder(Order o, Customer customer);
    
    /**
     * Updates a order line in the database based on the passed in OrderLine model object.
     * 
     * @param o The orderLine object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if orderLine was updated, or false if no updates were performed.
     */
    public boolean updateOrderLine(OrderLine o, Customer customer);
    
    /**
     * Adds a order in the database based on the passed in Order model object.
     *
     * @param o The order object to insert values from
     * @param customer The user making the change
     * @return Returns true if order was added, or false if no insert was performed.
     */
    public boolean addOrder(Order o, Customer customer);
    
    /**
     * Adds a orderLine in the database based on the passed in OrderLine model object.
     *
     * @param o The orderLine object to insert values from
     * @param customer The user making the change
     * @return Returns true if orderLine was added, or false if no insert was performed.
     */
    public boolean addOrderLine(OrderLine o, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a order to delete
     * @return Returns true if order was deleted, or false if it failed
     */

    public boolean deleteOrderById(int id);
    
    /**
     *
     * @param id The primary key ID of a orderLine to delete
     * @return Returns true if orderLine was deleted, or false if it failed
     */

    public boolean deleteOrderLineById(int id);
}
