/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.Currency;

/**
 * The interface for a Order repository.
 * 
 * See repository pattern: http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
 *
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public interface ICurrency {
    
    /* Select queries */

    /**
     * 
     * Returns a single order based on their ID
     *
     * @param id The currency's primary key ID.
     * @return Order object containing the order record.
     */
    public Currency getCurrencyById(int id);
    
}
