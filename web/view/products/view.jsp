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
<main role="main"  class="mt-5 pt-4">
  <div style="margin-top: 50px;"></div>
  <div class="container dark-grey-text mt-5">  
    <%= flash.displayMessages() %>
  <!--Main layout-->
    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/14.jpg" class="img-fluid" alt="Product Image">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <!--Content-->
        <div class="p-4">

          <div class="mb-3">
            <a href="">
              <span class="badge badge-pill badge-primary">Category 2</span>
            </a>
            <a href="">
              <span class="badge badge-pill badge-primary">New</span>
            </a>
            <a href="">
              <span class="badge badge-pill badge-primary">Bestseller</span>
            </a>
          </div>

          <p class="lead">
            <span><%= product.getPriceFormatted() %></span>
          </p>

          <p class="lead font-weight-bold"><%= product.getName() %></p>

          <p><%= product.getDescription() %></p>

          <form class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="text" value="1" aria-label="Search" class="form-control" style="width: 100px">
            &nbsp;
            <button class="btn btn-primary" type="submit">Add to cart
              <i class="fas fa-shopping-cart ml-1"></i>
            </button>

          </form>

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