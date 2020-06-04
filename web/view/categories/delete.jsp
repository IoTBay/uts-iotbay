<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
  Category category = (Category)request.getAttribute("category");

%>
<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">  
        <%= flash.displayMessages()%>

        <div class="py-5 text-center">
            <h2>Delete <%= category.getName() %></h2>
        </div>
        <p>Are you sure you want to delete <%= category.getName() %>?</p>
        <form method="post" action="<%= URL.Absolute("categories/delete/"+category.getId(), request) %>">
            <input type="submit" name="doDelete" value="Delete" class="btn btn-danger">
            <a href="<%= URL.Absolute("categories/list", request) %>" class="btn btn-primary">Go Back</a>
        </form>
    </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>