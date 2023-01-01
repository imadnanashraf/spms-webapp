 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
        body{
            background-image: url("${pageContext.request.contextPath}/resources/images/bgpurple-image.jpg");
        }
    </style>

    <title>CHANGE PASSWORD</title>
  </head>
  <body>
   
	<form:form action="${pageContext.request.contextPath}/password/change" modelAttribute="passwordModel" method="GET">	
			
		   <input type="hidden" name="email" value="${email}">
	       <input type="hidden" name="changeType" value="${changeType}">
			
			<div class="container py-5 h-100">
			  <div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-md-12 col-xl-4">
		  
				  <div class="card" style="border-radius: 15px;">
					<div class="card-body text-center">
					  <div class="mt-3 mb-4">
						<h4>Change Password</h4>
					  </div>
					 
					  <div class="mb-3">
						<form:input path="password" type="password" class="form-control" placeholder="Password"/>
					  	<form:errors path="password" style="color:red;"></form:errors>
					  </div>

					  <div class="mb-3">
						<form:input path="matchingPassword" type="password" class="form-control" placeholder="Confirm Password"/>
					  	<form:errors path="matchingPassword" style="color:red;"></form:errors>
					  </div>

					  
					  <button type=submit class="btn mb-5" style="background-color: #6675df;color: white;" >
						PROCEED
					  </button>
					  
					</div>
				  </div>
		  
				</div>
			  </div>
			</div>
            
   </form:form>



    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

   
  </body>
</html>
