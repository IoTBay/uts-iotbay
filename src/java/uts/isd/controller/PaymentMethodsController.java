/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.PaymentMethod;
import uts.isd.model.Customer;
import uts.isd.model.User;
import uts.isd.model.dao.DBPaymentMethod;
import uts.isd.model.dao.DBAuditLogs;
import uts.isd.model.dao.IPaymentMethod;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 * PaymentMethods controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-04
 */
public class PaymentMethodsController extends HttpServlet {


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
        
        //If no action is specified in the URI - e.g. /paymethods then assume we're listing.
        if (request.getPathInfo() == null || request.getPathInfo().equals("/"))
        {
            doListPaymentMethodsGet(request, response, "");
            return;
        }
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        

        
        switch (segments[1])
        {
            case "list":
                //Pass segment[2] in as the customerId if one is given in this request.
                //This is so staff can view paymethods for any customer with /adddresses/list/xxx
                doListPaymentMethodsGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "add":
                doAddPaymentMethodGet(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /paymethods/edit/x
                doUpdatePaymentMethodGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /paymethods/delete/x
                doDeletePaymentMethodGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }

    protected void doListPaymentMethodsGet(HttpServletRequest request, HttpServletResponse response, String customerIdStr)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {
            User user = (User)request.getSession().getAttribute("user");
            if (user == null)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }
            
            int customerId = user.getCustomerId(); //Default to seeing current user's paymethods.
            
             //Only staff can access records that don't belong to them.
            if (user.isAdmin() && !customerIdStr.isEmpty())
            {
                //Change to specified customerId if given
                customerId = Integer.parseInt(customerIdStr);
            }
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            List<PaymentMethod> paymethods = dbPaymentMethod.getAllPaymentMethodsByCustomerId(customerId);
            request.setAttribute("paymethods", paymethods);
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to list paymethods");
            Logging.logMessage("Unable to get paymethods");
            URL.GoBack(request, response);
            return;
        }
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view/paymethods/list.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doAddPaymentMethodGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        User user = (User)request.getSession().getAttribute("user");
        
        if (user == null)
        {
            flash.add(Flash.MessageType.Error, "You are not logged in");
            URL.GoBack(request, response);
            return;
        }
            
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view/paymethods/add.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doUpdatePaymentMethodGet(HttpServletRequest request, HttpServletResponse response, String paymethodStr)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {
            User user = (User)request.getSession().getAttribute("user");
            if (user == null)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }
            
            int paymethodId = Integer.parseInt(paymethodStr);
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            //Get the existing paymethod from the DB so we can pass it to the view to pre-load values.
            PaymentMethod paymethod = dbPaymentMethod.getPaymentMethodById(paymethodId);
            
            if (paymethod == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find paymethod to edit");
                URL.GoBack(request, response);
                return;
            }
            
            //Only staff can access records not related to them.
            if ((paymethod.getCustomerId() != user.getCustomerId()) && !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the paymethod object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("paymethod", paymethod);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/paymethods/edit.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to edit paymethod");
            Logging.logMessage("Unable to edit paymethod");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeletePaymentMethodGet(HttpServletRequest request, HttpServletResponse response, String paymethodStr)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {
            User user = (User)request.getSession().getAttribute("user");
            if (user == null || !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            int paymethodId = Integer.parseInt(paymethodStr);
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            //Get the existing paymethod from the DB so we can pass it to the view to pre-load values.
            PaymentMethod paymethod = dbPaymentMethod.getPaymentMethodById(paymethodId);
            
            if (paymethod == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find paymethod to delete");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the paymethod object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("paymethod", paymethod);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/paymethods/delete.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to delete paymethod");
            Logging.logMessage("Unable to delete paymethod");
            URL.GoBack(request, response);
            return;
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
            throws ServletException, IOException
    {
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
                
            case "add":
                doAddPaymentMethodPost(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /paymethods/edit/x
                doUpdatePaymentMethodPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /paymethods/delete/x
                doDeletePaymentMethodGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }
    
    protected void doAddPaymentMethodPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (customer != null && user != null);
        
        try
        {
            if (!isLoggedIn)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }

            Validator validator = new Validator(new ValidatorFieldRules[] {
                 new ValidatorFieldRules("Default Payment", "defaultPayment", "trim"),
                 new ValidatorFieldRules("Payment Type", "paymentType", "required|integer"), 
                 new ValidatorFieldRules("Card Name", "cardName", "required"),
                 new ValidatorFieldRules("Card Number", "cardNumber", "required|integer"),
                 new ValidatorFieldRules("Card CVV", "cardCVV", "required|integer")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            PaymentMethod paymethod = new PaymentMethod();
            paymethod.loadRequest(request);
            
            if (customer != null)
                paymethod.setCustomerId(customer.getId());
            
            if (user != null)
                paymethod.setUserId(user.getId());
            
            if (dbPaymentMethod.addPaymentMethod(paymethod, customer))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.PaymentMethods, "Added", "Added paymethod", customer.getId());
                flash.add(Flash.MessageType.Success, "New paymethod added successfully");
                response.sendRedirect(URL.Absolute("paymethods/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to add new paymethod");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/paymethods/add.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to add paymethod", e);
            flash.add(Flash.MessageType.Error, "Unable to add paymethod");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doUpdatePaymentMethodPost(HttpServletRequest request, HttpServletResponse response, String paymethodStr)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (customer != null && user != null);
        
        try
        {
            if (!isLoggedIn)
            {
                flash.add(Flash.MessageType.Error, "You are not logged in");
                URL.GoBack(request, response);
                return;
            }

            Validator validator = new Validator(new ValidatorFieldRules[] {
                 new ValidatorFieldRules("Default Payment", "defaultPayment", "trim"),
                 new ValidatorFieldRules("Payment Type", "paymentType", "required|integer"), 
                 new ValidatorFieldRules("Card Name", "cardName", "required"),
                 new ValidatorFieldRules("Card Number", "cardNumber", "required|integer"),
                 new ValidatorFieldRules("Card CVV", "cardCVV", "required|integer")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            
            //Instead of creating a blank paymethod, fetch the existing paymethod from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int paymethodId = Integer.parseInt(paymethodStr);
            PaymentMethod paymethod = dbPaymentMethod.getPaymentMethodById(paymethodId);
            
            if (paymethod == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find paymethod to edit");
                URL.GoBack(request, response);
                return;
            }
            
             //Only staff can access records not related to them.
            if ((paymethod.getCustomerId() != user.getCustomerId()) && !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            //Now load the submitted form fields into the paymethod object
            //over the top of the DB data.
            paymethod.loadRequest(request);
            
            //Run update instead of add
            if (dbPaymentMethod.updatePaymentMethod(paymethod, customer))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.PaymentMethods, "Updated", "Updated paymethod", customer.getId());
                flash.add(Flash.MessageType.Success, "Existing paymethod updated successfully");
                response.sendRedirect(URL.Absolute("paymethods/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to update paymethod");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/paymethods/edit.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update paymethod", e);
            flash.add(Flash.MessageType.Error, "Unable to update paymethod");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeletePaymentMethodPost(HttpServletRequest request, HttpServletResponse response, String paymethodStr)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (customer != null && user != null);
        
        try
        {
            if (!isLoggedIn || !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            if (request.getParameter("doDelete") == null)
            {
                flash.add(Flash.MessageType.Error, "Delete request invalid");
                URL.GoBack(request, response);
                return;
            }
            
            IPaymentMethod dbPaymentMethod = new DBPaymentMethod();
            
            //Instead of creating a blank paymethod, fetch the existing paymethod from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int paymethodId = Integer.parseInt(paymethodStr);;
            
            //Run update instead of add
            if (dbPaymentMethod.deletePaymentMethodById(paymethodId))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.PaymentMethods, "Deleted", "Deleted paymethod "+paymethodStr, customer.getId());
                flash.add(Flash.MessageType.Success, "PaymentMethod deleted successfully");
                response.sendRedirect(URL.Absolute("paymethods/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to delete paymethod");
                response.sendRedirect(URL.Absolute("paymethods/list", request));
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to delete paymethod", e);
            flash.add(Flash.MessageType.Error, "Unable to delete paymethod");
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
