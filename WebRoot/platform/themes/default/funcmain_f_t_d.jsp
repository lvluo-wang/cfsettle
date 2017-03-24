<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<tiles:insertAttribute name="head"/>

<!-- 由此开始页面布局 -->
<div id="work_page"  class="easyui-layout" fit="true"  border="false">	
	<!-- 工具栏区域-->
	<div region="north" border="false" split="false" style="padding:0px;">
		<div class="easyui-layout" fit="true" border="false">
			<div style="background:#efefef;padding:0px;padding-top:1px;height:29px;" border="false" region="north">
				<tiles:insertAttribute name="flow"/>
			</div>
			<div region="center" style="background:#efefef;padding:0px;padding-top:1px;height:28px;margin:0px;" border="false" >
				<span class="separator"></span>
				<tiles:insertAttribute name="tool"/>
			</div>
		</div>
	</div>
	<!-- 数据显示区域 -->
	<div region="center" border="false" style="overflow:hidden;border:0px;border-top:#99bbe8 1px solid;">
		<tiles:insertAttribute name="data"/>
	</div>
	<!-- 弹出窗口定义开始 -->
	<tiles:insertAttribute name="window"/>
	
	<!-- 右键菜单定义开始 -->
	<tiles:insertAttribute name="menu"/>
</div>

<tiles:insertAttribute name="end"/>
