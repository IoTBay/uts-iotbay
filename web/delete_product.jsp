<%-- 
    Document   : delete_product
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
    
<form method="post" action="<%= URL.Absolute("product/delete", request) %>">
  <div id="col" class="col-md-12 align-self-center" style='padding-top: 100px' style='padding-bottom: 100px'>
    
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-3">
            <label for="inputname">Product Name</label>
            <input type="text" class="form-control" name="name">
    </div>
    <div class="form-row" style='padding-left: 455px'>
        <input type="submit" class="btn btn-primary" value="submit">
    </div>
  </div>
</form>
  
<div style='padding-top: 100px'>
    <jsp:include page="footer.jsp" />
</div>
</html>
