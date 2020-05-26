/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import uts.isd.model.Product;

/**
 *
 * @author C_fin
 */
public class DBManager {
    
    private Statement st; 
    
    public DBManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }
    
    //Find product by id in the database - read one row in the database table
    public Product findProduct(String id, String name) throws SQLException {
        String fetch = "select * from IOTDB.Products where ID ='" + id + "'and NAME='" + name + "'";
        ResultSet rs = st.executeQuery(fetch);
        
        while(rs.next()) {
            String productId = rs.getString(5);
            String productName = rs.getString(1);
            if (productId.equals(id) && productName.equals(name)) {
                String productDescription = rs.getString(2);
                String productinitialQuantity = rs.getString(3);
                String productPrice = rs.getString(4);
                return new Product (productName,productDescription,productinitialQuantity,productPrice,productId);
            }
        }
        return null;
    }

    //Adding a new product into the database
    public void addProduct(String name,String description,String initialQuantity,String price,String id) throws
            SQLException {
        st.executeUpdate("INSERT INTO IOTDB.PRODUCTS " + "VALUES ('" + name +"', '" + description +"', '" + initialQuantity +"', '" + price +"', '" + description +"')");
    }
    
    //Update the product's information inside the database
    public void updateProduct(String name,String description,String initialQuantity,String price,String id) throws SQLException {
    st.executeUpdate("UPDATE IOTDB.PRODUCTS SET NAME='" + name + "',DESCRIPTION='" + description + "',INITIALQUANTITY='" + initialQuantity + "',PRICE='" + price + "'WHERE ID= '" + id + "'");
    }
    
    //Delete product from the database
    public void deleteProduct(String id) throws SQLException {
    st.executeUpdate("DELTE FROM IOTDB.PRODUCTS WHERE ID='" + id + "'");    
    }
    
    public ArrayList<Product>fetchProducts() throws SQLException {
        String fetch = "select * from PRODUCTS";
        ResultSet rs = st.executeQuery(fetch);
        ArrayList<Product> temp = new ArrayList();
        
        while (rs.next()){
            String name = rs.getString(1);
            String description = rs.getString(2);
            String initialQuantity = rs.getString(3);
            String price = rs.getString(4);
            String id = rs.getString(5);
            temp.add(new Product(name,description,initialQuantity,price,id));
        }
    return temp;
    }
    
    //Go through each row in products table and check to see if the product exists within the database
    public boolean checkProduct(String id, String name) throws SQLException {
        String fetch = "select * from IOTDB.Products where ID= '" + id + "' and name='" + name + "'";
        ResultSet rs = st.executeQuery(fetch);
        while (rs.next()){
            String productId = rs.getString(5);
            String productname = rs.getString(1);
            if(productId.equals(id) && productname.equals(name)){
                return true;
            }
        }
       return false; 
    }
}
