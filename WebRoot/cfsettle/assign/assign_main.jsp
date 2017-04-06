<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="客户分配" click="" />
		<span class="separator"></span>
		<x:button iconCls="icon-remove" text="del" click="doRemove" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td><input name="searchBean.realName" style="width:130px"></input></td>
					<td class="title">客户手机: </td>
					<td><input name="searchBean.mobile" style="width:130px"></input></td>
					<td class="title">是否验证:</td>
					<td>
						<x:combobox name="searchBean.isValid" list="yseNo" textField="codeName" valueField="codeNo"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/assign/assignManage_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="uid" />
				<x:column title="员工职位" field="posCode" align="center" width="140"/>
				<x:column title="姓名" field="userName" align="center" width="40" formatter=""/>
				<x:column title="归属团队" field="teamName" align="center" width="150" />
				<x:column title="所属营业部" field="deptName" align="center" width="150"/>
				<x:column title="归属区域" field="areaName" align="center" width="80" />
				<x:column title="状态" field="status" align="center" width="80" formatter=""/>
				<x:column title="客户数量" field="custNum" align="center" width="80" formatter=""/>

			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:750px;height:auto;display:none;"></div>
	<div id="project_edit_win" style="width:750px;height:auto;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	function doQuery(){
		dataTable.load();
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
