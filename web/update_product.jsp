<%-- 
    Document   : update_product
    Created on : 26/05/2020, 3:26:17 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
        <%
            Product product = (Product)session.getAttribute("product");
            String updated = request.getParameter("updated");
        %>
<body>   
    <h1>Edit product Information<span><%= (updated !=null) ? " Update was successful":""%></span></h1>
    <form action="update_product.jsp" method="post">
    <div>
    <label>Product Name</label>
    <input type="text" class="form-control" name="name" required="true" value="${product.name}">
    <label>Description</label>
    <input type="text" class="form-control" name="description" required="true" value="${product.description}">
    <label> Initial Quantity </label>
    <input type="initialQuantity" class="form-control" name="quantity" required="true" value="${product.initialQuantity}">
    <label>Price</label>
    <input type="text" class="form-control" name="price" value="${product.price}">
    <label> Id </label>
    <input type="float" name="id" value="${product.id}">
    <input type="submit" value="Update">
    <input type="hidden" name="updated" value="updated">
    </div>
    </form>
            <%
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String quantity = request.getParameter("quantity");
                String price = request.getParameter("price");
                String id = request.getParameter("id");
                product = new Product(name,description,quantity,price,id);
                session.setAttribute("product", product);
            %>
            <a href="view_product.jsp">GO BACK</a>      
</body>
</html>
