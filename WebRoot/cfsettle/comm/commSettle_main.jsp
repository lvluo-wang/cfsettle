<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="查询明细" click="doDetail" />
		|
		<x:button iconCls="icon-reload" text="待付款" click="queryToPay" />
		<x:button iconCls="icon-accept" text="全部" click="queryAll" />
		<x:button iconCls="icon-redo" text="已付款" click="queryPayed" />
		<x:button iconCls="icon-down" text="导出excel" click="doExport" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">姓名: </td>
					<td><input class="easyui-validatebox" type="text" name="searchBean.sysUserName"/></td>
					<td class="title">手机: </td>
					<td><input class="easyui-validatebox" type="text" name="searchBean.mobile" validType="mobile"/></td>
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
		<x:datagrid id="dataTable" url="/comm/commSettle_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="职位类型" field="posCode" align="center" width="140" formatter="formatPosCode"/>
				<x:column title="名称" field="sysUserName" align="center" width="70"/>
				<x:column title="联系方式" field="mobile" align="center" width="140"/>
				<x:column title="佣金计提月份" field="commSettleDate" align="center" width="140" formatter="format2Month"/>
				<x:column title="佣金总额" field="commMoney" align="center" width="70"/>
				<x:column title="付款时间" field="payTime" align="left" width="120" formatter="format2Time"/>
				<x:column title="状态" field="payStatus" align="center" width="150" formatter="formatPayStatus"/>
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
	var keys=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>","<%=UtilConstant.CFS_BUSER_POS_CODE%>","<%=UtilConstant.CFS_COMM_PAY_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	function formatSex(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_SEX%>",value);
	}
	
	function formatYesNo(value){
		 return code.getValue("<%=CfsConstant.CFS_COMM_YSE_NO%>",value);
	}
	
	function formatPosCode(val){
		return code.getValue("<%=UtilConstant.CFS_BUSER_POS_CODE%>",val);
	}
	
	function formatPayStatus(val,field,row) {
   		var value = code.getValue("<%=UtilConstant.CFS_COMM_PAY_STATUS%>",val);
    	if(val==1){
    		return"<a href='javascript:toRepay("+row.id+")'>"+value+"</a>";
    	}else{
    		return value;
    	}
    }
	
	function toRepay(id){
		info(id);
	}
	
	function doQuery(){
		if($('#query_form').form('validate')){
			dataTable.load();
		}
	}
		
	
	function doDetail(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/cust/cfscust_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
		
	}
	
	function queryToPay(){
		
	}
	
	function queryAll(){
			
		}
		
	function queryPayed(){
		
	}
	
	function doExport(){
		
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
