<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	<style>

        th, td {
                     padding: 15px;
                }

        th {
             border-radius: 1px;
             box-shadow: 2px 2px #888888;
             background:none repeat scroll 0 0 #6675df;
             font-weight: bold;
             color: white;
            }

        td {
            color: black;
            font-weight: bold;
            }
        table, th, td {
            border:1px solid black;
            }
    </style>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/faculty/facultyPage">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">SPMS <sup>2</sup></div>
            </a>

           <!-- Divider -->
           <!-- FOR FACULTY -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/faculty/facultyPage">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>HOME</span></a>
            </li>

            <!-- Divider -->
           <!-- FOR FACULTY -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/faculty/facultyProfile">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    <span>PROFILE</span></a>
            </li>

           <!-- Divider -->
           <!-- FOR FACULTY -->
           <hr class="sidebar-divider">

           <!-- Nav Item - Dashboard -->
           <li class="nav-item ">
               <a class="nav-link" href="${pageContext.request.contextPath}/faculty/evaluation">
                   <i class="fas fa-fw fa-chart-area"></i>
                   <span>EVALUATION</span></a>
           </li>
           
           <!-- Divider -->
           <!-- FOR FACULTY -->
           <hr class="sidebar-divider">

           <!-- Nav Item - Dashboard -->
           <li class="nav-item ">
               <a class="nav-link" href="${pageContext.request.contextPath}/faculty/semData">
                   <i class="fas fa-sort"></i>
                   <span>SORTED SEM DATA</span></a>
           </li>

           <!-- Divider -->
           <!-- FOR FACULTY -->
           <hr class="sidebar-divider">

           <!-- Nav Item - Dashboard -->
           <li class="nav-item ">
               <a class="nav-link" href="${pageContext.request.contextPath}/faculty/facultyList">
                   <i class="fas fa-list"></i>
                   <span>FACULTY LIST</span></a>
           </li>
           
           
		
			<sec:authorize access="hasRole('HOD')">
	            <!-- Divider -->
	            <!-- FOR HOD -->
	            <hr class="sidebar-divider">
	
	             <!-- Nav Item - Dashboard -->
	             <li class="nav-item ">
	                <a class="nav-link" href="${pageContext.request.contextPath}/faculty/hod/allotRoles">
	                    <i class="fas fa-fw fa-hand-point-up"></i>
	                    <span>ALLOT ROLES</span></a>
	            </li>
		
	            <!-- Divider -->
	            <!-- FOR HOD -->
	            <hr class="sidebar-divider">
	
	             <!-- Nav Item - Dashboard -->
	             <li class="nav-item ">
	                <a class="nav-link" href="${pageContext.request.contextPath}/faculty/hod/setCriteria">
	                    <i class="fas fa-fw fa-edit"></i>
	                    <span>SET CRITERIA</span></a>
	            </li>
			</sec:authorize>
			
			<sec:authorize access="hasRole('COORDINATOR')">
	             <!-- Divider -->
	             <!-- FOR COORDINATOR -->
	             <hr class="sidebar-divider">
	
	             <!-- Nav Item - Dashboard -->
	              <li class="nav-item ">
	                 <a class="nav-link" href="${pageContext.request.contextPath}/faculty/coordinator/subjects">
	                     <i class="fas fa-bell fa-newspaper"></i>
	                     <span>ADD SUBJECTS</span></a>
	             </li>
	
	             <!-- Divider -->
	             <!-- FOR COORDINATOR -->
	             <hr class="sidebar-divider">
	
	             <!-- Nav Item - Dashboard -->
	              <li class="nav-item active">
	                 <a class="nav-link" href="${pageContext.request.contextPath}/faculty/coordinator/viewMentors">
	                     <i class="fas fa-bell fa-eye"></i>
	                     <span>VIEW MENTORS</span></a>
	             </li>
	
	             <!-- Divider -->
	             <!-- FOR COORDINATOR -->
	             <hr class="sidebar-divider">
	
	             <!-- Nav Item - Dashboard -->
	              <li class="nav-item ">
	                 <a class="nav-link"href="${pageContext.request.contextPath}/faculty/coordinator/notification">
	                     <i class="fas fa-bell fa-fw"></i>
	                     <span>NOTIFICATION</span></a>
	             </li>
	        </sec:authorize>
	        
	        
			<sec:authorize access="hasRole('MENTOR')">
	            <!-- Divider -->
	            <!-- FOR MENTORS -->
	            <hr class="sidebar-divider">
	
	            <!-- Nav Item - Dashboard -->
	            <li class="nav-item ">
	                <a class="nav-link" href="${pageContext.request.contextPath}/faculty/mentor/inbox">
	                    <i class="fas fa-envelope fa-fw"></i>
	                    <span>MESSAGES</span></a>
	            </li>
	        </sec:authorize>


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

                    <!--Sidebar Toggle-->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

					
                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                    
                        
						<sec:authorize access="hasRole('MENTOR')">
	                        <!-- Nav Item - Messages -->
	                        <li class="nav-item dropdown no-arrow mx-1">
	                            <a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/faculty/mentor/inbox" role="button">
	                                <i class="fas fa-envelope fa-fw"></i>
	                                <!-- Counter - Alerts -->
                                <c:if test="${newMessage == true }">
                                <span class="position-absolute mt-4 top-0 start-100 translate-middle badge badge- bg-danger">
                                        New
                                </span>
                                </c:if>
	                            </a>
	                        </li>
                        </sec:authorize>

                       
                        <div class="topbar-divider d-none d-sm-block"></div>

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="navDown" role="button"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><sec:authentication property="principal.username"></sec:authentication></span>
                                <img class="img-profile rounded-circle"
                                    src="${pageContext.request.contextPath}/resources/profile/${currentFaculty.profilePic}">
                            </a>
                            <!-- Dropdown - User Information -->
                            <ul class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="navDown">
                                
                                <li><a class="dropdown-item " href="${pageContext.request.contextPath}/faculty/logout">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>Logout</a></li>
                                
                            </ul>
                        </li>

                        

                    </ul>

                </nav>
                <!-- End of Topbar -->

      
                <!-- Begin Page Content -->
                <div class="container-fluid">

              <c:if test="${mentors != null}">

                    <!-- Content Row -->

                    <div class="row">

                        <div class="col-sm-12 p-1 mx-auto">
                            <div class="card shadow mb-4 ">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary ">
                                        MENTOR LIST: ${currentFaculty.dept}
                                    </h6>
                                </div>
                                <div class="card-body ">
                                    <div style="overflow-x:auto;">
                                        <table style="width:100% ;" >
                                            <h6>
                                                <tr>
                                                    <th>S.No</th>
                                                    <th>Profile</th>
                                                    <th>Name</th>
                                                    <th>Faculty Email</th>
                                                    <th>Action</th>
                                                </tr>
                                            </h6>
                                          <c:set var="count" value="0"></c:set>
                                          
										  <c:forEach var="mentor" items="${mentors}">
										  <c:set var="count" value="${count+1}"></c:set>
										  
										  <c:url var="allot" value="/faculty/coordinator/allotStudentsToMentor">
										  	<c:param name="facultyEmail" value="${mentor.email}"></c:param>
										  </c:url>
                                            <h6>
                                                <tr >
                                                    <td>${count}</td>
                                                    <td>
                                                        <div class="text-center">
                                                            <a href="${pageContext.request.contextPath}/resources/profile/${mentor.profilePic}">
                                                                <img src="${pageContext.request.contextPath}/resources/profile/${mentor.profilePic}" class="img-thumbnail shadow-lg " alt="..." width="100px" height="100px" >
                                                            </a>
                                                        </div>
                                                    </td>
                                                    <td>${mentor.firstName} ${mentor.lastName}</td>
                                                    <td>${mentor.email}</td>
                                                    <td>
                                                        <a href="${allot}" onclick="if(!(confirm('ARE YOU SURE?'))) return false">Allot Students</a>
                                                    </td>
                                                </tr>
                                            </h6>
                                           </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
			  </c:if>  
                    
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