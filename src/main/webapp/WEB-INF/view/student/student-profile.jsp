<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SPMS</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    
    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resources/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/student/studentPage">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">SPMS <sup>2</sup></div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/studentPage">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>HOME</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/studentProfile">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    <span>PROFILE</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

             <!-- Nav Item - Dashboard -->
             <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/studentMarks">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>ADD MARKS</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/inbox">
                    <i class="fas fa-envelope fa-fw"></i>
                    <span>MESSAGES</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Dashboard -->
             <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/studentNotification">
                    <i class="fas fa-bell fa-fw"></i>
                    <span>NOTIFICATION</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/studentEvaluation">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>EVALUATION</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!--Sideba Toggle-->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                        
                         <!-- Nav Item - Alerts -->
                        <li class="nav-item  no-arrow mr-3 mx-1">
                            <a class="nav-link " href="${pageContext.request.contextPath}/student/studentNotification" role="button">
                            <div>
                                <i class="fas fa-bell fa-fw"></i>
                                    <!-- Counter - Alerts -->
                                <c:if test="${newNotification == true }">
                                <span class="position-absolute mt-4 top-0 start-100 translate-middle badge badge- bg-danger">
                                        New
                                </span>
                                </c:if>
                            </div>
                            </a> 
                        
                        </li>
                        

                        <!-- Nav Item - Messages -->
                        <li class="nav-item  no-arrow mr-3 mx-1">
                            <a class="nav-link " href="${pageContext.request.contextPath}/student/inbox" role="button">
                            <div>
                                <i class="fas fa-envelope fa-fw"></i>
                                     <!-- Counter - Alerts -->
                                <c:if test="${newMessage == true }">
                                <span class="position-absolute mt-4 top-0 start-100 translate-middle badge badge- bg-danger">
                                        New
                                </span>
                                </c:if>
                            </div>
                            </a> 
                        
                        </li>

                       
                        <div class="topbar-divider d-none d-sm-block"></div>

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="navDown" role="button"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><sec:authentication property="principal.username"></sec:authentication></span>
                                <img class="img-profile rounded-circle"
                                    src="${pageContext.request.contextPath}/resources/profile/${currentStudent.profilePic}">
                            </a>
                            <!-- Dropdown - User Information -->
                            <ul class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="navDown">
                                
                                <li><a class="dropdown-item " href="${pageContext.request.contextPath}/student/logout">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>Logout</a></li>
                                
                            </ul>
                        </li>

                        

                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Profile</h1>
                        
                    </div>

                    <!-- Content Row -->
                    <!--form area-->
         
                        <div class="row">

                            <!-- Picture Area -->
                            <div class="col-xl-4 col-lg-5 p-1">
                            
                            <form:form action="${pageContext.request.contextPath}/student/imageUpdating" enctype="multipart/form-data" method="post">
                                <div class="card shadow mb-4 ">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary text-center">Profile Pic</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="row pt-3">
                                            
                                            <div class="col-6 mx-auto">
                                                <a href="${pageContext.request.contextPath}/resources/profile/${currentStudent.profilePic}">
                                                    <img src="${pageContext.request.contextPath}/resources/profile/${currentStudent.profilePic}" class="img-thumbnail rounded-circle shadow-lg" alt="..." width="200px" height="200px">
                                                </a>
                                            </div>
                                            <div class="mb-3 mt-4">
                                                <input name="image" class="form-control" type="file" accept="image/jpeg, image/jpg" id="formFile">
                                                <span class="form-text" style="color:red;"/>${message}</span>
                                            </div>
                                            <div class="mb-1 mt-1">
                                                    <button type="submit" class="btn btn-primary">Upload</button>  
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </form:form>
                            
                            </div>
                            <!--Form details-->
                            <div class="col-xl-8 col-lg-7">
                            
							 <form:form action="${pageContext.request.contextPath}/student/profileUpdating" modelAttribute="profileModel" method="GET">
                                <div class="card shadow mb-4 ">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary text-center">User Details</h6>
                                    </div>
                                    <div class="card-body">
                                        

                                            <div class="mb-3">
                                                <label class="form-label">First Name</label>
                                                <form:input type="text" class="form-control" path="firstName"/>
                                                <form:errors class="form-text" style="color:red;" path="firstName"/>
                                                <div>
                                                    
                                                </div>
                                            </div>
                            
                                            <div class="mb-3">
                                                <label class="form-label">Last Name</label>
                                                <form:input type="text" class="form-control" path="lastName"/>
                                                <form:errors class="form-text" style="color:red;" path="lastName"/>
                                                
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Contact No</label>
                                                <form:input type="text" class="form-control" path="contactNo" placeholder="Personal Number"/>
                                                <form:errors class="form-text" style="color:red;" path="contactNo"/>
                                                
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Father's Name</label>
                                                <form:input type="text" class="form-control" path="fatherName" placeholder="Father's Full Name"/>
                                                <form:errors class="form-text" style="color:red;" path="fatherName"/>
                                                
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label class="form-label">Guardian Contact No</label>
                                                <form:input type="text" class="form-control" path="guardianContactNo" placeholder="Father's Number/ Guardian's Number"/>
                                                <form:errors class="form-text" style="color:red;" path="guardianContactNo"/>
                                                
                                            </div>
                            
                                            <div class="mb-3">
                                                <fieldset disabled>
                                                <label for="disabledTextInput" class="form-label">Email</label>
                                                <input type="text" id="disabledTextInput" class="form-control" placeholder="${currentStudent.email}"/>
                                                </fieldset>

                                            </div>
                            
                                            <div class="mb-3">
                                                <label class="form-label">University Enroll</label>
                                                <form:input type="text" class="form-control" path="universityEnroll"/>
                                                <form:errors class="form-text" style="color:red;" path="universityEnroll"/>
                                                <span class="form-text" style="color:red;"/>${enrollExists}</span>
                                            </div>
                            
                                            <div class="mb-3">
                                                <label class="form-label">Batch</label>
                                                <form:input type="text" class="form-control" path="batch"/>
                                                <form:errors class="form-text" style="color:red;" path="batch"/>
                                            </div>
                            
                                            <div class="mb-5  mt-5">
                                                
                                                <div class="row">
                            
                                                    <div class="col-sm-6 ">
                            
                                                        <fieldset disabled>
                                                            <label for="disabledTextInput" class="form-label">Department</label>
                                                            <input type="text" id="disabledTextInput" class="form-control" placeholder="${currentStudent.dept}" />
                                                        </fieldset>
                                                        
                                                    </div>
                            
                                                    <div class="col-sm-6 ">
                           
                                                        <label for="disabledTextInput" class="form-label">Account Type</label>
                                                        <div type="text"  class="form-control"
                                                            style="overflow-y:scroll">
                                                            <sec:authentication property="principal.authorities"/>
                                                        </div>
                                                    </div>
                            
                            
                                                </div>
                                                
                                            </div>
                            
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        
                                    </div>
                                </div>
							</form:form>
                            
                            </div>
  
                        </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

           

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->


 <!-- Bootstrap core JavaScript-->
 <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

 <!-- Core plugin JavaScript-->
 <script src="${pageContext.request.contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

 <!-- Custom scripts for all pages-->
 <script src="${pageContext.request.contextPath}/resources/js/sb-admin-2.min.js"></script>
   
</body>

</html>