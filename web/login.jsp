<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>

    <form class="form-signin" method="post" action="<%= URL.Absolute("user/login", request) %>">
        <svg class="bi bi-cloud" width="5em" height="5em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" d="M4.887 7.2l-.964-.165A2.5 2.5 0 103.5 12h10a1.5 1.5 0 00.237-2.981L12.7 8.854l.216-1.028a4 4 0 10-7.843-1.587l-.185.96zm9.084.341a5 5 0 00-9.88-1.492A3.5 3.5 0 103.5 13h9.999a2.5 2.5 0 00.394-4.968c.033-.16.06-.324.077-.49z" clip-rule="evenodd"/>
        </svg>
        <span style="font-size: 40pt;">&nbsp;IOTBay</span>
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <label for="email" class="sr-only">Email address</label>
        <input type="text" id="email" name="email" class="form-control" placeholder="Email address" value="<%= v.repopulate("email") %>" autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" value="<%= v.repopulate("password") %>">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
    <hr>
  </div> <!-- /container -->

</main>
<jsp:include page="footer.jsp" />
</html>