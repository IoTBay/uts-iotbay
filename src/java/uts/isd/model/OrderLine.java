/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.DBCustomer;
import uts.isd.model.dao.ICustomer;
import uts.isd.util.Logging;

/**
 * OrderLine model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class OrderLine {
    
    private int id;
    private int orderId;
    private int productId;
    private Product product;
    private int quantity;
    private double unitPrice;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public OrderLine() {
    }

    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public OrderLine(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.orderId = rs.getInt("OrderID");
            this.productId = rs.getInt("ProductID");
            this.quantity = rs.getInt("Quantity");
            this.unitPrice = rs.getDouble("UnitPrice");
            
            this.createdDate = rs.getTimestamp("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getTimestamp("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load OrderLine from ResultSet", e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
   
    public String getUnitPriceFormatted(String format) {
        return String.format(format, this.unitPrice);
    }
    
    public String getUnitPriceFormatted() {
        return String.format("$%.02f", this.unitPrice);
    }
    
    public double getPrice() {
        return this.unitPrice * this.quantity;
    }
   
    public String getPriceFormatted(String format) {
        return String.format(format, this.getPrice());
    }
    
    public String getPriceFormatted() {
        return String.format("$%.02f", this.getPrice());
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public Customer getCreatedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.createdBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
    
    public Customer getModifiedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.modifiedBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
}
