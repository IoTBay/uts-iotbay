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
                
            case "/logout":
                doLogoutGet(request, response);
                break;
                
            case "/cancel":
                doCancelGet(request, response);
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
                
            case "/cancel":
                doCancelPost(request, response);
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
            
            /*
            Can specify rules by giving a list of ValidationMethod classes
            OR See below - can specify a string to give a list of rules.
            
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
            */
            
            Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("Email", "email", "required|trim|email"), 
                new ValidatorFieldRules("Password", "password", "required|longerthan[2]")
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
                
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Users, "LoggedIn", user.getEmail()+" has logged in.", customer.getId());
                //Setup flash messages
                flash.add(Flash.MessageType.Success, "You logged in successfully. Welcome back!");
                
                //Instead of mocking, load the real order for this user.
                //This is because when a user logs in, their customer object changes.
                IOrder dbOrder = new DBOrder();
                IProduct dbProduct = new DBProduct();
                ICurrency dbCurrency = new DBCurrency();
                
                Order o = dbOrder.getCartOrderByCustomer(customer);
                o.setOrderLines(dbOrder.getOrderLines(o.getId()));
                o.setCurrency(dbCurrency.getCurrencyById(o.getCurrencyId()));

                for (OrderLine line : o.getOrderLines())
                    line.setProduct(dbProduct.getProductById(line.getProductId()));

                request.getSession().setAttribute("order", o);
                
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
                new ValidatorFieldRules("First Name", "firstName", "required"),
                new ValidatorFieldRules("Last Name", "lastName", "required"),
                new ValidatorFieldRules("Phone", "phone", "required|longerthan[9]|shorterthan[11]"),
                new ValidatorFieldRules("Email", "email", "required|trim|email"),
                new ValidatorFieldRules("Password", "password", "required|longerthan[2]"),
                new ValidatorFieldRules("Birth Date", "birthDate", "required|date"),
            });
            
            if (!validator.validate(request))
            {
                response.sendRedirect(request.getHeader("referer"));
                return;
            }
        
       /*Validator validator = new Validator(new ValidatorFieldRules[] {
        *    new ValidatorFieldRules("Email", "email", new ValidationMethod[] {
        *       new ValidateRequired(),
        *       new ValidateEmail(),
        *        new ValidateTrim()
        *    }),
        *    new ValidatorFieldRules("First Name", "firstName", new ValidationMethod[] {
        *        new ValidateRequired()
        *    })
        *});
        */
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
                customer.add(dbCustomer, null);
                
                //Create a connection to the DB for users table
                IUser dbUser = new DBUser();
                user = new User();
                user.setCustomerId(customer.getId()); //Link the new user to the customer we just created above.
                //Add user to DB
                user.loadRequest(request);

                if(request.getParameter("staffcode") != null && request.getParameter("staffcode").equals(User.STAFF_CODE))  
                { 
                    user.setAccessLevel(10);
                    flash.add(Flash.MessageType.Success, "You have been given staff access!");
                }
                else
                {
                    user.setAccessLevel(1);
                }
                
                boolean added = user.add(dbUser, customer);

                if (added)
                {
                    DBAuditLogs.addEntry(DBAuditLogs.Entity.Users, "Added", "Added user", customer.getId());
                    flash.add(Flash.MessageType.Success, "New user "+user.getEmail()+" added successfully!");
                }
                else
                {
                    flash.add(Flash.MessageType.Error, "Failed to add new user: "+user.getEmail());
                    URL.GoBack(request, response);
                    return;
                }

                //Store objects in session so we dont have to load from DB on every page.
                session.setAttribute("customer", customer);
                session.setAttribute("user", user);

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
        
         Validator validator = new Validator(new ValidatorFieldRules[] {
                new ValidatorFieldRules("First Name", "firstName", "required"),
                new ValidatorFieldRules("Last Name", "lastName", "required"),
                new ValidatorFieldRules("Phone", "phone", "required|longerthan[9]|shorterthan[11]"),
                new ValidatorFieldRules("Email", "email", "required|trim|email")
            });
            
        if (!validator.validate(request))
        {
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
        
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
                
                
                boolean updated = (customer.update(dbCustomer, customer) && user.update(dbUser, customer));
                Logging.logMessage("Updated profile");

                if (updated){
                    DBAuditLogs.addEntry(DBAuditLogs.Entity.Users, "Updated", "Updated user", customer.getId());
                    flash.add(Flash.MessageType.Success, "Your profile was updated successfully!");}
                else{
                    flash.add(Flash.MessageType.Error, "Failed to update your profile");}
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
     * Logout requests 
     */
    protected void doLogoutGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/logout.jsp");
        requestDispatcher.forward(request, response);
    }
    
    protected void doLogoutPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
                
        //We need to figure out if the user is logging out now, or not.
        //then invalidate the session BEFORE including the header, so it shows correctly.
        
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        //Store for later
        boolean isLoggedIn = (user != null);
        
        session.invalidate();
             
    }
    
    protected void doCancelGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/cancel_registration.jsp");
        requestDispatcher.forward(request, response);
    }
    
    protected void doCancelPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
                
        //Invalidate the session BEFORE including the header, so it shows correctly.
        
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        
        boolean isLoggedIn = (user != null && customer != null);
        
        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        
        if (!isLoggedIn)
        {
            flash.add(Flash.MessageType.Error, "You are not logged in");
            URL.GoBack(request, response);
            return;
        }

        Logging.logMessage("Updating profile");
        
        try
        {
            //submitted form
            if (request.getParameter("doCancel") != null)
            {
                //Create a connection to the DB for users table
                IUser dbUser = new DBUser();
                //set Access level to 0 to invalidate the account
                user.setAccessLevel(0);
                
                boolean updated = (user.update(dbUser, customer));
                Logging.logMessage("Updated profile");

                if (updated){
                    DBAuditLogs.addEntry(DBAuditLogs.Entity.Users, "Canceled", "Cancelled user", customer.getId());
                    flash.add(Flash.MessageType.Success, "Your profile was cancelled successfully!");
                    session.setAttribute("userCancelled" , true);
                    }
                else{
                    flash.add(Flash.MessageType.Error, "Failed to cancel your profile");
                }
                
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Form submission failed");
            }
            RequestDispatcher requestDispatcher;
            requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to cancel account");
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
