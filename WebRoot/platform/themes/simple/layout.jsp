<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="jscss"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/simple/css/layout.css'/>"/> 
<script type="text/javascript" src='<s:url value="/platform/static/easyui/jquery.portal.js"/>'></script>
<script>
	var mobileBrowser = false;
	<s:if test="#request.mobile">
		mobileBrowser = true;
	</s:if>
	<s:elseif test="#request.ipad">
		mobileBrowser = true;
	</s:elseif>
</script>
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/portal.css"/>"/>

<style type="text/css">
	#user_menu_down_panel ol {
		list-style: none outside none;
		margin: 0;
		padding: 8px 4px;
	}
	#user_menu_down_panel li {
		padding:2px;
		cursor:pointer;
		list-style: none outside none;
		margin: 0;
		padding: 0;
		padding: 8px 4px;
	}
	.user_menu_down_panel-hover{
		background:none repeat scroll 0 0 rgb(178,191,199);
	}
</style>

</head>
<body style="overflow:auto;">
<div id="loading" class="common-mask" style="display:none;"></div>
<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
<script type="text/javascript">
	loadmask();
	var url = "<s:url value='/security/user_getCurUser.jhtml'/>";
	doPost(url,{},function(result){
		if(result){
			var o=str2obj(result);
			if(o.error){
				error(o.error);
				return;
			}else{
				if(o.isChangePwd==0){
					var url='<s:url value="/security/user_toChangePassword.jhtml"/>?flag='+Math.random()*99999;
					requestAtWindow(url,"change_pwd",'<s:text name="modifyPwd"/>',{'isChangePwd':1},{closable:false});
				}
			}
		}
	});
	var userMenuStr='${userMenu}';
</script>

	<div id="header" region="north" style="padding:0px;overflow:hidden;">
		<tiles:insertAttribute name="header"/>
	</div>
	<div id="menuarea" region="west">
		<tiles:insertAttribute name="menuarea"/>
	</div>
	<div id="workarea" region="center">
		<tiles:insertAttribute name="workarea"/>
	</div>
	<div id="footer" region="south" border="false" style="height:25px;">
		<tiles:insertAttribute name="footer"/>
	</div>

</body>
<script language="javascript" src="<s:url value="/platform/themes/simple/js/layout.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/themes/simple/js/portal.js"/>"></script>
</html>