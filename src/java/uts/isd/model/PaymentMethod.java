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
import uts.isd.model.dao.IPaymentMethod;
import uts.isd.util.Logging;

/**
 * Payment Method model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class PaymentMethod {
    
    private int id;
    private int customerId;
    private int userId;
    private boolean defaultPayment;
    private int paymentType;
    private String cardName;
    private String cardNumber;
    private String cardCVV;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public static final int TYPE_NONE = 0;
    public static final int TYPE_CREDIT_CARD = 1;
    
    public static final String[] PAYMENT_TYPES = { "None", "Credit Card" };

    public PaymentMethod() {
    }
    
 /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public PaymentMethod(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.customerId = rs.getInt("CustomerID");
            this.userId = rs.getInt("UserID");
            this.defaultPayment = rs.getBoolean("DefaultPayment");
            this.paymentType = rs.getInt("PaymentType");
            this.cardName = rs.getString("CardName");
            this.cardNumber = rs.getString("CardNumber");
            this.cardCVV = rs.getString("CardCVV");
            
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load PaymentMethod from ResultSet for ID", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     */
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        this.defaultPayment = Boolean.parseBoolean(request.getParameter("productId"));
        this.paymentType = Integer.parseInt(request.getParameter("paymentType"));
        this.cardName = request.getParameter("cardName");
        this.cardNumber = request.getParameter("cardNumber");
        this.cardCVV = request.getParameter("cardCVV");

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;        
    }
    
    public boolean add(IPaymentMethod db, Customer customer)
    {
        try
        {
            //Assumes the PaymentMethod object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addPaymentMethod(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add payment method", e);
            return false;
        }        
    }
    
    public boolean update(IPaymentMethod db, Customer customer)
    {
        try
        {
            //Assumes the PaymentMethod object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updatePaymentMethod(this, customer);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update payment method", e);
            return false;
        }        
    }
    
    public boolean delete(IPaymentMethod db)
    {
        try
        {
            //Assumes the PaymentMethod object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deletePaymentMethodById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete paymentMethod", e);
            return false;
        }        
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getDefaultPayment() {
        return defaultPayment;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
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