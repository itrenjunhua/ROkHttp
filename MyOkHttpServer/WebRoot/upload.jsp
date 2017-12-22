<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'upload.jsp' starting page</title>
  </head>
  
  <body>
    <form action="/MyOkHttpServer/upload" method="post" enctype="multipart/form-data">
    	普通输入项：<input type="text" name="username"/>
    	<br/>
    	文件上传项：<input type="file" name="filename"/>
    	<br/>
    	<input type="submit" value="上传"/>
    </form>
  </body>
</html>
