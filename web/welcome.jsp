<%@page import="uts.isd.util.URL"%>
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
  
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">

        <%= flash.displayMessages() %>
        
        <% if (isLoggedIn) { //New user %>
        <h1>Welcome, <%= customer.getFirstName() %>!</h1>
        <p>Hi, <%= customer.getFirstName() %>! We've just setup your new account. Your current details are below:</p>
        <ul>
            <li>First Name: <%= customer.getFirstName() %></li>
            <li>Last Name: <%= customer.getLastName() %></li>
            <li>Email: <%= customer.getEmail() %></li>
            <li>Phone: <%= customer.getPhone() %></li>
            <li>Age: <%= user.getBirthDate() %> (<%= user.getAge() %> years old)</li>
            <li>Gender: <%= user.getSex() %></li>
        </ul>
        <p>Click <a href="<%= URL.Absolute("", request) %>">here</a> to look at more products.</p>
        <p>Click <a href="<%= URL.Absolute("user/profile", request) %>">here</a> to view your profile.</p>
        <% } else { // Not logged in %>
        <p>You are not logged in! Log in <a href="<%= URL.Absolute("user/login", request) %>">here</a>.</p>
        <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>