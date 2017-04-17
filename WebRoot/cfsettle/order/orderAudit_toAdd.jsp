<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="cfsOrderAuditForm">
			<table>
				<tr>
                    <td class="title">合同编号:</td>
                    <td>${cfsPrjOrder.contractNo}</td>
                    <td class="title">客户姓名:</td>
                    <td>${cfsCust.realName}</td>
                </tr>
                <tr>
                    <td class="title">所属客户经理:</td>
                    <td>${cfsPrjOrder.serviceSysName}</td>
                    <td class="title">客户经理电话:</td>
                    <td>${cfsPrjOrder.mobile}</td>
                </tr>
                <tr>
                    <td class="title">所属营业部:</td>
                    <td>${cfsPrjOrder.ownedDeptName}</td>
                    <td class="title">投资项目:</td>
                    <td>${prj.prjName}</td>
                </tr>
                <tr>
                    <td class="title">投资金额(元):</td>
                    <td>${cfsPrjOrder.money}</td>
                    <td class="title">投资时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="cfsPrjOrder.investTime"/></td>
                </tr>
				<tr>
                    <td class="title">付款银行:</td>
                    <td>
                        <x:codeItem codeNo="cfsPrjOrder.payBank" codeKey="<%=UtilConstant.CFS_BANK_TYPE%>"/>
                    </td>
                    <td class="title">资金流水编号:</td>
                    <td>
                    	<input name="cfsPrjOrder.paySerialNum" type="text" class="easyui-validatebox" maxLength="50" required="true"/>
                    	<input name="cfsPrjOrder.id" type="hidden" value="${cfsPrjOrder.id}"/>
                    </td>
                </tr>
				<tr>
					<td class="title">打款凭证</td>
                    <td><input name="cfsPrjOrder.payNotesAttid" type="hidden" id="order_pay_notes_id" value="${cfsPrjOrder.payNotesAttid}"/>
						<img id="order_pay_notes_pic" alt="打款凭证" src="http://" height="100px" width="200px"/>
					</td>
					<td colspan="2"></td>
                </tr>
                <tr>
                    <td class="title">审核备注：</td>
                    <td colspan="3"><textarea cols="65" class="easyui-validatebox" required="true" name="cfsPrjOrder.remark"></textarea></td>
                </tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="审核通过" click="doAddSave(4)" effect="round" />
		<x:button iconCls="icon-cancel" text="审核不通过" click="doAddSave(3)" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
		$('#order_pay_notes_pic').attr('src',fileDownLoadUrl+"?id="+'${cfsPrjOrder.payNotesAttid}');
	});
	function doAddSave(status) {
		var url="<s:url value='/order/orderAudit_doAudit.jhtml'/>";
		if ($("#cfsOrderAuditForm").form("validate")) {
			var param = formToObject("cfsOrderAuditForm");
			param['cfsPrjOrder.status'] = status;
			doPost(url, param,function(result) {
					if (!printError(result)) {
						closeWindow("project_add_win");
						dataTable.refresh();
					}
				});
		}
	}
	function doAddCancel() {
		closeWindow("project_add_win");
	}

	function idCardPayNotesCallBack(value) { 
		$('#order_pay_notes_pic').attr('src',fileDownLoadUrl+"?id="+value.id);
		$('#order_pay_notes_id').val(value.id);
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
