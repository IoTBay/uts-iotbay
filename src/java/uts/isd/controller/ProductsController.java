/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.Customer;
import uts.isd.model.Product;
import uts.isd.model.User;
import uts.isd.model.dao.DBProduct;
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
            case "/delete":
                deleteProductGet(request, response);
                break;
            case "/add":
                addProductGet(request, response);
                break;
            case "/view":
                //doFindProductGet(request, response);
                break;
            case "/update":
                ProductUpdateGet(request, response);
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
            case "/delete":
                deleteProductPost(request, response);
                break;
            case "/add":
                addProductPost(request, response);
                break;
            case "/view":
                //
                break;
            case "/update":
                ProductUpdatePost(request, response);
                break;
        }
    }
   
    protected void doFindProductGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("");
        requestDispatcher.forward(request, response);
    }
    
    protected void doFindProductPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);
        
        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        
        try {
            if (isLoggedIn){ 
               //create a connection to the DB for the Products table
               IProduct dbProduct = new DBProduct();
               Product product = new Product();
               product.loadRequest(request, user);
               product.add(dbProduct);
               boolean added = product.add(dbProduct); 
              
               if(added)
                   flash.add(Flash.MessageType.Success, "New product "+product.getName()+" added successfully");
               else
                   flash.add(Flash.MessageType.Error, "Failed to add new product: "+product.getName());
               //Store objects in the session so we don't have to load from the database on every page
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
    
    protected void addProductGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/add_product.jsp");
        requestDispatcher.forward(request, response);
    } 
    
   protected void addProductPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);
       
        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        
        try {
            if (isLoggedIn){ 
               //create a connection to the DB for the Products table
               IProduct dbProduct = new DBProduct();
               Product product = new Product();
               product.loadRequest(request, user);
               //product.add(dbProduct);
               boolean added = product.add(dbProduct); 
              
               if(added)
                   flash.add(Flash.MessageType.Success, "New product "+product.getName()+" added successfully");
               else
                   flash.add(Flash.MessageType.Error, "Failed to add new product: "+product.getName());
               //Store objects in the session so we don't have to load from the database on every page
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
    
    protected void ProductUpdateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/update_product.jsp");
        requestDispatcher.forward(request, response);
        
    }
  
    protected void ProductUpdatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);
        
        //We need to make sure the product exists 
        Product product = (Product)session.getAttribute("product");
        boolean productExists = (product != null);
        

        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        Logging.logMessage("Update product");
        
        try
        {
            //Is Logged in and submitted form
            if (isLoggedIn && productExists)
            {
                //Create a connection to the DB for the products table
                IProduct dbProduct = new DBProduct();
                //Don't create a new product, use the product that fits the search criteria.
                //customer = new Customer();
                product.loadRequest(request);
                //boolean updated = (product.update(dbProduct));
                boolean updated = product.update(dbProduct);
                Logging.logMessage("SOMETHING IS HAPPENING HERE BUT I DON'T KNOW WHAT");

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
            requestDispatcher = request.getRequestDispatcher("/update_product.jsp");
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update product");
            return;
        }
    }
    
    protected void ProductViewGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view_product.jsp");
        requestDispatcher.forward(request, response);
        
    }  
    
    protected void AllProductsGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try { 
           IProduct dbProduct = new DBProduct();
           Iterable<Product> products = dbProduct.getAllProducts();
       
       } catch (Exception e) {
       
       }
    }
    
    protected void deleteProductGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/delete_product.jsp");
        requestDispatcher.forward(request, response);
    }
    
    protected void deleteProductPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (user != null && customer != null);
        
        //We need to make sure the product exists 
        Product product = (Product)session.getAttribute("product");
        boolean productExists = (product != null);
        

        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        Logging.logMessage("Delete product");
        
        try
        {
            //Is Logged in and submitted form
            if (isLoggedIn && productExists)
            {
                //Create a connection to the DB for the products table
                IProduct dbProduct = new DBProduct();
                product.loadRequest(request);
                boolean deleted = product.delete(dbProduct);

                if (deleted)
                    flash.add(Flash.MessageType.Success, "The product was successfully deleted!");
                else
                    flash.add(Flash.MessageType.Error, "Failed to delete product");
            }
            else
            {
                flash.add(Flash.MessageType.Error, "submission failed");
            }
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/update_product.jsp");
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

