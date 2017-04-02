<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_QUERY_DATA">
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">佣金计提月份: </td>
					<td><input class="Wdate easyui-validatebox" type="text" name="searchBean.commSettleDate" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
					<td class="title">状态:</td>
					<td><x:combobox name="searchBean.repayStatus" list="commStatus" textField="codeName" valueField="codeNo"/></td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/comm/myComm_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="佣金计提月份" field="commSettleDate" align="center" width="140" formatter="format2Month"/>
				<x:column title="佣金总额" field="commMoney" align="center" width="70"/>
				<x:column title="付款时间" field="payTime" align="left" width="120" formatter="format2Time"/>
				<x:column title="状态" field="payStatus" align="center" width="150" />
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
	
	function formatSex(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
	}
	
	function formatYesNo(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
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
			if(dataTable.getSelectedFirstRow().isValid =='0'){
				var url="<s:url value='/cust/cfscust_toEdit.jhtml'/>";
				requestAtWindow(url,"project_edit_win","<s:text name='edit'/>",{id:selectedId});
			}else{
				info('已验证用户不能修改');
			}
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
