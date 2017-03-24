<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="code_addForm">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%">
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="no"/>:</td>
						<td><input name="code.codeNo" value="${code.codeNo}" class="easyui-validatebox" required="true" validType="codeNo" maxLength="15"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="name"/>:</td>
						<td><input name="code.codeName" value="${code.codeName}" class="easyui-validatebox" required="true" maxLength="25"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="codemeta.key"/>:</td>
						<td>
						<input id="add_code_codeKey" value="${cm.name}" class="inputSel easyui-validatebox" onClick="chooseCodeMeta(chooseAddCodeMetaCallback)" required="true"/>
						<s:hidden name="code.codeKey" id="addCodeMetaKey" />
						<font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="language"/>:</td>
						<td>
							<x:combobox name="code.langType" list="langType" required="true" valueField="codeNo" textField="codeName"/><font color=red>*</font>
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="code.id" value="${code.id}"/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" text="save" click="doSave" effect="round"/>
		<x:button iconCls="icon-cancel" text="cancel" click="doCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	$(function(){
		$.extend($.fn.validatebox.defaults.rules,{
			codeNo:{
				validator:function(value){
					return /^(\w|\d){1,}$/.test(value);
				},
				message:global.valid_codeNo
			}
		});
	});
	function doSave(){
		if($("#code_addForm").form("validate")){
			var url = '<s:url value="/dictionary/code_addOrEdit.jhtml"/>';
			doPost(url,formToObject("code_addForm"),function(result){
				if(!printError(result)){
					closeWindow("code_add_edit");
					dataTable.refresh();
				}
			});
		}
	}
	function doCancel(){
		closeWindow("code_add_edit");
	}
	function chooseAddCodeMetaCallback(row){
		var n = '',v='';
		if(row&&row.key&&row.name){
			n = row.name;
			v=row.key;
		}
		$("#add_code_codeKey").val(n);
		$("#addCodeMetaKey").val(v);
		$("#add_code_codeKey").validatebox("validate");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>