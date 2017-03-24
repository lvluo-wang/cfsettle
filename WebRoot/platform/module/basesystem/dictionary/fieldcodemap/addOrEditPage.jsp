<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="fieldcodemap_addForm">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="table"/>:</td>
						<td><input name="fcm.tableName" value="${fcm.tableName}" class="easyui-validatebox" required="true" validType="tabField" maxLength="30"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="field"/>:</td>
						<td><input name="fcm.fieldName" value="${fcm.fieldName}" class="easyui-validatebox" required="true" validType="tabField" maxLength="30"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="codemeta.key"/>:</td>
						<td>
						<input id="add_fcm_codeKey_name" value="${cm.name}"  class="inputSel easyui-validatebox" required="true" onClick="chooseCodeMeta(chooseAddCodeMetaCallback)" readonly="readonly"/>
						<s:hidden name="fcm.codeKey" id="addFcmCodeKey" />
						<font color=red>*</font></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="fcm.id" value="${fcm.id}"/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button effect="round" iconCls="icon-save" text="save" click="doSave"/>
		<x:button effect="round" iconCls="icon-cancel" text="cancel" click="doCancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	$(function(){
		$.extend($.fn.validatebox.defaults.rules,{
			tabField:{
				validator:function(value){
					return /^([A-Z]|_|\d)+$/.test(value);
				},
				message:global.valid_table_field
			}
		});
	});
	function doSave(){
		if($("#fieldcodemap_addForm").form("validate")){
			var url = '<s:url value="/dictionary/fieldcodemap_addOrEdit.jhtml"/>';
			doPost(url,formToObject("fieldcodemap_addForm"),function(result){
				if(!printError(result)){
					closeWindow("fieldcodemap_add_edit");
					dataTable.refresh();
				}
			});
		}
	}
	function doCancel(){
		closeWindow("fieldcodemap_add_edit");
	}
	function chooseAddCodeMetaCallback(row){
		var n = '',v='';
		if(row&&row.key&&row.name){
			n = row.name;
			v=row.key;
		}
		$("#add_fcm_codeKey_name").val(n);
		$("#addFcmCodeKey").val(v);
		$("#add_fcm_codeKey_name").validatebox("validate");
	}
</script>	
	</tiles:putAttribute>
</tiles:insertDefinition>
