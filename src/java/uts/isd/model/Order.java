/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 2
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.DBCustomer;
import uts.isd.model.dao.ICustomer;
import uts.isd.model.dao.IOrder;
import uts.isd.util.Logging;

/**
 * Order model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class Order implements Serializable {
    
    private int id;
    private int customerId;
    private int currencyId;
    private Currency currency;
    private int userId;
    private int billingAddressId;
    private Address billingAddress;
    private int shippingAddressId;
    private Address shippingAddress;
    private int paymentMethodId;
    private PaymentMethod paymentMethod;
    private double totalCost;
    private int status;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    List<OrderLine> orderLines;
    
    public static final int STATUS_DRAFT = 0;
    public static final int STATUS_SUBMITTED = 1;
    public static final int STATUS_PAYMENT_PROCESSING = 2;
    public static final int STATUS_PAYMENT_SUCCESSFUL = 3;
    public static final int STATUS_PICKING_ORDER = 4;
    public static final int STATUS_AWAITING_COURIER = 5;
    public static final int STATUS_DELIVERING = 6;
    public static final int STATUS_COMPLETED = 7;
    
    public static final int STATUS_OUT_OF_STOCK = 8;
    public static final int STATUS_PAYMENT_FAILED = 9;
    public static final int STATUS_ON_HOLD = 10;
    public static final int STATUS_CANCELLED = 11;
    
    public static final  String[] ORDER_STATUS = {
        "Draft",
        "Submitted",
        "PaymentProcessing",
        "PaymentSuccessful",
        "PickingOrder",
        "AwaitingPickup",
        "Delivering",
        "Completed",
        
        //Failed statuses
        "OutOfStock",
        "PaymentFailed",
        "OnHold",
        "Cancelled"
    };

    public Order() {
        this.orderLines = new ArrayList<>();
        this.currencyId = 1; //Hard set to ID 1 because we only have 1 currency right now.
        
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
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
            
            this.createdDate = rs.getTimestamp("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getTimestamp("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");    
            
            this.orderLines = new ArrayList<>();
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
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));
        
        if (request.getParameter("currencyId") != null)
            this.currencyId = Integer.parseInt("currencyId");
        
        this.shippingAddressId = Integer.parseInt(request.getParameter("shippingAddress"));
        this.billingAddressId = Integer.parseInt(request.getParameter("billingAddress"));
        this.paymentMethodId = Integer.parseInt(request.getParameter("paymentMethod"));
        
        if (request.getParameter("totalCost") != null)
            this.totalCost = Double.parseDouble(request.getParameter("totalCost"));
        
        if (request.getParameter("status") != null)
            this.status = Integer.parseInt(request.getParameter("status"));        
    }
    
    public boolean add(IOrder db, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addOrder(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add order", e);
            return false;
        }        
    }
    
    public boolean update(IOrder db, Customer customer)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updateOrder(this, customer);
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
    
    public Currency getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
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
    
    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }
    
    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        this.totalCost += line.getPrice();
    }
    
    public void removeOrderLine(OrderLine line)
    {
        this.orderLines.remove(line);
        this.totalCost -= line.getPrice();
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
