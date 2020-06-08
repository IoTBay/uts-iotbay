/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.util.Logging;
import uts.isd.model.*;
import uts.isd.model.dao.*;
import uts.isd.util.Flash;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 * Orders controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-30
 */
public class OrdersController extends HttpServlet {
    
    private Customer customer;
    private Order cart;
    private Product product;


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * Set things we want available on any request, such as flash
         */
        request.setAttribute("flash", Flash.getInstance(request.getSession()));
        request.setAttribute("customer", (Customer)request.getSession().getAttribute("customer"));
        request.setAttribute("user", (User)request.getSession().getAttribute("user"));
        
        
        //If no action is specified in the URI - e.g. /addresses then assume we're listing.
        if (request.getPathInfo() == null || request.getPathInfo().equals("/"))
        {
            doOrdersListGet(request, response);
            return;
        }
        
        
        /* Use getPathInfo to figure out what URL suffix is being used in the request.
         * i.e. the bit after the controller mapping.
         * https://stackoverflow.com/questions/4278083/how-to-get-request-uri-without-context-path
         * 
         * ALSO: Make sure in web.xmxl you use a wildcard to match any sub-path you want to use in this controller.
         *  <servlet-mapping>
         *    <servlet-name>UsersController</servlet-name>
         *    <url-pattern>/User/*</url-pattern>
         *   </servlet-mapping>
         */
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "cart":
                doViewCartGet(request, response);
                break;
                
            case "checkout":
                doCheckoutGet(request, response);
                break;
                
            case "list":
                doOrdersListGet(request, response);
                break;
                
            case "view":
                doOrderViewGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "cancel":
                doCancelOrderGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /* Use getPathInfo to figure out what URL suffix is being used in the request.
         * i.e. the bit after the controller mapping.
         * https://stackoverflow.com/questions/4278083/how-to-get-request-uri-without-context-path
         * 
         * ALSO: Make sure in web.xmxl you use a wildcard to match any sub-path you want to use in this controller.
         *  <servlet-mapping>
         *    <servlet-name>UsersController</servlet-name>
         *    <url-pattern>/User/*</url-pattern>
         *   </servlet-mapping>
         */
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "addline":
                doAddLinePost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "deleteline":
                doDeleteLinePost(request, response);
                break;
                
            case "updateqty":
                doUpdateQuantityPost(request, response);
                break;
                
            case "checkout":
                doCheckoutPost(request, response);
                break;
                
            case "search":
                doOrdersSearchPost(request, response);
                break;
                
           case "cancel":
                doCancelOrderPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
        }
    }
    
    /*
     * View order
     */
    
    protected void doOrderViewGet(HttpServletRequest request, HttpServletResponse response, String orderIdStr)
            throws ServletException, IOException 
    {
        try
        {  
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            Customer customer = (Customer)session.getAttribute("customer");
            User user = (User)session.getAttribute("user");

            if (customer == null || user == null)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }

            int orderId = Integer.parseInt(orderIdStr);
            IOrder dbOrder = new DBOrder();
            Order o = dbOrder.getOrderById(orderId);
            
            if (o == null)
            {
                flash.add(Flash.MessageType.Error, "Could not find this order");
                URL.GoBack(request, response);
                return;
            }
            
            Order.getFullOrder(o);
            request.setAttribute("order", o);
            
            IPaymentTransaction dbTransaction = new DBPaymentTransaction();
            List<PaymentTransaction> transactions = dbTransaction.getAllPaymentTransactionsByOrderId(o.getId());
            request.setAttribute("transactions", transactions);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/view.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to run doOrderViewGet", e);
        }
    }
    
    /*
     * List orders
     */
    
    protected void doOrdersListGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {   
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            Customer customer = (Customer)session.getAttribute("customer");
            User user = (User)session.getAttribute("user");

            if (customer == null || user == null)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }

            IOrder dbOrder = new DBOrder();
            List<Order> orders = dbOrder.getOrdersByCustomerId(customer.getId());
            for (Order o : orders)
            {
                Order.getFullOrder(o);
            }

            request.setAttribute("orders", orders);

            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/list.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to run doOrdersList", e);
        }
    }
    
    
     protected void doOrdersSearchPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {   
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            Customer customer = (Customer)session.getAttribute("customer");
            User user = (User)session.getAttribute("user");

            if (customer == null || user == null)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }
            
            Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("Search term", "search", "required")
            });
          
            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IOrder dbOrder = new DBOrder();
            
            //First try and do a lookup of the order ID
            int orderId = 0;
            try
            {
                orderId = Integer.parseInt(request.getParameter("search"));
            }
            catch (Exception e)
            {
                orderId = 0;
            }
            
            if (orderId > 0)
            {
                response.sendRedirect(URL.Absolute("order/view/"+orderId, request));
                return;
            }
            else
            {
                //Extract dates:
                //Matches from:YYYY-MM-DD to:YYYY-MM-DD in either oreintation
                //NOTE: For some reason java won't match without throwing in .* around the pattern.
                final String fromPattern = ".*?(?:from\\:(\\d{4}\\-\\d{2}\\-\\d{2})).*?";
                final String toPattern = ".*?(?:to\\:(\\d{4}\\-\\d{2}\\-\\d{2})).*?";
                Pattern fromP = Pattern.compile(fromPattern);
                Pattern toP = Pattern.compile(toPattern);
                
                //Java regex is weirdly not working with my pattern properly.
                //Easiest way to get it to work is to add .*? everywhere and split into two separate patterns.
                //This simplifies the logic anyway.
                Matcher matchesFrom = fromP.matcher(request.getParameter("search"));
                Matcher matchesTo = toP.matcher(request.getParameter("search"));
                
                if (!matchesFrom.matches() || !matchesTo.matches())
                {
                    flash.add(Flash.MessageType.Error, "Use dates format from:YYYY-MM-DD to:YYYY-MM-DD");
                    URL.GoBack(request, response);
                    return;
                }
                
                Logging.logMessage("From full match is: "+matchesFrom.group(0));
                Logging.logMessage("From Match 1: "+matchesFrom.group(1));
                Logging.logMessage("To full match is: "+matchesTo.group(0));
                Logging.logMessage("To Match 1: "+matchesTo.group(1));
                
                String start = matchesFrom.group(1);
                String end = matchesTo.group(1);
                
                List<Order> orders = dbOrder.searchOrdersByDateForCustomerId(start, end, customer.getId());
                for (Order o : orders)
                {
                    Order.getFullOrder(o);
                }
                flash.add(Flash.MessageType.Success, "You search returned "+orders.size()+" results");
                request.setAttribute("orders", orders);

                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/orders/list.jsp");
                requestDispatcher.forward(request, response);
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to run doOrdersList", e);
        }
    }
     
    /*
     * Cancel order
     */
  protected void doCancelOrderGet(HttpServletRequest request, HttpServletResponse response, String orderIdStr)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);
        
        try
        {
            int orderId = Integer.parseInt(orderIdStr);
            IOrder dbOrder = new DBOrder();
            Customer customer = (Customer)session.getAttribute("customer");
            User user = (User)session.getAttribute("user");

            if (customer == null)
            {
                flash.add(Flash.MessageType.Error, "You are not a customer yet! Login or place an order");
                URL.GoBack(request, response);
                return;
            }
            
            Order order = dbOrder.getOrderById(orderId);
            
            if (order == null)
            {
                flash.add(Flash.MessageType.Error, "Could not find this order");
                URL.GoBack(request, response);
                return;
            }
            
            if (order.getStatus() >= Order.STATUS_DELIVERING)
            {
                flash.add(Flash.MessageType.Error, "You can't cancel orders with this status.");
                URL.GoBack(request, response);
                return;
            }
            
            request.setAttribute("order", order);
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/cancel.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to doViewCartGet", e);
        }
    }
  
   protected void doCancelOrderPost(HttpServletRequest request, HttpServletResponse response, String orderIdStr)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);

        try
        {  
            int orderId = Integer.parseInt(orderIdStr);
            IOrder dbOrder = new DBOrder();
            Customer customer = (Customer)session.getAttribute("customer");
            User user = (User)session.getAttribute("user");

            if (customer == null)
            {
                flash.add(Flash.MessageType.Error, "You are not a customer yet! Login or place an order");
                URL.GoBack(request, response);
                return;
            }
            
            Order order = dbOrder.getOrderById(orderId);
            
            if (order == null)
            {
                flash.add(Flash.MessageType.Error, "Could not find this order");
                URL.GoBack(request, response);
                return;
            }
            
            if (order.getStatus() >= Order.STATUS_DELIVERING)
            {
                flash.add(Flash.MessageType.Error, "You can't cancel orders with this status.");
                URL.GoBack(request, response);
                return;
            }
            
            Order.getFullOrder(order); //Load all related properties to get product info
            
            //Don't actually delete, just set status to cancel and then take stock back.
            order.setStatus(Order.STATUS_CANCELLED);
            if (updateOrderStock(order, customer, "add") && dbOrder.updateOrder(order, customer))
            {
                flash.add(Flash.MessageType.Success, "Successfully cancelled your order! Stock has been returned");
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to cancel order");
            }
            
            response.sendRedirect(URL.Absolute("order/list", request));
        }
        catch (Exception e)
        {
            flash.add(Flash.MessageType.Error, "Could not load cancel page");
            URL.GoBack(request, response);
            Logging.logMessage("Unable to doViewCartGet", e);
        }
    }
    
    /*
     * View Cart 
     */
   
    protected void doViewCartGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {   
            //Get order and pass it to request for JSP
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/view_cart.jsp");
            requestDispatcher.forward(request, response);
        } 
        catch (Exception e)
        {
            Logging.logMessage("Unable to doViewCartGet", e);
        }
    }
    
    /*
     * Checkout
     */
    
    protected void doCheckoutGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {   
            //Get order and pass it to request for JSP
            IOrder dbOrder = new DBOrder();
            IProduct dbProduct = new DBProduct();
            
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
            
            /**
             * Probably don't need this as order should already be set
            this.cart.setOrderLines(dbOrder.getOrderLines(this.cart.getId()));
            //o.setCurrency(dbCurrency.getCurrencyById(o.getCurrencyId()));
            
            for (OrderLine line : this.cart.getOrderLines())
                line.setProduct(dbProduct.getProductById(line.getProductId()));

            request.getSession().setAttribute("order", this.cart);
            */
            
            //Load default addresses for the form
            User user = (User)request.getSession().getAttribute("user");
            Address defaultShippingAddress = new Address();
            Address defaultBillingAddress = new Address();
            List<Address> savedAddresses = new ArrayList<Address>();
            
            PaymentMethod defaultPaymentMethod = new PaymentMethod();
            List<PaymentMethod> savedPaymethods = new ArrayList<PaymentMethod>();
            
            //Check if user is logged in
            if (user != null)
            {
                IAddress dbAddress = new DBAddress();
                defaultShippingAddress = dbAddress.getDefaultShippingAddressByUserId(user.getId());
                defaultBillingAddress = dbAddress.getDefaultBillingAddressByUserId(user.getId());
                savedAddresses = dbAddress.getAllAddressesByCustomerId(this.customer.getId());
                
                if (defaultShippingAddress == null)
                    defaultShippingAddress = new Address();
                
                if (defaultBillingAddress == null)
                    defaultBillingAddress = new Address();
                
                if (savedAddresses == null)
                    savedAddresses = new ArrayList<Address>();
                
                IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
                defaultPaymentMethod = dbPaymentMethod.getDefaultPaymentMethodByUserId(user.getId());
                savedPaymethods = dbPaymentMethod.getAllPaymentMethodsByCustomerId(this.customer.getId());
                
                
                if (defaultPaymentMethod == null)
                    defaultPaymentMethod = new PaymentMethod();
                
                if (savedPaymethods == null)
                    savedPaymethods = new ArrayList<PaymentMethod>();
            }
            
            request.setAttribute("defaultShippingAddress", defaultShippingAddress);
            request.setAttribute("defaultBillingAddress", defaultBillingAddress);
            request.setAttribute("savedAddresses", savedAddresses);
            
            request.setAttribute("defaultPaymentMethod", defaultPaymentMethod);
            request.setAttribute("savedPaymethods", savedPaymethods);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/checkout.jsp");
            requestDispatcher.forward(request, response);
        } 
        catch (Exception e)
        {
            Logging.logMessage("Unable to doCheckoutGet", e);
        }
    }
    
    protected void doCheckoutPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
            
            //Validate
            //if id = -1 Add fields as needed.
            Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("First Name", "firstName", "required"),
                new ValidatorFieldRules("Last Name", "lastName", "required"),
                new ValidatorFieldRules("Phone", "phone", "required|longerthan[9]|shorterthan[11]"),
                new ValidatorFieldRules("Email", "email", "required|trim|email"), 
            });

            //Do we need to validate shipping address inputs.
            if (request.getParameter("shippingAddress") == null || request.getParameter("shippingAddress").equals("-1"))
            {
                validator.addField(new ValidatorFieldRules("Shipping Address 2", "shipping_addressPrefix1", "trim"));
                validator.addField(new ValidatorFieldRules("Shipping Street Number", "shipping_streetNumber", "required|integer|shorterthan[11]"));
                validator.addField(new ValidatorFieldRules("Shipping Street Name", "shipping_streetName", "required|shorterthan[61]"));
                validator.addField(new ValidatorFieldRules("Shipping Street Type", "shipping_streetType", "required|shorterthan[21]"));
                validator.addField(new ValidatorFieldRules("Shipping Suburb", "shipping_suburb", "required|shorterthan[61]"));
                validator.addField(new ValidatorFieldRules("Shipping State", "shipping_state", "required|shorterthan[31]"));
                validator.addField(new ValidatorFieldRules("Shipping Post Code", "shipping_postcode", "required|shorterthan[5]"));
                validator.addField(new ValidatorFieldRules("Shipping Country", "shipping_country", "required|shorterthan[31]"));
            }

            //Do we need to validate shipping address inputs.
            if (request.getParameter("billingAddress") == null || request.getParameter("billingAddress").equals("-1"))
            {
                validator.addField(new ValidatorFieldRules("Billing Address 2", "billing_addressPrefix1", "trim"));
                validator.addField(new ValidatorFieldRules("Billing Street Number", "billing_streetNumber", "required|integer|shorterthan[11]"));
                validator.addField(new ValidatorFieldRules("Billing Street Name", "billing_streetName", "required|shorterthan[61]"));
                validator.addField(new ValidatorFieldRules("Billing Street Type", "billing_streetType", "required|shorterthan[21]"));
                validator.addField(new ValidatorFieldRules("Billing Suburb", "billing_suburb", "required|shorterthan[61]"));
                validator.addField(new ValidatorFieldRules("Billing State", "billing_state", "required|shorterthan[31]"));
                validator.addField(new ValidatorFieldRules("Billing Post Code", "billing_postcode", "required|shorterthan[5]"));
                validator.addField(new ValidatorFieldRules("Billing Country", "billing_country", "required|shorterthan[31]"));
            }

            //Do we need to validate shipping address inputs.
            if (request.getParameter("paymentMethod") == null || request.getParameter("paymentMethod").equals("-1"))
            {
                validator.addField(new ValidatorFieldRules("Payment Type", "paymentType", "required|integer"));
                validator.addField(new ValidatorFieldRules("Card Name", "cardName", "required"));
                validator.addField(new ValidatorFieldRules("Card Number", "cardNumber", "required|integer|longerthan[7]|shorterthan[20]"));
                validator.addField(new ValidatorFieldRules("Card CVV", "cardCVV", "required|integer|longerthan[2]|shorterthan[4]"));
                validator.addField(new ValidatorFieldRules("Card Expiry", "cardExpiry", "required|integer|shorterthan[5]|longerthan[3]"));
            }

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IOrder dbOrder = new DBOrder();
            IProduct dbProduct = new DBProduct();
            IAddress dbAddress = new DBAddress();
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            IPaymentTransaction dbTransaction = new DBPaymentTransaction();
            
            User user = (User)request.getSession().getAttribute("user");
            
            
            Flash flash = Flash.getInstance(request.getSession());
                        
            //Before we go any further, check order stock
            if (!this.updateOrderStock(this.cart, this.customer, "remove"))
            {
                flash.add(Flash.MessageType.Error, "Unsufficient stock to submit this order");
                URL.GoBack(request, response);
                return;
            }

            //Load initial form data.
            this.cart.loadRequest(request);

            //If id = -1 add address or payment
            if (request.getParameter("shippingAddress") == null || request.getParameter("shippingAddress").equals("-1"))
            {
                Address shippingAddress = new Address();
                shippingAddress.loadRequest(request, "shipping_");
                shippingAddress.setCustomerId(this.customer.getId());
                if (user != null)
                    shippingAddress.setUserId(user.getId());
                
                if (!dbAddress.addAddress(shippingAddress, this.customer))
                {
                    flash.add(Flash.MessageType.Error, "Unable to add new shipping address");
                    URL.GoBack(request, response);
                    return;
                }
                //Set ID on order
                this.cart.setShippingAddressId(shippingAddress.getId());
            }
            
            if (request.getParameter("billingAddress") == null || request.getParameter("billingAddress").equals("-1"))
            {
                Address billingAddress = new Address();
                billingAddress.setCustomerId(this.customer.getId());
                if (user != null)
                    billingAddress.setUserId(user.getId());
                
                billingAddress.loadRequest(request, "billing_");
                if (!dbAddress.addAddress(billingAddress, this.customer))
                {
                    flash.add(Flash.MessageType.Error, "Unable to add new billing address");
                    URL.GoBack(request, response);
                    return;
                }
                //Set ID on order
                this.cart.setBillingAddressId(billingAddress.getId());
            }
            
            if (request.getParameter("paymentMethod") == null || request.getParameter("paymentMethod").equals("-1"))
            {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setCustomerId(this.customer.getId());
                if (user != null)
                    paymentMethod.setUserId(user.getId());
                
                paymentMethod.loadRequest(request);
                if (!dbPaymentMethod.addPaymentMethod(paymentMethod, this.customer))
                {
                    flash.add(Flash.MessageType.Error, "Unable to add new payment method");
                    URL.GoBack(request, response);
                    return;
                }
                //Set ID on order
                this.cart.setPaymentMethodId(paymentMethod.getId());
            }

            //Update order with fields and update status to submitted
            this.cart.setStatus(Order.STATUS_SUBMITTED);
            
            if (!dbOrder.updateOrder(this.cart, this.customer))
            {
                flash.add(Flash.MessageType.Error, "Unable to submit order");
                URL.GoBack(request, response);
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Success, "Successfully submitted order. Order status is now submitted");
            }
            
            //Insert payment transaction linked to order
            //In reality this would be an API call to an external payment gateway.
            PaymentTransaction tx = new PaymentTransaction();
            tx.setCustomerId(this.customer.getId());
            tx.setOrderId(this.cart.getId());
            tx.setAmount(this.cart.getTotalCost());
            tx.setDescription("Full payment for order #"+this.cart.getId());
            tx.setStatus(PaymentTransaction.PAYMENT_SUCCESSFUL);
            //Make up a payment transaction ID
            tx.setPaymentGatewayTransaction(java.util.UUID.randomUUID().toString());
            if (dbTransaction.addPaymentTransaction(tx, this.customer))
            {
                flash.add(Flash.MessageType.Success, "Payment for order was successful");
                flash.add(Flash.MessageType.Success, "Payment Transaction: "+tx.getPaymentGatewayTransaction());
                this.cart.setStatus(Order.STATUS_PAYMENT_SUCCESSFUL);
                dbOrder.updateOrder(this.cart, this.customer);
            }
            
            //Set new draft order as cart.
            request.getSession().removeAttribute("order");
            this.cart = dbOrder.getCartOrderByCustomer(this.customer);
            request.getSession().setAttribute("order", cart);

            //Go back to index page
            response.sendRedirect(URL.Absolute("", request));
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to doCartCheckout to submit order", e);
        }
    }
    
    /*
     * Order Lines
     * For adding/removing items to your order
     */
    
    /**
     * This method contains the logic of finding an existing customer, or
     * creating a new customer, even for anonymous users.
     * 
     * @param session The session that contains 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    protected Customer getCustomerForOrder(HttpSession session) throws SQLException, ClassNotFoundException
    {
        Customer customer = (Customer)session.getAttribute("customer");
        
        //If the customer does not exist, then create a new customer automatically.
        if (customer == null)
        {
            Logging.logMessage("Visitor is anonymous and doesn't have a customer in session. Creating customer.");
            ICustomer dbCustomer = new DBCustomer();
            customer = new Customer();
            if (dbCustomer.addCustomer(customer, null)) //Customer is an anonymous user so don't pass changed by.
            {
                customer = dbCustomer.getCustomerById(customer.getId());
                session.setAttribute("customer", customer);
                Logging.logMessage("Successfully added customer ID "+customer.getId()+" for anonymous user.");
            }
            else
            {
                Logging.logMessage("Failed to create new customer for anonymous user.");    
            }
        }
        else
        {
            Logging.logMessage("Found customer in session");
        }
        return customer;
    }
    
    protected boolean initialiseCart(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {   
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            this.customer = this.getCustomerForOrder(session);
            IOrder dbOrder = new DBOrder();
            ICurrency dbCurrency = new DBCurrency();
            IProduct dbProduct = new DBProduct();
            
            if (this.customer == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to initialise your customer");
                URL.GoBack(request, response);
                return false;
            }
            
            this.cart = (Order)session.getAttribute("order");
                    
            if (this.cart == null)
            {
                this.cart = dbOrder.getCartOrderByCustomer(this.customer);
                this.cart.setOrderLines(dbOrder.getOrderLines(this.cart.getId()));
                this.cart.setCurrency(dbCurrency.getCurrencyById(this.cart.getCurrencyId()));

                for (OrderLine line : this.cart.getOrderLines())
                    line.setProduct(dbProduct.getProductById(line.getProductId()));
                
                session.setAttribute("order", this.cart);
            }
            
            //If still null cart, then throw error.
            if (this.cart == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to load your cart to add item");
                URL.GoBack(request, response);
                return false;
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to initialise cart in: initialiseCart", e);
            return false;
        }
        return true;
    }
    
    protected boolean updateOrderTotal()
    {
        try
        {
            double totalPrice = 0;

            for (OrderLine line : this.cart.getOrderLines())
                totalPrice += line.getPrice();

            this.cart.setTotalCost(totalPrice);
            IOrder dbOrder = new DBOrder();
            return dbOrder.updateOrder(this.cart, this.customer);
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update order total", e);
            return false;
        }
    }
    
    protected boolean checkProductStock(Product product, int quantity)
    {
        return (product.getCurrentQuantity() >= quantity);
    }
    
    protected boolean updateOrderStock(Order order, Customer customer, String action)
    {
        if (!action.equals("add") && !action.equals("remove"))
        {
            Logging.logMessage("Invalid action passed to updateOrderStock: "+action);
            return false;
        }
        
        //First go through and see if we have enough stock for every line in order
        if (action.equals("remove"))
        {
            for (OrderLine line : order.getOrderLines())
            {
                if (!this.checkProductStock(line.getProduct(), line.getQuantity()))
                    return false;
            }
        }
        //Now subtract or add this from available stock.
        try
        {
            IProduct dbProduct = new DBProduct();

            for (OrderLine line : order.getOrderLines())
            {
                //Make sure we have the latest product info
                Product p = dbProduct.getProductById(line.getProductId());
                
                if (action.equals("remove"))
                    p.setCurrentQuantity(p.getCurrentQuantity() - line.getQuantity());
                else if (action.equals("add"))
                    p.setCurrentQuantity(p.getCurrentQuantity() + line.getQuantity());
                
                if (!dbProduct.updateProduct(p, customer))
                {
                    Logging.logMessage("Failed to update quantity of product ID:"+p.getId());
                    return false;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to run updateOrderStock", e);
            return false;
        }
    }
    
    protected void doAddLinePost(HttpServletRequest request, HttpServletResponse response, String productIdStr)
            throws ServletException, IOException 
    {
        try 
        {
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
            
            int productId = Integer.parseInt(productIdStr);
            
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            IOrder dbOrder = new DBOrder();
            
            if (request.getParameter("addQuantity") == null)
            {
                flash.add(Flash.MessageType.Error, "Quantity to add to product was not submitted");
                URL.GoBack(request, response);
                return;
            }
            
            String qtyStr = request.getParameter("addQuantity");
            int quantity = Integer.parseInt(qtyStr);
            
            IProduct dbProduct = new DBProduct();
            this.product = dbProduct.getProductById(productId);
            
            if (product == null)
            {
                flash.add(Flash.MessageType.Error, "Could not find this product");
                URL.GoBack(request, response);
                return;
            }
            
            //Figure out if this product exists in an order line, and if so update quantity.
            OrderLine existingLine = null;
            
            for (OrderLine line : this.cart.getOrderLines())
            {
                if (line.getProductId() == product.getId())
                {
                    Logging.logMessage("Adding quantity to existing line ID: "+line.getId());
                    existingLine = line;
                    break;
                }
            }
            
            //Check stock of order
            if (!checkProductStock(this.product, (existingLine == null ? quantity : existingLine.getQuantity() + quantity)))
            {
                flash.add(Flash.MessageType.Error, "This product has insufficient stock");
                URL.GoBack(request, response);
                return;
            }

            if (existingLine != null)
            {
                existingLine.setQuantity(existingLine.getQuantity() + quantity);
                
                if (dbOrder.updateOrderLine(existingLine, this.customer))
                {
                    flash.add(Flash.MessageType.Success, "Successfully updated quantity");
                }
                else
                {
                    flash.add(Flash.MessageType.Error, "Failed to update item quantity. Try Again?");
                    URL.GoBack(request, response);
                    return;
                }
            }
            else
            {
                OrderLine line = new OrderLine();
                line.setOrderId(this.cart.getId());
                line.setProductId(this.product.getId());
                line.setProduct(this.product);
                line.setQuantity(quantity);
                line.setUnitPrice(this.product.getPrice());

                if (dbOrder.addOrderLine(line, this.customer))
                {
                    this.cart.addOrderLine(line);
                    flash.add(Flash.MessageType.Success, "Successfully added new item '"+this.product.getName()+"' to cart!");
                }
                else
                {
                    flash.add(Flash.MessageType.Error, "Failed to add new item to cart. Try Again?");
                    URL.GoBack(request, response);
                    return;
                }
            }
            
            if (!this.updateOrderTotal())
            {
                flash.add(Flash.MessageType.Error, "Failed to update order total");
                Logging.logMessage("Failed to update order total");
            }
            
            //Still return back to the product page even when successful
            URL.GoBack(request, response);
        }
        catch (Exception  e)
        {
            Logging.logMessage("Unable to doAddLinePost", e);
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeleteLinePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try 
        {
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
                        
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            IOrder dbOrder = new DBOrder();
            
            if (request.getParameter("lineId") == null || request.getParameter("productId") == null)
            {
                flash.add(Flash.MessageType.Error, "This request is invalid");
                URL.GoBack(request, response);
                return;
            }
            
            int lineId = Integer.parseInt(request.getParameter("lineId"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            IProduct dbProduct = new DBProduct();
            Product product = dbProduct.getProductById(productId);
            String productName = (product == null ? "" : "("+product.getName()+") ");
            
            if (dbOrder.deleteOrderLineById(lineId))
            {
                //Keep cart in sync
                for (OrderLine line : this.cart.getOrderLines())
                {
                    if (line.getId() == lineId)
                    {
                        this.cart.removeOrderLine(line);
                        break;
                    }
                }
                flash.add(Flash.MessageType.Success, "Successfully deleted product "+productName+"from cart");
            }
            else
                flash.add(Flash.MessageType.Error, "Failed to remove product from cart");
            
            if (!this.updateOrderTotal())
            {
                flash.add(Flash.MessageType.Error, "Failed to update order total");
                Logging.logMessage("Failed to update order total");
            }
            
            //Still return back to the product page even when successful
            URL.GoBack(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to delete line item", e);
        }
    }
    
    protected void doUpdateQuantityPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try 
        {
            if (!this.initialiseCart(request, response))
            {
                Logging.logMessage("Failed to initialise cart");
                return;
            }
            
            HttpSession session = request.getSession();
            Flash flash = Flash.getInstance(session);
            IOrder dbOrder = new DBOrder();
            
            String lineIdStr = request.getParameter("lineId");
            int orderLineId = Integer.parseInt(lineIdStr);

            OrderLine existingLine = dbOrder.getOrderLineById(orderLineId);
            IProduct dbProduct = new DBProduct();
            this.product = dbProduct.getProductById(existingLine.getProductId());
            
            if (existingLine == null || this.product == null)
            {
                flash.add(Flash.MessageType.Error, "This order line no longer exists");
                URL.GoBack(request, response);
                return;
            }
            
            int quantity = existingLine.getQuantity();
            
            //Check stock of order
            if (!checkProductStock(this.product, (request.getParameter("doAdd") != null ? (quantity + 1) : quantity)))
            {
                flash.add(Flash.MessageType.Error, "This product has insufficient stock");
                URL.GoBack(request, response);
                return;
            }
            
            if (request.getParameter("doAdd") != null)
            {
                existingLine.setQuantity(quantity + 1);
            }
            else if (request.getParameter("doSubtract") != null)
            {
                //If subtracting, do checks on minimum quantity
                if (quantity == 1)
                {
                    flash.add(Flash.MessageType.Error, "Can't lower quantity any further.");
                    URL.GoBack(request, response);
                    return;
                }
                existingLine.setQuantity(quantity - 1);
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Quantity change was not submitted properly.");
                URL.GoBack(request, response);
                return;
            }
            
            //Need to set the new quantity on the session's cart object, so it doesn't
            //get out of sync
            for (OrderLine line : this.cart.getOrderLines())
            {
                if (line.getId() == existingLine.getId())
                {
                    line.setQuantity(existingLine.getQuantity());
                    break;
                }
            }
            
            if (dbOrder.updateOrderLine(existingLine, this.customer))
            {
                flash.add(Flash.MessageType.Success, "Successfully updated quantity");
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to update item quantity. Try Again?");
                URL.GoBack(request, response);
                return;
            }
            
            if (!this.updateOrderTotal())
            {
                flash.add(Flash.MessageType.Error, "Failed to update order total");
                Logging.logMessage("Failed to update order total");
            }
            
            //Still return back to the product page even when successful
            URL.GoBack(request, response);
        }
        catch (Exception  e)
        {
            Logging.logMessage("Unable to doUpdateQuantityPost", e);
            URL.GoBack(request, response);
            return;
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
