<%@page import="com.upg.cfsettle.util.UtilConstant"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="cfscustAddForm">
			<table>
				<tr>
                    <td class="title">客户姓名:</td>
                    <td>${cfsCust.realName}</td>
                    <td class="title">所属客户经理:</td>
                    <td>${cfsPrjOrder.serviceSysName}</td>
                </tr>
				<tr>
                    <td class="title">客户经理电话:</td>
                    <td>${cfsPrjOrder.mobile}</td>
                    <td class="title">所属营业部:</td>
                    <td>${cfsPrjOrder.ownedDeptName}</td>
                </tr>
                <tr>
                    <td class="title">投资项目:</td>
                    <td>${prj.prjName}</td>
                    <td class="title">投资金额(元):</td>
                    <td>${cfsPrjOrder.money}</td>
                </tr>
                <tr>
                    <td class="title">投资时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="cfsPrjOrder.investTime"/></td>
                    <td class="title">付款银行:</td>
                    <td>
                        <x:codeItem codeNo="cfsPrjOrder.payBank" codeKey="<%=UtilConstant.CFS_BANK_TYPE%>"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">付款卡号:</td>
                    <td>${cfsPrjOrder.payAccountNo}</td>
                    <td class="title">资金流水编号:</td>
                    <td>${cfsPrjOrder.paySerialNum}</td>
                </tr>
                <tr>
                    <td class="title">审核结果:</td>
                    <td><x:codeItem codeNo="cfsPrjOrder.status" codeKey="<%=UtilConstant.CFS_PRJ_ORDER_STATUS %>"/></td>
                    <td class="title">审核时间:</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="cfsPrjOrder.collectAuditTime"/></td>
                </tr>
                <tr>
                    <td class="title">审核人：</td>
                    <td>${cfsPrjOrder.contractAuditUser}</td>
                    <td class="title">打款凭证</td>
                    <td><img id="pay_notes_attid" alt="打款凭证" src="http://" height="100px" width="200px"/></td>
                </tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
		$('#pay_notes_attid').attr('src',fileDownLoadUrl+"?id="+'${cfsPrjOrder.payNotesAttid}');
	});
	function doAddCancel() {
		closeWindow("project_edit_win");
	}

	function idCardFaceCallBack(value) { 
		$('#id_card_face_pic').attr('src',fileDownLoadUrl+"?id="+value.id);
		$('#cfscust_face_card_id').val(value.id);
	}
	
	function idCardBackCallBack(value) { 
		$('#id_card_back_pic').attr('src',fileDownLoadUrl+"?id="+value.id);
		$('#cfscust_back_card_id').val(value.id);
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>