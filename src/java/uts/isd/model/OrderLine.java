/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import javax.servlet.ServletRequest;
import uts.isd.util.Logging;

/**
 *
 * @author rhys
 */
public class OrderLine {
    
    private int id;
    private int orderId;
    private int productId;
    private Product product;
    private int quantity;
    private double unitPrice;
    
    public OrderLine() {
    }

    public OrderLine(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.orderId = rs.getInt("OrderID");
            this.productId = rs.getInt("ProductID");
            this.quantity = rs.getInt("Quantity");
            this.unitPrice = rs.getDouble("UnitPrice");
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load OrderLine from ResultSet", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addOrderLine(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.orderId = Integer.parseInt(request.getParameter("orderId"));
        this.productId = Integer.parseInt(request.getParameter("productId"));
        this.quantity = Integer.parseInt(request.getParameter("quantity"));
        this.unitPrice = Integer.parseInt(request.getParameter("unitPrice"));
        
        return true;
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
}
