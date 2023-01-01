<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <div class="row">
                <div class="col-sm-12 mt-4">
                    <h3 class="mx-4">About:</h3>
                    <p class="mx-4">
                        
                        Student's Performance Monitoring System allows academic institutions to monitor and gather data about the academic performance of students where decisions are derived to further improve the students' learning outcomes.The manual tracking and monitoring takes time and may encounter human errors resulting in obtaining incorrect information that will affect the decisions made. To avoid these problems, this project will automate the process and teachers will utilize the system from giving assessments up to recording the scores of the students. The system will be reliable in collecting accurate information to help educational institutions arrived at decisions that will help students to improve their academic performance. The segregation is done on the basis of previous year results, class performance(which comprises of attendance, attentiveness and behavior) and mid semester exams. All these factors contribute to the overall percentage.
        
                    </p>
                </div>
            </div>

            <div class="row mt-4 mb-4">
              <div class="col-sm-2">

              </div>
              <div class="col-sm-8 ">
                <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                      <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                      
                      <c:forEach var="picNumber" items="${picNumber}">
                      	<button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${picNumber}" aria-label="Slide ${picNumber+1}"></button>
                      </c:forEach>
                      
                    </div>
                    <div class="carousel-inner">
                      <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/resources/images/project/projectpic (1).png" class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                      
                        </div>
                      </div>
                      <c:forEach var="picNumber" items="${picNumber}">
                      	<div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/resources/images/project/projectpic (${picNumber+1}).png" class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                          
                        </div>
                      </div>
                      
                      </c:forEach>
                      
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                      <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                      <span class="carousel-control-next-icon" aria-hidden="true"></span>
                      <span class="visually-hidden">Next</span>
                    </button>
                  </div>
            </div>
            <div class="col-sm-2">

            </div>
            </div>
            
            <div class="row">
                <div class="col-sm-4 mt-4">
                    <div class="card mx-auto" style="width: 12rem;">
                        <img src="${pageContext.request.contextPath}/resources/images/user/user1.jpg"  class="card-img-top" alt="...">
                        <div class="card-body">
                          <p class="card-text ">
                            <h4 class="text-center">Adnan Ashraf</h4>
                            <h5 class="text-center">CSE</h5>
                            <h5 class="text-center">SSM COET</h5>
                            <h5 class="text-center">Batch: 2018</h5>
                          </p>
                        </div>
                      </div>
                </div>

                <div class="col-sm-4 mt-4">
                    <div class="card mx-auto" style="width: 12rem;">
                        <img src="${pageContext.request.contextPath}/resources/images/user/user2.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                          <p class="card-text ">
                            <h4 class="text-center">Faria Farheen</h4>
                            <h5 class="text-center">CSE</h5>
                            <h5 class="text-center">SSM COET</h5>
                            <h5 class="text-center">Batch: 2018</h5>
                          </p>
                        </div>
                      </div>
                    
                </div>

                <div class="col-sm-4 mt-4">
                    <div class="card mx-auto" style="width: 12rem;">
                        <img src="${pageContext.request.contextPath}/resources/images/user/user3.jpeg" class="card-img-top" alt="...">
                        <div class="card-body">
                          <p class="card-text ">
                            <h4 class="text-center">Syed Dawar</h4>
                            <h5 class="text-center">CSE</h5>
                            <h5 class="text-center">SSM COET</h5>
                            <h5 class="text-center">Batch: 2018</h5>
                          </p>
                        </div>
                      </div>
                    
                </div>
            </div>
            <div class="row mt-5 align-items-center" style="height:150px ;">
                <div class="col">
                    <div class="d-grid gap-2 col-4 mx-auto shadow-lg">
                        <a href="${pageContext.request.contextPath}/start/homePage" class="btn" style="background-color: #6675df;color: white;" >Back To Home Page</a>
                      </div>

                </div>
            </div>
            
    </div>



    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

   
  </body>
</html>
