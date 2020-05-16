<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  //Log user in and mock user object
  User user = new User();
  user.setId(1);
  user.setCustomerId(1);
  user.setEmail(request.getParameter("email"));
  user.setPassword(request.getParameter("password"));
  user.setBirthDate("1991-06-14");
  user.setAccessLevel(1); //Normal user
  session.setAttribute("user", user);
  //Mock customer
  Customer customer = new Customer(request.getParameter("email"), "Rhys", "Hanrahan");
  customer.setId(1);
  session.setAttribute("customer", customer);
  //Mock currency
  Currency currency = new Currency();
  currency.setId(1);
  currency.setName("Australian Dollar");
  currency.setAbbreviation("AUD");
  currency.setCostConversionRate(0.64297);
  currency.setRetailConversionRate(0.650);
  //Mock category
  ProductCategory cat1 = new ProductCategory(1, "Transistors", "There are some transistors here.", "transistors.jpg");
  ProductCategory cat2 = new ProductCategory(2, "PCBs", "There are some PCBs here.", "pcbs.jpg");
  List<ProductCategory> categories = new ArrayList<ProductCategory>();
  categories.add(cat1);
  categories.add(cat2);
  session.setAttribute("categories", categories);
  //Load products
  Product p1 = new Product();
  p1.setId(1);
  p1.setCurrencyId(1);
  p1.setCategoryId(1);
  p1.setName("Widget");
  p1.setDescription("This is a widget");
  p1.setPrice(12.50);
  Product p2 = new Product();
  p2.setId(2);
  p2.setCurrencyId(1);
  p2.setCategoryId(2);
  p2.setName("Thingy");
  p2.setDescription("This is a thingy");
  p2.setPrice(52.75);
  
  List<Product> products = new ArrayList<Product>();
  products.add(p1);
  products.add(p2);
  session.setAttribute("products", products);
  
  //Load order
  Order order = new Order();
  order.setId(1);
  order.setCustomerId(1);
  order.setBillingAddressId(1);
  order.setShippingAddressId(1);
  order.setPaymentMethodId(1);
  order.setUserId(1);
  
  OrderLine line = new OrderLine();
  line.setId(1);
  line.setOrderId(1);
  line.setProductId(1);
  line.setQuantity(3);
  line.setUnitPrice(12.50);
  order.addOrderLine(line);
  
  OrderLine line2 = new OrderLine();
  line2.setId(2);
  line2.setOrderId(1);
  line2.setProductId(2);
  line2.setQuantity(2);
  line2.setUnitPrice(52.75);
  order.addOrderLine(line2);
  session.setAttribute("order", order);

%>
<jsp:include page="header.jsp" />

<main role="main">

  <div class="container">
      <br><br>
      <p>Logging you in. Click <a href="index.jsp">here</a> if you are not redirected.</p>
      <% response.sendRedirect(request.getContextPath() + "/index.jsp"); %>
      <hr>
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>