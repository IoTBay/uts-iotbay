<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  //We need to figure out if the user is logging out now, or not.
  //then invalidate the session BEFORE including the header, so it shows correctly.
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  ProductCategory category = (ProductCategory)request.getAttribute("category");
  List<Product> products = (List<Product>)request.getAttribute("products");
%>

<jsp:include page="../../header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <h1><%= category.getName() %></h1>
        <% if (products == null || products.size() == 0) { %>
        <p>There are no categories yet.</p>
        <% } else { %>
        <p>Take a look at some of the products in this category. <%= category.getDescription() %></p>
        <div class="row row-cols-1 row-cols-md-3">
            <% for (Product product : products) { %>
            <div class="col mb-4">
                <div class="card" style="width: 18rem;">
                  <% if (product.getImage() == null || product.getImage().isEmpty()) { %>
                    <svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Default Image"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96" /><text x="50%" y="50%" fill="#dee2e6" dy=".3em">Default Image</text></svg>
                  <% } else { %>
                    <img src="<%= URL.Image("products/"+product.getImage(), request) %>" class="card-img-top img-grayscale" alt="<%= product.getName() %>">
                    <!-- https://codepen.io/philcheng/pen/YWyYwG -->
                    <div class="btn-hover"><a href="<%= URL.Absolute("products/view/"+product.getId(), request) %>" class="btn btn-dark">View Product</a></div>
                  <% } %>
                  <div class="card-body">
                      <h5 class="card-title"><%= product.getName() %></h5>
                    <p class="card-text"><%= product.getDescription() %></p>
                    <a href="<%= URL.Absolute("product/view/"+product.getId(), request) %>" class="btn btn-primary">View Product</a>
                  </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="../../footer.jsp" />
</html>