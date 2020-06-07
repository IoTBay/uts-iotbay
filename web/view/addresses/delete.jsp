<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
  Address address = (Address)request.getAttribute("address");

%>
<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">  
        <%= flash.displayMessages()%>

        <div class="py-5 text-center">
            <h2>Delete <%= address.getFullAddress() %></h2>
        </div>
        <p>Are you sure you want to delete <%= address.getFullAddress() %>?</p>
        <form method="post" action="<%= URL.Absolute("addresses/delete/"+address.getId(), request) %>">
            <input type="submit" name="doDelete" value="Delete" class="btn btn-danger">
            <a href="<%= URL.Absolute("addresses/list", request) %>" class="btn btn-primary">Go Back</a>
        </form>
    </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>