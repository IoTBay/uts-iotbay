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
    
    <h1> you sure you want to delete? </h1>
        
    <form method="post" action="<%= URL.Absolute("product/delete/", request) %>">
        <input type="submit" name="doDelete" value="Delete" class="btn btn-primary">
    </form>

</html>
