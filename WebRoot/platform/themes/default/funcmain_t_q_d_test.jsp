<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
<!--platform js -->
<script language="javascript" src="<s:url value="/platform/static/plugins/jquery-1.6.min.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/easyui/jquery.easyui.min_inner.test.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/locale/easyui-lang-${session.WW_TRANS_I18N_LOCALE }.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/base.js"/>"></script>
<script type="text/javascript">
	setBaseDir('${pageContext.request.contextPath}');
</script>
<script language="javascript" src="<s:url value="/platform/static/plugins/jsextend.js"/>"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/i18n/global_message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="<s:url value="/platform/static/base/tool.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/component.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/base/validate.js"/>"></script>
<script language="javascript" src="<s:url value="/platform/static/plugins/mask/mask.js"/>"></script>
<!--platform css  -->
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/default/easyui.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value="/platform/static/easyui/themes/icon.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/plugins/mask/css/mask.css'/>"/> 
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/table_form.css'/>" />
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/css/step.css'/>" />


<!-- My97DatePicker -->
<script language="javascript" type="text/javascript" src='<s:url value="/platform/static/plugins/My97DatePicker/WdatePicker.js"/>' ></script>

<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/funcmain.css'/>" />
<style>
#queryMainForm label{
	font-size:12px;
}
</style>
</head>
	<body id="pageContent" class="easyui-layout" fit="true" border="false">
	<div id="loading" class="common-mask" style="display:none;"></div>
	<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
		<div id="wrapContent" region="center" border="false" style="overflow:hidden;padding:0px;margin:0px;">
			
	<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->
	<!-- 工具栏区域-->
	<div class="func_tool_area">
		<tiles:insertAttribute name="tool"/>
	</div>
	<div class="func_query_area">
		<tiles:insertAttribute name="query"/>
	</div>
	<!-- 数据显示区域 -->
	<div class="func_data_area">
		<tiles:insertAttribute name="data"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>

<tiles:insertAttribute name="end"/>

</div>


	</body>
<script>
	

	function renderPage(){
		if( typeof initPage != 'undefined' && $.isFunction(initPage)){
			initPage();
		}
	}
	setTimeout("renderPage()",0);
	/*
	function doResize(){
		$("#wrapContent").width($(parent.document.getElementById("job_work_area")).width());
		$("#wrapContent").height($(parent.document.getElementById("job_work_area")).height());
	}
	*/
</script>
</html>