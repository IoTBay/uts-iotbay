<%-- 
    Document   : view_userlog
    Created on : 08/06/2020, 6:14:36 PM
    Author     : Ashley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  List<AuditLog> auditLogs = (List<AuditLog>)request.getAttribute("auditlogs");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <form method="post" action="<%= URL.Absolute("user/accesslog", request) %>" class="form-inline">
        <label class="my-1 mr-2" for="search">Search</label>
        <input type="text" class="form-control mb-6 mr-sm-2 col-md-6" id="search" name="search" placeholder="Order # or from:YYYY-MM-DD to:YYYY-MM-DD">
        <input type="submit" class="btn btn-primary" value="Search">
        <span style="margin: 50px;"></span>
        <a class="btn btn-secondary" href="<%= URL.Absolute("user/accesslog", request) %>">Back to full list</a>
    </form>

    <% if (auditLogs == null || auditLogs.size() == 0) { %>
    <h5>You have no access logs</h5>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Entity</th>
              <th scope="col">Event</th>
              <th scope="col">Message</th>
              <th scope="col">Customer ID</th>
              <th scole="col">Event Date</th>
            </tr>
          </thead>
          <tbody>
          <% for (AuditLog auditlog : auditLogs) { %>
            <tr>
              <td><%= auditlog.getId() %></td>
              <td><%= auditlog.getEntity() %></td>
              <td><%= auditlog.getEvent() %></td>
              <td><%= auditlog.getMessage() %></td>
              <td><%= auditlog.getCustomerId() %></td>
              <td><%= auditlog.getEventDate() %></td>
            </tr>
            <% } %>
          </tbody>
        </table>
    <% } %>
    <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="footer.jsp" />
</html>