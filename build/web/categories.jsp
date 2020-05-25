<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  //We need to figure out if the user is logging out now, or not.
  //then invalidate the session BEFORE including the header, so it shows correctly.
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  List<Product> products = (List<Product>)session.getAttribute("products");
  List<ProductCategory> categories = (List<ProductCategory>)session.getAttribute("categories");
  //Store for later
  boolean isLoggedIn = (user != null);
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <h1>Categories</h1>
        <% if (categories == null || categories.size() == 0) { %>
        <p>There are no categories yet.</p>
        <% } else { %>
        <p>Take a look at some of the categories below.</p>
        <div class="row row-cols-1 row-cols-md-3">
            <% for (ProductCategory cat : categories) { %>
            <div class="col mb-4">
                <div class="card" style="width: 18rem;">
                  <% if (cat.getImage().isEmpty()) { %>
                    <svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Default Image"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96" /><text x="50%" y="50%" fill="#dee2e6" dy=".3em">Default Image</text></svg>
                  <% } else { %>
                    <img src="<%= cat.getImage() %>" class="card-img-top img-grayscale" alt="<%= cat.getName() %>">
                    <!-- https://codepen.io/philcheng/pen/YWyYwG -->
                    <div class="btn-hover"><a href="view_category.jsp?id=<%= cat.getId() %>" class="btn btn-dark">View Category</a></div>
                  <% } %>
                  <div class="card-body">
                      <h5 class="card-title"><%= cat.getName() %></h5>
                    <p class="card-text"><%= cat.getDescription() %></p>
                    <a href="view_category.jsp?id=<%= cat.getId() %>" class="btn btn-primary">View Products</a>
                  </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>