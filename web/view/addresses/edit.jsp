<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
  Address address = (Address)request.getAttribute("address");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
 
    <div class="py-5 text-center">
        <h2>Edit your address</h2>
    </div>
    <form method="post" action="<%= URL.Absolute("addresses/edit/"+address.getId(), request) %>">
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Address Details</h4>
          <form class="needs-validation" novalidate>
        <!-- Shipping address form -->
        <div class="row">
            <div class="col-md-2 mb-3">
              <label for="streetNumber">Number</label>
              <input type="text" class="form-control" id="streetNumber" name="streetNumber" placeholder="1234" value="<%= v.repopulate("streetNumber", address.getStreetNumber()) %>" >
            </div>

            <div class="col-md-4 mb-3">
              <label for="streetName">Name</label>
              <input type="text" class="form-control" id="streetName" name="streetName" placeholder="Main" value="<%= v.repopulate("streetName", address.getStreetName()) %>">
            </div>

            <div class="col-md-3 mb-3">
              <label for="streetType">Type</label>
              <input type="text" class="form-control" id="streetType" name="streetType" placeholder="Street" value="<%= v.repopulate("streetType", address.getStreetType()) %>">
            </div>
        </div>

        <div class="mb-3">
          <label for="addressPrefix1">Address 2 <span class="text-muted">(Optional)</span></label>
          <input type="text" class="form-control" id="addressPrefix1" name="addressPrefix1" placeholder="Apartment or suite" value="<%= v.repopulate("addressPrefix1", address.getAddressPrefix1()) %>">
        </div>

        <div class="row">
          <div class="col-md-5 mb-3">
            <label for="suburb">Suburb</label>
            <input type="text" class="form-control" id="suburb" name="suburb" placeholder="Suburb" value="<%= v.repopulate("suburb", address.getSuburb()) %>">
          </div>
          <div class="col-md-4 mb-3">
            <label for="state">State</label>
            <select class="custom-select d-block w-100" id="state" name="state">
              <option value="">Choose...</option>
              <% for (String state : Address.STATES) { %>
              <option value="<%= state %>" <%= (v.repopulate("state", address.getState()).equals(state) ? "selected=\"selected\"" : "") %>><%= state %></option>
              <% } %>
            </select>
          </div>
          <div class="col-md-3 mb-3">
            <label for="postcode">Postcode</label>
            <input type="text" class="form-control" id="postcode" name="postcode" placeholder="" value="<%= v.repopulate("postcode", address.getPostcode()) %>">
          </div>
        </div>
          
        <div class="row">
           <div class="col-md-5 mb-3">
            <label for="country">Country</label>
            <select class="custom-select d-block w-100" id="country" name="country">
              <option value="">Choose...</option>
              <% for (String country : Address.COUNTRIES) { %>
              <option value="<%= country %>" <%= (v.repopulate("country", address.getCountry()).equals(country) ? "selected=\"selected\"" : "") %>><%= country %></option>
              <% } %>
            </select>
          </div>
        </div>
          
        <hr class="mb-4">
        <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" id="defaultShippingAddress" name="defaultShippingAddress" value="1" <%= (v.repopulate("defaultShippingAddress", address.getDefaultShippingAddress()).equals("1") ? "checked=\"checked\"" : "") %>>
          <label class="custom-control-label" for="defaultShippingAddress">Use this as my default shipping address</label>
        </div>
        <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" id="defaultBillingAddress" name="defaultBillingAddress" value="1" <%= (v.repopulate("defaultBillingAddress", address.getDefaultBillingAddress()).equals("1") ? "checked=\"checked\"" : "") %>>
          <label class="custom-control-label" for="defaultBillingAddress">Use this as my default billing address</label>
        </div>
        <hr class="mb-4">
        <input class="btn btn-primary" type="submit" name="doAdd" value="Add">
    </form>
        <hr class="mb-4">
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>