/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

/**
 *
 * @author rhys
 */
public class Search {
    
    public void doSearch(String searchTerm)
    {
        getProductsByName(searchTerm);
        getCategoriesByName(searchTerm);
        getOrderById(searchTerm);
    }
    
    public void getProductsByName(String term)
    {
        //SELECT * FROM PRODUCTS WHERE Name LIKE '%xxx%'
        return
    }
    
    public void getCategoriesByName(String term)
    {
        //SELECT * FROM Categories Where ID = term
        return
    }
    
}
