<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page import="javax.rmi.CORBA.Util" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="合同审核" click="toReview" />
		<x:button iconCls="icon-view" text="合同订单日志" click="toOrderLog" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td style="width: 20px;"><input name="orderBean.realName" /></td>
					<td class="title">手机号: </td>
					<td style="width: 20px;"><input name="orderBean.mobile" /></td>
					<td class="title">项目名: </td>
					<td style="width: 20px;"><input name="orderBean.prjName" /></td>
					<td class="title">合同编号: </td>
					<td style="width: 20px;"><input name="orderBean.contractNo" /></td>
					<td class="title">服务员工名: </td>
					<td style="width: 20px;"><input name="orderBean.serviceSysName" /></td>
					<td class="title">状态:</td>
					<td style="width: 20px;" >
						<x:combobox name="orderBean.status" list="orderStatusList" textField="codeName" valueField="codeNo"/>
					</td>
				</tr>
				<tr>
					<td class="title">投资日期:</td>
					<td colspan="3">
						<input id="beginTime" name="orderBean.startDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime\',{d:-31})}',maxDate:'#F{$dp.$D(\'endTime\')}',onpicked:function(){endTime.focus();}})" />
						-
						<input id="endTime" name="orderBean.endDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTime\',{d:31})}',minDate:'#F{$dp.$D(\'beginTime\')}'})" />
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/contract/contract_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="合同编号" field="CONTRACT_NO" align="left" width="90"/>
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="150" formatter="formatTime" />
				<x:column title="客户姓名" field="REAL_NAME" align="center" width="140"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="150" />
				<x:column title="购买金额" field="MONEY" align="center" width="150" formatter="formateMoney"/>
				<x:column title="付款银行" field="PAY_BANK" align="center" width="80" formatter="formateBank"/>
				<x:column title="付款卡号" field="PAY_ACCOUNT_NO" align="center" width="140"/>
				<x:column title="服务员工名" field="SERVICE_SYS_NAME" align="center" width="140"/>
				<x:column title="状态" field="STATUS" align="center" width="150" formatter="formateStatus" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
		<!-- 弹出窗口定义开始 -->
		<div id="project_add_win" style="width:700px;height:330px;display:none;"></div>
		<div id="project_log_win" style="width:700px;height:440px;display:none;"></div>
	</tiles:putAttribute>

	<tiles:putAttribute name="end">
		<script type="text/javascript">
			var keys=["<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>"];
			var code=new XhhCodeUtil(keys);
			code.loadData();

			function formatTime(value) {
				if(value == ""){
					return '';
				}
				return DateFormat.format(new Date(value*1000),"yyyy-MM-dd hh:mm:ss");
			}

			function formateMoney(value) {
				return value/100;
			}

			function formateStatus(value) {
				return code.getValue("<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>",value);
			}
			function formateBank(value) {
				return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
			}

			function doQuery(){
				dataTable.load();
			}

			function toReview(){
				if(isSingleSelected(dataTable)){
					var selectedId=dataTable.getSelectedField("ID");
					var url="<s:url value='/contract/contract_toReview.jhtml'/>?id="+selectedId;
					requestAtWindow(url,"project_add_win","审核");
				}
			}

			function toOrderLog(){
				if(isSingleSelected(dataTable)){
					var prjId=dataTable.getSelectedField("PRJ_ID");
					var contractNo=dataTable.getSelectedField("CONTRACT_NO");
					var url="<s:url value='/contract/contract_toOrderLog.jhtml'/>?prjId="+prjId+"&contractNo="+contractNo;
					requestAtWindow(url,"project_log_win","合同日志");
				}
			}

		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>