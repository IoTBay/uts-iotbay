<%@page import="java.util.List"%>
<%@page import="uts.isd.validation.Validator"%>
<%@page import="uts.isd.util.Flash"%>
<%@page import="uts.isd.util.URL"%>
<%@page import="uts.isd.model.*"%> 
<jsp:include page="../../header.jsp" />
<%
  Flash flash = Flash.getInstance(session);
  Validator v = new Validator(session);
%>
<main role="main">
    <div style="margin-top: 50px;"></div>
    <div class="container">  
        <%= flash.displayMessages()%>

        <div class="py-5 text-center">
            <h2>Add a category</h2>
            <p class="lead">Please add a category to contain products.</p>
        </div>
        <form method="post" action="<%= URL.Absolute("staff/categories/add", request)%>">
            <div class="col-md-12">
                <h4 class="mb-3">Category Details</h4>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="<%= v.repopulate("name")%>" >
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-10 mb-3">
                        <label for="description">Description</label>
                        <input type="text" class="form-control" id="description" name="description" placeholder="Description" value="<%= v.repopulate("description")%>">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label for="image">Image</label>
                        <input type="text" class="form-control" id="image" name="image" placeholder="Image Path" value="<%= v.repopulate("image")%>">
                    </div>
                </div>
                <hr class="mb-4">
                <input class="btn btn-primary" type="submit" name="doAdd" value="Add">
            </div>
        </form>
        <hr class="mb-4">
    </div> <!-- /container -->
</main>
<jsp:include page="../../footer.jsp" />
</html>