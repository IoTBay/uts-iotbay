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
import uts.isd.model.User;
import uts.isd.model.dao.DBCategory;
import uts.isd.model.dao.ICategory;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

/**
 * Staff Categories controller
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-04
 */
public class StaffCategoriesController extends HttpServlet {


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
                
            case "add":
                doAddCategoryGet(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /addresses/edit/x
                doUpdateCategoryGet(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /addresses/delete/x
                doDeleteCategoryGet(request, response, (segments.length == 3 ? segments[2] : ""));
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
        requestDispatcher = request.getRequestDispatcher("/view/categories/list.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doViewCategoryGet(HttpServletRequest request, HttpServletResponse response, String categoryStr)
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
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("category", category);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/categories/view.jsp");
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
    
    protected void doAddCategoryGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        User user = (User)request.getSession().getAttribute("user");
        
        if (user == null || !user.isAdmin())
        {
            flash.add(Flash.MessageType.Error, "Access denied");
            URL.GoBack(request, response);
            return;
        }
            
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view/categories/add.jsp");
        requestDispatcher.forward(request, response); 
    }
    
    protected void doUpdateCategoryGet(HttpServletRequest request, HttpServletResponse response, String categoryStr)
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
            
            int categoryId = Integer.parseInt(categoryStr);
            
            ICategory dbCategory = new DBCategory();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            ProductCategory category = dbCategory.getCategoryById(categoryId);
            
            if (category == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find category to edit");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("category", category);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/categories/edit.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to edit category");
            Logging.logMessage("Unable to edit category");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeleteCategoryGet(HttpServletRequest request, HttpServletResponse response, String categoryStr)
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
            
            int categoryId = Integer.parseInt(categoryStr);
            
            ICategory dbCategory = new DBCategory();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            ProductCategory category = dbCategory.getCategoryById(categoryId);
            
            if (category == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find category to delete");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("category", category);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/categories/delete.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to delete category");
            Logging.logMessage("Unable to delete category");
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
                doAddCategoryPost(request, response);
                break;
                
            case "edit":
                //Segments[2] is the ID to edit in /addresses/edit/x
                doUpdateCategoryPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
            case "delete":
                //Segments[2] is the ID to delete in /addresses/delete/x
                doDeleteCategoryPost(request, response, (segments.length == 3 ? segments[2] : ""));
                break;
                
        }
    }
    
    protected void doAddCategoryPost(HttpServletRequest request, HttpServletResponse response)
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

            Validator validator = new Validator(new ValidatorFieldRules[] {
                 new ValidatorFieldRules("Name", "name", "trim|required"),
                 new ValidatorFieldRules("Description", "description", "required"), 
                 new ValidatorFieldRules("Image", "image", "trim")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            ICategory dbCategory = new DBCategory();
            ProductCategory category = new ProductCategory();
            category.loadRequest(request);
            
            if (dbCategory.addCategory(category, customer))
            {
                flash.add(Flash.MessageType.Success, "New category added successfully");
                response.sendRedirect(URL.Absolute("categories/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to add new category");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/categories/add.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to add category", e);
            flash.add(Flash.MessageType.Error, "Unable to add category");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doUpdateCategoryPost(HttpServletRequest request, HttpServletResponse response, String categoryStr)
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

            Validator validator = new Validator(new ValidatorFieldRules[] {
                 new ValidatorFieldRules("Name", "name", "trim|required"),
                 new ValidatorFieldRules("Description", "description", "required"), 
                 new ValidatorFieldRules("Image", "image", "trim")
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
            
            ICategory dbCategory = new DBCategory();
            
            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int categoryId = Integer.parseInt(categoryStr);
            ProductCategory category = dbCategory.getCategoryById(categoryId);
            
            if (category == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find category to edit");
                URL.GoBack(request, response);
                return;
            }
            //Now load the submitted form fields into the address object
            //over the top of the DB data.
            category.loadRequest(request);
            
            //Run update instead of add
            if (dbCategory.updateCategory(category, customer))
            {
                flash.add(Flash.MessageType.Success, "Existing category updated successfully");
                response.sendRedirect(URL.Absolute("categories/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to update category");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/view/categories/edit.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update category", e);
            flash.add(Flash.MessageType.Error, "Unable to update category");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void doDeleteCategoryPost(HttpServletRequest request, HttpServletResponse response, String categoryStr)
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
            
            ICategory dbCategory = new DBCategory();
            
            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int categoryId = Integer.parseInt(categoryStr);;
            
            //Run update instead of add
            if (dbCategory.deleteCategoryById(categoryId))
            {
                flash.add(Flash.MessageType.Success, "Category deleted successfully");
                response.sendRedirect(URL.Absolute("categories/list", request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to delete category");
                response.sendRedirect(URL.Absolute("categories/list", request));
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to delete category", e);
            flash.add(Flash.MessageType.Error, "Unable to delete category");
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
