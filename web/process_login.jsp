<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<jsp:include page="header.jsp" />

<main role="main">

  <div class="container">
      <br><br>
      <p>Logging you in. Click <a href="index.jsp">here</a> if you are not redirected.</p>
      <% response.sendRedirect(request.getContextPath() + "/index.jsp"); %>
      <hr>
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>