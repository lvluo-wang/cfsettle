<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<!--platform js -->
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/jquery-1.11.0.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/jquery-migrate-1.2.1.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/jsrender/jsrender.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/jquery.easyui.min_inner.test.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/easyui/locale/easyui-lang-${session.WW_TRANS_I18N_LOCALE }.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/base/base.js"></script>
<script type="text/javascript">
	setBaseDir('${pageContext.request.contextPath}');
</script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/jsextend.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/i18n/global_message_${session.WW_TRANS_I18N_LOCALE}.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/currency/jquery.currency.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/base/tool.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/base/component.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/base/validate.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/mask/mask.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/jquery.PrintArea.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/qtip/jquery.qtip.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/kindeditor/kindeditor-min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/kindeditor/zh_CN.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/uploadfy/swfobject.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/platform/static/plugins/uploadfy/jqueryuploadify.js"></script>
<!--platform css  -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/plugins/mask/css/mask.css"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/css/main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/css/table_form.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/css/step.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/css/print.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/plugins/qtip/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/static/plugins/kindeditor/themes/default/default.css" />
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/static/plugins/uploadfy/css/uploadify.css'/>" />
	

<script src="<s:url value='/platform/static/base/xdatagrid.js'/>" autoload="false" type="text/javascript"></script>
<!-- My97DatePicker -->
<script language="javascript" type="text/javascript" src='${pageContext.request.contextPath}/platform/static/plugins/My97DatePicker/WdatePicker.js' ></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/platform/themes/simple/css/funcmain.css" />
<s:include value="/customizeJsCss.jsp"/>
</head>
	<body>
	<div id="loading" class="common-mask" style="display:none;"></div>
	<div id="loading_msg" class="common-mask-msg" style="display:none;"></div>
	<div id="sys_task_history" style="width:760px;height:450px;padding:3px;display:none;"></div>