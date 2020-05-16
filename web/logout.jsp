<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  //We need to figure out if the user is logging out now, or not.
  //then invalidate the session BEFORE including the header, so it shows correctly.
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  //Store for later
  boolean isLoggedIn = (user != null);
  session.invalidate();
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
    <%
        if (!isLoggedIn) {
    %>
        <p>You are not logged in! Log in <a href="login.jsp">here</a>.</p>
    <%
    } else {
    %>
        <p>Bye, <%= customer.getFirstName() %>! Come back soon.</p>
        <p>Click <a href="index.jsp">here</a> to look at more products.</p>
        <p>Click <a href="login.jsp">here</a> to log back in.</p>
    <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>