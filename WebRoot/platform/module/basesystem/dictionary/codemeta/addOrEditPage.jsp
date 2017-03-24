<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="codemeta_addForm">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%">
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="codemeta.key"/></td>
						<td><input name="cm.key" id="codemeta_key" value="${cm.key}" class="easyui-validatebox" required="true" validType="codemetaKey" maxLength="10"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="codemeta.name"/></td>
						<td><input name="cm.name" id="codemeta_name" value="${cm.name}" class="easyui-validatebox" required="true" maxLength="30" ></input><font color=red>*</font></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="cm.id" value="${cm.id}"/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" text="save" effect="round" click="doCMSave"/>
		<x:button iconCls="icon-cancel" text="cancel" effect="round" click="doCMCancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	$(function(){
		$.extend($.fn.validatebox.defaults.rules,{
			codemetaKey:{
				validator:function(value){
					return /^[A-Z]{1}\d{3,}$/.test(value);
				},
				message:global.valid_metakey
			}
		});
	});
	function doCMSave(){
		if($("#codemeta_addForm").form("validate")){
			var url = '<s:url value="/dictionary/codeMeta_addOrEdit.jhtml"/>';
			doPost(url,formToObject("codemeta_addForm"),function(result){
				if(!printError(result)){
					closeWindow("codemeta_add_edit");
					dataTable.refresh();
				}
			});
		}
	}
	function doCMCancel(){
		closeWindow("codemeta_add_edit");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>