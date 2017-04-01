<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="项目审核" click="toReview" />

	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">团队名称: </td>
					<td><input name="searchBean.teamName" style="width:130px"/></td>
					<td class="title">营业部名: </td>
					<td><input name="searchBean.ownedDept" style="width:130px"/></td>
					<td class="title">区域名: </td>
					<td><input name="searchBean.ownedArea" style="width:130px"/></td>
					<td class="title">是否开启:</td>
					<td>
						<x:combobox name="searchBean.status" list="isActiveList" textField="codeName" valueField="codeNo" cssStyle="width:80px;"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" singleSelect="true" url="/prj/prjManage_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="项目名" field="PRJ_NAME" align="center" width="140"/>
				<x:column title="计划募集金额(元)" field="DEMAND_AMOUNT" align="center" width="320"/>
				<x:column title="实际募集金额(元)" field="REMAINING_AMOUNT" align="left" width="320" formatter="formateRaiseAmount" />
				<x:column title="项目期限" field="TIME_LIMIT" align="left" width="320" formatter="formateTimelimit"/>
				<x:column title="年利率" field="YEAR_RATE" align="left" width="140" formatter="formateRate"/>
				<x:column title="还款时间" field="LAST_REPAY_TIME" align="left" width="140" formatter="formatTime"/>
				<x:column title="项目收款银行" field="TENANT_BANK" align="left" width="140" formatter="formateBank"/>
				<x:column title="项目收款支行" field="SUB_BANK" align="left" width="140" />
				<x:column title="项目收款账号" field="ACCOUNT_NO" align="left" width="140" />
				<x:column title="状态" field="STATUS" align="left" width="140" formatter=""/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->

	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function formatIsActive(value){
		 return code.getValue("<%=UtilConstant.YES_NO%>",value);
	}

	function formateRaiseAmount(value,field,row) {
	    return row.DEMAND_AMOUNT-value;

    }

    function formateBank(value) {
        return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
    }

    function formateRate(value) {
		return value+"%";
    }

    function formateTimelimit(value,field,row) {
	    var timeLimitUnit = code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>>",row.TIME_LIMIT_UNIT);
	    return value+timeLimitUnit;

    }
	function formatTime(value) {
	    if(value == null){
	        return;
		}
		return DateFormat.format(new Date(value*1000),"yyyy-MM-dd hh:mm:ss");
	}

	function doQuery(){
		dataTable.load();
	}
		
	function toAdd(){
		var url="<s:url value='/prj/prjManage_toAdd.jhtml'/>";
		redirectUrl(url);
	}

	function toReview(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("ID");
			var url = "<s:url value='/prj/prjAudit_toReview.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
	}

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
