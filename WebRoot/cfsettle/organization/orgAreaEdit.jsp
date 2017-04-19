<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="orgAreaEditForm">
			<input type="hidden" name="orgArea.id" value="${orgArea.id}"/>
			<input type="hidden" name="orgArea.ctime" value="${orgArea.ctime}"/>
			<input type="hidden" name="orgArea.csysid" value="${orgArea.csysid}"/>
			<table>
				<tbody>
				<tr>
				<td class="title">区域名: </td>
				<td colspan="3" >
				<input name="orgArea.areaName" value="${orgArea.areaName}" class="easyui-validatebox" required="true"/>
				</td>
				</tr>
					<tr>
						<td class="title">管辖区域: </td>
						<td colspan="3" >
							<input name="orgArea.overRange" value="${orgArea.overRange}" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
					<td class="title">状态<font color="red">*</font>:</td>
						<td colspan="3"><x:combobox name="orgArea.status" list="isActiveList" value="${orgArea.status}"
								required="true" textField="codeName" valueField="codeNo"
								cssStyle="width:142px;" /></td>
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
			function doEditSave(){
				if ($("#orgAreaEditForm").form("validate")) {
					var url = '<s:url value="/orgArea/orgAreaManage_doEdit.jhtml"/>';
					doPost(url, formToObject("orgAreaEditForm"),
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
