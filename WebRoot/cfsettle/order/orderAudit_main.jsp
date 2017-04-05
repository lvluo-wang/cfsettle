<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-audit" text="合同审核" click="doAudit" />
		<x:button iconCls="icon-view" text="合同详情" click="toView" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td><input name="searchBean.realName" /></td>
					<td class="title">手机号: </td>
					<td><input name="searchBean.mobile" /></td>
					<td class="title">项目名: </td>
					<td><input name="searchBean.prjName" /></td>
					<td class="title">合同编号: </td>
					<td><input name="searchBean.contractNo" /></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td>
						<x:combobox name="searchBean.status" list="" textField="codeName" valueField="codeNo"/>
					</td>
					<td class="title">投资日期:</td>
					<td colspan="3">
						<input id="beginTime" name="searchBean.startDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime\',{d:-31})}',maxDate:'#F{$dp.$D(\'endTime\')}',onpicked:function(){endTime.focus();}})" />
						-
						<input id="endTime" name="searchBean.endDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTime\',{d:31})}',minDate:'#F{$dp.$D(\'beginTime\')}'})" />
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/order/orderAudit_list.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="合同编号" field="CONTRACT_NO" align="left" width="90"/>
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="150" formatter="formatTimeStamp"/>
				<x:column title="客户姓名" field="REAL_NAME" align="center" width="140"/>
				<x:column title="客户手机" field="MOBILE" align="left" width="90"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="150" />
				<x:column title="购买金额" field="MONEY" align="center" width="150"/>
				<x:column title="付款银行" field="PAY_BANK" align="center" width="80" formatter="formateBank"/>
				<x:column title="付款卡号" field="PAY_ACCOUNT_NO" align="center" width="140"/>
				<x:column title="服务员工" field="SERVICE_SYS_NAME" align="center" width="90"/>
				<x:column title="状态" field="STATUS" align="center" width="150" formatter="formatOrderStatus"/>
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
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>","<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatTimeUnit(value,field,row){
		 return value+code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>",row.timeLimitUnit);
	}
	
	function formatReapayWay(value){
		 return code.getValue("<%=UtilConstant.CFS_REPAYMENT_TYPE%>",value);
	}
	
	 function formateBank(value) {
 		return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
     }
	function doQuery(){
		dataTable.load();
	}
	function formatOrderStatus(value){
		return code.getValue("<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>",value);
	}
	function doAudit(){
		if(isSingleSelected(dataTable)) {
			var row = dataTable.getSelectedFirstRow();
			if(row.STATUS=='1'||row.STATUS=='3'){
				var selectedId = dataTable.getSelectedField("ID");
				var url="<s:url value='/order/orderAudit_toAdd.jhtml'/>?id="+selectedId;
				requestAtWindow(url,"project_add_win","<s:text name='审核合同'/>");
			}else{
				warning('只有待审核合同才能审核');
				return;
			}
		}
	}
	
	function toView(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("ID");
			var url="<s:url value='/order/orderAudit_toView.jhtml'/>?id="+selectedId;
			requestAtWindow(url,"project_edit_win","<s:text name='view'/>");
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
