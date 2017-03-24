<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertAttribute name="head"/>
<!-- 由此开始页面布局 -->
	<div class="func_flow_area">
		<tiles:insertAttribute name="flow"/>
	</div>
	<div class="func_data_area" style="overflow:auto">
		<tiles:insertAttribute name="data"/>
	</div>
	<div class="func_form_area">
		<tiles:insertAttribute name="form"/>
	</div>
	<div class="func_button_area">
		<tiles:insertAttribute name="button"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>

<tiles:insertAttribute name="end"/>
<script type="text/javascript">
	$(function(){
		autoHeightFit($(".func_flow_area").parent(),".func_flow_area,.func_form_area,.func_button_area",".func_data_area");
	});
			
</script>