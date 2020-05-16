/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.util.Date;
import javax.servlet.ServletRequest;

/**
 *
 * @author rhys
 */
public class PaymentTransaction {
    private int id;
    private int customerId;
    private int orderId;
    private int paymentMethodId;
    private double amount;
    private String description;
    private String paymentGatewayTransaction;
    private int status;
    
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;
    
    public PaymentTransaction() {}
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addCurrency(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        this.orderId = Integer.parseInt(request.getParameter("orderId"));
        this.paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
        this.amount = Double.parseDouble(request.getParameter("amount"));
        this.description = request.getParameter("description");
        this.paymentGatewayTransaction = request.getParameter("paymentGatewayTransaction");
        this.status = Integer.parseInt(request.getParameter("retailConversionRate"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        
        return true;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getPaymentGatewayTransaction() {
        return paymentGatewayTransaction;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }
}

