<%-- 
    Document   : add_product
    Created on : 21/05/2020, 3:22:53 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.dao.DBProduct"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
    <% 
        Product product = (Product)session.getAttribute("product");
        Flash flash = Flash.getInstance(session);
    %>
    
    <main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <%= flash.displayMessages() %>
    
<form method="post" action="<%= URL.Absolute("product/add", request) %>">
  <div id="col" class="col-md-12 align-self-center" style='padding-top: 100px' style='padding-bottom: 100px'>
    
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-5">
            <label for="inputname">Product Name</label>
            <input type="text" class="form-control" name="name">
        </div> 
        <div class="form-group col-md-3">
            <label for="inputprice">Product Price</label>
            <input type="text" class="form-control" name="price">
        </div>
    </div>
    
    <div class="form-row" style='padding-left: 450px'>  
        <div class="form-group col-md-7">    
            <label for="inputdescription">Description</label>
            <input type="text" class="form-control" name="description" required="true">
        </div>
        <div class="form-group col-md-5">
            <label for="inputname">Image Link</label>
            <input type="text" class="form-control" name="image">
        </div>    
   </div>
      
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-3">
            <label for="inputprice">Category</label>
            <input type="text" class="form-control" name="categoryId">
        </div>
        <div class="form-group col-md-3">
            <label for="inputinitialquantity">Initial Quantity</label>
            <input type="initialquantity" class="form-control" name="initialQuantity">
        </div>
        <div class="form-group col-md-3">
            <label for="inputcurrentquantity">Current Quantity</label>
            <input type="text" class="form-control" name="currentQuantity">
        </div>
    </div>
     
    <!--<div class="form-row" style='padding-left: 450px'>
          <div class="form-group col-md-2">
            <label for="createddate">Created Date</label>
            <input type="date" class="form-control" name="createdDate">
          </div>
          <div class="form-group col-md-2">
            <label for="modifieddate">Modified Date</label>
            <input type="date" class="form-control" name="modifiedDate">
        </div>-->
     </div> 
      
    <div class="form-row" style='padding-left: 470px'>
        <input type="submit" class="btn btn-primary" value="submit">
    </div>
  </div>
</form>
  
<div style='padding-top: 100px'>
    <jsp:include page="footer.jsp" />
</div>
</html>
