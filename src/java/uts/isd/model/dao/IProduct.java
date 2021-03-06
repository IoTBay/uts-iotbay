/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.util.List;
import javax.servlet.ServletRequest;
import uts.isd.model.Customer;
import uts.isd.model.Product;
import uts.isd.model.User;

/**
 * The interface for a User repository.
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 */
public interface IProduct {
    
    /* Select queries */
    /**
     * Returns a single Product based on its ID
     * @param id The Product's primary key ID.
     * @return Product object containing the product record.
     */    
    public Product getProductById(int id);
    
    public Product authenticateProduct(String name);
    
    /**
     * Returns a single product based on unique id
     * @param email The user's unique email address.???
     * @return  User object containing the user record.
     */
    public Product getProductByName(String name);
    
    /**
     * Returns all products in the products table
     * 
     * @return List of all products
     */
    public List<Product> getAllProducts();
    
    
    /**
     * Returns all products belonging to a particular category.
     * 
     * @param categoryId The categoryID primary key to get products for.
     * @return List of product objects
     */
    public List<Product> getProductsByCategoryId(int categoryId);
    
    /* Update queries */
    
    /**
     * Updates a user in the database based on the passed in User model object.
     * 
     * @param u The user object to take updated values from.
     * @param customer The user making the change
     * @return Returns true if user was updated, or false if no updates were performed.
     */
    public boolean updateProduct(Product pr, Customer customer);
    
    /**
     * Adds a user in the database based on the passed in User model object.
     *
     * @param u The user object to insert values from
     * @param customer The user making the change
     * @return Returns true if user was added, or false if no insert was performed.
     */
    public boolean addProduct(Product pr, Customer customer);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a user to delete
     * @return Returns true if user was deleted, or false if it failed
     */

    public boolean deleteProductById(int id);
 
}

    
