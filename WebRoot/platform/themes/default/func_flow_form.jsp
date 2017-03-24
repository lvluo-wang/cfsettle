<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/funcmain.css'/>" />
<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->
<div id="work_page"  class="easyui-layout" fit="true"  border="false">	
	<!-- 流程区域-->
	<div region="north" border="false" split="false" style="padding:0px;margin:0px;border-bottom:1px solid #ccc;height:29px;">
		<tiles:insertAttribute name="flow"/>
	</div>
	<!-- 数据显示区域 -->
	<div region="center" border="false" style="padding:10px;margin:0px;border:0px;border-top:#99bbe8 1px solid;overflow:hidden;">
		<tiles:insertAttribute name="form"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>
</div>

<tiles:insertAttribute name="end"/>
