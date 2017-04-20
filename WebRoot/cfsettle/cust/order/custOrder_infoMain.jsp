<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page import="javax.rmi.CORBA.Util" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<%--判断登录人能看到的订单--%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="订单详情" click="doView" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户姓名: </td>
					<td style="width: 20px;"><input name="searchBean.realName" /></td>
					<td class="title">项目名: </td>
					<td style="width: 20px;"><input name="searchBean.prjName" /></td>
					<td class="title">合同编号: </td>
					<td style="width: 20px;"><input name="searchBean.contractNo" /></td>
					<td class="title">状态:</td>
					<td style="width: 20px;" >
						<x:combobox name="searchBean.status" list="orderStatusList" textField="codeName" valueField="codeNo"/>
					</td>
					</tr>
					<tr>
					<td class="title">投资日期:</td>
					<td colspan="3">
						<input id="beginTime" name="searchBean.startDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'#F{$dp.$D(\'endTime\',{d:-31})}',maxDate:'#F{$dp.$D(\'endTime\')}',onpicked:function(){endTime.focus();}})" />
						-
						<input id="endTime" name="searchBean.endDate" class="Wdate" value="<s:date format="yyyy-MM-dd" name=""/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:'#F{$dp.$D(\'beginTime\',{d:31})}',minDate:'#F{$dp.$D(\'beginTime\')}'})" />
					</td>
					<td class="title">服务员工: </td>
					<td style="width: 20px;"><input name="searchBean.serviceSysName" /></td>
					<s:if test='logonInfo.userType==@com.upg.ucars.mapping.basesystem.security.Buser@TYPE_BRCH_GLOBAL_MANAGER'>
					<td class="title">服务类型:</td>
					<td style="width: 20px;" >
						<x:combobox name="searchBean.serviceSysType" list="buserPosCodeList" textField="codeName" valueField="codeName"/>
					</td>
					</s:if>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/custOrder/custOrder_orderInfoList.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="合同编号" field="CONTRACT_NO" align="left" width="90"/>
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="150" formatter="formatTime" />
				<x:column title="客户姓名" field="REAL_NAME" align="center" width="140"/>
				<x:column title="客户手机" field="MOBILE" align="left" width="90" formatter="formatMobile"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="150" />
				<x:column title="购买金额(元)" field="MONEY" align="center" width="150" formatter="formateMoney"/>
				<x:column title="付款银行" field="PAY_BANK" align="center" width="80" formatter="formateBank"/>
				<x:column title="付款卡号" field="PAY_ACCOUNT_NO" align="center" width="140"/>
				<x:column title="预计还款时间" field="CURRENT_REPAY_DATE" align="center" width="140" formatter="format2Time"/>
				<x:column title="服务员工类型" field="SERVICE_SYS_TYPE" align="left" width="90" formatter="formateLeaveJob"/>
				<x:column title="服务员工姓名" field="SERVICE_SYS_NAME" align="center" width="150" formatter="formateLeaveJob" />
				<x:column title="归属团队" field="OWNED_TEAM_NAME" align="center" width="140" formatter="formateLeaveJob"/>
				<x:column title="归属营业部" field="OWNED_DEPT_NAME" align="left" width="90" formatter="formateLeaveJob"/>
				<x:column title="归属区域" field="OWNED_AREA_NAME" align="left" width="90" formatter="formateLeaveJob"/>
				<x:column title="状态" field="STATUS" align="center" width="150" formatter="formateStatus" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	</tiles:putAttribute>
	
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=["<%=UtilConstant.CFS_BANK_TYPE%>","<%=UtilConstant.CFS_PRJ_ORDER_STATUS%>","<%=UtilConstant.CFS_BUSER_POS_CODE%>"];
	var code=new XhhCodeUtil(keys);
	code.loadData();

	function formatMobile(value,field,row) {
		var buserId = ${logonInfo.sysUserId};
		if(buserId != row.SERVICE_SYSID){
			return '-';
		}
		return value;
	}

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
    function formateLeaveJob(value,field,row) {
		if(row.BUSER_STATUS=='4'){
		    return "待分配";
		}
		if(field == 'SERVICE_SYS_TYPE'){
			return code.getValue("<%=UtilConstant.CFS_BUSER_POS_CODE%>",value);
		}
		return value;
    }
	
	function doQuery(){
		dataTable.load();
	}

	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("ID");
			var url="<s:url value='/custOrder/custOrder_toView.jhtml'/>?id="+selectedId;
			redirectUrl(url);
		}
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
