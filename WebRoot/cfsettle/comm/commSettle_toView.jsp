<%@page import="com.upg.ucars.constant.CodeKey"%>
<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_TOOL_FORM">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="commSettleView">
			<table>
				<colgroup>
					<col width="25%"/>
					<col width="30%"/>
					<col width="25%"/>
					<col width="30%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title">员工姓名: </td>
						<td>${buser.userName}</td>
						<td class="title">员工手机: </td>
						<td>${buser.userNo}</td>
					</tr>
					<tr>
						<td class="title">员工职位: </td>
						<td><x:codeItem codeKey="<%=UtilConstant.CFS_BUSER_POS_CODE%>" codeNo="buser.posCode"/></td>
						<td class="title">佣金计提金额(元): </td>
						<td>${commInfo.commMoney}</td>
					</tr>
					
				</tbody>
			</table>
		</form>
		<div class="func_data_area">
   			<x:datagrid id="dataTableView"  url="/comm/myComm_commDetailList.jhtml?id=${commInfo.id}" autoload="true">
				<x:columns>
					<x:column title="合同编号" field="CONTRACT_NO" align="center" width="140"/>
					<x:column title="投资时间" field="INVEST_TIME" align="center" width="140" formatter="formatTime"/>
					<x:column title="客户名" field="REAL_NAME" align="center" width="100"/>
					<x:column title="购买项目" field="PRJ_NAME" align="center" width="100"/>
					<x:column title="购买金额" field="MONEY" align="center" width="100"/>
					<x:column title="佣金计提比例" field="COMM_RATE" align="center" width="90" formatter="formatCommRate"/>
					<x:column title="佣金金额" field="COMM_ACCOUNT" align="center" width="90"/>
				</x:columns>
			</x:datagrid>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	function doCancel(){
		history.back();
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>