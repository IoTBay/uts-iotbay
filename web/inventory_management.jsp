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
<jsp:include page="header.jsp" />
<main role="main">
    <div style='text-align: center'> 
    <div class="btn-group" role="group" aria-label="Basic example">
        <a href="add_product.jsp" class="btn btn-primary btn-lg">Add Product</a>
        <a href="update_product.jsp" class="btn btn-primary btn-lg">Update Product</a>
        //<a href="add_product.jsp" class="btn btn-primary btn-lg">Delete Product</a>
        //<a href="add_product.jsp" class="btn btn-primary btn-lg">Show All Products</a>
    </div>
    </div>     
    </main>
<jsp:include page="footer.jsp" />
</html>


                    