<%-- 
    Document   : inventory_management
    Created on : 21/05/2020, 6:48:00 AM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div style='padding-bottom: 100px'>
        <jsp:include page="header.jsp" />
    </div>
    
<main role="main">
    <div style='text-align: center'>
        <h1> Inventory Management </h1>
    </div>
    <div id="col" class="col-md-12 align-self-center" style='padding-top: 100px' style='padding-bottom: 100px'>
        <div style='text-align: center'> 
        <div class="btn-group" role="group" aria-label="Basic example">
            <a href="add_product.jsp" class="btn btn-primary btn-lg">Add Product</a>
            <a href="update_product.jsp" class="btn btn-primary btn-lg">Update Product</a>
            <a href="" class="btn btn-primary btn-lg">Delete Product</a>
            <a href="add_product.jsp" class="btn btn-primary btn-lg">View Products</a>
        </div>
        </div> 
    </div>
    </main>
<div style='padding-top: 100px'>
<jsp:include page="footer.jsp" />
<div>
</html>


                    