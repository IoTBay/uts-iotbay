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
import uts.isd.model.ProductCategory;
import uts.isd.model.Customer;
import uts.isd.model.Product;
import uts.isd.model.User;
import uts.isd.model.dao.DBCategory;
import uts.isd.model.dao.DBProduct;
import uts.isd.model.dao.ICategory;
import uts.isd.model.dao.IProduct;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 * Categories controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class CategoriesController extends HttpServlet {


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
            doListCategoriesGet(request, response);
            return;
        }
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "list":
                doListCategoriesGet(request, response);
                break;
                
            case "view":
                doViewCategoryGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                                
        }
    }

    protected void doListCategoriesGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {            
            ICategory dbCategory = new DBCategory();
            List<ProductCategory> categories = dbCategory.getAllCategories();
            request.setAttribute("categories", categories);
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to list categories");
            Logging.logMessage("Unable to get categories");
            URL.GoBack(request, response);
            return;
        }
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view/categories/list_customer.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doViewCategoryGet(HttpServletRequest request, HttpServletResponse response, String categoryStr)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {   
            int categoryId = Integer.parseInt(categoryStr);
            
            ICategory dbCategory = new DBCategory();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            ProductCategory category = dbCategory.getCategoryById(categoryId);
            
            if (category == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find category to view");
                URL.GoBack(request, response);
                return;
            }
            
            IProduct dbProduct = new DBProduct();
            List<Product> products = dbProduct.getProductsByCategoryId(category.getId());
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("category", category);
            request.setAttribute("products", products);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/categories/view_customer.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to view category");
            Logging.logMessage("Unable to view category");
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
