/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
                doCheckoutGet(request, response);
                break;
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
            IOrder dbOrder = new DBOrder();
            IProduct dbProduct = new DBProduct();
            
            Customer customer = getCustomerForOrder(request.getSession());

            Order o = dbOrder.getCartOrderByCustomer(customer);
            o.setOrderLines(dbOrder.getOrderLines(o.getId()));
            //o.setCurrency(dbCurrency.getCurrencyById(o.getCurrencyId()));
            
            for (OrderLine line : o.getOrderLines())
                line.setProduct(dbProduct.getProductById(line.getProductId()));

            request.getSession().setAttribute("order", o);
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/view_cart.jsp");
            requestDispatcher.forward(request, response);
        } 
        catch (Exception e)
        {
            Logging.logMessage("Unable to doViewCartGet", e);
        }
    }
    
    protected void doViewCartPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {    
        request.getRequestDispatcher("/view/orders/view_cart.jsp").forward(request, response);
    }
    
    /*
     * Checkout
     */
    
    protected void doCheckoutGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        request.getRequestDispatcher("/view/orders/checkout.jsp").forward(request, response);
    }
    
    protected void doCheckoutPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        request.getRequestDispatcher("/view/orders/checkout.jsp").forward(request, response);
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
            
            if (this.customer == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to initialise your customer");
                URL.GoBack(request, response);
                return false;
            }
            
            this.cart = (Order)session.getAttribute("order");
                    
            if (this.cart == null)
            {
                this.cart = dbOrder.getCartOrderByCustomer(customer);
                session.setAttribute("order", cart);
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
        request.getRequestDispatcher("/view/orders/checkout.jsp").forward(request, response);
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
            
            if (existingLine == null)
            {
                flash.add(Flash.MessageType.Error, "This order line no longer exists");
                URL.GoBack(request, response);
                return;
            }
            
            int quantity = existingLine.getQuantity();
            
            if (request.getParameter("doAdd") != null)
            {
                existingLine.setQuantity(quantity + 1);
            }
            else if (request.getParameter("doSubtract") != null)
            {
                existingLine.setQuantity(quantity - 1);
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Quantity change was not submitted properly.");
                URL.GoBack(request, response);
                return;
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
