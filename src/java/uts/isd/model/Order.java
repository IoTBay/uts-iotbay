/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletRequest;

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
        OnHold
    }

    public Order() {
        this.orderLines = new ArrayList<>();
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * @return boolean - Returns true if adding the properties was successful. Otherwise false.
     */
    public boolean addOrder(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));

        this.shippingAddressId = Integer.parseInt(request.getParameter("shippingAddressId"));
        this.billingAddressId = Integer.parseInt(request.getParameter("billingAddressId"));
        this.paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
        this.totalCost = Integer.parseInt(request.getParameter("totalCost"));
        this.status = Integer.parseInt(request.getParameter("status"));

        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
        
        this.orderLines = new ArrayList<>();
        
        return true;
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
