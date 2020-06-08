<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Order order = (Order)request.getAttribute("order");
  List<PaymentTransaction> transactions = (List<PaymentTransaction>)request.getAttribute("transactions");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <h2>Viewing Order #<%= order.getId() %></h2>
    <hr>
    <a class="btn btn-primary" href="<%= URL.Absolute("order/list", request) %>">Back to full list</a>
    <a class="btn btn-danger" href="<%= URL.Absolute("order/cancel/"+order.getId(), request) %>">Cancel Order</a>
    <hr>
    
    <table class="table table-striped">
        <tbody>
          <tr>
            <td>ID</td>
            <td><%= order.getId() %></td>
          </tr>
          <tr>
            <td>Status</td>
            <td><span class="badge badge-pill badge-success"><%= Order.ORDER_STATUS[order.getStatus()] %></span></td>
          </tr>
          <tr>
            <td>Total Cost</td>
            <td><%= order.getTotalCostFormatted() %></td>
          </tr>
          <tr>
            <td>Currency</td>
            <td><%= order.getCurrency().getAbbreviation() %></td>
          </tr>
          <tr>
            <td>Shipping Address</td>
            <td><%= (order.getShippingAddress() == null ? "" : order.getShippingAddress().getFullAddress()) %></td>
          </tr>
          <tr>
            <td>Billing Address</td>
            <td><%= (order.getBillingAddress() == null ? "" : order.getBillingAddress().getFullAddress()) %></td>
          </tr>
          <tr>
            <td>Payment Method</td>
            <td><%= (order.getPaymentMethod()== null ? "" : order.getPaymentMethod().getDescription()) %></td>
          </tr>
          <tr>
            <td>Created Date</td>
            <td><%= order.getCreatedDate() %></td>
          </tr>
          <tr>
            <td>Created By</td>
            <td><%= (order.getCreatedBy() != null ? order.getCreatedBy().getEmail() : "") %></td>
          </tr>
          <tr>
            <td>Modified Date</td>
            <td><%= order.getModifiedDate() %></td>
          </tr>
          <tr>
            <td>Modified By</td>
            <td><%= (order.getModifiedBy() != null ? order.getModifiedBy().getEmail() : "") %></td>
          </tr>
        </tbody>
    </table>
    <br>
    <br>
    <% if (order.getLineCount() == 0) { %>
    <h5>This order is empty.</h5>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Quantity</th>
              <th scope="col">Product</th>
              <th scope="col">Unit Price</th>
              <th scole="col">Sub Total</th>
            </tr>
          </thead>
          <tbody>
          <% for (OrderLine line : order.getOrderLines()) { %>
            <tr>
              <td><%= line.getId() %></td>
              <td><%= line.getQuantity() %></td>
              <td><%= line.getProduct().getName() %></td>
              <td><%= line.getProduct().getPriceFormatted() %></td>
              <td><%= line.getPriceFormatted() %></td>
            </tr>
            <% } %>
          </tbody>
        </table>
    <% } %>
    <br>
    <br>
    
    
    <% if (transactions == null || transactions.size() == 0) { %>
    <h5>This order has no payment transactions.</h5>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Payment Gateway ID</th>
              <th scope="col">Date</th>
              <th scope="col">By</th>
              <th scope="col">Amount</th>
              <th scope="col">Description</th>
              <th scole="col">Status</th>
            </tr>
          </thead>
          <tbody>
          <% for (PaymentTransaction tx : transactions) { %>
            <tr>
              <td><%= tx.getId() %></td>
              <td><%= tx.getPaymentGatewayTransaction() %></td>
              <td><%= tx.getCreatedDate() %></td>
              <td><%= (tx.getCreatedBy() != null ? tx.getCreatedBy().getEmail() : "") %></td>
              <td><%= tx.getAmount() %></td>
              <td><%= tx.getDescription() %></td>
              <td><%= PaymentTransaction.PAYMENT_STATUS[tx.getStatus()] %></td>
            </tr>
            <% } %>
          </tbody>
        </table>
    <% } %>
    <br>
    <br>
          
    <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>