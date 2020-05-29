<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  boolean isLoggedIn = (user != null);
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
    <%
        if (!isLoggedIn) {
    %>
        <p>You are not logged in! Log in <a href="<%= URL.Absolute("user/login", request) %>">here</a>.</p>
    <%
    } else {
    %>
        <p>Bye, <%= customer.getFirstName() %>! Come back soon.</p>
        <p>Click <a href="<%= URL.Absolute("", request) %>">here</a> to look at more products.</p>
        <p>Click <a href="<%= URL.Absolute("user/login", request) %>">here</a> to log back in.</p>
    <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>