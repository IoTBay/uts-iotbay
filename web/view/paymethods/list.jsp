<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  List<PaymentMethod> paymethods = (List<PaymentMethod>)request.getAttribute("paymethods");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <% if (paymethods == null || paymethods.size() == 0) { %>
    <h5>You have no payment methods</h5>
    <a href="<%= URL.Absolute("paymethods/add", request) %>" class="btn btn-primary">Add a payment method</a>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">Action</th>
              <th scope="col">Default</th>
              <th scope="col">Type</th>
              <th scope="col">Name</th>
              <th scope="col">Number</th>
              <th scope="col">CVV</th>
            </tr>
          </thead>
          <tbody>
            <% for (PaymentMethod method : paymethods) { %>
            <tr>
              <th>
                  <a href="<%= URL.Absolute("paymethods/view/"+method.getId(), request) %>" class="btn btn-primary">View</a>
                  <a href="<%= URL.Absolute("paymethods/edit/"+method.getId(), request) %>" class="btn btn-primary">Edit</a>
                  <a href="<%= URL.Absolute("paymethods/delete/"+method.getId(), request) %>" class="btn btn-danger">Delete</a>
              </th>
              <td><%= (method.getDefaultPayment() ? "Yes" : "No") %></td>
              <td><%= method.getPaymentType() %></td>
              <td><%= method.getCardName() %></td>
              <td><%= method.getCardNumber() %></td>
              <td><%= method.getCardCVV() %></td>
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