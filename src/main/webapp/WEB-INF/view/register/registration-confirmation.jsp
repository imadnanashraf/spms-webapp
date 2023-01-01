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

    <title>SPMS CONFIRMATION</title>
  </head>
  <body>
    <div class="container mt-5 bg-light w-75 rounded  shadow-lg "  >
        <div class="row pt-5">
            <div class="col-sm-12 text-center">
                    <img src="${pageContext.request.contextPath}/resources/images/messagelogo.png" class="img-thumbnail rounded-circle center shadow-lg" alt="..." width="50%" height="50%">
            </div>
            <div class="col text-center mt-4 mb-5">
                <h2>THANK YOU FOR YOUR REGISTRATION AS ${role}</h2>
            </div>
        </div>
        
        <div class="row pb-5">
            <div class="col">
                <div class="d-grid gap-2 col-4 mx-auto shadow-lg">
                    <a href="${pageContext.request.contextPath}/start/homePage" class="btn" style="background-color: #6675df;color: white;" >Continue</a>
                </div>

            </div>
        </div>
        
            
    </div>



    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

   
  </body>
</html>
