<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  List<Address> addresses = (List<Address>)request.getAttribute("addresses");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <% if (addresses == null || addresses.size() == 0) { %>
    <h5>You have no addresses</h5>
    <a href="<%= URL.Absolute("addresses/add", request) %>" class="btn btn-primary">Add an address</a>
    <% } else { %>
        <a href="<%= URL.Absolute("addresses/add", request) %>" class="btn btn-primary">Add an address</a>
        <hr>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">Action</th>
              <th scope="col">Address</th>
              <th scope="col">Suburb</th>
              <th scope="col">State</th>
              <th scope="col">Post Code</th>
              <th scope="col">Country</th>
            </tr>
          </thead>
          <tbody>
            <% for (Address address : addresses) { %>
            <tr>
              <th>
                  <a href="<%= URL.Absolute("addresses/view/"+address.getId(), request) %>" class="btn btn-primary">View</a>
                  <a href="<%= URL.Absolute("addresses/edit/"+address.getId(), request) %>" class="btn btn-primary">Edit</a>
                  <a href="<%= URL.Absolute("addresses/delete/"+address.getId(), request) %>" class="btn btn-danger">Delete</a>
              </th>
              <td>
                  <%= (address.getAddressPrefix1().isEmpty() ? "" : address.getAddressPrefix1()+", ") %>
                  <%= address.getStreetNumber()+" "+address.getStreetName() +" "+address.getStreetType() %>
              </td>
              <td><%= address.getSuburb() %></td>
              <td><%= address.getState() %></td>
              <td><%= address.getPostcode() %></td>
              <td><%= address.getCountry() %></td>
            </tr>
            <% } %>
          </tbody>
        </table>
    <% } %>
    <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>