<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
  //We need to figure out if the user is logging out now, or not.
  //then invalidate the session BEFORE including the header, so it shows correctly.
  User user = (User)session.getAttribute("user");
  Customer customer = (Customer)session.getAttribute("customer");
  //Store for later
  boolean isLoggedIn = (user != null);
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
    <%
        if (isLoggedIn) {
    %>
        <p>You are already logged in. Please go <a href="<%= URL.Absolute("", request) %>">here</a>.</p>
    <%
    } else {
    %>
    <div class="py-5 text-center">
        <h2>Registration form</h2>
        <p class="lead">Hi there! We hope you like IOT Bay. Please create an account for a wealth of user-only features like saved addresses.</p>
    </div>
    <form method="post" action="<%= URL.Absolute("user/register", request) %>">
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">User Details</h4>
          <form class="needs-validation" novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">First name</label>
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="" value="" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="" value="" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="phone">Phone</label>
                <input type="text" class="form-control" id="phone" name="phone" placeholder="" value="" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
            </div>

            <row class="row">
                <div class="col-md-6 mb-3">
                  <label for="email">Email</label>
                  <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" required>
                  <div class="invalid-feedback">
                    Please enter a valid email address for your account.
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="password">Password</label>
                  <input type="password" class="form-control" id="password" name="password" required>
                  <div class="invalid-feedback">
                    Please enter a strong password for your account.
                  </div>
                </div>
            </div>
        
            <row class="row">
                <div class="col-md-6 mb-3">
                  <label for="dob_dd">Birth Date</label>
                  <div class="input-group">
                    <input type="text" class="form-control col-md-2" id="dob_dd" name="dob_dd" placeholder="DD" required>
                    <div class="input-group-append">
                      <div class="input-group-text">/</div>
                      <input type="text" class="form-control col-md-2" id="dob_mm" name="dob_mm" placeholder="MM" required>
                      <div class="input-group-append">
                        <div class="input-group-text">/</div>
                      </div>
                      <input type="text" class="form-control col-md-2" id="dob_yyyy" name="dob_yyyy" placeholder="YYYY" required>
                    </div>
                  </div>
                  <div class="invalid-feedback">
                    Please enter a valid date of birth for your account.
                  </div>
                </div>
            </row>
        
            <div class="row">
              <legend class="col-form-label col-sm-2 pt-0">Gender</legend>
              <div class="col-sm-10">
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="sex" id="gender1" value="1" checked>
                  <label class="form-check-label" for="gender1">
                    Male
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="sex" id="gender2" value="2">
                  <label class="form-check-label" for="gender2">
                    Female
                  </label>
                </div>
              </div>
            </div>

        <!-- Shipping address form
        <div class="row">
            <div class="col-md-2 mb-3">
              <label for="streetNumber">Number</label>
              <input type="text" class="form-control" id="streetNumber" name="streetNumber" placeholder="1234" required>
              <div class="invalid-feedback">
                Please enter your shipping address street number.
              </div>
            </div>

            <div class="col-md-4 mb-3">
              <label for="streetName">Name</label>
              <input type="text" class="form-control" id="streetName" name="streetName" placeholder="Main" required>
              <div class="invalid-feedback">
                Please enter your shipping address street name.
              </div>
            </div>

            <div class="col-md-3 mb-3">
              <label for="streetType">Type</label>
              <input type="text" class="form-control" id="streetType" name="streetType" placeholder="Street" required>
              <div class="invalid-feedback">
                Please enter your shipping address street type.
              </div>
            </div>
        </div>

        <div class="mb-3">
          <label for="addressPrefix1">Address 2 <span class="text-muted">(Optional)</span></label>
          <input type="text" class="form-control" id="addressPrefix1" name="addressPrefix1" placeholder="Apartment or suite">
        </div>

        <div class="row">
          <div class="col-md-5 mb-3">
            <label for="country">Country</label>
            <select class="custom-select d-block w-100" id="country" name="country" required>
              <option value="">Choose...</option>
              <option value="Australia">Australia</option>
            </select>
            <div class="invalid-feedback">
              Please select a valid country.
            </div>
          </div>
          <div class="col-md-4 mb-3">
            <label for="state">State</label>
            <select class="custom-select d-block w-100" id="state" name="state" required>
              <option value="">Choose...</option>
              <option value="New South Wales">New South Wales</option>
            </select>
            <div class="invalid-feedback">
              Please provide a valid state.
            </div>
          </div>
          <div class="col-md-3 mb-3">
            <label for="postcode">Postcode</label>
            <input type="text" class="form-control" id="postcode" name="postcode" placeholder="" required>
            <div class="invalid-feedback">
              Post code required.
            </div>
          </div>
        </div>
        -->
          
        <!--
        Use this later
        <hr class="mb-4">
        <div class="custom-control custom-checkbox">
          <input type="checkbox" class="custom-control-input" id="same-address">
          <label class="custom-control-label" for="same-address">Shipping address is the same as my billing address</label>
        </div>
        <div class="custom-control custom-checkbox">
          <input type="checkbox" class="custom-control-input" id="save-info">
          <label class="custom-control-label" for="save-info">Save this information for next time</label>
        </div>
        <hr class="mb-4">
        -->

        <!-- Payment form
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
        -->
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block col-md-6" name="doRegister" value="doRegister" type="submit">Register</button>
    </form>
    <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>