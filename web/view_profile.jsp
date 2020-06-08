
<%@page import="uts.isd.util.*"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp" />
<%
    User user = (User)session.getAttribute("user");
    Customer customer = (Customer)session.getAttribute("customer");
    boolean isLoggedIn = (user != null);
   
    //Setup flash messages
    Flash flash = Flash.getInstance(session);
%>

<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">
      <%= flash.displayMessages() %>
  <%
      if (!isLoggedIn) {
  %>
    <p>Sorry, you're not logged in! <a href="<%= URL.Absolute("user/register", request) %>">Register</a> or <a href="<%= URL.Absolute("user/login", request) %>">login</a> to see this page.</p>
  <% } else if (isLoggedIn && user.getAccessLevel() == 0 ) { %>
    <p> This account is inactive. </</p>
  <% } else { %>
    <p>Welcome back, <%= customer.getFirstName() %>! Your current details are below:</p>
    <ul>
        <li>First Name: <%= customer.getFirstName() %></li>
        <li>Last Name: <%= customer.getLastName() %></li>
        <li>Email: <%= customer.getEmail() %></li>
        <li>Phone: <%= customer.getPhone() %></li>
        <li>Age: <%= user.getBirthDate() %> (<%= user.getAge() %> years old)</li>
        <li>Gender: <%= user.getSex() %></li>
    </ul>
    <hr>
    <a href="<%= URL.Absolute("user/edit", request) %>" class="btn btn-primary">Edit Profile</a>
    <a href="<%= URL.Absolute("user/accesslog", request) %>" class="btn btn-primary">View Access Log</a>
    <a href="<%= URL.Absolute("user/cancel", request) %>" class="btn btn-primary">Cancel Account</a>
    <span style="margin-left: 100px;"></span>
    <a href="<%= URL.Absolute("order/list", request) %>" class="btn btn-primary">View Orders</a>
    <a href="<%= URL.Absolute("addresses/list", request) %>" class="btn btn-primary">View Addresses</a>
    <a href="<%= URL.Absolute("paymethods/list", request) %>" class="btn btn-primary">View Payment Methods</a>
  <% } %>
    
    <hr>
  
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>
