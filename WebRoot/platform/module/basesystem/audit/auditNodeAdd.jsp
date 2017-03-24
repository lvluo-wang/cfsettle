<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form method="post" class="busi_form" id="auditNodeAddForm" >
					<s:hidden name="auditNode.auditRouteId"/>
						<table>
							<colgroup>
								<col width="45%"/>
								<col width="55%"/>
							</colgroup>
							<tbody>
							<tr>
								<td class="title"><s:text name="auditNodeName"/>:</td>
								<td><input name="auditNode.auditNodeName" class="easyui-validatebox" validType="fieldName" maxlength="20" required="true" /><font color=red>*</font></td>
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
		<x:button iconCls="icon-save" onClick="doAuditNodeAddSave()" effect="round" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doAuditNodeAddCancel()" effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		function doAuditNodeAddSave(){
			if($("#auditNodeAddForm").form("validate")){
				var url = "<s:url value='/audit/audit_saveAuditNode.jhtml'/>";
				var parm=$("#auditNodeAddForm").serialize();
				doPost(url,parm,function(result){
					if(!printError(result)){
						reload();
						$(".easyui-window").window("close");
					}
				});
			}
		}
		function doAuditNodeAddCancel(){
			$(".easyui-window").window("close");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>