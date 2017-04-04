<%@page import="com.upg.cfsettle.util.UtilConstant"%>
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
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="150" formatter="formatTime" />
				<x:column title="客户姓名" field="REAL_NAME" align="center" width="140"/>
				<x:column title="客户手机" field="MOBILE" align="left" width="90"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="150" />
				<x:column title="购买金额" field="MONEY" align="center" width="150"/>
				<x:column title="付款银行" field="PAY_BANK" align="center" width="80" formatter=""/>
				<x:column title="付款卡号" field="PAY_ACCOUNT_NO" align="center" width="140"/>
				<x:column title="服务员工" field="SERVICE_SYS_NAME" align="center" width="40"/>
				<x:column title="状态" field="STATUS" align="center" width="150" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();
	
	function formatTimeUnit(value,field,row){
		 return value+code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>",row.timeLimitUnit);
	}
	
	function formatReapayWay(value){
		 return code.getValue("<%=UtilConstant.CFS_REPAYMENT_TYPE%>",value);
	}
	function doQuery(){
		dataTable.load();
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
