<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:include value="/platform/themes/simple/top.jsp"/>
<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->
	<!-- 工具栏区域-->

	<div class="func_tool_area">
		<tiles:insertAttribute name="tool"/>
	</div>
	<!-- 数据显示区域 -->
	<div class="func_form_area">
		<tiles:insertAttribute name="form"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>

<tiles:insertAttribute name="end"/>
<script type="text/javascript">
			autoHeightFit("body",".func_tool_area",".func_form_area");
	</script>
<s:include value="/platform/themes/simple/bottom.jsp"/>