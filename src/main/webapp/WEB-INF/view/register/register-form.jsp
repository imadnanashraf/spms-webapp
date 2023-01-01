<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <title>SPMS REGISTRATION</title>
  </head>
  <body>
    <div class="container mt-5 bg-light w-75 rounded  shadow-lg "  >
       <div class="row p-3 pb-5">
        <div class="col">

            <form:form action="${pageContext.request.contextPath}/registration/processing"
			 modelAttribute="tempRegisterModel" method="Get">

                <div class="mb-3">
                    <label class="form-label">First Name</label>
                    <form:input path="firstName" placeholder="First Name(*)"  class="form-control"/>
                    <form:errors path="firstName" class="form-text" style="color:red;"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Last Name</label>
                    <form:input path="lastName" placeholder="last Name(*)" class="form-control"/>
			 		<form:errors path="lastName" class="form-text" style="color:red;"/>
                </div>

                <div class="mb-3">
                  <label class="form-label">Email address</label>
                  <form:input path="email" placeholder="Email(*)"  class="form-control"/>
			 		<form:errors path="email" class="form-text" style="color:red;"/>
			 		<span class="form-text" style="color:red;"/>${emailExists}</span>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <form:input type="password" path="password" placeholder="Password(*)" class="form-control"/>
			 		<form:errors path="password" class="form-text" style="color:red;" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Confirm Password</label>
                    <form:input type="password" path="matchingPassword" placeholder="Matching Password(*)" class="form-control"/>
			 <form:errors path="matchingPassword" class="form-text" style="color:red;" />
                </div>

                <div class="mb-5  mt-5">
                    
                    <div class="row">

                        <div class="col-sm-6 ">

                            <div>
                            
                                <form:select path="dept" class="form-select" aria-label="Default select example">
                                   <form:option value="">Department(*)</form:option>
                                    <form:option value="CSE">COMPUTER SCIENCE</form:option>
                                    <form:option value="CIVIL">CIVIL</form:option>
                                    <form:option value="ELECTRICAL">ELECTRICAL</form:option>
                                    <form:option value="E&C">ELECTRONICS & COMMUNICATION</form:option>
                                    <form:option value="MECHANICAL">MECHANICAL</form:option>
                                  </form:select>
                            </div>
                            <div class="mb-5">
                            	<form:errors path="dept" class="form-text" style="color:red;"/>
                            </div>
                            
                        </div>

                        <div class="col-sm-6 ">

                            <div>
                                <form:select path="basicRole" class="form-select" aria-label="Default select example">
                                   <form:option value="">Registration Type(*)</form:option>
                                    <form:option value="faculty">TEACHER</form:option>
                                    <form:option value="student">STUDENT</form:option>
                                  </form:select>
                            </div>
                            <div class="mb-5">
                            	<form:errors path="basicRole" class="form-text" style="color:red;"/>     
                            </div>
                            
                        </div>


                    </div>
                    
                </div>

                <button type="submit" class="btn" style="background-color: #6675df;color: white;">Submit</button>
              </form:form>

        </div>
       </div> 
        
            
    </div>



    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

   
  </body>
</html>
