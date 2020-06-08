/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.Customer;
import uts.isd.model.Order;
import uts.isd.model.User;
import uts.isd.model.dao.DBOrder;
import uts.isd.model.dao.IOrder;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 *
 * @author rhys
 */
public class StaffOrdersController extends HttpServlet {

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
            throws ServletException, IOException
    {
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
            doOrdersListGet(request, response);
            return;
        }
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "list":
                doOrdersListGet(request, response);
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
            throws ServletException, IOException
    {
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {       
            case "search":
                doOrdersSearchPost(request, response);
                break;
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

            if (customer == null || user == null || !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }

            IOrder dbOrder = new DBOrder();
            List<Order> orders = dbOrder.getAllOrders();
            for (Order o : orders)
            {
                Order.getFullOrder(o);
            }

            request.setAttribute("orders", orders);

            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/orders/list_staff.jsp");
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

            if (customer == null || user == null || !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
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
                
                List<Order> orders = dbOrder.searchOrdersByDate(start, end);
                for (Order o : orders)
                {
                    Order.getFullOrder(o);
                }
                flash.add(Flash.MessageType.Success, "You search returned "+orders.size()+" results");
                request.setAttribute("orders", orders);

                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/orders/list_staff.jsp");
                requestDispatcher.forward(request, response);
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to run doOrdersList", e);
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
