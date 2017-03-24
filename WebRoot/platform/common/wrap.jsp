<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.upg.ucars.framework.base.SessionTool"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String location = (String)request.getAttribute("resultLocation");
	String theme="default";
	if(SessionTool.getUserLogonInfo()!=null){
		theme=SessionTool.getUserLogonInfo().getTheme();
	}
	String bodyJsCss="/platform/themes/"+theme+"/bodyJsCss.jsp";
%>
<html>
	<head>
	<jsp:include page="<%=bodyJsCss %>"  flush="true" />
	<s:include value="/customizeJsCss.jsp"/>
	</head>
	<body id="pageContent" class="easyui-layout" fit="true" border="false">
	<div id="loading" class="common-mask" style="display:none;"></div>
	<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
		<div id="wrapContent" region="center" border="false" style="overflow:hidden;padding:0px;margin:0px;">
			<jsp:include page='<%=location==null?"":location%>' flush="true"/>
		</div>
	</body>
	<script>
		$(function(){
			if( typeof proxy$Array != 'undefined' && $.isArray(proxy$Array) ){
				var fn = null;
				while( proxy$Array.length > 0 ){
					fn = proxy$Array.shift();
					if( typeof fn != 'undefined' && $.isFunction(fn) ){
						fn();
					}
				}
			}
		});
		//doResize();
		function renderPage(){
			if( typeof initPage != 'undefined' && $.isFunction(initPage)){
				initPage();
			}
		}
		setTimeout("renderPage()",0);
		function doResize(){
			$("#wrapContent").width($(parent.document.getElementById("job_work_area")).width());
			$("#wrapContent").height($(parent.document.getElementById("job_work_area")).height());
		}
	</script>
</html>