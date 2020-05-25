/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.User;

/**
 * The interface for a User repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan - 11000801
 */
public interface IUser {
    
    /**
     * This method looks for the relevant user and makes sure the entered
     * username and password are correct.
     * 
     * If they are correct, a User object is returned. Otherwise NULL is returned.
     * 
     * @param email Email address of user to authenticate.
     * @param password Plaintext password of user to hash then authenticate.
     * @return User object if authenticated, otherwise NULL.
     */
    public User authenticateUser(String email, String password);
    
    /* Select queries */

    /**
     * 
     * Returns a single user based on their ID
     *
     * @param id The user's primary key ID.
     * @return User object containing the user record.
     */

    public User getUserById(int id);

    /**
     * Returns a single user based on their unique email
     * 
     * @param email The user's unique email address.
     * @return  User object containing the user record.
     */
    public User getUserByEmail(String email);
    public Iterable<User> getUsersByFirstName(String firstName);
    
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
    public boolean updateUser(User u);
    
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
