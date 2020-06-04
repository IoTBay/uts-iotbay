<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
 
    <div class="py-5 text-center">
        <h2>Edit your payment method</h2>
    </div>
    <form method="post" action="<%= URL.Absolute("paymethods/add", request) %>">
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Payment Method Details</h4>
          <form class="needs-validation" novalidate>
        <div class="row">
            <div class="col-md-5 mb-3">
              <label for="cardName">Name</label>
              <input type="text" class="form-control" id="cardName" name="cardName" placeholder="John Smith" value="<%= v.repopulate("cardName") %>" >
            </div>

            <div class="col-md-4 mb-3">
              <label for="cardNumber">Number</label>
              <input type="text" class="form-control" id="cardNumber" name="cardNumber" placeholder="4012 8888 8888 1881" value="<%= v.repopulate("cardNumber") %>">
            </div>

            <div class="col-md-2 mb-3">
              <label for="cardCVV">CVV</label>
              <input type="text" class="form-control" id="cardCVV" name="cardCVV" placeholder="123" value="<%= v.repopulate("cardCVV") %>">
            </div>
        </div>
        <div class="row">
           <div class="col-md-4 mb-3">
            <label for="paymentType">Type</label>
            <select class="custom-select d-block w-100" id="paymentType" name="paymentType" value="<%= v.repopulate("paymentType") %>">
              <option value="">Choose...</option>
              <% for (int i = 1; i < PaymentMethod.PAYMENT_TYPES.length; i++) { %>
              <option value="<%= i %>"><%= PaymentMethod.PAYMENT_TYPES[i] %></option>
              <% } %>
            </select>
          </div>
        </div>
          
        <hr class="mb-4">
        <div class="custom-control custom-checkbox">
          <input type="checkbox" class="custom-control-input" id="defaultPayment" name="defaultPayment" value="<%= v.repopulate("defaultPayment") %>">
          <label class="custom-control-label" for="defaultPayment">Use this as my default payment method</label>
        </div>
        <hr class="mb-4">
        <input class="btn btn-primary" type="submit" name="doAdd" value="Add">
    </form>
        <hr class="mb-4">
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>