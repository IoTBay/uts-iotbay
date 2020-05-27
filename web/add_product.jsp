<%-- 
    Document   : add_product
    Created on : 21/05/2020, 3:22:53 PM
    Author     : C_fin
--%>

<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Management</title>
    </head>
    <form action="ProductsController.java" method="post">
  <div class="form-group row">
    <label for="inputname" class="col-sm-2 col-form-label">Product Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" placeholder="Enter name of product here" name="name" required="true">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputdescription" class="col-sm-2 col-form-label">Description</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" placeholder="Description for product" name="description" required="true">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputinitialquantity" class="col-sm-2 col-form-label">Initial Quantity</label>
    <div class="col-sm-10">
        <input type="initialquantity" class="form-control" placeholder="Intial quantity for product" name="initialQuantity" required="true">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputprice" class="col-sm-2 col-form-label"  >Price</label>
    <div class="col-sm-10">
      <input type="float" class="form-control" placeholder="Price per unit" name="price">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputid" class="col-sm-2 col-form-label">Id</label>
    <div class="col-sm-10">
      <input type="id" class="form-control" placeholder="id" name="id">
    </div>
  </div>
  <div class="form-group row">
    <div class="col-sm-10">
      <input type="submit" class="btn btn-primary" value="Sign up">
    </div>
  </div>
</form>
<jsp:include page="footer.jsp" />
</html>
