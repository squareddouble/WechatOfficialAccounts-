<%@ page language="java" contentType="text/html;charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="Data.Database"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>
    <style>

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
<!--- header-top ---->
<div id="home" class="header-top">
    <!--- container ---->
    <div class="container">
        <div class="header-logo">
            <a href="#"><img src="../img/logo1.png" alt=""/></a>
        </div>
        <div class="header-top-grids content-top-grid">
            <div class="col-sm-4 about-nav4">
                <img src="../img/right.png" alt=""  onclick="logout()"/>
                <a class="scroll" href="#services"></a>
                <p>注销</p>
            </div>
            <div class="clear"> </div>
        </div>
        <div class="header-top-grids content-top-grid">
            <div class="col-sm-4 about-nav4">
                <img src="../img/paper.png" alt="" />
                <a class="scroll" href="#services"></a>
                <p>联系我们</p>
            </div>
            <div class="clear"> </div>
        </div>
    </div>
    <div class="arrow-grid">
        <div class="arrow">
            <a class="scroll" href="#header-nav"><span> </span></a>
        </div>
    </div>
</div>
<div class="border"> </div>
<!--- container ---->
<div class="container">

    <nav class="navbar navbar-default col-sm-4">
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
    <div class="border" id="message"> </div>
    <div class="content">
        <!--- container ---->
        <div class="container" >
            <div class="row">
                <div class="col-sm-3 content-top-grid">
                    <img src="../img/1.png" alt=""/>
                    <h3>个人信息</h3>
                    <p>查看学生个人信息</p>
                </div>
                <div class="col-sm-3 content-top-grid">
                    <img src="../img/2.png" alt=""/>
                    <h3>课表查询</h3>
                    <p>各学期所选科目与课程</p>
                </div>
                <div class="col-sm-3 content-top-grid">
                    <img src="../img/3.png" alt=""/>
                    <h3>考试安排时间</h3>
                    <p>各科目考场与时间安排</p>
                </div>
                <div class="col-sm-3 content-top-grid">
                    <img src="../img/4.png" alt=""/>
                    <h3>简洁的信息系统</h3>
                    <p>快捷地得到你想要的资料</p>
                </div>
            </div>
        </div>
    </div>
    <div class="border"> </div>
    <nav class="navbar navbar-default col-sm-4">
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
    <div  class="team">
        <div class="container">
            <h3>优秀的师资</h3>
            <div class="border" id="tec"> </div>
            <div class="team-grids-top">
                <div class="col-sm-4 team-grid">
                    <img src="../img/tec1.jpg" alt="" />
                    <h5>John Smith</h5>
                    <p>Senior Professor of Software.</p>
                </div>
                <div class="col-sm-4 team-grid">
                    <img src="../img/tec2.jpg" alt="" />
                    <h5>Patric Dow</h5>
                    <p>Senior Professor of Philosophy.</p>
                </div>
                <div class="clear"> </div>
            </div>
        </div>
    </div>
    <div  class="team">
        <div class="container">
            <h3>先进的教学</h3>
            <div class="border line"> </div>
            <div class="team-grids-top">
                <div class="col-sm-4 team-grid">
                    <img src="../img/e2.jpg" alt="" />
                    <h5>名师指导</h5>
                    <p>不再因为枯燥无味的课堂而烦恼.</p>
                </div>
                <div class="col-sm-4 team-grid">
                    <img src="../img/e4.jpg" alt="" />
                    <h5>课堂互动</h5>
                    <p>高频率互动让学习过程更生动.</p>
                </div>
                <div class="clear"> </div>
            </div>
        </div>
    </div>
    <div  class="team">
        <!-- container -->
        <div class="container">
            <h3>丰富的活动</h3>
            <div class="border line"> </div>
            <div class="team-grids-top">
                <div class="col-sm-4 team-grid">
                    <img src="../img/p1.jpg" alt="" />
                    <h5>享受生活</h5>
                    <p>重新定义校园生活.</p>
                </div>
                <div class="col-sm-4 team-grid">
                    <img src="../img/p2.jpg" alt="" />
                    <h5>创业团队</h5>
                    <p>找到更多志同道合的小伙伴.</p>
                </div>
                <div class="clear"> </div>
            </div>
        </div>
    </div>
    <div class="border" id="other"> </div>
    <nav class="navbar navbar-default col-sm-4">
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
    <div  class="team">
        <!-- container -->
        <div class="container">
            <h3>其他功能</h3>
            <div class="border line"> </div>
            <div class="team-grids-top">
                <div class="col-sm-4 team-grid">
                    <img src="../img/ceo.png" alt="" />
                    <h5>视频直播</h5>
                    <p>名师为你授课.</p>
                </div>
                <div class="col-sm-4 team-grid">
                    <img src="../img/t1.jpg" alt="" />
                    <h5>视频点播</h5>
                    <p>丰富的视频资源任你选择.</p>
                </div>
                <div class="col-sm-4 team-grid">
                    <img src="../img/team4.jpg" alt="" />
                    <h5>社区讨论</h5>
                    <p>课后课余的话题大杂烩.</p>
                </div>
                <div class="clear"> </div>
            </div>
            <div class="border line"> </div>
        </div>
        <div class="top-nav">
            <ul id="menu" class="nav1">
                <li><a href="#message" class="scroll">个人信息</a></li>
                <li><a href="#tec" class="scroll">教学管理系统</a></li>
                <li><a href="#other" class="scroll">其他功能</a></li>
            </ul>
        </div>

    </div>
</div>
<script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
</body>
</html>