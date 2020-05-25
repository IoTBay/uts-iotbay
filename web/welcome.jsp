<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%
  //We need to figure out if the user is logging out now, or not.
  //then invalidate the session BEFORE including the header, so it shows correctly.
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  boolean isLoggedIn = (user != null && customer != null);
  
  //Setup flash messages
  Flash flash = Flash.getInstance(session);
  
  int status = 0;
  
  //Not logged in but submitted registration
  if (!isLoggedIn && request.getParameter("doRegister") != null)
  {
    //Create a connection to the DB for the customers table
    ICustomer dbCustomer = new DBCustomer();
    customer = new Customer();
    customer.addCustomer(request, dbCustomer);
    
    //Create a connection to the DB for users table
    IUser dbUser = new DBUser();
    user = new User();
    user.setCustomerId(customer.getId()); //Link the new user to the customer we just created above.
    //Add user to DB
    boolean added = user.addUser(request, dbUser);
    
    if (added)
        flash.add(Flash.MessageType.Success, "New user "+user.getEmail()+" added successfully!");
    else
        flash.add(Flash.MessageType.Error, "Failed to add new user: "+user.getEmail());
    
    //Store objects in session so we dont have to load from DB on every page.
    session.setAttribute("customer", customer);
    session.setAttribute("user", user);
    
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
  }
  else if (!isLoggedIn)
  {
    status = 0; //Not logged in at all
  } else {
    status = 2; //Existing user.
  //TODO:
  //if user is logged in already, dont register.
  //if user is not logged in, create user, customer and add to session.
  //check if user posted to this page or not.
  }
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">

        <%= flash.displayMessages() %>
        
        <% if (status == 1) { //New user %>
        <h1>Welcome, <%= customer.getFirstName() %>!</h1>
        <p>Hi, <%= customer.getFirstName() %>! We've just setup your new account. Your current details are below:</p>
        <ul>
            <li>First Name: <%= customer.getFirstName() %></li>
            <li>Last Name: <%= customer.getLastName() %></li>
            <li>Email: <%= customer.getEmail() %></li>
            <li>Age: <%= user.getBirthDate() %> (<%= user.getAge() %> years old)</li>
            <li>Gender: <%= user.getSex() %></li>
        </ul>
        <p>Click <a href="index.jsp">here</a> to look at more products.</p>
        <p>Click <a href="view_profile.jsp">here</a> to view your profile.</p>
        <% } else if (status == 2) { //Existing user %>
        <p>Welcome back, <%= customer.getFirstName() %>,</p>
        <p>You are logged in. Click <a href="view_profile.jsp">here</a> to view your profile.</p>
        <% } else { // Not logged in %>
        <p>You are not logged in! Log in <a href="login.jsp">here</a>.</p>
        <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>