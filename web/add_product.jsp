<%-- 
    Document   : add_product
    Created on : 21/05/2020, 3:22:53 PM
    Author     : C_fin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Management</title>
    </head>
    <form>
  <div class="form-group row">
    <label for="inputname" class="col-sm-2 col-form-label">Product Name</label>
    <div class="col-sm-10">
      <input type="name" class="form-control" id="inputname" placeholder="Enter name of product here">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputdescription" class="col-sm-2 col-form-label">Description</label>
    <div class="col-sm-10">
      <input type="description" class="form-control" id="inputdescription" placeholder="Description for product">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputinitialquantity" class="col-sm-2 col-form-label">Initial Quantity</label>
    <div class="col-sm-10">
      <input type="initialquantity" class="form-control" id="inputinitialquantity" placeholder="Intial quantity for product">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputprice" class="col-sm-2 col-form-label">Price</label>
    <div class="col-sm-10">
      <input type="float" class="form-control" id="inputprice" placeholder="Price per unit">
    </div>
  </div>
  <div class="form-group row">
    <label for="inputimage" class="col-sm-2 col-form-label">Image?</label>
    <div class="col-sm-10">
      <input type="image" class="form-control" id="inputimage" placeholder="image link">
    </div>
  </div>
  <div class="form-group row">
    <div class="col-sm-10">
      <button type="submit" class="btn btn-primary">Submit form</button>
    </div>
  </div>
</form>
<jsp:include page="footer.jsp" />
</html>
