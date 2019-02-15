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
    <div class="container text-center">
        <form action="/wechatAutoResponder/CheckAccountPassword" method="post">
            <div class="row">
                <table align="center">
                    <tr>
                        <td>学号：</td>
                        <td><input type="number" name="StudentID"></td>
                    </tr>
                    <tr>
                        <td>密码：</td>
                        <td><input type="password" name="StudentPassword"></td>
                    </tr>
                </table>
            </div>
            <div class="row">
                <input type="submit" name="submit" value="绑定">
            </div>
        </form>
    </div>
</body>
</html>
