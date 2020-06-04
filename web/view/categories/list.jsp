<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  List<ProductCategory> categories = (List<ProductCategory>)request.getAttribute("categories");
%>
<main role="main">
  <div style="margin-top: 50px;"></div>
  <div class="container">  
    <%= flash.displayMessages() %>
    
    <% if (categories == null || categories.size() == 0) { %>
    <h5>You have no categories</h5>
    <a href="<%= URL.Absolute("categories/add", request) %>" class="btn btn-primary">Add a category</a>
    <% } else { %>
        <table class="table table-striped">
          <thead>
            <tr>
              <th scope="col">Action</th>
              <th scope="col">Name</th>
              <th scope="col">Description</th>
              <th scope="col">Image</th>
            </tr>
          </thead>
          <tbody>
            <% for (ProductCategory category : categories) { %>
            <tr>
              <th>
                  <a href="<%= URL.Absolute("staff/categories/view/"+category.getId(), request) %>" class="btn btn-primary">View</a>
                  <a href="<%= URL.Absolute("staff/categories/edit/"+category.getId(), request) %>" class="btn btn-primary">Edit</a>
                  <a href="<%= URL.Absolute("staff/categories/delete/"+category.getId(), request) %>" class="btn btn-danger">Delete</a>
              </th>
              <td><%= category.getName() %></td>
              <td><%= category.getDescription()%></td>
              <td><%= category.getImage()%></td>
            </tr>
            <% } %>
          </tbody>
        </table>
    <% } %>
    <hr>
  </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>