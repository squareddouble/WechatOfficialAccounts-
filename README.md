#一些开发过程的问题

###登录
登录页面为login.jsp，请求发送至/wechatAutoResponder/CheckAccountPassword；
然后处理请求判断账号密码，CheckAccountPassword类31行处尚未编写完成；
该处主要功能为验证成功后的动作以及重定向页面；

login.jsp以及login_fail.jsp前端页面尚未处理