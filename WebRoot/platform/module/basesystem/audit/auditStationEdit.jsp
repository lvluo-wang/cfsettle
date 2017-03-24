<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form method="post" class="busi_form" id="auditStationEditForm" >
					<s:hidden name="auditStation.id" />
					<s:hidden name="auditStation.auditNodeId"/>
					<s:hidden name="auditStation.auditRouteId"/>
					<s:hidden name="auditStation.version" />
					<s:hidden name="auditStation.sortNo"/>
					<s:hidden name="auditStation.createBrchId"/>
					<s:hidden name="auditStation.bindBrchId" id="brchId"/>
						<table>
							<colgroup>
								<col width="45%"/>
								<col width="55%"/>
							</colgroup>
							<tbody>
							<tr>
								<td class="title"><s:text name="auditStationName"/>:</td>
								<td><input name="auditStation.auditStationName" value="${auditStation.auditStationName }" class="easyui-validatebox" maxlength="20" validType="fieldName" required="true" /><font color=red>*</font></td>
							</tr>
							<s:if test='auditNode.isPrivilegeCtrl=="1"'>
							<tr>
								<td class="title"><s:text name="auditStationPrivilege"/>:</td>
								<td><input name="auditStation.auditStationPrivilege" value="${auditStation.auditStationPrivilege }" class="easyui-validatebox" maxlength="20" validType="money" required="true" /><font color=red>*</font></td>
							</tr>
							</s:if>
							<s:else>
							<input type="hidden" name="auditStation.auditStationPrivilege" value="${auditStation.auditStationPrivilege }"/>
							</s:else>
							<s:if test="auditStation.createBrchId==null">
							<tr>
								<td class="title"><s:text name="stationBindBrch"/>:</td>
								<td>
										<input id="brchName"  value="${bindBrch.brchName}"  />
										<x:button icon="icon-search" click="chooseBranch(chooseBrchCallback)"/>
								</td>
							</tr>
							</s:if>
							<s:else>
							<tr>
								<td class="title"><s:text name="stationBindBrch"/>:</td>
								<td>
										<input id="brchName"  value="${bindBrch.brchName}" class="easyui-validatebox"  required="true" /><font color=red>*</font>
										<x:button icon="icon-search" click="chooseBranch(chooseBrchCallback)"/>
								</td>
							</tr>
							</s:else>
							
							<tr>
								<td class="title"><s:text name="auditStationRemark"/>:</td>
								<td>
									<textarea name="auditStation.auditStationRemark" class="easyui-validatebox"  required="true" validType="length[0,50]">${auditStation.auditStationRemark }</textarea><font color=red>*</font>
								</td>
							</tr>
							</tbody>
						</table>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" onClick="doAuditStationEditSave()" effect="round" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doAuditStationEditCancel()" effect="round" text="cancel"/>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	function doAuditStationEditSave(){
		if($("#auditStationEditForm").form("validate")){
			if($("#brchName").val()==""){
				$("#brchId").val("");
			}
			var url = "<s:url value='/audit/audit_modifyAuditStation.jhtml'/>";
			var parm=$("#auditStationEditForm").serialize();
			doPost(url,parm,function(result){
				if(!printError(result)){
					$(".easyui-window").window("close");
					reload();
				}
			});
		}
	}
	function doAuditStationEditCancel(){
		$(".easyui-window").window("close");
	}

</script>
</tiles:putAttribute>
</tiles:insertDefinition>