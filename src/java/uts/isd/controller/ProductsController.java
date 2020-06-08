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
import uts.isd.model.Customer;
import uts.isd.model.Product;
import uts.isd.model.User;
import uts.isd.model.dao.DBAuditLogs;
import uts.isd.model.dao.DBCategory;
import uts.isd.model.dao.DBProduct;
import uts.isd.model.dao.ICategory;
import uts.isd.model.dao.IProduct;
import uts.isd.util.Flash;
import uts.isd.util.Logging;
import uts.isd.util.URL;
import uts.isd.validation.Validator;
import uts.isd.validation.ValidatorFieldRules;

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
        
        if (request.getPathInfo() == null || request.getPathInfo().equals("/"))
        {
            viewProductsGet(request,response);
            return; 
        }
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "delete":
                deleteProductGet(request, response, segments[2]);
                break;
            case "add":
                addProductGet(request, response);
                break;
            case "list":
                viewProductsGet(request, response);
                break;
            case "update":
                doUpdateProductGet(request, response, segments[2]);
                break;
            case "view":
                viewProductGet(request, response, segments[2]);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        Logging.logMessage("** Path Info is: "+request.getPathInfo());
        String[] segments = request.getPathInfo().split("/");
        
        switch (segments[1])
        {
            case "delete":
                deleteProductPost(request, response, segments[2]);
                break;
            case "add":
                addProductPost(request, response);
                break;
            case "update":
                doUpdateProductPost(request, response, segments[2]);
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
               product.loadRequest(request);
               boolean added = product.add(dbProduct, customer); 
              
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
        
        Validator validator = new Validator(new ValidatorFieldRules[] {
                 //new ValidatorFieldRules("Product Price", "price", "ValidateDouble"),
                 new ValidatorFieldRules("Product Name", "name", "required|shorterthan[61]"),
                 new ValidatorFieldRules("Product Description", "description", "required|shorterthan[61]"),
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }
        
        try {
            if (isLoggedIn){ 
               //create a connection to the DB for the Products table
               IProduct dbProduct = new DBProduct();
               Product product = new Product();
               product.loadRequest(request);
               //product.add(dbProduct);

               boolean added = product.add(dbProduct, customer); 
        
               if(added)
               {   
                   flash.add(Flash.MessageType.Success, "New product "+product.getName()+" added successfully");
                   DBAuditLogs.addEntry(DBAuditLogs.Entity.Products, "Added", "Added product ", customer.getId());
               }
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
    
    protected void doUpdateProductGet(HttpServletRequest request, HttpServletResponse response, String productStr)
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

            int productId = Integer.parseInt(productStr);

            IProduct dbProduct = new DBProduct();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            Product product = dbProduct.getProductById(productId);
           
            if (product == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find product");
                URL.GoBack(request, response);
                return;
            }   
            
            //Set the product object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("product", product);

            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/update_product.jsp");
            requestDispatcher.forward(request, response); 
        } 
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to edit product");
            Logging.logMessage("Unable to edit product");
            URL.GoBack(request, response);
            return;
        }
    }
  
    protected void doUpdateProductPost(HttpServletRequest request, HttpServletResponse response, String productStr)
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
                 //new ValidatorFieldRules("Product Price", "price", "ValidateDouble"),
                 new ValidatorFieldRules("Product Name", "name", "required|shorterthan[61]"),
                 new ValidatorFieldRules("Product Description", "description", "required|shorterthan[61]"),
            });

            if (!validator.validate(request))
            {
                URL.GoBack(request, response);
                return;
            }

            IProduct dbProduct = new DBProduct();

            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int productId = Integer.parseInt(productStr);
            Product product = dbProduct.getProductById(productId);

            if (product == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find product to edit");
                URL.GoBack(request, response);
                return;
            }
            
            //Updating products is only available to staff
            if ((product.getId() != user.getCustomerId()) && !user.isAdmin())
            {
                flash.add(Flash.MessageType.Error, "Access denied");
                URL.GoBack(request, response);
                return;
            }

            //Now load the submitted form fields into the address object
            //over the top of the DB data.
            product.loadRequest(request);

            //Run update
            if (dbProduct.updateProduct(product, customer))
            {
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Products, "Updated", "Updated product", customer.getId());
                flash.add(Flash.MessageType.Success, "Existing product updated successfully");
                response.sendRedirect(URL.Absolute("product/update/" +product.getId(), request));
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to update products");
                RequestDispatcher requestDispatcher; 
                requestDispatcher = request.getRequestDispatcher("/update_product.jsp");
                requestDispatcher.forward(request, response); 
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update product", e);
            flash.add(Flash.MessageType.Error, "Unable to update product");
            URL.GoBack(request, response);
            return;
        }
    }
    
    protected void viewProductsGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Flash flash = Flash.getInstance(request.getSession());
        try
        {
            IProduct dbProduct = new DBProduct();
            List<Product> products = dbProduct.getAllProducts();
            request.setAttribute("products", products);
        } catch (Exception e) {
            Logging.logMessage("Unable to view product");
            return;
        }
        
        RequestDispatcher requestDispatcher; 
        requestDispatcher = request.getRequestDispatcher("/view_product.jsp");
        requestDispatcher.forward(request, response);
    } 
    
    /**
     * This function is for the customer view for a single product
     * 
     * @param request
     * @param response
     * @param productStr The productID to view, taken from the URI
     * @throws ServletException
     * @throws IOException 
     */
    protected void viewProductGet(HttpServletRequest request, HttpServletResponse response, String productStr) throws ServletException, IOException {

        int productId = 0;
        HttpSession session = request.getSession();
        Flash flash = Flash.getInstance(session);
        Product product;
        
        try {
            productId = Integer.parseInt(productStr);
            IProduct dbProduct = new DBProduct();
            product = dbProduct.getProductById(productId);
            
            ICategory dbCategory = new DBCategory();
            product.setCategory(dbCategory.getCategoryById(product.getCategoryId()));
            
            if (product == null)
            {
                flash.add(Flash.MessageType.Error, "The product was not found");
                URL.GoBack(request, response);
                return;
            }
            
            request.setAttribute("product", product);
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/view/products/view.jsp");
            requestDispatcher.forward(request, response);
            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to view product ID "+productId, e);
        }
        
    }
    
    protected void deleteProductGet(HttpServletRequest request, HttpServletResponse response, String productStr)
            throws ServletException, IOException {
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
            int productId = Integer.parseInt(productStr);
            
            IProduct dbProduct = new DBProduct();
            //Get the existing address from the DB so we can pass it to the view to pre-load values.
            Product product = dbProduct.getProductById(productId);
            
            if (product == null)
            {
                flash.add(Flash.MessageType.Error, "Unable to find product to delete");
                URL.GoBack(request, response);
                return;
            }
            
            //Set the address object on the request so it can be used by the view for this request only.
            //i.e. Don't use the session because this is for a single page request.
            request.setAttribute("product", product);
            
            RequestDispatcher requestDispatcher; 
            requestDispatcher = request.getRequestDispatcher("/delete_product.jsp");
            requestDispatcher.forward(request, response); 
        }
        catch (Exception e) 
        {
            flash.add(Flash.MessageType.Error, "Unable to delete product");
            Logging.logMessage("Unable to delete product");
            URL.GoBack(request, response);
            return;
        }
    }
  }
        
    protected void deleteProductPost(HttpServletRequest request, HttpServletResponse response, String productStr)
            throws ServletException, IOException {
  
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Customer customer = (Customer)session.getAttribute("customer");
        boolean isLoggedIn = (customer != null && user != null);

        //Setup flash messages
        Flash flash = Flash.getInstance(session);
        int status = 0;
        Logging.logMessage("Delete product");
        
        try
        {
            //Is Logged in and submitted form
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
            
            IProduct dbProduct = new DBProduct();
            
            //Instead of creating a blank address, fetch the existing address from the DB
            //so we have a fully populated oobject and don't risk losing data.
            int productId = Integer.parseInt(productStr);
            
            //Run update instead of add
             if (dbProduct.deleteProductById(productId))
            {
                
                DBAuditLogs.addEntry(DBAuditLogs.Entity.Products, "Deleted", "Deleted product ", customer.getId());
                flash.add(Flash.MessageType.Success, "Product deleted successfully");
                response.sendRedirect(URL.Absolute("product/list", request));
                
                return;
            }
            else
            {
                flash.add(Flash.MessageType.Error, "Failed to delete product");
                response.sendRedirect(URL.Absolute("product/list.jsp", request));
            }
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to delete product", e);
            flash.add(Flash.MessageType.Error, "Unable to delete product");
            URL.GoBack(request, response);
            return;
        }
    }
 
  @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
  
}

