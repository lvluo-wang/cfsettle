<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form method="post" class="busi_form" id="auditNodeEditForm" >
					<s:hidden name="auditNode.id" />
					<s:hidden name="auditNode.auditRouteId"/>
					<s:hidden name="auditNode.sortNo"/>
					<s:hidden name="auditNode.version" />
						<table>
							<colgroup>
								<col width="45%"/>
								<col width="55%"/>
							</colgroup>
							<tbody>
							<tr>
								<td class="title"><s:text name="auditNodeName"/>:</td>
								<td><input name="auditNode.auditNodeName" value="${auditNode.auditNodeName }" class="easyui-validatebox" maxlength="20" validType="fieldName" required="true" /><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="openAuditStationPrivilege"/>:</td>
								<td>
								<x:combobox name="auditNode.isPrivilegeCtrl" required="true" list="isPrivilegeCtrl" textField="codeName" valueField="codeNo"/><font color=red>*</font>
								</td>
							</tr>
							</tbody>
						</table>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" onClick="doAuditNodeEditSave()" effect="round" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doAuditNodeEditCancel()" effect="round" text="cancel"/>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	function doAuditNodeEditSave(){
		if($("#auditNodeEditForm").form("validate")){
			var url = "<s:url value='/audit/audit_modifyAuditNode.jhtml'/>";
			var parm=$("#auditNodeEditForm").serialize();
			doPost(url,parm,function(result){
				if(!printError(result)){
					$(".easyui-window").window("close");
					reload();
				}
			});
		}
	}
	function doAuditNodeEditCancel(){
		$("#auditNode_add_edit").window("close");
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>