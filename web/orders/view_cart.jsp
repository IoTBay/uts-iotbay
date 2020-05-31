<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
  <div class="row">
    <div class="col-md-12 order-md-2 mb-12">
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-muted">Your cart</span>
        <span class="badge badge-secondary badge-pill">3</span>
      </h4>
      <ul class="list-group mb-12">
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div class="col-md-1">
              <div class="btn-group" role="group" aria-label="Change item quantity">
                <a href="<%= URL.Absolute("/order/decrease_item/1/1", request) %>" class="btn btn-outline-dark">-</a>
                <a href="<%= URL.Absolute("/order/increase_item/1/1", request) %>" class="btn btn-outline-dark">+</a>
              </div>
          </div>
          <div class="col-md-10">
            <h6 class="my-0">Product name</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$12</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div class="col-md-1">
              <div class="btn-group" role="group" aria-label="Change item quantity">
                <a href="<%= URL.Absolute("/order/decrease_item/1/1", request) %>" class="btn btn-outline-dark">-</a>
                <a href="<%= URL.Absolute("/order/increase_item/1/1", request) %>" class="btn btn-outline-dark">+</a>
              </div>
          </div>
          <div class="col-md-10">
            <h6 class="my-0">Second product</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$8</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div class="col-md-1">
              <div class="btn-group" role="group" aria-label="Change item quantity">
                <a href="<%= URL.Absolute("/order/decrease_item/1/1", request) %>" class="btn btn-outline-dark">-</a>
                <a href="<%= URL.Absolute("/order/increase_item/1/1", request) %>" class="btn btn-outline-dark">+</a>
              </div>
          </div>
          <div class="col-md-10">
            <h6 class="my-0">Third item</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$5</span>
        </li>
        <li class="list-group-item d-flex justify-content-between">
          <span>Total (USD)</span>
          <strong>$20</strong>
        </li>
      </ul>
    </div>
  </div>
  <hr>
  <a href="<%= URL.Absolute("/order/checkout", request) %>" class="btn btn-primary">Checkout</a>
  <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="../footer.jsp" />
</html>