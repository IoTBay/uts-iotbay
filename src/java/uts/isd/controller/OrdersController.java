/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uts.isd.util.Logging;
import uts.isd.model.*;
import uts.isd.model.dao.*;
import uts.isd.util.Flash;

/**
 * Orders controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-30
 */
public class OrdersController extends HttpServlet {


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
            case "cart":
                doViewCartPost(request, response);
                break;
                
            case "checkout":
                doCheckoutPost(request, response);
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
            ICurrency dbCurrency = new DBCurrency();
            
            Customer customer = (Customer)request.getSession().getAttribute("customer");

            Order o = dbOrder.getCartOrderByCustomer(customer);
            o.setOrderLines(dbOrder.getOrderLines(o.getId()));
            o.setCurrency(dbCurrency.getCurrencyById(o.getCurrencyId()));
            
            for (OrderLine line : o.getOrderLines())
                line.setProduct(dbProduct.getProductById(line.getProductId()));

            request.getSession().setAttribute("order", o);
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/orders/view_cart.jsp");
            requestDispatcher.forward(request, response);
        } 
        catch (Exception e)
        {
            Logging.logMessage("Unable to doViewCardGet", e);
        }
    }
    
    protected void doViewCartPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {    
        request.getRequestDispatcher("/orders/view_cart.jsp").forward(request, response);
    }
    
    /*
     * Checkout
     */
    
    protected void doCheckoutGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        request.getRequestDispatcher("/orders/checkout.jsp").forward(request, response);
    }
    
    protected void doCheckoutPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        request.getRequestDispatcher("/orders/checkout.jsp").forward(request, response);
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
