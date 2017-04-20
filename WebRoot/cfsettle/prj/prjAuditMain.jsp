<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="项目审核" click="toReview" />
        <x:button iconCls="icon-view" text="项目成立" click="toBuild" />
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
				<x:column title="计划募集金额(元)" field="DEMAND_AMOUNT" align="center" width="140" formatter="formatAmount"/>
				<x:column title="实际募集金额(元)" field="REMAINING_AMOUNT" align="left" width="140" formatter="formateRaiseAmount" />
				<x:column title="项目期限" field="TIME_LIMIT" align="left" width="80" formatter="formateTimelimit"/>
				<x:column title="年利率" field="YEAR_RATE" align="left" width="80" formatter="formateRate"/>
				<x:column title="还款时间" field="LAST_REPAY_TIME" align="left" width="140" formatter="formatTime"/>
				<x:column title="项目收款银行" field="TENANT_BANK" align="left" width="140" formatter="formateBank"/>
				<x:column title="项目收款支行" field="SUB_BANK" align="left" width="140" />
				<x:column title="项目收款账号" field="ACCOUNT_NO" align="left" width="140" />
				<x:column title="状态" field="STATUS" align="left" width="80" formatter="forPrjStatus"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
        <div id="project_build_win" style="width:400px;height:auto;display:none;"></div>
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

	function formatAmount(value){
		return formatCurrency(value);
	}

	function formateRaiseAmount(value,field,row) {
	    return formatCurrency(row.DEMAND_AMOUNT-value);

    }

    function formateBank(value) {
        return code.getValue("<%=UtilConstant.CFS_BANK_TYPE%>",value);
    }

    function formateRate(value) {
		return value+"%";
    }

    function formateTimelimit(value,field,row) {
	    var timeLimitUnit = code.getValue("<%=UtilConstant.CFS_TIMELIMIT_UNIT%>",row.TIME_LIMIT_UNIT);
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
		
	function toReview(){
		if(isSingleSelected(dataTable)) {
			var selectedId = dataTable.getSelectedField("ID");
            var status = dataTable.getSelectedField("STATUS");
            if(status =="1" || status =="2"){
                var url = "<s:url value='/prj/prjAudit_toReview.jhtml'/>?id="+selectedId;
                redirectUrl(url);
			}else{
                warning("项目已审核通过，不能再次审核");
                return;
			}

		}
	}

    function toBuild(value) {
        if(isSingleSelected(dataTable)) {
            var selectedId = dataTable.getSelectedField("ID");
            var status = dataTable.getSelectedField("STATUS");
                var url = "<s:url value='/prj/prjAudit_toBuild.jhtml'/>?id="+selectedId;
                requestAtWindow(url,"project_build_win","成立项目");
        }
    }

	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
