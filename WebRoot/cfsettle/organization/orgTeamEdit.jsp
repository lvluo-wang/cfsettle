<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="orgTeamEditForm">
			<input type="hidden" value="${orgTeam.id}" name="orgTeam.id"/>
			<input type="hidden" name="orgTeam.ctime" value="${orgTeam.ctime}">
			<input type="hidden" name="orgTeam.csysid" value="${orgTeam.csysid}">
			<table>
				<tbody>
				<tr>
				<td class="title">团队名称: </td>
				<td colspan="3" >
				<input name="orgTeam.teamName" value="${orgTeam.teamName}" class="easyui-validatebox" required="true"/>
				</td>
				</tr>
					<tr>
						<td class="title">所属区域: </td>
						<td colspan="3">
							<input id="area_name" value="${orgTeam.areaName}" onClick="chooseArea(callBackArea)" class="inputSel easyui-validatebox" required="true"/>
							<input type="hidden" value="${orgTeam.ownedArea}" name="orgTeam.ownedArea" id="area_id">
						</td>
					</tr>
				<tr>
					<td class="title">所属营业部: </td>
					<td colspan="3" >
						<input id="dept_name" value="${orgTeam.deptName}" onClick="chooseDept(callBackDept,'area_id')" class="inputSel easyui-validatebox" required="true"/>
						<input type="hidden"  value="${orgTeam.ownedDept}" name="orgTeam.ownedDept" id="dept_id">
					</td>
				</tr>
					<tr>
					<td class="title">是否开启:<font color="red">*</font>:</td>
						<td colspan="3"><x:combobox name="orgTeam.status" value="${orgTeam.status}" list="isActiveList"
								required="true" textField="codeName" valueField="codeNo"
								cssStyle="width:142px;" pleaseSelect="false"/></td>
					</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="save" click="doEditSave" effect="round" />
		<x:button iconCls="icon-cancel" text="cancel" click="doEditCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">

			function callBackArea(row) {
                  if(row&&row.id&&row.areaName){
					  $("#area_id").val(row.id);
					  $("#area_name").val(row.areaName);
				  }
			}

			function callBackDept(row) {
				if(row&&row.id&&row.deptName){
					$("#dept_id").val(row.id);
					$("#dept_name").val(row.deptName);
				}
			}

			function doEditSave(){
				if ($("#orgTeamEditForm").form("validate")) {
					var url = '<s:url value="/orgTeam/orgTeamManage_doEdit.jhtml"/>';
					doPost(url, formToObject("orgTeamEditForm"),
							function(result) {
								if (!printError(result)) {
									closeWindow("project_edit_win");
									dataTable.refresh();
								}
							});

				}
			}
			function doEditCancel(){
				closeWindow("project_edit_win");
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
