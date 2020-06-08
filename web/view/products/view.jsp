<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
  Product product = (Product)request.getAttribute("product");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
  <!--Main layout-->
    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <div class="col-md-6 mb-4">

            <% if (product.getImage() == null || product.getImage().isEmpty()) { %>
            <svg class="bd-placeholder-img card-img-top" width="100%" height="300" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Default Image"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96" /><text x="50%" y="50%" fill="#dee2e6" dy=".3em">Default Image</text></svg>
          <% } else { %>
            <img src="<%= URL.Image("products/"+product.getImage(), request) %>" height="300" class="img-responsive img-rounded" alt="<%= product.getName() %>">
          <% } %>
      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <!--Content-->
        <div class="p-4">

          <div class="mb-3">
              <a href="<%= URL.Absolute("categories/view/"+product.getCategoryId(), request) %>">
              <span class="badge badge-pill badge-primary"><%= product.getCategory().getName() %></span>
            </a>
          </div>

          <p class="lead">
            <span><%= product.getPriceFormatted() %></span>
          </p>

          <p class="lead font-weight-bold"><%= product.getName() %></p>

          <p><%= product.getDescription() %></p>

          <form method="post" action="<%= URL.Absolute("order/addline/"+product.getId(), request) %>" class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="text" value="1" aria-label="Quantity" name="addQuantity" class="form-control" style="width: 100px">
            &nbsp;
            <button class="btn btn-primary" type="submit">Add to cart
              <i class="fas fa-shopping-cart ml-1"></i>
            </button>

          </form>
          <% if (product.getCurrentQuantity() == 0) { %>
          <span class="badge badge-pill badge-danger">Out of Stock</span>
          <% } else if (product.getCurrentQuantity() < Product.LOW_STOCK) { %>
          <span class="badge badge-pill badge-danger">Low stock</span>
          <% } %>

        </div>
        <!--Content-->

      </div>
      <!--Grid column-->

    </div>
    <!--Grid row-->

  <!--Main layout-->
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>