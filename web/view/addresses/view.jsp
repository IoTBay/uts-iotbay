<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Address address = (Address)request.getAttribute("address");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <h2>Viewing Address #<%= address.getId() %></h2>
    <hr>
    <a class="btn btn-primary" href="<%= URL.Absolute("addresses/list", request) %>">Back to full list</a>
    <a class="btn btn-danger" href="<%= URL.Absolute("addresses/delete/"+address.getId(), request) %>">Delete</a>
    <hr>
    
    <table class="table table-striped">
        <tbody>
          <tr>
            <td>ID</td>
            <td><%= address.getId() %></td>
          </tr>
          <tr>
            <td>Address 2</td>
            <td><%= address.getAddressPrefix1() %></span></td>
          </tr>
          <tr>
            <td>Street Number</td>
            <td><%= address.getStreetNumber() %></td>
          </tr>
          <tr>
            <td>Street Name</td>
            <td><%= address.getStreetName() %></td>
          </tr>
          <tr>
            <td>Street Type</td>
            <td><%= address.getStreetType() %></td>
          </tr>
          <tr>
            <td>Suburb</td>
            <td><%= address.getSuburb() %></td>
          </tr>
          <tr>
            <td>State</td>
            <td><%= address.getState()%></td>
          </tr>
          <tr>
            <td>Post Code</td>
            <td><%= address.getPostcode()%></td>
          </tr>
          <tr>
            <td>Country</td>
            <td><%= address.getCountry()%></td>
          </tr>
          <tr>
            <td>Created Date</td>
            <td><%= address.getCreatedDate() %></td>
          </tr>
          <tr>
            <td>Created By</td>
            <td><%= (address.getCreatedBy() != null ? address.getCreatedBy().getEmail() : "") %></td>
          </tr>
          <tr>
            <td>Modified Date</td>
            <td><%= address.getModifiedDate() %></td>
          </tr>
          <tr>
            <td>Modified By</td>
            <td><%= (address.getModifiedBy() != null ? address.getModifiedBy().getEmail() : "") %></td>
          </tr>
        </tbody>
    </table>
    <br>
    <br>
          
    <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>