<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="cfscustAddForm">
			<table>
				<tr>
                    <td class="title">项目名:</td>
                    <td>${prj.prjName}</td>
                    <td class="title">项目方名:</td>
                    <td>${prj.prjUseName}</td>
                </tr>
                <tr>
                    <td class="title">项目联系电话:</td>
                    <td>${prj.prjMobile}</td>
                </tr>
                <tr>
                    <td class="title">募集金额:</td>
                    <td>${prj.demandAmount}万</td>
                    <td class="title">项目期限:</td>
                    <td>
                    	${prj.timeLimit}
                        <x:codeItem codeNo="prj.timeLimitUnit" codeKey="<%=UtilConstant.CFS_TIMELIMIT_UNIT %>"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">项目利率:</td>
                    <td>
                       ${prj.yearRate}%
                    </td>
                    <td class="title">募集期利率:</td>
                    <td>
                        ${prj.periodRate}%
                    </td>
                </tr>
                <tr>
                    <td class="title">还款方式:</td>
                    <td><x:codeItem codeNo="prj.repayWay" codeKey="<%=UtilConstant.CFS_REPAYMENT_TYPE %>"/></td>
                    <td class="title">项目成立金额:</td>
                    <td>${prj.minLoanAmount}万</td>
                </tr>
                <tr>
                    <td class="title">融资开标时间：</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.startBidTime"/></td>
                    <td class="title">融资截标时间：</td>
                    <td><s:date format="yyyy-MM-dd HH:mm:ss" name="prj.endBidTime"/></td>
                </tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="save" click="doAddSave" effect="round" />
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	function doAddSave() {
		var url="<s:url value='/cust/cfscust_doAdd.jhtml'/>";
		if ($("#cfscustAddForm").form("validate")) {
			doPost(url, formToObject("cfscustAddForm"),
				function(result) {
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
