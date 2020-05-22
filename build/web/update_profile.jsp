<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp" />
<%
    User user = (User)session.getAttribute("user");
    Customer customer = (Customer)session.getAttribute("customer");
    boolean isLoggedIn = (user != null);
%>

<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">
  <%
      if (!isLoggedIn) {
  %>
    <p>Sorry, you're not logged in! <a href="register.jsp">Register</a> or <a href="login.jsp">login</a> to see this page.</p>
  
    <% } else if (isLoggedIn && request.getParameter("doUpdate") == null) { %>
    <p>Form was not submitted properly.</p>
 <%
    } else {
        customer.addCustomer(request);
        user.addUser(request);
        session.setAttribute("customer", customer);
        session.setAttribute("user", user);
  %>
    <p><%= request.getParameter("firstName") %>, your profile has been updated successfully. Please view your new profile <a href="view_profile.jsp">here</a>.</p>
  <% } %>
    
    <hr>
  
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>