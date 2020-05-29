<%-- 
    Document   : add_product
    Created on : 21/05/2020, 3:22:53 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.dao.DBManager"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
    <% 
        Product product = (Product)session.getAttribute("product");
    %>
   
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Management</title>
    </head>
  
<form method="post" action="<%= URL.Absolute("product/add_product", request) %>">
  <div id="col" class="col-md-12 align-self-center" style='padding-top: 100px' style='padding-bottom: 100px'>
    
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-3">
            <label for="inputname">Product Name</label>
            <input type="text" class="form-control" name="name" required="true">
        </div> 
        <div class="form-group col-md-2">
            <label for="inputprice">Product Price</label>
            <input type="text" class="form-control" name="price" required="true">
        </div>
        <div class="form-group col-md-2">
            <label for="inputinitialquantity">Initial Quantity</label>
            <input type="initialquantity" class="form-control" name="initialQuantity" required="true">
        </div>
    </div>
    
    <div class="form-row" style='padding-left: 450px'>  
        <div class="form-group col-md-5">    
            <label for="inputdescription">Description</label>
            <input type="text" class="form-control" name="description" required="true">
        </div>
        <div class="form-group col-md-2">
            <label for="inputname">Image Link</label>
            <input type="text" class="form-control" name="name" required="true">
        </div>    
   </div>
      
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-2">
            <label for="inputprice">Category</label>
            <input type="int" class="form-control" name="categoryid">
        </div>
        <div class="form-group col-md-2">
            <label for="inputcurrentquantity">Current Quantity</label>
            <input type="int" class="form-control" name="currentQuantity">
        </div>
    </div>
     
      <div class="form-row" style='padding-left: 450px'>
          <div class="form-group col-md-2">
            <label for="createddate">Created Date</label>
            <input type="date" class="form-control" name="createdDate">
          </div>
          <div class="form-group col-md-2">
            <label for="modifieddate">Modified Date</label>
            <input type="date" class="form-control" name="modifiedDate">
          </div>
     </div>  
      
    <div class="form-row" style='padding-left: 455px'>
        <input type="submit" class="btn btn-primary" value="submit">
        <a href="update_product.jsp"> CLICK HERE PLEASE </a>
    </div>
  </div>
</form>
<div style='padding-top: 100px'>
<jsp:include page="footer.jsp" />
</div>
</html>
