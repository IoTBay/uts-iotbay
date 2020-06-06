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
            <form method="post" action="<%= URL.Absolute("order/deleteline", request) %>">
               <input type="hidden" name="lineId" value="<%= line.getId() %>">
               <input type="hidden" name="productId" value="<%= line.getProductId() %>">
               <button type="submit" class="btn btn-danger btn-lg" name="doDelete">
                   <svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                     <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                     <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                   </svg>
               </button>
             </form>
          </div>
          <div class="col-md-1">
              <form method="post" action="<%= URL.Absolute("order/updateqty", request) %>">
                <input type="hidden" name="lineId" value="<%= line.getId() %>">
                <div class="btn-group" role="group" aria-label="Change item quantity">
                  <input type="submit" class="btn btn-outline-dark" name="doSubtract" value="-">
                  <input type="submit" class="btn btn-outline-dark" name="doAdd" value="+">
                </div>
              </form>
          </div>
          <div class="col-md-9">
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