<%-- 
    Document   : view_product
    Created on : 26/05/2020, 5:01:33 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Product</title>
    </head>
    <body>
        <%
            Product product = (Product) session.getAttribute("product");
        %>
        <h1>Product List !</h1>
        <table id="product table">
            <thead><th>name</th><th>Description</th><th>Initial Quantity</th><th>price</th><th>id</th></thead>
            <tr><td>${product.name}</td><td>${product.description}</td><td>${product.initialQuantity}</td><td>${product.price}</td><td>${product.id}</td></tr>
    </table>
    <a href="update_product.jsp"> update </a>
</body>
</html>
