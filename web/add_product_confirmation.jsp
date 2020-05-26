<%-- 
    Document   : add_product_confirmation
    Created on : 26/05/2020, 1:28:21 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>product added</title>
    </head>
    <body>
        <%
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String initialQuantity = request.getParameter("initialQuantity");
            String price = request.getParameter("price");
            String id = request.getParameter("id");
        %>
        <h1>Hello World!</h1>
        <p>product name is : <%= name %></p>
        <p>product description is : <%= description %></p>
        <p>product initial Quantity is : <%= initialQuantity %></p>
        <p>product price is : <%= price %></p>
        <p>product is is : <%= id %></p>
        <%
            Product product = new Product(name,description,initialQuantity,price,id);
            session.setAttribute("product", product);
        %>
    </body>
    <a href="view_product.jsp">CLICK HERE</a>
</html>
