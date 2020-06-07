<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
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
  
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
%>

<jsp:include page="header.jsp" />

<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <%= flash.displayMessages() %>

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
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="" value="<%= v.repopulate("firstName") %>" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="" value="<%= v.repopulate("lastName") %>" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="phone">Phone</label>
                <input type="text" class="form-control" id="phone" name="phone" placeholder="" value="<%= v.repopulate("phone") %>" required>
              </div>
            </div>

            <row class="row">
                <div class="col-md-6 mb-3">
                  <label for="email">Email</label>
                  <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" value="<%= v.repopulate("email") %>" required>
                  <div class="invalid-feedback">
                    Please enter a valid email address for your account.
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="password">Password</label>
                  <input type="password" class="form-control" id="password" name="password" value="<%= v.repopulate("password") %>" required>
                  <div class="invalid-feedback">
                    Please enter a strong password for your account.
                  </div>
                </div>
            </div>
        
            <row class="row">
                <div class="col-md-6 mb-3">
                <label for="staffcode">If you are a staff member, please enter your staff code</label>
                <input type="text" class="form-control" id="staffcode" name="staffcode" placeholder="" value="">
              </div>
            </row>
        
            <row class="row">
                <div class="col-md-6 mb-3">
                  <label for="dob_dd">Birth Date</label>
                  <div class="input-group">
                      <input type="text" class="form-control col-md-2" id="dob_dd" name="dob_dd" placeholder="DD" value="<%= v.repopulate("dob_dd") %>">
                    <div class="input-group-append">
                      <div class="input-group-text">/</div>
                      <input type="text" class="form-control col-md-2" id="dob_mm" name="dob_mm" placeholder="MM" value="<%= v.repopulate("dob_mm") %>">
                      <div class="input-group-append">
                        <div class="input-group-text">/</div>
                      </div>
                      <input type="text" class="form-control col-md-2" id="dob_yyyy" name="dob_yyyy" placeholder="YYYY" value="<%= v.repopulate("dob_yyyy") %>">
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
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block col-md-6" name="doRegister" value="doRegister" type="submit">Register</button>
    </form>
    <% } %>
        <hr>
    </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>