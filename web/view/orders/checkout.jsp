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
                   <div class="col-md-12 mb-3">
                    <label for="shippingAddressSelect">Select Address</label>
                    <input type="hidden" name="shippingAddress" value="<%= v.repopulate("shippingAddress", defaultShippingAddress.getId()) %>">
                    <select class="custom-select d-block w-100" id="shippingAddressSelect" name="shippingAddressSelect">
                      <option value="-1">Add New...</option>
                      <% for (Address address : savedAddresses) { %>
                      <option value="<%= address.getId() %>" <%= (v.repopulate("shippingAddress", defaultShippingAddress.getId()).isEmpty() == false && Integer.parseInt(v.repopulate("shippingAddress", defaultShippingAddress.getId())) == address.getId() ? "selected=\"selected\"" : "") %>><%= address.getFullAddress() %></option>
                      <% } %>
                    </select>
                    <br>
                    <h6 class="mb-3">Or select "Add New" and input below.</h6>
                  </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                      <label for="shipping_streetNumber">Number</label>
                      <input type="text" class="form-control shippingField" id="shipping_streetNumber" name="shipping_streetNumber" placeholder="1234" value="<%= (defaultShippingAddress == null || defaultShippingAddress.getStreetNumber() == 0 ? "" : v.repopulate("shipping_streetNumber", defaultShippingAddress.getStreetNumber())) %>" >
                    </div>

                    <div class="col-md-4 mb-3">
                      <label for="shipping_streetName">Name</label>
                      <input type="text" class="form-control shippingField" id="shipping_streetName" name="shipping_streetName" placeholder="Main" value="<%= v.repopulate("shipping_streetName", defaultShippingAddress.getStreetName()) %>">
                    </div>

                    <div class="col-md-3 mb-3">
                      <label for="shipping_streetType">Type</label>
                      <input type="text" class="form-control shippingField" id="shipping_streetType" name="shipping_streetType" placeholder="Street" value="<%= v.repopulate("shipping_streetType", defaultShippingAddress.getStreetType()) %>">
                    </div>
                </div>

                <div class="mb-3">
                  <label for="shipping_addressPrefix1">Address 2 <span class="text-muted">(Optional)</span></label>
                  <input type="text" class="form-control shippingField" id="shipping_addressPrefix1" name="shipping_addressPrefix1" placeholder="Apartment or suite" value="<%= v.repopulate("shipping_addressPrefix1", defaultShippingAddress.getAddressPrefix1()) %>">
                </div>

                <div class="row">
                  <div class="col-md-5 mb-3">
                    <label for="shipping_suburb">Suburb</label>
                    <input type="text" class="form-control shippingField" id="shipping_suburb" name="shipping_suburb" placeholder="Suburb" value="<%= v.repopulate("shipping_suburb", defaultShippingAddress.getSuburb()) %>">
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="shipping_state">State</label>
                    <select class="custom-select d-block w-100 shippingField" id="shipping_state" name="shipping_state">
                      <option value="">Choose...</option>
                      <% for (String state : Address.STATES) { %>
                      <option value="<%= state %>" <%= (v.repopulate("shipping_state", defaultShippingAddress.getState()).equals(state) ? "selected=\"selected\"" : "") %>><%= state %></option>
                      <% } %>
                    </select>
                  </div>
                  <div class="col-md-3 mb-3">
                    <label for="shipping_postcode">Postcode</label>
                    <input type="text" class="form-control shippingField" id="shipping_postcode" name="shipping_postcode" placeholder="" value="<%= v.repopulate("shipping_postcode", defaultShippingAddress.getPostcode()) %>">
                  </div>

                   <div class="col-md-5 mb-3">
                    <label for="shipping_country">Country</label>
                    <select class="custom-select d-block w-100 shippingField" id="shipping_country" name="shipping_country">
                      <option value="">Choose...</option>
                      <% for (String country : Address.COUNTRIES) { %>
                      <option value="<%= country %>" <%= (v.repopulate("shipping_country", defaultShippingAddress.getCountry()).equals(country) ? "selected=\"selected\"" : "") %>><%= country %></option>
                      <% } %>
                    </select>
                  </div>
                </div>
                <hr class="mb-4">
                
                <!-- Billing Address -->
                <h4 class="mb-3">Billing Address</h4>
                <form class="needs-validation" novalidate>
                <!-- Billing address form -->
                <div class="row">
                   <div class="col-md-12 mb-3">
                    <label for="billingAddressSelect">Select Address</label>
                    <input type="hidden" name="billingAddress" value="<%= v.repopulate("billingAddress", defaultBillingAddress.getId()) %>">
                    <select class="custom-select d-block w-100" id="billingAddressSelect" name="billingAddressSelect">
                      <option value="-1">Add New...</option>
                      <% for (Address address : savedAddresses) { %>
                      <option value="<%= address.getId() %>" <%= (v.repopulate("billingAddress", defaultBillingAddress.getId()).isEmpty() == false && Integer.parseInt(v.repopulate("billingAddress", defaultBillingAddress.getId())) == address.getId() ? "selected=\"selected\"" : "") %>><%= address.getFullAddress() %></option>
                      <% } %>
                    </select>
                    <br>
                    <h6 class="mb-3">Or select "Add New" and input below.</h6>
                  </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                      <label for="billing_streetNumber">Number</label>
                      <input type="text" class="form-control billingField" id="billing_streetNumber" name="billing_streetNumber" placeholder="1234" value="<%= (defaultBillingAddress == null || defaultBillingAddress.getStreetNumber() == 0 ? "" : v.repopulate("billing_streetNumber", defaultBillingAddress.getStreetNumber())) %>" >
                    </div>

                    <div class="col-md-4 mb-3">
                      <label for="billing_streetName">Name</label>
                      <input type="text" class="form-control billingField" id="billing_streetName" name="billing_streetName" placeholder="Main" value="<%= v.repopulate("billing_streetName", defaultBillingAddress.getStreetName()) %>">
                    </div>

                    <div class="col-md-3 mb-3">
                      <label for="billing_streetType">Type</label>
                      <input type="text" class="form-control billingField" id="billing_streetType" name="billing_streetType" placeholder="Street" value="<%= v.repopulate("billing_streetType", defaultBillingAddress.getStreetType()) %>">
                    </div>
                </div>

                <div class="mb-3">
                  <label for="billing_addressPrefix1">Address 2 <span class="text-muted">(Optional)</span></label>
                  <input type="text" class="form-control billingField" id="billing_addressPrefix1" name="billing_addressPrefix1" placeholder="Apartment or suite" value="<%= v.repopulate("billing_addressPrefix1", defaultBillingAddress.getAddressPrefix1()) %>">
                </div>

                <div class="row">
                  <div class="col-md-5 mb-3">
                    <label for="billing_suburb">Suburb</label>
                    <input type="text" class="form-control billingField" id="billing_suburb" name="billing_suburb" placeholder="Suburb" value="<%= v.repopulate("billing_suburb", defaultBillingAddress.getSuburb()) %>">
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="billing_state">State</label>
                    <select class="custom-select d-block w-100 billingField" id="billing_state" name="billing_state">
                      <option value="">Choose...</option>
                      <% for (String state : Address.STATES) { %>
                      <option value="<%= state %>" <%= (v.repopulate("billing_state", defaultBillingAddress.getState()).equals(state) ? "selected=\"selected\"" : "") %>><%= state %></option>
                      <% } %>
                    </select>
                  </div>
                  <div class="col-md-3 mb-3">
                    <label for="billing_postcode">Postcode</label>
                    <input type="text" class="form-control billingField" id="billing_postcode" name="billing_postcode" placeholder="" value="<%= v.repopulate("billing_postcode", defaultBillingAddress.getPostcode()) %>">
                  </div>

                   <div class="col-md-5 mb-3">
                    <label for="billing_country">Country</label>
                    <select class="custom-select d-block w-100 billingField" id="billing_country" name="billing_country">
                      <option value="">Choose...</option>
                      <% for (String country : Address.COUNTRIES) { %>
                      <option value="<%= country %>" <%= (v.repopulate("billing_country", defaultBillingAddress.getCountry()).equals(country) ? "selected=\"selected\"" : "") %>><%= country %></option>
                      <% } %>
                    </select>
                  </div>
                </div>
                <hr class="mb-4">
                
                <!-- Payment method -->
                
                    <h4 class="mb-3">Payment</h4>

                    <div class="d-block my-3">
                        <div class="custom-control custom-radio">
                            <input id="credit" name="paymentType" type="radio" value="1" class="custom-control-input" checked required>
                            <label class="custom-control-label" for="credit">Credit card</label>
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
                    <hr class="mb-4">
                </form>
            </div>
        </div>
        <% } %>
        <hr>
    </div> <!-- /container -->
</main>
<script>
$(document).ready(function() {
    var shippingAddress = $('input[name="shippingAddress"]');
    var shippingAddressSelect = $('#shippingAddressSelect');
    
    var billingAddress = $('input[name="billingAddress"]');
    var billingAddressSelect = $('#billingAddressSelect');
    //Run on form load
    setShippingAddressFields(shippingAddressSelect.val());
    setBillingAddressFields(billingAddressSelect.val());
    
    //Run on address field change
    $('#shippingAddressSelect').on('change', function () {
        shippingAddress.val(shippingAddressSelect.val()); //Controller uses this to decide whether to create new address.
        setShippingAddressFields(shippingAddressSelect.val()); //Update whether address fields should be disabled
    });
    
    $('#billingAddressSelect').on('change', function () {
        billingAddress.val(billingAddressSelect.val()); //Controller uses this to decide whether to create new address.
        setBillingAddressFields(billingAddressSelect.val()); //Update whether address fields should be disabled
    });
    
    function setShippingAddressFields(selectedShippingValue) {
        if (selectedShippingValue === "-1")
        {
            $('.shippingField').prop("disabled", false);
            $('#shipping_streetNumber').val('');
            $('#shipping_streetName').val('');
            $('#shipping_streetType').val('');
            $('#shipping_addressPrefix1').val('');
            $('#shipping_suburb').val('');
            $('#shipping_state').val('');
            $('#shipping_postcode').val('');
            $('#billing_country').val('');
        }
        else
        {
            $('.shippingField').prop("disabled", true);
        }
    }
    
    function setBillingAddressFields(selectedBillingValue) {
        if (selectedBillingValue === "-1")
        {
            $('.billingField').prop("disabled", false);
            $('#billing_streetNumber').val('');
            $('#billing_streetName').val('');
            $('#billing_streetType').val('');
            $('#billing_addressPrefix1').val('');
            $('#billing_suburb').val('');
            $('#billing_state').val('');
            $('#billing_postcode').val('');
            $('#billing_country').val('');
        }
        else
        {
            $('.billingField').prop("disabled", true);
        }
    }
});
</script>
<jsp:include page="../../footer.jsp" />
</html>
