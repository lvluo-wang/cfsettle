<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="回款录入" click="toAdd"/>
		<x:button iconCls="icon-view" text="回款详情" click="toView"/>
		<x:button iconCls="icon-reload" text="本周截止回款" click="queryWeek"/>
		<x:button iconCls="icon-reload" text="本月截止回款" click="queryMonth"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">项目名: </td>
					<td><input name="searchBean.prjName" style="width:130px"/></td>
					<td class="title">状态:</td>
					<td>
						<x:combobox name="searchBean.status" list="prjStatusList" textField="codeName" valueField="codeNo" cssStyle="width:80px;"/>
					</td>
					<td class="title">回款截止时间:</td>
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
		<x:datagrid id="dataTable" singleSelect="true" url="/prj/payBack_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="项目名" field="PRJ_NAME" align="center" width="140"/>
				<x:column title="项目电话" field="PRJ_MOBILE" align="center" width="90"/>
				<x:column title="实际募集金额(元)" field="ACT_AMOUNT" align="center" width="120"/>
				<x:column title="项目利率" field="YEAR_RATE" align="center" width="60" formatter="formateRate"/>
				<x:column title="项目期限" field="TIME_LIMIT" align="center" width="60" formatter="formateTimelimit"/>
				<x:column title="回款本息(元)" field="PRI_INTEREST" align="center" width="100"/>
				<x:column title="回款本金(元)" field="PRINCIPAL" align="center" width="100" />
				<x:column title="回款利息(元)" field="YIELD" align="center" width="100"/>
				<x:column title="回款截止时间" field="REPAY_DATE" align="center" width="90" formatter="format2Date"/>
				<x:column title="回款期数" field="REPAY_PERIODS" align="center" width="140" formatter="formatPeriods"/>
				<x:column title="状态" field="STATUS" align="center" width="80" formatter="forRepayPlanStatus"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>"
	,"<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function forRepayPlanStatus(value){
		 return code.getValue("<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>",value);
	}

    function formateBank(value) {
        return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
    }

    function formateRate(value) {
		return value+"%";
    }
    
    function queryWeek(){
    	dataTable.load();
    }
    
    function queryMonth(){
    	dataTable.load();
    }

    function formateTimelimit(value,field,row) {
	    var timeLimitUnit = code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>",row.TIME_LIMIT_UNIT);
	    return value+timeLimitUnit;

    }
    
    function formatPeriods(val){
    	if(val==0){
    		return "募集期";
    	}else{
    		return "第"+val+"期";
    	}
    }

	function doQuery(){
		dataTable.load();
	}
		
	function toAdd(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("ID");
			var url="<s:url value='/prj/prjLoan_toAdd.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
	}

	function toView(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("ID");
            var status = dataTable.getSelectedField("STATUS");
            var url = "<s:url value='/prj/payBack_toView.jhtml'/>?id="+selectedId;
            redirectUrl(url);
		}
	}

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
