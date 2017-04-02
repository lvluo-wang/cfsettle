<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="cfscustEditForm">
			<table>
				<tr>	
					<td class="title">客户姓名<font color="red">*</font>:</td>
					<td >
						<input name="cfsCust.id" type="hidden" value="${cfsCust.id}"/>
						<input name="cfsCust.isValid" type="hidden" value="${cfsCust.isValid}"/>
						<input name="cfsCust.realName" class="easyui-validatebox" maxLength="50" required="true" value="${cfsCust.realName}">
					</td>
					<td class="title">客户手机<font color="red">*</font>:</td>
					<td ><input name="cfsCust.mobile" class="easyui-validatebox" maxLength="11" required="true" validType="mobile" value="${cfsCust.mobile}"></td>
				</tr>
				<tr>
					<td class="title">身份证号<font color="red">*</font>:</td>
					<td ><input name="cfsCust.idCard" class="easyui-validatebox" maxLength="21" required="true" validType="idCard" value="${cfsCust.idCard}"></td>
					<td class="title">性别<font color="red">*</font>:</td>
					<td ><x:combobox name="cfsCust.sex" list="sexList" required="true" textField="codeName" valueField="codeNo" value="${cfsCust.sex}" cssStyle="width:142px;" pleaseSelect="false"/></td>
				</tr>
				<tr>
					<td class="title">身份证正面: </td>
					<td>
						<input name="cfsCust.cardFace" type="hidden" id="cfscust_face_card_id_edit" value="${cfsCust.cardFace}"/>
						<img id="id_card_face_pic_edit" alt="身份证正面" src="http://" height="100px" width="200px"/>
					</td>
					<td colspan="2">
						<s:include value="/platform/common/uploadFile.jsp">
							<s:param name="refresh">y</s:param>
							<s:param name="suffix">1</s:param>
							<s:param name="imgServer">true</s:param>
							<s:param name="nowater">1</s:param>
							<s:param name="callback">idCardFaceCallBack</s:param>
							<s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
						</s:include></td>
				</tr>
				<tr>
					<td class="title">身份证反面: </td>
					<td>
						<input name="cfsCust.cardBack" type="hidden" id="cfscust_back_card_id_edit" value="${cfsCust.cardBack}"/>
						<img id="id_card_back_pic_edit" alt="身份证正面" src="http://" height="100px" width="200px"/>
					</td>
					<td colspan="2">
						<s:include value="/platform/common/uploadFile.jsp">
							<s:param name="refresh">y</s:param>
							<s:param name="suffix">2</s:param>
							<s:param name="imgServer">true</s:param>
							<s:param name="nowater">1</s:param>
							<s:param name="callback">idCardBackCallBack</s:param>
							<s:param name="opt">{'fileExt':'*.gif;*.jpg;*.png;*.jpeg','fileDesc':'图片文件'}</s:param>
						</s:include></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="save" click="doEditSave" effect="round" />
		<x:button iconCls="icon-cancel" text="cancel" click="doEditCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	$(function(){
		$('#id_card_face_pic_edit').attr('src',fileDownLoadUrl+"?id="+'${cfsCust.cardFace}');
		$('#id_card_back_pic_edit').attr('src',fileDownLoadUrl+"?id="+'${cfsCust.cardBack}');
	});
	function doEditSave() {
		var url="<s:url value='/cust/cfscust_doEdit.jhtml'/>";
		if ($("#cfscustEditForm").form("validate")) {
			doPost(url, formToObject("cfscustEditForm"),
				function(result) {
					if (!printError(result)) {
						closeWindow("project_edit_win");
						dataTable.refresh();
					}
				});
		}
	}
	function doEditCancel() {
		closeWindow("project_edit_win");
	}

	function idCardFaceCallBack(value) { 
		$('#id_card_face_pic_edit').attr('src',fileDownLoadUrl+"?id="+value.id);
		$('#cfscust_face_card_id_edit').val(value.id);
	}
	
	function idCardBackCallBack(value) { 
		$('#id_card_back_pic_edit').attr('src',fileDownLoadUrl+"?id="+value.id);
		$('#cfscust_back_card_id_edit').val(value.id);
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>