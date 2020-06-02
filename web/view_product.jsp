<%-- 
    Document   : view_product
    Created on : 26/05/2020, 5:01:33 PM
    Author     : C_fin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="java.lang.Iterable"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />   
<% List<Product> products = (List<Product>)request.getAttribute("products");%> 
<html>
<form method="post" action="<%= URL.Absolute("product/view", request) %>">   
    &nbsp;&nbsp;&nbsp;
    <h2>Inventory Management
    <a href="<%= URL.Absolute("product/add", request) %>">Add New Product</a>
    &nbsp;&nbsp;&nbsp;
    <a href="<%= URL.Absolute("product/view", request) %>">View All Products</a>
    </h2>
    <table class="table table-hover table-striped" style='padding-top: 100px;'>
        <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Category</th>
                <th>Current Quantity</th>
                
            </tr>
        </thead>
        <tbody>
           <c:forEach var="product" items="${listProduct}">
                <tr>
                    <td><c:out value="${product.id}" /></td>
                    <td><c:out value="${product.name}" /></td>
                    <td><c:out value="${product.price}" /></td>
                    <td><c:out value="${product.description}" /></td>
                    <td> 
                        <a href="/edit?id=<c:out value='${product.id}' />">Update</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/delete?id=<c:out value='${product.id}' />">Delete</a>   
                    </td
                </tr>
             </c:forEach>
            </tbody>
        </table>
    </form>

<div style='padding-top: 100px'>
<jsp:include page="footer.jsp" />
</div>
</html>


