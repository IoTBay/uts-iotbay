/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.IOrder;
import uts.isd.util.Logging;

/**
 *
 * @author rhys
 */
public class Order {
    
    private int id;
    private int customerId;
    private int currencyId;
    private int userId;
    private int billingAddressId;
    private int shippingAddressId;
    private int paymentMethodId;
    private double totalCost;
    private int status;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;

    
    List<OrderLine> orderLines;
    
    public enum Status {
        Draft,
        Submitted,
        PaymentProcessing,
        PaymentSuccessful,
        PickingOrder,
        AwaitingPickup,
        Delivering,
        Completed,
        
        //Failed statuses
        OutOfStock,
        PaymentFailed,
        OnHold,
        Cancelled
    }

    public Order() {
        this.orderLines = new ArrayList<>();
    }
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public Order(ResultSet rs)
    {
        try
        {
            this.id = rs.getInt("ID");
            this.customerId = rs.getInt("CustomerID");
            this.userId = rs.getInt("UserID");
            this.currencyId = rs.getInt("CurrencyID");
            this.billingAddressId = rs.getInt("BillingAddressID");
            this.shippingAddressId = rs.getInt("ShippingAddressID");
            this.paymentMethodId = rs.getInt("PaymentMethodID");
            this.totalCost = rs.getDouble("TotalCost");
            this.status = rs.getInt("Status");
            
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load Order from ResultSet for ID", e);
        }
    }

    public void loadRequest(ServletRequest request)
    {
        this.loadRequest(request, null);
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @param changedBy The customer who made this request.
     */
    public void loadRequest(ServletRequest request, Customer changedBy)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));

        this.currencyId = Integer.parseInt("currencyId");
        this.shippingAddressId = Integer.parseInt(request.getParameter("shippingAddressId"));
        this.billingAddressId = Integer.parseInt(request.getParameter("billingAddressId"));
        this.paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
        this.totalCost = Integer.parseInt(request.getParameter("totalCost"));
        this.status = Integer.parseInt(request.getParameter("status"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        
        if (changedBy != null)
        {
            this.createdBy = changedBy.getId();
            this.modifiedBy = changedBy.getId();
        }
        else
        {
            this.createdBy = 1;
            this.modifiedBy = 1;
        }
        
        this.orderLines = new ArrayList<>();        
    }
    
    public boolean add(IOrder db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addOrder(this);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add order", e);
            return false;
        }        
    }
    
    public boolean update(IOrder db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updateOrder(this);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update order", e);
            return false;
        }        
    }
    
    public boolean delete(IOrder db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deleteOrderById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete order", e);
            return false;
        }        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getCurrencyId() {
        return currencyId;
    }
    
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public double getTotalCost() {
        return totalCost;
    }
    
    public String getTotalCostFormatted(String format) {
        return String.format(format, this.totalCost);
    }
    
    public String getTotalCostFormatted() {
        return String.format("$%.02f", this.totalCost);
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
    
    public int getLineCount() {
        return this.orderLines.size();
    }
    
    public boolean isEmpty() {
        return (this.orderLines.size() == 0);
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    
    public void addOrderLine(OrderLine line)
    {
        this.orderLines.add(line);
        this.totalCost += (line.getUnitPrice() * line.getQuantity());
    }
    
    public void removeOrderLine(OrderLine line)
    {
        this.orderLines.remove(line);
        this.totalCost -= (line.getUnitPrice() * line.getQuantity());
    }
    
    public int getTotalQuantity()
    {
        int quantity = 0;
        for (int i = 0; i < this.orderLines.size(); i++)
            quantity += this.orderLines.get(i).getQuantity();
        
        return quantity;
    }
    
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public int getCreatedBy() {
        return this.createdBy;
    }
    
    public int getModifiedBy() {
        return this.modifiedBy;
    }
}
