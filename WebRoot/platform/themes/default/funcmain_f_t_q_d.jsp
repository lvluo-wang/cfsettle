<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/funcmain.css'/>" />
<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->
	<!-- 工具栏区域-->
	<div class="func_flow_area">
		<tiles:insertAttribute name="flow"/>
	</div>
	<div class="func_tool_area">
		<tiles:insertAttribute name="tool"/>
	</div>
	<div class="func_query_area">
		<tiles:insertAttribute name="query"/>
	</div>

	<div class="func_data_area">
		<tiles:insertAttribute name="data"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>
	<tiles:insertAttribute name="end"/>
