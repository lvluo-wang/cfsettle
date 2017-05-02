<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="查询明细" click="doDetail" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">佣金计提月份: </td>
					<td><input class="Wdate"  name="searchBean.commSettleDateStr" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
					<td class="title">状态:</td>
					<td><x:combobox name="searchBean.payStatus" list="commStatus" textField="codeName" valueField="codeNo"/></td>
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
				<x:column title="佣金总额" field="commMoney" align="center" width="70" formatter="formatAmount"/>
				<x:column title="付款时间" field="payTime" align="left" width="120" />
				<x:column title="状态" field="payStatus" align="center" width="150" formatter="formatterStatus"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:850px;height:500px;display:none;"></div>
	<div id="project_edit_win" style="width:750px;height:auto;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_COMM_PAY_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function formatterStatus(value) {
		return code.getValue("<%=UtilConstant.CFS_COMM_PAY_STATUS%>",value);
	}

	function formatAmount(value){
		return formatCurrency(value);
	}
	
	function doQuery(){
		dataTable.load();
	}
		
	function doDetail(){
		if(isSingleSelected(dataTable)){
			var id = dataTable.getSelectedField("id");
			var dateMonth = dataTable.getSelectedField("commSettleDate");
			var url="<s:url value='/comm/myComm_view.jhtml'/>?id="+id+"&dateMonth="+dateMonth;
			requestAtWindow(url,"project_add_win","佣金明细");
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
