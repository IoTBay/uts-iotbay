<%-- 
    Document   : view_product
    Created on : 26/05/2020, 5:01:33 PM
    Author     : C_fin
--%>

<%@page import="java.util.List"%>
<%@page import="java.lang.Iterable"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
<form method="post" action="<%= URL.Absolute("product/view", request) %>">
    <%
    List<Product> products = (List<Product>)request.getAttribute("products");
    %>
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Current Quantity</th>
            </tr>
        </thead>
        <tbody>
            <% for (Product p: products) { %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getDescription() %></td>
                    <td><%= p.getPriceFormatted() %></td>
                    <td><%= p.getCurrentQuantity() %></td>
                    <td>
                        <a href="<%= URL.Absolute("product/update/" +p.getId(), request) %>">Edit</a>
                        <a href="<%= URL.Absolute("product/delete/" +p.getId(), request) %>">Delete</a>
                    </td>
                </tr>
            <% } //Close for loop %>
            </tbody>
        </table>
    </form>
<div class="form-row" style='padding-left: 870px'>
        <a href="<%= URL.Absolute("product/add", request) %>" class="btn btn-primary"> Add a new product </a>
</div>
<jsp:include page="footer.jsp" />
</html>
