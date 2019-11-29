<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'uploadlist.jsp' starting page</title>

  </head>
  
  <body>
    <form id="form1" action="/MyOkHttpServer/upload" method="post" enctype="multipart/form-data">
    	<input type="button" value="上传" onclick="uploadfile();"/> <input type="button" value="增加" onclick="addFile();"/>
    	<div id="div1"></div>
    </form>
  </body>
  <script type="text/javascript">
  
  	//提交表单，实现上传（使用button提交的表单）
  	function uploadfile() {
  		//得到form标签
  		var form1 = document.getElementById("form1");
  		form1.submit();
  	}
  	
  	//增加一行
  	function addFile() {
  		//得到div
  		var div1 = document.getElementById("div1");
  		div1.innerHTML += "<div><input type='file' name='filename'/><input type='button' value='删除' onclick='del1(this);'/></div>";
  	}
  	
  	//删除一行
  	function del1(who) {
  		//因为要删除文件上传项和删除按钮，这两个内容都在div里面，删除div就可以了
  		//得到当前点击的删除按钮所在的div
  		var div = who.parentNode;
  		//删除div，使用dom里面的方法 removeChild()，不能自己删除自己，通过父标签删除
  		//得到要删除div的父标签
  		var div1 = div.parentNode;
  		div1.removeChild(div);
  	}
  </script>
</html>






