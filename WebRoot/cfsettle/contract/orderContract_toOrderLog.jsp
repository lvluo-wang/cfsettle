<%@page import="com.upg.cfsettle.util.CfsConstant"%>
<%@ page import="com.upg.cfsettle.util.UtilConstant" %>
<%@ page import="javax.rmi.CORBA.Util" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_BLANK">
	<tiles:putAttribute name="body">
		<form id="logQueryForm">
			<input type="hidden" value="${prjId}" name="auditLogBean.prjId"/>
			<input type="hidden" value="${contractNo}" name="auditLogBean.contractNo"/>
		</form>
		<x:datagrid id="dataTableLog" url="/contract/contract_orderLogList.jhtml" autoload="true" form="logQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="ID" />
				<x:column title="合同编号" field="CONTRACT_NO" align="left" width="80"/>
				<x:column title="投资时间" field="INVEST_TIME" align="center" width="100" formatter="formatTime" />
				<x:column title="客户姓名" field="REAL_NAME" align="center" width="80"/>
				<x:column title="购买项目" field="PRJ_NAME" align="center" width="100" />
				<x:column title="购买金额(元)" field="MONEY" align="center" width="80" formatter="formateMoney"/>
				<x:column title="付款银行" field="PAY_BANK" align="center" width="80" formatter="formateBank"/>
				<x:column title="付款卡号" field="PAY_ACCOUNT_NO" align="center" width="140"/>
				<x:column title="服务员工名" field="SERVICE_SYS_NAME" align="center" width="100"/>
				<x:column title="审核结果" field="LOG_STATUS" align="center" width="80" formatter="formateLogStatus"/>
				<x:column title="审核时间" field="AUDIT_TIME" align="center" width="100" formatter="formatTime"/>
				<x:column title="审核人" field="AUDIT_NAME" align="center" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="end">
		<script type="text/javascript">
			function formateLogStatus(value) {
				if(value == 1){
					return '通过';
				}
				if(value == 2){
					return '驳回';
				}
			}


		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>