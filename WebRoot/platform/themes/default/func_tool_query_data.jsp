<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertAttribute name="head"/>
<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/default/css/funcmain.css'/>" />


			
<!-- 由此开始页面布局 -->
<div id="work_page"  class="easyui-layout" fit="true" style="border-right:1px solid #ccc;" border="false">	
	<div region="north"  border="false" split="false">
	<!-- 工具栏区域-->

				<div id="toolsDiv">
						<tiles:insertAttribute name="tool"/>
				</div>
	<!-- 查询条件区域 -->	
				 <div id="queryDiv">
				    <tiles:insertAttribute name="query"/>
				 </div>   
	</div>
	<!-- 数据显示区域 -->
	<div region="center" style="overflow:hidden;border-left:0px;border-right:0px;border-bottom:0px;">
		<tiles:insertAttribute name="data"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>
</div>

<tiles:insertAttribute name="end"/>
