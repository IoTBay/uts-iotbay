<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
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
  <p>Sorry, you're not logged in! <a href="<%= URL.Absolute("user/register", request) %>">Register</a> or <a href="<%= URL.Absolute("user/login", request) %>">login</a> to see this page.</p>
  
    <% } else if (isLoggedIn && request.getParameter("doUpdate") == null) { %>
    <p>Form was not submitted properly.</p>
 <%
    } else {
        ICustomer dbCustomer = new DBCustomer();
        IUser dbUser = new DBUser();

        customer.loadRequest(request, customer);
        user.loadRequest(request, customer);
        if (customer.update(dbCustomer) && user.update(dbUser))
            Flash.getInstance(session).add(Flash.MessageType.Success, "Your profile has been updated successfully");
        else
            Flash.getInstance(session).add(Flash.MessageType.Error, "Your profile could not be updated");

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