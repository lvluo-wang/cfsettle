<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/prompt.css'/>">
</head>
  
  <body style="margin:0px;">

  
       <!--提示信息开始-->
 <div class="err_wrap">
	<div class="err_content">
    
    ${error}
    <s:property value="exception.message"/>
    <s:actionmessage/>
	<p class="btn_return">
		<script type="text/javascript" reload="1">setTimeout("history.go(-1);", 3000);</script>
		<a href="javascript:history.go(-1)" >如果您的浏览器没有自动跳转，请点击此链接“返回”</a>
	</p>
    </div>
 </div> 
 <!--提示信息结束-->
  </body>
</html>
