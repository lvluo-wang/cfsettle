<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="auditRouteAddForm" >

						<table>
							<colgroup>
								<col width="45%"/>
								<col width="55%"/>
							</colgroup>
							<tbody>
							<tr>
								<td class="title" ><s:text name="auditRouteName"/>:</td>
								<td><input name="auditRoute.auditRouteName" class="easyui-validatebox" maxlength="20" validType="fieldName" required="true" /><font color=red>*</font></td>
							</tr>
							<tr>
								<td class="title"><s:text name="auditMode"/>:</td>
								<td>
									<x:combobox name="auditRoute.auditMode" required="true" list="auditMode" textField="codeName" valueField="codeNo"/><font color=red>*</font>
								</td>
							</tr>
							<tr>
								<td class="title"><s:text name="auditNodeExecMode"/>:</td>
								<td>
								<x:combobox name="auditRoute.auditNodeExecMode" required="true" list="nodeExecMode" textField="codeName" valueField="codeNo"/><font color=red>*</font>
								</td>
							</tr>
							<tr>
								<td class="title"><s:text name="auditRouteRemark"/>:</td>
								<td><textarea name="auditRoute.auditRouteRemark" class="easyui-validatebox"  required="true" validType="length[0,100]"></textarea><font color=red>*</font></td>
							</tr>
							</tbody>
						</table>
				</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" onClick="doAddSave()" effect="round" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doAddCancel()" effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
		function doAddSave(){
			if($("#auditRouteAddForm").form("validate")){
				var url = "<s:url value='/audit/audit_saveAuditRoute.jhtml'/>";
				var parm=formToObject("auditRouteAddForm");
				auditRouteListGrid.call(url,parm,function(){
					$("#auditRoute_add_edit").window("close");
				});
			}
		}
		function doAddCancel(){
			$("#auditRoute_add_edit").window("close");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
