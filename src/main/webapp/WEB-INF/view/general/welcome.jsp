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

    <title>SPMS</title>
  </head>
  <body>
    <div class="container mt-5 bg-light w-75 rounded  shadow-lg "  >
            <div class="row p-5">
                <div class="col text-center p-2">
                        <h3 >STUDENT PERFORMANCE MONITORING SYSTEM <h3>      
                </div>
            </div>
            <div class="row pt-3 ">
                <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/student/studentPage" >
                                <img src="${pageContext.request.contextPath}/resources/images/student-logo.png" class="img-thumbnail rounded-circle center shadow-lg" alt="..." width="50%" height="50%">
                              </a> 
                              <h3 class="text-center p-3">Student</h3>   
                </div>
                <div class="col-sm-6 text-center">
                    <a href="${pageContext.request.contextPath}/faculty/facultyPage">
                        <img src="${pageContext.request.contextPath}/resources/images/teacher-logo.png" class="img-thumbnail rounded-circle shadow-lg" alt="..." width="50%" height="50%">
                    </a>
                    <h3 class="text-center p-2">Teacher</h3> 
                </div>
            </div>
            <div class="row mt-5 align-items-center" style="height:150px ;">
                <div class="col">
                    <div class="d-grid gap-2 col-6 mx-auto shadow-lg">
                        <a href="${pageContext.request.contextPath}/registration/registerNewUser" class="btn" style="background-color: #6675df;color: white;" >REGISTER NEW ACCOUNT</a>
                      </div>

                </div>
                
                
            </div>
            
            <div class="row align-items-center" style="height:150px ;">
                <div class="col">
                    <!-- Footer -->
            		<div class="copyright text-center my-auto">
                   		<span><a href="${pageContext.request.contextPath}/start/about"><h5>About &copy;</h5></a></span>
            		 </div>
            		<!-- End of Footer -->
                </div>
                
                
            </div>
            

    </div>



    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

   
  </body>
</html>
