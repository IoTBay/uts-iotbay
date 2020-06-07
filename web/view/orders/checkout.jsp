<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
    Flash flash = Flash.getInstance(session);
    Validator v = new Validator(session);
    Order order = (Order) session.getAttribute("order");
    Customer customer = (Customer)session.getAttribute("customer");
    Address defaultBillingAddress = (Address)request.getAttribute("defaultBillingAddress");
    Address defaultShippingAddress = (Address)request.getAttribute("defaultShippingAddress");
    List<Address> savedAddresses = (List<Address>)request.getAttribute("savedAddresses");
%>
<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">  
        <%= flash.displayMessages()%>
        <div class="py-5 text-center">
            <h2>Ready to submit your order?</h2>
            <p class="lead">Please fill out all your billing, shipping and contact information below, so we can process your order. If you are a register user, we've pre-loaded any saved values for you to speed things up!</p>
        </div>

        <% if (order == null || order.isEmpty()) { %>
       <h5>Your cart is empty. Please <a href="<%= URL.Absolute("categories", request) %>">order some items</a> order some items first.</h5>
       <% } else { %>
        <div class="row">
            <div class="col-md-4 order-md-2 mb-4">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted">Your cart</span>
                    <span class="badge badge-secondary badge-pill">3</span>
                </h4>
                <% if (order == null || order.isEmpty()) { %>
                <h5>Your cart is empty. Please <a href="<%= URL.Absolute("categories", request) %>">order some items</a> first.</h5>
                <% } else { %>
                <ul class="list-group mb-3">
                <% for (OrderLine line : order.getOrderLines()) { %>
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0"><%= line.getQuantity() %> x <%= (line.getProduct() == null ? "" : line.getProduct().getName()) %></h6>
                            <small class="text-muted"><%= (line.getProduct() == null ? "" : line.getProduct().getDescription()) %></small>
                        </div>
                        <span class="text-muted"><%= line.getUnitPriceFormatted() %></span>
                    </li>
                    <% } %>

                    <li class="list-group-item d-flex justify-content-between">
                        <span>Total (<%= (order.getCurrency() == null ? "" : order.getCurrency().getAbbreviation()) %>)</span>
                        <strong><%= order.getTotalCostFormatted() %></strong>
                    </li>
                </ul>
                <% } %>
            </div>
            
            <div class="col-md-8 order-md-1">
                <div class="col-md-8 order-md-1">
                  <h4 class="mb-3">Shipping Address</h4>
                  <form class="needs-validation" novalidate>
                <!-- Shipping address form -->
                <div class="row">
                    <div class="col-md-2 mb-3">
                      <label for="streetNumber">Number</label>
                      <input type="text" class="form-control" id="streetNumber" name="streetNumber" placeholder="1234" value="<%= (defaultShippingAddress == null || defaultShippingAddress.getStreetNumber() == 0 ? "" : v.repopulate("streetNumber", defaultShippingAddress.getStreetNumber())) %>" >
                    </div>

                    <div class="col-md-4 mb-3">
                      <label for="streetName">Name</label>
                      <input type="text" class="form-control" id="streetName" name="streetName" placeholder="Main" value="<%= v.repopulate("streetName", defaultShippingAddress.getStreetName()) %>">
                    </div>

                    <div class="col-md-3 mb-3">
                      <label for="streetType">Type</label>
                      <input type="text" class="form-control" id="streetType" name="streetType" placeholder="Street" value="<%= v.repopulate("streetType", defaultShippingAddress.getStreetType()) %>">
                    </div>
                </div>

                <div class="mb-3">
                  <label for="addressPrefix1">Address 2 <span class="text-muted">(Optional)</span></label>
                  <input type="text" class="form-control" id="addressPrefix1" name="addressPrefix1" placeholder="Apartment or suite" value="<%= v.repopulate("addressPrefix1", defaultShippingAddress.getAddressPrefix1()) %>">
                </div>

                <div class="row">
                  <div class="col-md-5 mb-3">
                    <label for="suburb">Suburb</label>
                    <input type="text" class="form-control" id="suburb" name="suburb" placeholder="Suburb" value="<%= v.repopulate("suburb", defaultShippingAddress.getSuburb()) %>">
                  </div>
                  <div class="col-md-4 mb-3">
                    <label for="state">State</label>
                    <select class="custom-select d-block w-100" id="state" name="state" value="<%= v.repopulate("state", defaultShippingAddress.getState()) %>">
                      <option value="">Choose...</option>
                      <% for (String state : Address.STATES) { %>
                      <option value="<%= state %>"><%= state %></option>
                      <% } %>
                    </select>
                  </div>
                  <div class="col-md-3 mb-3">
                    <label for="postcode">Postcode</label>
                    <input type="text" class="form-control" id="postcode" name="postcode" placeholder="" value="<%= v.repopulate("postcode", defaultShippingAddress.getPostcode()) %>">
                  </div>
                </div>

                <div class="row">
                   <div class="col-md-5 mb-3">
                    <label for="country">Country</label>
                    <select class="custom-select d-block w-100" id="country" name="country" value="<%= v.repopulate("country", defaultShippingAddress.getCountry()) %>">
                      <option value="">Choose...</option>
                      <% for (String country : Address.COUNTRIES) { %>
                      <option value="<%= country %>"><%= country %></option>
                      <% } %>
                    </select>
                  </div>
                </div>
                <hr class="mb-4">

                    <h4 class="mb-3">Payment</h4>

                    <div class="d-block my-3">
                        <div class="custom-control custom-radio">
                            <input id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked required>
                            <label class="custom-control-label" for="credit">Credit card</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="debit" name="paymentMethod" type="radio" class="custom-control-input" required>
                            <label class="custom-control-label" for="debit">Debit card</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="paypal" name="paymentMethod" type="radio" class="custom-control-input" required>
                            <label class="custom-control-label" for="paypal">PayPal</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="cc-name">Name on card</label>
                            <input type="text" class="form-control" id="cc-name" placeholder="" required>
                            <small class="text-muted">Full name as displayed on card</small>
                            <div class="invalid-feedback">
                                Name on card is required
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="cc-number">Credit card number</label>
                            <input type="text" class="form-control" id="cc-number" placeholder="" required>
                            <div class="invalid-feedback">
                                Credit card number is required
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 mb-3">
                            <label for="cc-expiration">Expiration</label>
                            <input type="text" class="form-control" id="cc-expiration" placeholder="" required>
                            <div class="invalid-feedback">
                                Expiration date required
                            </div>
                        </div>
                        <div class="col-md-3 mb-3">
                            <label for="cc-cvv">CVV</label>
                            <input type="text" class="form-control" id="cc-cvv" placeholder="" required>
                            <div class="invalid-feedback">
                                Security code required
                            </div>
                        </div>
                    </div>
                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">Continue to checkout</button>
                </form>
            </div>
        </div>
        <% } %>
        <hr>
    </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>
