<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  List<Order> orders = (List<Order>)request.getAttribute("orders");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <form method="post" action="<%= URL.Absolute("order/search", request) %>" class="form-inline">
        <label class="my-1 mr-2" for="search">Search</label>
        <input type="text" class="form-control mb-6 mr-sm-2 col-md-6" id="search" name="search" placeholder="Order # or from:YYYY-MM-DD to:YYYY-MM-DD">
        <input type="submit" class="btn btn-primary" value="Search">
        <span style="margin: 50px;"></span>
        <a class="btn btn-secondary" href="<%= URL.Absolute("order/list", request) %>">Back to full list</a>
    </form>

    <% if (orders == null || orders.size() == 0) { %>
    <h5>You have no orders</h5>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">Action</th>
              <th scope="col">ID</th>
              <th scope="col">Status</th>
              <th scope="col"># Lines</th>
              <th scope="col">Total Cost</th>
              <th scole="col">Currency</th>
              <th scope="col">Shipping Address</th>
              <th scope="col">Created Date</th>
              <th scole="col">Modified Date</th>
            </tr>
          </thead>
          <tbody>
          <% for (Order o : orders) { %>
            <tr>
              <th>
                  <a href="<%= URL.Absolute("order/view/"+o.getId(), request) %>" class="btn btn-primary">View</a>
                  <a href="<%= URL.Absolute("order/edit/"+o.getId(), request) %>" class="btn btn-primary">Edit</a>
                  <a href="<%= URL.Absolute("order/delete/"+o.getId(), request) %>" class="btn btn-danger">Delete</a>
              </th>
              <td><%= o.getId() %></td>
              <td><%= Order.ORDER_STATUS[o.getStatus()] %></td>
              <td><%= o.getOrderLines().size() %></td>
              <td><%= o.getTotalCostFormatted() %></td>
              <td><%= o.getCurrency().getAbbreviation() %></td>
              <td><%= (o.getShippingAddress() == null ? "" : o.getShippingAddress().getFullAddress()) %></td>
              <td><%= o.getCreatedDate() %></td>
              <td><%= o.getModifiedDate() %></td>
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