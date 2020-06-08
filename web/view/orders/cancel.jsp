
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@page import="uts.isd.util.*"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="../../header.jsp" />
<%
    User user = (User)session.getAttribute("user");
    Customer customer = (Customer)session.getAttribute("customer");
    boolean isLoggedIn = (user != null);
   
    //Setup flash messages
    Flash flash = Flash.getInstance(session);
    Order order = (Order)request.getAttribute("order");
%>

<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">
      <%= flash.displayMessages() %>
  <%
      if (!isLoggedIn) {
  %>
    <p>Sorry, you're not logged in! <a href="<%= URL.Absolute("user/register", request) %>">Register</a> or <a href="<%= URL.Absolute("user/login", request) %>">login</a> to see this page.</p>
  <% } else { %>
    <p>Are you sure you want to cancel order #<%= order.getId() %>?</p>
    <form method="post" action="<%= URL.Absolute("order/cancel/"+order.getId(), request) %>">
        <input type="submit" name="doCancel" value="Cancel" class="btn btn-danger">
        <a href="<%= URL.Absolute("order/view/"+order.getId(), request) %>" class="btn btn-primary">Go Back</a>
    </form>
  <% } %>
  <hr>    
    <hr>
  
  </div> <!-- /container -->

</main>
<jsp:include page="../../footer.jsp" />
</html>
</html>
