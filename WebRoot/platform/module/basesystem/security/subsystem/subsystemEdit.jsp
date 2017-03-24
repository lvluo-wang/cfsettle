<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
				<form method="post" id="editForm" class="busi_form">
					<s:hidden name="subsystem.version"></s:hidden>
					<s:hidden name="subsystem.subsysId"></s:hidden>
					<s:hidden name="subsystem.subsysStatus"></s:hidden>
					<table>
					<colgroup>
	                    <col width="35%"/>
	                    <col width="65%"/>
	                 </colgroup>
					 <tbody>
						<tr>
							<td class="title"><s:text name="name"/>:</td>
							<td><input name="subsystem.subsysName" value="${subsystem.subsysName}"  class="easyui-validatebox" required="true" maxLength="20"  validType="subsysName"></input><font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="subsystem.type"/>:</td>
							<td>
								<x:combobox name="subsystem.type" value="${subsystem.type}" valueField="codeNo" textField="codeName" list="typeList" pleaseSelect="false"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="title"><s:text name="remark"/>:</td>
							<td><textarea class="easyui-validatebox" validType="length[0,100]" name="subsystem.remark" >${subsystem.remark}</textarea></td>
						</tr>
						</tbody>
						</table>
				</form> 
			</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button click="doMondify" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doMondify(){
		if($("#editForm").form("validate")){
			$.messager.confirm(global.alert,global.update_confirm_info,function(r){
				if(r){
					var url = "<s:url value='/security/subsystem_update.jhtml'/>";
					doPost(url,formToObject("editForm"),function(result){
				if(result){
					var obj=str2obj(result);
					if(obj.error){
						error(obj.error);
					}
					return;
				}
				subsysMainDG.refresh();
				$('#subsystem_add_edit').window('close');
			});
				}
			});
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>