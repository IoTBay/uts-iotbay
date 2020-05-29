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
import uts.isd.validation.*;

/**
 *
 * @author rhys
 */
public class UsersController extends HttpServlet {

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
                
            case "/register":
                doRegisterGet(request, response);
                break;
                
            case "/profile":
                doProfileViewGet(request, response);
                break;
                
            case "/edit":
                doProfileEditGet(request, response);
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
                
            case "/register":
                doRegisterPost(request, response);
                break;
                
            case "/edit":
                doProfileEditPost(request, response);
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
            
            Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("Email", "email", new ValidationMethod[] {
                    new ValidateRequired(),
                    new ValidateEmail(),
                    new ValidateTrim()
                }),
                new ValidatorFieldRules("Password", "password", new ValidationMethod[] {
                    new ValidateRequired()
                })
            });
            
            if (!validator.validate(request))
            {
                response.sendRedirect(request.getHeader("referer"));
                return;
            }
            
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
                
                //Re-load the login page
                 RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(request, response);
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
                response.sendRedirect(request.getContextPath() + "/");
                //request.getRequestDispatcher("/index.jsp");
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to login", e);
        }
    }

    
    /*
     * Register requests
     */
    
    protected void doRegisterGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/register.jsp");
        requestDispatcher.forward(request, response);
        
    }
    
    protected void doRegisterPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("Email", "email", new ValidationMethod[] {
                    new ValidateRequired(),
                    new ValidateEmail(),
                    new ValidateTrim()
                }),
                new ValidatorFieldRules("First Name", "firstName", new ValidationMethod[] {
                    new ValidateRequired()
                })
            });
        
        HttpSession session = request.getSession();
        
        //We need to figure out if the user is logging out now, or not.
        //then invalidate the session BEFORE including the header, so it shows correctly.
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);

        //Setup flash messages
        Flash flash = Flash.getInstance(session);

        int status = 0;
        
        try
        {
            //Not logged in but submitted registration
            if (!isLoggedIn && request.getParameter("doRegister") != null)
            {
                if (!validator.validate(request))
                {
                    response.sendRedirect(request.getHeader("referer"));
                }
                
                //Create a connection to the DB for the customers table
                ICustomer dbCustomer = new DBCustomer();
                customer = new Customer();
                customer.loadRequest(request);
                customer.add(dbCustomer);

                //Create a connection to the DB for users table
                IUser dbUser = new DBUser();
                user = new User();
                user.setCustomerId(customer.getId()); //Link the new user to the customer we just created above.
                //Add user to DB
                user.loadRequest(request);
                boolean added = user.add(dbUser);


                if (added)
                    flash.add(Flash.MessageType.Success, "New user "+user.getEmail()+" added successfully!");
                else
                    flash.add(Flash.MessageType.Error, "Failed to add new user: "+user.getEmail());

                //Store objects in session so we dont have to load from DB on every page.
                session.setAttribute("customer", customer);
                session.setAttribute("user", user);

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
                
            }
            else
            {
                flash.add(Flash.MessageType.Error, "You can't register if you are already logged in!");
            }
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to register new user", e);
            return;
        }
        
    }
    
    /*
     * Profile requests
     */
    
    protected void doProfileViewGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view_profile.jsp");
        requestDispatcher.forward(request, response);
        
    }
    
     protected void doProfileEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/edit_profile.jsp");
        requestDispatcher.forward(request, response);
        
    }
     
    protected void doProfileEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        //We need to figure out if the user is logging out now, or not.
        //then invalidate the session BEFORE including the header, so it shows correctly.
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);

        //Setup flash messages
        Flash flash = Flash.getInstance(session);

        Logging.logMessage("Updating profile");
        
        try
        {
            //Is Logged in and submitted form
            if (isLoggedIn && request.getParameter("doUpdate") != null)
            {
                //Create a connection to the DB for the customers table
                ICustomer dbCustomer = new DBCustomer();
                //Don't create a new customer, use the current logged in customer.
                //customer = new Customer();
                customer.loadRequest(request);

                //Create a connection to the DB for users table
                IUser dbUser = new DBUser();
                //user = new User();
                user.loadRequest(request);
                
                
                boolean updated = (customer.update(dbCustomer) && user.update(dbUser));
                Logging.logMessage("Updated profile");

                if (updated)
                    flash.add(Flash.MessageType.Success, "Your profile was updated successfully!");
                else
                    flash.add(Flash.MessageType.Error, "Failed to update your profile");
            }
            else
            {
                flash.add(Flash.MessageType.Error, "You are not logged in, or form submission failed");
            }
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view_profile.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update profile");
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
