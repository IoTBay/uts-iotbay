<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.util.Flash"%>
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
    
  <!-- Main jumbotron for a primary marketing message or call to action -->
  <div class="banner">
    <div class="banner-text">
    <div class="container">
      <h1 class="display-3">Welcome to <svg class="bi bi-cloud" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M4.887 7.2l-.964-.165A2.5 2.5 0 103.5 12h10a1.5 1.5 0 00.237-2.981L12.7 8.854l.216-1.028a4 4 0 10-7.843-1.587l-.185.96zm9.084.341a5 5 0 00-9.88-1.492A3.5 3.5 0 103.5 13h9.999a2.5 2.5 0 00.394-4.968c.033-.16.06-.324.077-.49z" clip-rule="evenodd"/>
</svg>&nbsp;IOT Bay!</h1>
      <p>Welcome to IoT Bay, the one-stop-shop for internet of things devices. Take a look around, or login to get started!</p>
      <% if (!isLoggedIn) { %>
      <p><a class="btn btn-primary btn-lg" href="<%= URL.Absolute("user/register", request) %>" role="button">Register here &raquo;</a></p>
      <% } else { %>
      <p><a class="btn btn-primary btn-lg" href="<%= URL.Absolute("products/categories", request) %>" role="button">View Categories &raquo;</a></p>
      <% } %>
    </div>
    </div>
  </div>

  <div class="container">
  <%= flash.displayMessages() %>

      
  <%
      if (!isLoggedIn) {
  %>
    <p>Welcome anonymous user, <a href="<%= URL.Absolute("user/register", request) %>">register</a> or <a href="<%= URL.Absolute("user/login", request) %>">login</a>, or look at some categories to continue.</p>
    <% } else if (session.getAttribute("userCancelled") != null) {
        session.invalidate();
    } else { %>
    <p>Welcome back, <%= customer.getFirstName() %>! We see you are now logged in:</p>
    <ul>
        <li>Email: <%= user.getEmail() %></li>
        <li>Age: <%= user.getBirthDate() %></li>
    </ul>
  <% } %>
    
    <hr>
  
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>