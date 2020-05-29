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
import javax.servlet.http.HttpSession;
import uts.isd.model.Product;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.IProduct;
import uts.isd.util.Flash;
import uts.isd.util.Logging;

public class ProductsController extends HttpServlet {

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
            case "/inventory_management":
                //
                break;
            case "/add_product":
                addProductGet(request, response);
                break;
            case "/view_product":
                //doFindProductGet(request, response);
                break;
            case "/update_product":
                //ProductUpdateGet(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        switch (request.getPathInfo())
        {
            case "/inventory_management":
                //
                break;
            case "/add_product":
                addProductPost(request, response);
                break;
            case "/view_product":
                //
                break;
            case "/update_product":
                //ProductUpdatePost(request, response);
                break;
        }
    }
   
    protected void doFindProductGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("");
        requestDispatcher.forward(request, response);
    }
    
    protected void doFindProductPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            //Create a connection to the DB for Products table
            IProduct dbManager = new DBManager();
            Product product = dbManager.authenticateProduct(request.getParameter("name"));
            // flash object for success/fail message
            Flash flash = Flash.getInstance(session);
            // If product does not exist in the database, it can be added
            if (product == null) {
                session.setAttribute("product",product);
               //Setup flash messages
                flash.add(Flash.MessageType.Success, "Product added succesfully");   
            } 
        } catch (Exception e) {
            Logging.logMessage("error", e);
        }
    }
    
    protected void addProductGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/add_product.jsp");
        requestDispatcher.forward(request, response);
    } 
    
   protected void addProductPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Product product = (Product)session.getAttribute("product");
        boolean isProduct = (product != null);
        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        
        try {
            if (isProduct){ //I think there is a problem here
               //create a connection to the DB for the Products table
               IProduct dbProduct = new DBManager();
               product = new Product();
               product.add(dbProduct);
               boolean added = product.add(dbProduct); 
              
               if(added)
                   flash.add(Flash.MessageType.Success, "New product "+product.getName()+" added successfully");
               else
                   flash.add(Flash.MessageType.Error, "Failed to add new product: "+product.getName());
               //Store objects in the session so we don't have to load from the database on every page
               session.setAttribute("product", product);
            }
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/add_product.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e){
            Logging.logMessage("Unable to register new product", e);
            return;
        }
    } 
    
    protected void ProductViewGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view_product.jsp");
        requestDispatcher.forward(request, response);
        
    }  
  
    protected void ProductUpdateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/edit_product.jsp");
        requestDispatcher.forward(request, response);
        
    }
  
    protected void ProductUpdatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        //We need to make sure the product exists 
        Product product = (Product)session.getAttribute("product");
        boolean productExists = (product != null);

        //Setup flash messages
        Flash flash = Flash.getInstance(session);

        Logging.logMessage("Update product");
        
        try
        {
            //Is Logged in and submitted form
            if (productExists)
            {
                //Create a connection to the DB for the customers table
                IProduct dbManager = new DBManager();
                //Don't create a new customer, use the current logged in customer.
                //customer = new Customer();
                product.loadRequest(request);
                
                boolean updated = (product.update(dbManager));
                Logging.logMessage("Updated product");

                if (updated)
                    flash.add(Flash.MessageType.Success, "The product was successfully updated!");
                else
                    flash.add(Flash.MessageType.Error, "Failed to update product");
            }
            else
            {
                flash.add(Flash.MessageType.Error, "submission failed");
            }
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view_product.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update product");
            return;
        }
    }
  
  @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
  
}
