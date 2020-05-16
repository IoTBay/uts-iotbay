<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%> 
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%
    User user = (User)session.getAttribute("user");
    Customer customer = (Customer)session.getAttribute("customer");
    Order order = (Order)session.getAttribute("order");
    List<Product> products = (List<Product>)session.getAttribute("products");
    
    int totalQuantity = (order == null || products == null ? 0 : order.getTotalQuantity());
    String totalCost = (order == null || products == null ? "$0.00" : order.getTotalCostFormatted());
    boolean isLoggedIn = (user != null && customer != null);
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Rhys Hanrahan">
    <title>IOTBay</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <meta name="theme-color" content="#563d7c">
  </head>
  <body>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">
            <svg class="bi bi-cloud" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M4.887 7.2l-.964-.165A2.5 2.5 0 103.5 12h10a1.5 1.5 0 00.237-2.981L12.7 8.854l.216-1.028a4 4 0 10-7.843-1.587l-.185.96zm9.084.341a5 5 0 00-9.88-1.492A3.5 3.5 0 103.5 13h9.999a2.5 2.5 0 00.394-4.968c.033-.16.06-.324.077-.49z" clip-rule="evenodd"/>
            </svg>
            &nbsp;IOTBay
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="index.jsp">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="categories.jsp">Categories</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0 ml-auto mr-auto col-lg-6">
        <input class="form-control mr-sm-2 col-lg-10" type="text" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
    </form>
    <% 
        if (isLoggedIn) { 
            //User is logged in
    %>
    <div class="account">
        <a href="view_profile.jsp" class="btn btn-outline-light">
        <svg class="bi bi-person-fill" width="1.2em" height="1.2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 100-6 3 3 0 000 6z" clip-rule="evenodd"/>
        </svg>
        <%= customer.getFirstName() %>
        </a>
    </div>
    &nbsp;
    <div>
        <a href="logout.jsp" class="btn btn-outline-light">Logout</a>
    </div>
    <% } else { %>
    <a href="register.jsp" class="btn btn-outline-light">Register</a>
    &nbsp;
    <a href="login.jsp" class="btn btn-outline-light">Login</a>
    <% } %>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <div class="cart">
        <div class="dropdown">
            <a class="btn" href="#" id="dropdown-cart" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <svg class="bi bi-bag" style="color: white;" width="2em" height="2em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" d="M14 5H2v9a1 1 0 001 1h10a1 1 0 001-1V5zM1 4v10a2 2 0 002 2h10a2 2 0 002-2V4H1z" clip-rule="evenodd"/>
                    <path d="M8 1.5A2.5 2.5 0 005.5 4h-1a3.5 3.5 0 117 0h-1A2.5 2.5 0 008 1.5z"/>
                </svg> 
                <span class="badge badge-pill badge-light"><%= totalQuantity %></span>
            </a>
            <% if (totalQuantity == 0) { %>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdown-cart">                
                <a class="dropdown-item" href="#">You have no items in your cart.</a>
            </div>
            <%
            } else {
            %>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdown-cart">
                <%
                for (int i = 0; i < order.getLineCount(); i++) {
                    OrderLine line = order.getOrderLines().get(i);
                %>
                <a class="dropdown-item" href="#"><%= line.getQuantity() %>x <%= products.get(line.getProductId() - 1).getName() %>: <%= line.getPriceFormatted() %></a>
                <% } //End of for %>
                <hr>
                <div class="dropdown-item">
                    <strong>Total: <%= totalCost %></strong>
                </div>
                <hr>
                <div class="dropdown-item">
                    <a href="view_cart.jsp" class="btn btn-outline-primary">View Cart</a>
                    <a href="checkout.jsp" class="btn btn-primary">Checkout</a>
                </div>
            </div>
            &nbsp;&nbsp;&nbsp;                                                        
            <%= totalCost %>
            <%
            } //End of if
            %>
      </div>
  </div>
</nav>