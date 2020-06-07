/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.Address;
import uts.isd.model.Customer;
import uts.isd.model.User;
import uts.isd.model.dao.DBAuditLogs;
import uts.isd.model.dao.DBAddress;
import uts.isd.model.dao.IAddress;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 * Addresses controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class AddressesController extends HttpServlet {


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
        
        //If no action is specified in the URI - e.g. /addresses then assume we're listing.
        if (request.getPathInfo() == null || request.getPathInfo().equals("/"))
        {
            doListAddressesGet(request, response, "");
            return;
        }
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        

        
        switch (segments[1])
        {
            case "list":
                //Pass segment[2] in as the customerId if one is given in this request.
                //This is so staff can view addresses for any customer with /adddresses/list/xxx
                doListAddressesGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "add":
                doAddAddressGet(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /addresses/edit/x
                doUpdateAddressGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /addresses/delete/x
                doDeleteAddressGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }

    protected void doListAddressesGet(HttpServletRequest request, HttpServletResponse response, String customerIdStr)
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
            
            int customerId = user.getCustomerId(); //Default to seeing current user's addresses.
            
             //Only staff can access records that don't belong to them.
            if (user.isAdmin() && !customerIdStr.isEmpty())
            {
                //Change to specified customerId if given
                customerId = Integer.parseInt(customerIdStr);
            }
            
            IAddress dbAddress = new DBAddress();
            List<Address> addresses = dbAddress.getAllAddressesByCustomerId(customerId);
            request.setAttribute("addresses", addresses);
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to list addresses");
            Logging.logMessage("Unable to get addresses");
            URL.GoBack(request, response);
            return;
        }
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view/addresses/list.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doAddAddressGet(HttpServletRequest request, HttpServletResponse response)
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
        requestDispatcher = request.getRequestDispatcher("/view/addresses/add.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doUpdateAddressGet(HttpServletRequest request, HttpServletResponse response, String addressStr)
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
            
            int addressId = Integer.parseInt(addressStr);
            
            IAddress dbAddress = new DBAddress();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            Address address = dbAddress.getAddressById(addressId);
            
            if (address == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find address to edit");
                URL.GoBack(request, response);
                return;
            }
            
            //Only staff can access records not related to them.
            if ((address.getCustomerId() != user.getCustomerId()) && !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("address", address);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/addresses/edit.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to edit address");
            Logging.logMessage("Unable to edit address");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeleteAddressGet(HttpServletRequest request, HttpServletResponse response, String addressStr)
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
            
            int addressId = Integer.parseInt(addressStr);
            
            IAddress dbAddress = new DBAddress();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            Address address = dbAddress.getAddressById(addressId);
            
            if (address == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find address to delete");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("address", address);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/addresses/delete.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to delete address");
            Logging.logMessage("Unable to delete address");
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
                doAddAddressPost(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /addresses/edit/x
                doUpdateAddressPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /addresses/delete/x
                doDeleteAddressPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }
    
    protected void doAddAddressPost(HttpServletRequest request, HttpServletResponse response)
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
                 new ValidatorFieldRules("Address 2", "addressPrefix1", "trim"),
                 new ValidatorFieldRules("Street Number", "streetNumber", "required|shorterthan[11]"), 
                 new ValidatorFieldRules("Street Name", "streetName", "required|shorterthan[61]"),
                 new ValidatorFieldRules("Street Type", "streetType", "required|shorterthan[21]"),
                 new ValidatorFieldRules("Suburb", "suburb", "required|shorterthan[61]"),
                 new ValidatorFieldRules("State", "state", "required|shorterthan[31]"),
                 new ValidatorFieldRules("Post Code", "postcode", "required|shorterthan[5]"),
                 new ValidatorFieldRules("Country", "country", "required|shorterthan[31]"),
                 new ValidatorFieldRules("Default Shipping Address", "defaultShippingAddress", "trim"),
                 new ValidatorFieldRules("Default Billing Address", "defaultBillingAddress", "trim")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IAddress dbAddress = new DBAddress();
            Address address = new Address();
            address.loadRequest(request);
            
            if (customer != null)
                address.setCustomerId(customer.getId());
            
            if (user != null)
                address.setUserId(user.getId());
            
            if (dbAddress.addAddress(address, customer))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Addresses, "Added", "Added address", customer.getId());
                flash.add(Flash.MessageType.Success, "New address added successfully");
                response.sendRedirect(URL.Absolute("addresses/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to add new address");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/addresses/add.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to add address", e);
            flash.add(Flash.MessageType.Error, "Unable to add address");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doUpdateAddressPost(HttpServletRequest request, HttpServletResponse response, String addressStr)
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
                 new ValidatorFieldRules("Address 2", "addressPrefix1", "trim"),
                 new ValidatorFieldRules("Street Number", "streetNumber", "required|shorterthan[11]"), 
                 new ValidatorFieldRules("Street Name", "streetName", "required|shorterthan[61]"),
                 new ValidatorFieldRules("Street Type", "streetType", "required|shorterthan[21]"),
                 new ValidatorFieldRules("Suburb", "suburb", "required|shorterthan[61]"),
                 new ValidatorFieldRules("State", "state", "required|shorterthan[31]"),
                 new ValidatorFieldRules("Post Code", "postcode", "required|shorterthan[5]"),
                 new ValidatorFieldRules("Country", "country", "required|shorterthan[31]"),
                 new ValidatorFieldRules("Default Shipping Address", "defaultShippingAddress", "trim"),
                 new ValidatorFieldRules("Default Billing Address", "defaultBillingAddress", "trim")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            IAddress dbAddress = new DBAddress();
            
            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int addressId = Integer.parseInt(addressStr);
            Address address = dbAddress.getAddressById(addressId);
            
            if (address == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find address to edit");
                URL.GoBack(request, response);
                return;
            }
            
             //Only staff can access records not related to them.
            if ((address.getCustomerId() != user.getCustomerId()) && !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }
            
            //Now load the submitted form fields into the address object
            //over the top of the DB data.
            address.loadRequest(request);
            
            //Run update instead of add
            if (dbAddress.updateAddress(address, customer))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Addresses, "Updated", "Updated address", customer.getId());
                flash.add(Flash.MessageType.Success, "Existing address updated successfully");
                response.sendRedirect(URL.Absolute("addresses/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to update address");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/addresses/edit.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update address", e);
            flash.add(Flash.MessageType.Error, "Unable to update address");
            URL.GoBack(request, response);
            return;
        }
    }
    
    
    protected void doDeleteAddressPost(HttpServletRequest request, HttpServletResponse response, String addressStr)
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
            
            if (request.getParameter("doDelete") == null)
            {
                flash.add(Flash.MessageType.Error, "Delete request invalid");
                URL.GoBack(request, response);
                return;
            }
            
            IAddress dbAddress = new DBAddress();
            
            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int addressId = Integer.parseInt(addressStr);
            
            //Run update instead of add
            if (dbAddress.deleteAddressById(addressId))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Addresses, "Deleted", "Deleted address "+addressStr, customer.getId());
                flash.add(Flash.MessageType.Success, "Address deleted successfully");
                response.sendRedirect(URL.Absolute("addresses/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to delete address");
                response.sendRedirect(URL.Absolute("addresses/list", request));
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to delete address", e);
            flash.add(Flash.MessageType.Error, "Unable to delete address");
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
