/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.*;
import uts.isd.model.dao.*;
import uts.isd.util.*;

/**
 *
 * @author rhys
 */
public class UsersController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UsersController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsersController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        //processRequest(request, response);
        
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
        switch (request.getPathInfo())
        {
            case "/login":
                doLoginGet(request, response);
                break;
                
            case "/create":
                doCreateGet(request, response);
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
        //processRequest(request, response);
        
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
        switch (request.getPathInfo())
        {
            case "/login":
                doLoginPost(request, response);
                break;
                
            case "/create":
                doCreatePost(request, response);
                break;
        }
    }
    
    /*
     * Login requests
     */
    
    protected void doLoginGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //request.getRequestDispatcher("login.jsp").forward(request, response);
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request, response);
        //response.sendRedirect(request.getContextPath() + "/index.jsp");
        
    }
    
    protected void doLoginPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            HttpSession session = request.getSession();
            
            //Create a connection to the DB for users table
            IUser dbUser = new DBUser();
            User user = dbUser.authenticateUser(request.getParameter("email"), request.getParameter("password"));

            Flash flash = Flash.getInstance(session);

            if (user == null)
            {
                //User not logged in - throw error.
                System.out.println("ERROR: Did not auth");
                //Setup flash messages
                flash.add(Flash.MessageType.Error, "Your username and/or password were incorrect for user "+request.getParameter("email"));
            }
            else
            {
                //save to session
                session.setAttribute("user", user);
                //Now get the customer related to the user.
                ICustomer dbCustomer = new DBCustomer();
                Customer customer = dbCustomer.getCustomerById(user.getCustomerId());
                session.setAttribute("customer", customer);

                //Setup flash messages
                flash.add(Flash.MessageType.Success, "You logged in successfully. Welcome back!");


                //For now mock everything else till they are implemented.

                //Mock currency
                Currency currency = new Currency();
                currency.setId(1);
                currency.setName("Australian Dollar");
                currency.setAbbreviation("AUD");
                currency.setCostConversionRate(0.64297);
                currency.setRetailConversionRate(0.650);
                //Mock category
                ProductCategory cat1 = new ProductCategory(1, "Transistors", "There are some transistors here.", "transistors.jpg");
                ProductCategory cat2 = new ProductCategory(2, "PCBs", "There are some PCBs here.", "pcbs.jpg");
                List<ProductCategory> categories = new ArrayList<ProductCategory>();
                categories.add(cat1);
                categories.add(cat2);
                session.setAttribute("categories", categories);
                //Load products
                Product p1 = new Product();
                p1.setId(1);
                p1.setCurrencyId(1);
                p1.setCategoryId(1);
                p1.setName("Widget");
                p1.setDescription("This is a widget");
                p1.setPrice(12.50);
                Product p2 = new Product();
                p2.setId(2);
                p2.setCurrencyId(1);
                p2.setCategoryId(2);
                p2.setName("Thingy");
                p2.setDescription("This is a thingy");
                p2.setPrice(52.75);

                List<Product> products = new ArrayList<Product>();
                products.add(p1);
                products.add(p2);
                session.setAttribute("products", products);

                //Load order
                Order order = new Order();
                order.setId(1);
                order.setCustomerId(1);
                order.setBillingAddressId(1);
                order.setShippingAddressId(1);
                order.setPaymentMethodId(1);
                order.setUserId(1);

                OrderLine line = new OrderLine();
                line.setId(1);
                line.setOrderId(1);
                line.setProductId(1);
                line.setQuantity(3);
                line.setUnitPrice(12.50);
                order.addOrderLine(line);

                OrderLine line2 = new OrderLine();
                line2.setId(2);
                line2.setOrderId(1);
                line2.setProductId(2);
                line2.setQuantity(2);
                line2.setUnitPrice(52.75);
                order.addOrderLine(line2);
                session.setAttribute("order", order);
                
                //Now load index.jsp
                Logging.logMessage("Logged in OK. RequestDispatcher to index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                //request.getRequestDispatcher("/index.jsp");
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to login", e);
        }
    }

    
    /*
     * Create requests
     */
    
    protected void doCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    protected void doCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
