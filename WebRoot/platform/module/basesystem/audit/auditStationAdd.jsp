<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form method="post" class="busi_form" id="auditStationAddForm" >
				<s:hidden name="auditStation.bindBrchId" id="brchId" />
				<s:hidden name="auditStation.auditNodeId"  />
				<s:hidden name="auditStation.auditRouteId"/>
				<s:hidden name="auditStation.createBrchId"/>
						<table>
							<colgroup>
								<col width="45%"/>
								<col width="55%"/>
							</colgroup>
							<tbody>
							<tr>
								<td class="title" ><s:text name="auditStationName"/>:</td>
								<td><input name="auditStation.auditStationName" class="easyui-validatebox" maxlength="20" validType="fieldName" required="true" /><font color=red>*</font></td>
							</tr>
							<s:if test='auditNode.isPrivilegeCtrl=="1"'>
							<tr>
								<td class="title" ><s:text name="auditStationPrivilege"/>:</td>
								<td><input name="auditStation.auditStationPrivilege" class="easyui-validatebox" maxlength="20" validType="money" required="true" /><font color=red>*</font></td>
							</tr>
							</s:if>
							<s:else>
							<input type="hidden" name="auditStation.auditStationPrivilege" value="0" />
							</s:else>
							<s:if test="auditStation.createBrchId==null">
							<tr>
								<td class="title" ><s:text name="stationBindBrch"/>:</td>
								<td>
										<input id="brchName" />
										<x:button icon="icon-search" click="chooseBranch(chooseBrchCallback)"/>
								</td>
							</tr>
							</s:if>
							<s:else>
							<tr>
								<td class="title" ><s:text name="stationBindBrch"/>:</td>
								<td>
										<input id="brchName"  class="easyui-validatebox"  required="true" /><font color=red>*</font>
										<x:button icon="icon-search" click="chooseBranch(chooseBrchCallback)"/>
								</td>
							</tr>
							</s:else>
							
							<tr>
								<td class="title" ><s:text name="auditStationRemark"/>:</td>
								<td>
									<textarea name="auditStation.auditStationRemark" class="easyui-validatebox"  required="true" validType="length[0,50]"></textarea><font color=red>*</font>
								</td>
							</tr>
							</tbody>
						</table>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" onClick="doAuditStationAddSave()" effect="round" text="save"/>
		<x:button iconCls="icon-cancel" onClick="doAuditStationAddCancel()" effect="round" text="cancel"/>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	
	function doAuditStationAddSave(){
		if($("#auditStationAddForm").form("validate")){
			if($("#brchName").val()==""){
				$("#brchId").val("");
			}
			var url = '<s:url value="/audit/audit_saveAuditStation.jhtml"/>';
			var parm=$("#auditStationAddForm").serialize();
			doPost(url,parm,function(result){
				if(!printError(result)){
					reload();
					$(".easyui-window").window("close");
				}
			});
		}
	}
	function doAuditStationAddCancel(){
		$(".easyui-window").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>