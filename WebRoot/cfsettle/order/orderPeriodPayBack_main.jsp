<%@page import="com.upg.ucars.util.DateTimeUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_QUERY_DATA">
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td>
						<input name="searchBean.realName" style="width:130px"/>
						<input name="searchBean.isPeriod" type="hidden" value="0"/>
					</td>
					<td class="title">客户手机: </td>
					<td><input name="searchBean.mobile" style="width:130px"/></td>
					<td class="title">项目名: </td>
					<td><input name="searchBean.prjName" style="width:130px"/></td>
					<td class="title">还款状态:</td>
					<td>
						<x:combobox name="searchBean.status" list="planStatus" textField="codeName" valueField="codeNo" cssStyle="width:114px;"/>
					</td>
				</tr>
				<tr>
					<td class="title">投资时间:</td>
					<td colspan="3">
						<input id="beginTime" name="searchBean.startDate" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						-
						<input id="endTime" name="searchBean.endDate" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					</td>
					<td class="title">项目状态:</td>
					<td>
						<x:combobox name="searchBean.prjStatus" list="prjStatus" textField="codeName" valueField="codeNo" cssStyle="width:114px;"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" singleSelect="true" url="/order/orderPeriod_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="合同编号" field="CONTRACT_NO" align="center" width="140" formatter="formatContract"/>
				<x:column title="客户名" field="REAL_NAME" align="center" width="90"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="120"/>
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="140" formatter="format2Time"/>
				<x:column title="项目启动时间" field="BUILD_TIME" align="center" width="90" formatter="format2Date"/>
				<x:column title="还款时间" field="REPAY_DATE" align="center" width="90" formatter="format2Date"/>
				<x:column title="计息天数" field="COUNT_DAY" align="center" width="60"/>
				<x:column title="募集期利率" field="PERIOD_RATE" align="center" width="60" formatter="formateRate"/>
				<x:column title="待付本息(元)" field="PRI_INTEREST" align="center" width="80"/>
				<x:column title="待付本金(元)" field="PRINCIPAL" align="center" width="80"/>
				<x:column title="待付利息(元)" field="YIELD" align="center" width="80"/>
				<x:column title="回款期数" field="REPAY_PERIODS" align="center" width="70" formatter="formatPeriods"/>
				<x:column title="剩余本金(元)" field="REST_PRINCIPAL" align="center" width="80"/>
				<x:column title="购买金额(元)" field="MONEY" align="center" width="80"/>
				<x:column title="项目状态" field="PRJSTATUS" align="center" width="140" formatter="formatPrjStatus"/>
				<x:column title="状态" field="PLANSTATUS" align="center" width="80" formatter="formatPlanStatus"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_PRJ_STATUS%>","<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function formatPrjStatus(value){
		return code.getValue("<%=UtilConstant.CFS_PRJ_STATUS%>",value);
	}

    function formatPlanStatus(val,field,row) {
   		var value = code.getValue("<%=UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS%>",val);
   		var nowTime = parseInt("<%=DateTimeUtil.getNowSeconds()%>");
    	if(val==1&&Date.parse(new Date(row.REPAY_DATE))/1000 <= nowTime){
    		return"<a href='javascript:toRepay("+row.ID+")'>"+value+"</a>";
    	}else{
    		return value;
    	}
    }
	
    function formateRate(value) {
		return value+"%";
    }
    
    function formatContract(val,field,row){
    	return "<a href='javascript:toPeriod("+row.ID+")'>"+val+"</a>";
    }
    
    function toPeriod(planId) {
		var url = '<s:url value="/order/orderPeriod_toView.jhtml"/>?id='+planId;
		redirectUrl(url);
	}
    
    function toRepay(planId) {
		var url = '<s:url value="/order/orderPeriod_toAdd.jhtml"/>?id='+planId;
		redirectUrl(url);
	}
    
    function queryWeek(){
    	dataTable.params={'searchBean.queryType':'W'};
    	dataTable.load();
    }
    
    function queryMonth(){
    	dataTable.params={'searchBean.queryType':'M'};
    	dataTable.load();
    }
    
    function formatPeriods(val){
    	if(val==0){
    		return "募集期";
    	}else{
    		return "第"+val+"期";
    	}
    }

	function doQuery(){
		dataTable.params={'searchBean.queryType':''};
		dataTable.load();
	}
		
	function toAdd(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("PLANID");
			var url="<s:url value='/prj/payBack_toAdd.jhtml'/>?id="+selectedId;
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
