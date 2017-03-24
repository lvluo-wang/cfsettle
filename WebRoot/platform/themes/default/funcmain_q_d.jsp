<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertAttribute name="head"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/query.css'/>" />
<!-- 由此开始页面布局 -->
<div id="work_page"  class="easyui-layout" fit="true" style="border-right:1px solid #ccc;" border="false">	
	<!-- 流程区域-->
	<div region="north" border="false" split="false" style="background:#fafafa;border:0px;padding-top:2px;padding-left:2px;">
		<tiles:insertAttribute name="query"/>
	</div>
	<!-- 数据显示区域 -->
	<div region="center"  border="false" style="overflow:hidden;border:0px;border-top:#99bbe8 1px solid;">
		<tiles:insertAttribute name="data"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>
</div>

<tiles:insertAttribute name="end"/>
