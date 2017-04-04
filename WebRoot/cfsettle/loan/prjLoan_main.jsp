<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="放款录入" click="toAdd"/>
		<x:button iconCls="icon-view" text="放款详情" click="toView"/>
		<x:button iconCls="icon-down" text="导出Excel" click="doExport"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">借款人姓名: </td>
					<td><input name="searchBean.prjUseName" style="width:130px"/></td>
					<td class="title">借款人手机: </td>
					<td><input name="searchBean.prjMobile" style="width:130px"/></td>
					<td class="title">项目名: </td>
					<td><input name="searchBean.prjName" style="width:130px"/></td>
					<td class="title">状态:</td>
					<td>
						<x:combobox name="searchBean.status" list="prjStatusList" textField="codeName" valueField="codeNo" cssStyle="width:80px;"/>
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" singleSelect="true" url="/prj/prjLoan_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="项目名" field="PRJ_NAME" align="center" width="140"/>
				<x:column title="计划募集金额(元)" field="DEMAND_AMOUNT" align="center" width="140"/>
				<x:column title="实际募集金额(元)" field="REMAINING_AMOUNT" align="left" width="140" formatter="formateRaiseAmount" />
				<x:column title="项目期限" field="TIME_LIMIT" align="left" width="80" formatter="formateTimelimit"/>
				<x:column title="年利率" field="YEAR_RATE" align="left" width="80" formatter="formateRate"/>
				<x:column title="还款时间" field="LAST_REPAY_TIME" align="left" width="140" formatter="formatTime"/>
				<x:column title="项目收款银行" field="TENANT_BANK" align="left" width="140" formatter="formateBank"/>
				<x:column title="项目收款支行" field="SUB_BANK" align="left" width="140" />
				<x:column title="项目收款账号" field="ACCOUNT_NO" align="left" width="140" />
				<x:column title="已放款金额(元)" field="LOANED_AMOUNT" align="center" width="140" formatter="formateRaiseAmount"/>
				<x:column title="待放款金额(元)" field="LOANING_AMOUNT" align="left" width="140" formatter="formateRaiseAmount"/>
				<x:column title="状态" field="STATUS" align="left" width="80" formatter="forPrjStatus"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_TIMELIMIT_UNIT%>","<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_REPAYMENT_TYPE%>"
	,"<%=UtilConstant.CFS_PRJ_STATUS%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function forPrjStatus(value){
		 return code.getValue("<%=UtilConstant.CFS_PRJ_STATUS%>",value);
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
	    if(value == ""){
	        return '';
		}
		return DateFormat.format(new Date(value*1000),"yyyy-MM-dd hh:mm:ss");
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
            if(status =="1" || status =="2"){
                var url = "<s:url value='/prj/prjLoan_toReview.jhtml'/>?id="+selectedId;
                redirectUrl(url);
			}else{
                warning("项目已审核通过，不能再次审核");
                return;
			}

		}
	}

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
