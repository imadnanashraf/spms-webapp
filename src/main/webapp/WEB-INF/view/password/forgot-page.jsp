 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>FORGOT PASSWORD</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/util.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<!--===============================================================================================-->
</head>
<body style="background-color: #666666;">
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
			
				<form:form  class="login100-form validate-form" 
				action="${pageContext.request.contextPath}/password/processing" 
				modelAttribute="tempPasswordModel" method="GET">
				
					<span class="login100-form-title p-b-43">
						Forgot Password
					</span>
					
					<div class="row mb-3">
						<div class="col">
							<div class="wrap-input100 " >
								<form:input path="email" class="input100" type="text"  placeholder="EMAIL"></form:input>
								<form:errors path="email"></form:errors>
								<span>${emailExist}</span>
							</div>
						</div>
					</div>
					
					
					<div class="row mb-3" >
						<div class="col">
							<form:select path="role" class="input100" aria-label="Default select example">
								<option value="">Account(*)</option>
								<option value="student">STUDENT</option>
								<option value="faculty">FACULTY</option>
							  </form:select>
							  <form:errors path="role"></form:errors>
						</div>
					</div>

					<div class="flex-sb-m w-full p-t-3 p-b-32">
						
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn primary">
							Proceed
						</button>
					</div>
					
					
					
				</form:form>

				<div class="login100-more" style='background-image: url("${pageContext.request.contextPath}/resources/images/bg-01.jpg");'>
				</div>
			</div>
		</div>
	</div>
	
	

</body>
</html>