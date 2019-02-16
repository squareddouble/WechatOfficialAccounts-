<%--
  Created by IntelliJ IDEA.
  User: 龙猫
  Date: 2019/2/15
  Time: 13:42
  email: foxmaillucien@126.com
  Description:用户绑定页面
--%>

<%--
  Created by IntelliJ IDEA.
  User: 龙猫
  Date: 2019/2/15
  Time: 13:42
  email: foxmaillucien@126.com
  Description:用户绑定页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        h3 {
            text-align: center;
        }

    </style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用户绑定</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="form-group">
        <img src="../../img/timg.jpg" class="img-responsive col-sm-1  center-block"   >
        <div class="col-sm-2">
        <h3>用户绑定</h3>
        </div>
    </div>
</div>
    <form class="form-horizontal" action="/wechatAutoResponder/CheckAccountPassword" method="post">
           <div class="form-group">
                      <label for="StudentID" class="col-sm-3 control-label">学号:</label>
                      <div class="col-sm-6">
                        <input type="text" class="form-control" name="StudentID" id="StudentID"
                               placeholder="请输入你的学号" oninput="b()">
                      </div>
           </div>
           <div class="form-group">
                      <label for="StudentPassword" class="col-sm-3 control-label">密码:</label>
                      <div class="col-sm-6">
                        <input type="password" class="form-control" name="StudentPassword" id="StudentPassword"
                               placeholder="请输入你的密码" >
                      </div>
           </div>
           <div class="form-group">
                      <div class="col-sm-offset-3 col-sm-6">
                        <button type="submit" name="submit" class="btn btn-default"  >绑定</button>
                      </div>
           </div>
    </form>
</div>
</body>
</html>