<%@page import="com.upg.cfsettle.util.LcsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd" />
		<x:button iconCls="icon-edit" text="edit" click="doEdit" />
		<x:button iconCls="icon-view" text="新增订单" click="doView" />
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
		<x:datagrid id="dataTable" url="/cust/cfscust_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户姓名" field="realName" align="center" width="140"/>
				<x:column title="性别" field="sex" align="center" width="40" formatter="formatSex"/>
				<x:column title="客户手机" field="mobile" align="left" width="90"/>
				<x:column title="身份证号" field="idCard" align="center" width="150" />
				<x:column title="添加时间" field="ctime" align="center" width="150"/>
				<x:column title="验证" field="isValid" align="center" width="80" formatter="formatYesNo"/>
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
	var keys=["<%=LcsConstant.CFS_COMM_YSE_NO%>","<%=LcsConstant.CFS_COMM_SEX%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatSex(value){
		 return code.getValue("<%=LcsConstant.CFS_COMM_SEX%>",value);
	}
	
	function formatYesNo(value){
		 return code.getValue("<%=LcsConstant.CFS_COMM_YSE_NO%>",value);
	}
	
	function doQuery(){
		dataTable.load();
	}
		
	function doAdd(){
		var url="<s:url value='/cust/cfscust_toAdd.jhtml'/>";
		requestAtWindow(url,"project_add_win","<s:text name='add'/>");
	}
	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/cust/cfscust_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
		
	}
	
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/cust/cfscust_toEdit.jhtml' />?id="+selectedId;
			requestAtWindow(url,"project_edit_win","<s:text name='edit'/>");
		}
	}

	function doRemove(){
		if(isSelected(dataTable)){
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
					dataTable.call('<s:url value="/cust/cfscust_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
