<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.*"%>

<jsp:include page="header.jsp" />
<%
    User user = (User)session.getAttribute("user");
    Customer customer = (Customer)session.getAttribute("customer");
    boolean isLoggedIn = (user != null);
    
    Validator v = new Validator(session);
%>

<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">
  <%
      if (!isLoggedIn) {
  %>
    <p>Sorry, you're not logged in! <a href="<%= URL.Absolute("user/register", request) %>">Register</a> or <a href="<%= URL.Absolute("user/login", request) %>">login</a> to see this page.</p>
  <% } else { %>
    <p><%= customer.getFirstName() %>, please update your profile details below:</p>
    <form method="post" action="<%= URL.Absolute("user/edit", request) %>">
        <div class="form-group row">
          <label for="firstName" class="col-sm-2 col-form-label">First Name</label>
          <div class="col-sm-10">
            <input type="text" class="form-control col-md-5" id="firstName" name="firstName" value="<%= v.repopulate("firstName", customer.getFirstName()) %>">
          </div>
        </div>
          
        <div class="form-group row">
          <label for="lastName" class="col-sm-2 col-form-label">Last Name</label>
          <div class="col-sm-10">
            <input type="text" class="form-control col-md-5" id="lastName" name="lastName" value="<%= v.repopulate("lastName", customer.getLastName()) %>">
          </div>
        </div>
          
        <div class="form-group row">
          <label for="email" class="col-sm-2 col-form-label">Email</label>
          <div class="col-sm-10">
              <input type="email" class="form-control col-md-5" id="email" name="email" value="<%= v.repopulate("email", customer.getEmail()) %>">
          </div>
        </div>
          
        <row class="row">
            <div class="col-md-6 mb-3">
              <label for="dob_dd">Birth Date</label>
              <%
                  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                  String dateString = dateFormat.format(user.getBirthDate());
                  //https://www.geeksforgeeks.org/split-string-java-examples/
                  String[] dateParts = dateString.split("-");
              %>
              <div class="input-group">
                <input type="text" class="form-control col-md-2" id="dob_dd" name="dob_dd" placeholder="DD" value="<%= dateParts[2] %>" required>
                <div class="input-group-append">
                  <div class="input-group-text">/</div>
                  <input type="text" class="form-control col-md-2" id="dob_mm" name="dob_mm" placeholder="MM" value="<%= dateParts[1] %>" required>
                  <div class="input-group-append">
                    <div class="input-group-text">/</div>
                  </div>
                  <input type="text" class="form-control col-md-2" id="dob_yyyy" name="dob_yyyy" placeholder="YYYY" value="<%= dateParts[0] %>" required>
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
                <input class="form-check-input" type="radio" name="sex" id="gender1" value="1" <%= (user.getSex().equals("Male") ? "checked" : "") %>>
              <label class="form-check-label" for="gender1">
                Male
              </label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="sex" id="gender2" value="2" <%= (user.getSex().equals("Female") ? "checked" : "") %>>
              <label class="form-check-label" for="gender2">
                Female
              </label>
            </div>
          </div>
        </div>
        <hr>
        <input type="submit" name="doUpdate" value="Update" class="btn btn-primary">

       
    </form>
  <% } %>
    
    <hr>
  
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>