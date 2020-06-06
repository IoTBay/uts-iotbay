<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
  Order order = (Order)session.getAttribute("order");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
  <div class="row">
    <div class="col-md-12 order-md-2 mb-12">
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-muted">Your cart</span>
        <span class="badge badge-secondary badge-pill"><%= order.getTotalQuantity()%></span>
      </h4>
      <% if (order == null || order.isEmpty()) { %>
      <h5>Your cart is empty.</h5>
      <% } else { %>
      <ul class="list-group mb-12">
        <% for (OrderLine line : order.getOrderLines()) { %>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div class="col-md-1">
              <form method="post" action="<%= URL.Absolute("order/updateqty", request) %>">
                <input type="hidden" name="lineId" value="<%= line.getId() %>">
                <div class="btn-group" role="group" aria-label="Change item quantity">
                  <input type="submit" class="btn btn-outline-dark" name="doSubtract" value="-">
                  <input type="submit" class="btn btn-outline-dark" name="doAdd" value="+">
                </div>
              </form>
          </div>
          <div class="col-md-10">
            <h6 class="my-0"><%= line.getQuantity() %> x <%= line.getProduct().getName() %></h6>
            <small class="text-muted"><%= line.getProduct().getDescription() %></small>
          </div>
          <span class="text-muted"><%= line.getUnitPriceFormatted() %></span>
        </li>
        <% } %>
        <li class="list-group-item d-flex justify-content-between">
          <span>Total (<%= order.getCurrency().getAbbreviation() %>)</span>
          <strong><%= order.getTotalCostFormatted() %></strong>
        </li>
      </ul>
      <% } %>
    </div>
  </div>
  <hr>
  <% if (order != null && !order.isEmpty()) { %>
  <a href="<%= URL.Absolute("order/checkout", request) %>" class="btn btn-primary">Checkout</a>
  <hr>
  <% } %>
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>