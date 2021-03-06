<%-- 
    Document   : update_product
    Created on : 26/05/2020, 3:26:17 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
   
    <%
        Product product = (Product)request.getAttribute("product");
        Flash flash = Flash.getInstance(session);
        Validator v = new Validator(session);
    %>

    <main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <%= flash.displayMessages() %> 
  
        <form method="post" action="<%= URL.Absolute("product/update/"+product.getId(), request) %>">
    <div id="col" class="col-md-12 align-self-center" style='padding-top: 100px' style='padding-bottom: 100px'>
    
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-5">
            <label for="inputname">Product Name</label>
            <input type="text" class="form-control" name="name" value="<%=v.repopulate("name",product.getName()) %>">
        </div> 
        <div class="form-group col-md-3">
            <label for="inputprice">Product Price</label>
            <input type="text" class="form-control" name="price" value="<%=v.repopulate("price",product.getPrice()) %>">
        </div>
    </div>
    
    <div class="form-row" style='padding-left: 450px'>  
        <div class="form-group col-md-7">    
            <label for="inputdescription">Description</label>
            <input type="text" class="form-control" name="description" value="<%=v.repopulate("description",product.getDescription()) %>">
        </div>
        <div class="form-group col-md-5">
            <label for="inputname">Image Link</label>
            <input type="text" class="form-control" name="name" value="<%=v.repopulate("name",product.getDescription()) %>">
        </div>    
   </div>
      
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-3">
            <label for="inputprice">Category</label>
            <input type="text" class="form-control" name="categoryId" value="<%=v.repopulate("categoryId",product.getCategoryId()) %>">
        </div>
        <div class="form-group col-md-3">
            <label for="inputinitialquantity">Initial Quantity</label>
            <input type="initialquantity" class="form-control" name="initialQuantity" value="<%=v.repopulate("initialQuantity",product.getInitialQuantity()) %>">
        </div>
        <div class="form-group col-md-3">
            <label for="inputcurrentquantity">Current Quantity</label>
            <input type="text" class="form-control" name="currentQuantity" value="<%=v.repopulate("currentQuantity",product.getCurrentQuantity()) %>">
        </div>
    </div>
    <div class="form-row" style='padding-left: 450px'>
        <input type="submit" class="btn btn-primary" value="submit">
        <div class="form-row" style='padding-left: 10px'>
            <a href="<%= URL.Absolute("product/list", request) %>" class="btn btn-secondary"> Return to product list </a>
        </div>  
    </div>
</div>
</form>           
<jsp:include page="footer.jsp" />
</html>


