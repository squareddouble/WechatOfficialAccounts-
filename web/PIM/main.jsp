<%@ page language="java" contentType="text/html;charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="Data.Database"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <style>
        div.logout{float:right}
    </style>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>Twitter Bootstrap Tutorial - A responsive layout tutorial</title>
    <link href="../css/bootstrap.css" rel="stylesheet"/>
     <script>
         var out = localStorage.getItem("StudentID");
         function logout(out)
         {
             $.ajax({
                 type:"get",
                 url:"${pageContext.request.contextPath }",
                 contentType : 'application/json;charset=utf-8', //可能需要去掉，否则Servlet接收到的值为NULL
                 data:{"id":out},
                 success:function(data) {
                     alert(data);
                     window.location.href="PIM/Register/login.jsp";
                 },error : function(error) {
                     alert("发送请求失败");
                     }
             }
             );
         }
     </script>
</head>
<body>
<div class="container">
    <div class="form-group">
    <div class="col-sm-1 col-sm-offset-2 logout">
        <button type="button" class="btn btn-primary" onclick="logout()" >注销</button>
    </div>
    </div>
    <div class="form-group">
        <div class="col-sm-2 col-md-2">
            <img src="img/logo.png" class="img-responsive   center-block"   >
        </div>
    </div>
    <nav class="navbar navbar-default">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">个人信息管理</a>
        </div>
        <div id="navbar-menu1" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li ><a href="#">个人信息</a></li>
                <li><a href="#">课表查询</a></li>
                <li><a href="#">考试时间安排</a></li>
            </ul>
        </div>
    </nav>
    <nav class="navbar navbar-default">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu2" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">教学管理系统</a>
        </div>
        <div id="navbar-menu2" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li ><a href="#">授课教师信息查询</a></li>
                <li><a href="#">教学资料查询下载</a></li>
                <li><a href="#">教务通知查询</a></li>
                <li><a href="#">教学评价</a></li>
                <li><a href="#">教学调查问卷</a></li>
            </ul>
        </div>
    </nav>
    <nav class="navbar navbar-default">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu3" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">其他功能</a>
        </div>
        <div id="navbar-menu3" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#">视频点播</a></li>
                <li><a href="#">视频直播</a></li>
                <li><a href="#">社区讨论</a></li>
            </ul>
        </div>
    </nav>
</div>
<script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
</body>

</html>