package uts.isd.controller;

    import java.io.IOException;
    import java.sql.SQLException;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import uts.isd.model.Product;
    import uts.isd.model.dao.DBManager;
     
public class AddProductServlet extends HttpServlet {
     @Override   
     protected void doPost(HttpServletRequest request, HttpServletResponse response)       
                throws ServletException, IOException { 
            HttpSession session = request.getSession();
            Validator validator = new Validator();
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            DBManager manager = (DBManager) session.getAttribute("manager");
            Product product = null;
            validator.clear(session);
            
            if(!validator.validateId(id)){
               session.setAttribute("idErr", "Error: Id format incorrect");
               request.getRequestDispatcher("add_product.jsp").include(request, response);
            } else if (!validator.validateName(name)) {
                session.setAttribute("nameErr", "Error: name format incorrect");
                request.getRequestDispatcher("add_product.jsp").include(request, response);
            } else {
                try{
                    product = manager.findProduct(id, name);
                    if (product !=null){
                        session.setAttribute("", manager);
                        request.getRequestDispatcher("add_product.jsp").include(request, response);
                    } else {
                        session.setAttribute(id, manager);
                        request.getRequestDispatcher("add_product.jsp").include(request, response);
                    }
                } catch (SQLException | NullPointerException ex){
                    System.out.println(ex.getMessage() == null ? "Product does not exist" : "Welcome");
                }
            }
        }    
            
                
                
             
      }