<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page import="entity.StatusEntity" %>
<%@ page import="config.PathConfig" %>
    
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="plugins/images/favicon.png">
    <title>Pixel Admin</title>
    <!-- Bootstrap Core CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- Animation CSS -->
    <link href="css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
    <!-- color CSS you can use different color css from css/colors folder -->
    <!-- We have chosen the skin-blue (blue.css) for this starter
          page. However, you can choose any other skin from folder css / colors .
-->
    <link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="./css/custom.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
   		<!-- Common bars -->
   		<jsp:include page="common-bars.jsp"/>
   		
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Chi tiết công việc </h4>
                    </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                        <ol class="breadcrumb">
                            <li><a href="${ pageContext.request.contextPath }">Dashboard</a></li>
                            <li class="active">Blank Page</li>
                        </ol>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- BEGIN THỐNG KÊ -->
                <div class="row">
                    <!--col -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i data-icon="E"
                                        class="linea-icon linea-basic"></i>
                                    <h5 class="text-muted vb">CHƯA BẮT ĐẦU</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-danger">${ notStartPercent }%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:${ notStartPercent }%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->
                    <!--col -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                        data-icon="&#xe01b;"></i>
                                    <h5 class="text-muted vb">ĐANG THỰC HIỆN</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-megna">${ inProgressPercent }%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-megna" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${ inProgressPercent }%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->
                    <!--col -->
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                        data-icon="&#xe00b;"></i>
                                    <h5 class="text-muted vb">HOÀN THÀNH</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-primary">${ finishPercent }%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${ finishPercent }%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- END THỐNG KÊ -->

                <!-- BEGIN DANH SÁCH CÔNG VIỆC -->
                <c:forEach items="${ map.entrySet() }" var="pair">
                	<div class="row">
	                    <div class="col-xs-12">
	                        <a href="#" class="group-title">
	                            <img width="30" src="${pair.getKey().avatar }" class="img-circle" />
	                            <span>${ pair.getKey().fullname }</span>
	                        </a>
	                    </div>
	                   	<div class="col-md-4">
		                	<div class="white-box">
		                		<h3 class="box-title">Chưa thực hiện</h3>
		                        <div class="message-center">
		                         	<c:forEach items="${ pair.getValue() }" var="task">		                         		
		                        		<c:if test="${ task.statusName eq StatusEntity.STATUS_NOT_START }">                         
			                                <a href="#">
			                                    <div class="mail-contnet">
			                                        <h5>${ pair.getKey().fullname }</h5>
			                                        <span class="mail-desc">${ task.name }</span> <span
			                                            class="time">${ task.startDate }</span>
			                                    </div>
			                                </a>
			                            </c:if>
		                        	</c:forEach>
		                        </div>
		                    </div>
		                </div>                 
		                <div class="col-md-4">
		                	<div class="white-box">
		                    	<h3 class="box-title">Đang thực hiện</h3>
		                        <div class="message-center">
		                        	<c:forEach items="${ pair.getValue() }" var="task">
		                        		<c:if test="${ task.statusName eq StatusEntity.STATUS_IN_PROGRESS }">
			                                <a href="#">
			                                    <div class="mail-contnet">
			                                        <h5>${ pair.getKey().fullname }</h5> <span class="mail-desc">${ task.name }</span> <span
			                                            class="time">${ task.startDate }</span>
			                                    </div>
			                                </a>
		                                </c:if>
		                           	</c:forEach>
		                       </div>
		                    </div>
		               	</div>
		                <div class="col-md-4">
	                        <div class="white-box">
	                            <h3 class="box-title">Đã hoàn thành</h3>
	                            <div class="message-center">
		                            <c:forEach items="${ pair.getValue() }" var="task">
		                            	<c:if test="${ task.statusName eq StatusEntity.STATUS_FINISH }">
			                                <a href="#">
			                                    <div class="mail-contnet">
			                                        <h5>${ pair.getKey().fullname }</h5> <span class="mail-desc">${ task.name }</span> <span
			                                            class="time">${ task.startDate }</span>
			                                    </div>
			                                </a>
		                                </c:if>
		                            </c:forEach>  
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </c:forEach>
                <!-- END DANH SÁCH CÔNG VIỆC -->
       		</div>
            <!-- /.container-fluid -->
            <footer class="footer text-center"> 2018 &copy; myclass.com </footer>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!--slimscroll JavaScript -->
    <script src="js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="js/waves.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="js/custom.min.js"></script>
</body>

</html>