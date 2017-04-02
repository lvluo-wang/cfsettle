<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="订单详情" click="doView" />
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
					<td class="title">状态:</td>
					<td>
						<x:combobox name="searchBean.status" list="" textField="codeName" valueField="codeNo"/>
					</td>
					<td class="title">投资日期:</td>
					<td>
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
		<x:datagrid id="dataTable" url="/custOrder/custOrder_list.jhtml" autoload="true" form="mainQueryForm">
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
				<x:column title="预计还款时间" field="CURRENT_REPAY_DATE" align="center" width="40" formatter="formatTime"/>
				<x:column title="还款期数" field="CURRENT_PREIOD" align="left" width="90" formatter="formatPeriod"/>
				<x:column title="状态" field="STATUS" align="center" width="150" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:750px;height:480px;display:none;"></div>
	<div id="project_edit_win" style="width:750px;height:480px;display:none;"></div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=CfsConstant.CFS_COMM_YSE_NO%>","<%=CfsConstant.CFS_COMM_SEX%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

    function formatTime(value) {
        if(value == ""){
            return '';
        }
        return DateFormat.format(new Date(value*1000),"yyyy-MM-dd hh:mm:ss");
    }

    function formatPeriod(value,field,row) {
		if(value == null){
		    return '-';
		}
		return value+"/"+row.TOTAL_PERIOD;
    }
	
	function doQuery(){
		dataTable.load();
	}

	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("ID");
			var url="<s:url value='/cust/custOrder_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
