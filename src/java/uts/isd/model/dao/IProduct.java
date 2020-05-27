/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
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
    /**
     * Returns a single product based on unique id
     * @param email The user's unique email address.???
     * @return  User object containing the user record.
     */
    public Product getProductByName(String name);
    
    /**
     * Returns all users in the users table
     * 
     * @return List of all users
     */
    public Iterable<Product> getAllProducts();
    
    /* Update queries */
    
    /**
     * Updates a user in the database based on the passed in User model object.
     * 
     * @param u The user object to take updated values from.
     * @return Returns true if user was updated, or false if no updates were performed.
     */
    public boolean updateProduct(Product pr);
    
    /**
     * Adds a user in the database based on the passed in User model object.
     *
     * @param u The user object to insert values from
     * @return Returns true if user was added, or false if no insert was performed.
     */
    public boolean addProduct(Product pr);
    
    /* Delete queries */

    /**
     *
     * @param id The primary key ID field of a user to delete
     * @return Returns true if user was deleted, or false if it failed
     */

    public boolean deleteProductById(int id);
}
