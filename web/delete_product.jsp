<%-- 
    Document   : delete_product
    Created on : 04/06/2020, 8:13:21 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.validation.Validator"%>
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
   
 
 <div class="container" style="text-align: center"> 
    <h1 style="padding-top: 30px"> Are you sure you want to delete? </h1>
    <div class="form-row">       
       <form method="post" action="<%= URL.Absolute("product/delete/"+product.getId(), request) %>">
           <input type="submit" name="doDelete" class="btn btn-success" value="Yes">
       </form>
       <div style="padding-left: 10px">
           <a href="<%= URL.Absolute("product/list", request) %>" class="btn btn-danger"> No, Return to product list </a>
       </div> 
    </div>
 </div>
<jsp:include page="footer.jsp" />       
</html>
