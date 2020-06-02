<%-- 
    Document   : add_product
    Created on : 21/05/2020, 3:22:53 PM
    Author     : C_fin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.model.dao.DBProduct"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp" />
    <% 
        //Product product = (Product)session.getAttribute("product");
        Flash flash = Flash.getInstance(session);
    %>
    
    <main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">
        <%= flash.displayMessages() %>
    
    <h1>Management</h1>
    <h2>
        <a href="<%= URL.Absolute("product/add", request) %>">Add New Product</a>
        &nbsp;&nbsp;&nbsp;
        <a href="<%= URL.Absolute("product/view", request) %>">View All Products</a>
    </h2>
        <c:if test="${product != null}">
            <form action="update" method="post"></form>                                                              
        </c:if>
        <c:if test="${product == null}">
            <form action="add" method="post"></form>
        </c:if> 
        <caption>
            <h2>
                <c:if test="${product != null}">
                    Updating
                </c:if>
                <c:if test="${product == null}">
                    Adding
                </c:if>
            </h2>
        <table>    
        </caption>
                <c:if test="${product != null}">
                    <input type="hidden" name="id" value="<c:out value='${prouct.id}' />" />
                </c:if>
        <form>            
                <tr>  
                    <div class="form-group">
                        <th>Product Name </th>    
                        <td>
                            <input type="text" class="form-control" name="name" size="45" value="<c:out value='${product.name}' />"/>
                        </td>
                    </div>
                    
                    <div class="form-group">
                        <th>Price</th>
                        <td>
                             <input type="text" class="form-control" name="price" size="45" value="<c:out value='${product.price}' />"/>
                        </td>
                    </div>
                  
                    <div class="form-group">
                    <th>Initial Quantity</th>
                    <td>
                        <input type="text" class="form-control" name="initialQuantity" size="45" value="<c:out value='${product.initialQuantity}' />"/>
                    </td>
                    </div>
              
                    <div class="form-group">
                    <th>Description</th>
                    <td>
                        <input type="text" class="form-control" name="description" size="45" value="<c:out value='${product.description}' />"/>
                    </td>
                    </div>
                   
                    <div class="form-group">
                    <th>Image</th>
                    <td>
                        <input type="text" class="form-control" name="name" size="45" value="<c:out value='${product.image}' />"/>
                    </td>
                    </div>
                    
                    <div class="form-group">
                    <th>Category</th>
                    <td>
                        <input type="text" class="form-control" name="categoryId" size="45" value="<c:out value='${product.categoryId}' />"/>
                    </td>
                    </div>
                   
                    <div class="form-group">
                    <th>Current Quantity</th>
                    <td>
                        <input type="text" class="form-control" name="currentQuantity" size="45" value="<c:out value='${product.currentQuantity}' />"/>
                    </td>
                    </div>
                    
                    <div class="form-group">
                    <th>Created Date</th>
                    <td>
                        <input type="text" class="form-control" name="createdDate" size="45" value="<c:out value='${product.createdDate}' />"/>
                    </td>
                    </div>
                    
                    <div class="form-group">
                    <th>Modified Date</th>
                    <td>
                        <input type="text" class="form-control" name="modifiedDate" size="45" value="<c:out value='${product.modifiedDate}' />"/>
                    </td>
                    </div>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Save" />
                    </td>
                </tr>
                </table>
           
                </form>
            </div>       
<div style='padding-top: 100px'>
    <jsp:include page="footer.jsp" />
</div>
</html>


<!--
<div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-3">
            <label for="inputname">Product Name</label>
            <input type="text" class="form-control" name="name">
        </div> 
        <div class="form-group col-md-2">
            <label for="inputprice">Product Price</label>
            <input type="text" class="form-control" name="price">
        </div>
        <div class="form-group col-md-2">
            <label for="inputinitialquantity">Initial Quantity</label>
            <input type="initialquantity" class="form-control" name="initialQuantity">
        </div>
    </div>
    
    <div class="form-row" style='padding-left: 450px'>  
        <div class="form-group col-md-5">    
            <label for="inputdescription">Description</label>
            <input type="text" class="form-control" name="description" required="true">
        </div>
        <div class="form-group col-md-2">
            <label for="inputname">Image Link</label>
            <input type="text" class="form-control" name="name">
        </div>    
   </div>
      
    <div class="form-row" style='padding-left: 450px'>
        <div class="form-group col-md-2">
            <label for="inputprice">Category</label>
            <input type="text" class="form-control" name="categoryId">
        </div>
        <div class="form-group col-md-2">
            <label for="inputcurrentquantity">Current Quantity</label>
            <input type="text" class="form-control" name="currentQuantity">
        </div>
    </div>
     
      <div class="form-row" style='padding-left: 450px'>
          <div class="form-group col-md-2">
            <label for="createddate">Created Date</label>
            <input type="date" class="form-control" name="createdDate">
          </div>
          <div class="form-group col-md-2">
            <label for="modifieddate">Modified Date</label>
            <input type="date" class="form-control" name="modifiedDate">
          </div>
     </div>  
-->

