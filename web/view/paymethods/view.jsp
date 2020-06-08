<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  PaymentMethod paymethod = (PaymentMethod)request.getAttribute("paymethod");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <h2>Viewing Payment Method #<%= paymethod.getId() %></h2>
    <hr>
    <a class="btn btn-primary" href="<%= URL.Absolute("paymethods/list", request) %>">Back to full list</a>
    <a class="btn btn-danger" href="<%= URL.Absolute("order/delete/"+paymethod.getId(), request) %>">Delete</a>
    <hr>
    
    <table class="table table-striped">
        <tbody>
          <tr>
            <td>ID</td>
            <td><%= paymethod.getId() %></td>
          </tr>
          <tr>
            <td>Payment Type</td>
            <td><%= PaymentMethod.PAYMENT_TYPES[paymethod.getPaymentType()] %></span></td>
          </tr>
          <tr>
            <td>Card Name</td>
            <td><%= paymethod.getCardName() %></td>
          </tr>
          <tr>
            <td>Card Number</td>
            <td><%= paymethod.getCardNumber() %></td>
          </tr>
          <tr>
            <td>Card Expiry</td>
            <td><%= paymethod.getCardExpiry() %></td>
          </tr>
          <tr>
            <td>Card CVV</td>
            <td><%= paymethod.getCardCVV() %></td>
          </tr>
          <tr>
            <td>Created Date</td>
            <td><%= paymethod.getCreatedDate() %></td>
          </tr>
          <tr>
            <td>Created By</td>
            <td><%= (paymethod.getCreatedBy() != null ? paymethod.getCreatedBy().getEmail() : "") %></td>
          </tr>
          <tr>
            <td>Modified Date</td>
            <td><%= paymethod.getModifiedDate() %></td>
          </tr>
          <tr>
            <td>Modified By</td>
            <td><%= (paymethod.getModifiedBy() != null ? paymethod.getModifiedBy().getEmail() : "") %></td>
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