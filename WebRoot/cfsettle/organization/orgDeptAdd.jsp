<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="orgDeptAddForm">
			<table>
				<tbody>
				<tr>
				<td class="title">营业部名称: </td>
				<td colspan="3" >
				<input name="orgDept.deptName" class="easyui-validatebox" required="true"/>
				</td>
				</tr>
					<tr>
						<td class="title">所属区域: </td>
						<td colspan="3">
							<input id="area_name" onClick="chooseArea(callBackArea)" class="inputSel easyui-validatebox" required="true"/>
							<input type="hidden" name="orgDept.ownedArea" id="area_id">
						</td>
					</tr>
				<tr>
					<td class="title">营业部电话: </td>
					<td colspan="3" >
						<input name="orgDept.deptMobile" class="easyui-validatebox" required="true"/>
					</td>
				</tr>
				<tr>
					<td class="title">营业部地址: </td>
					<td colspan="3" >
						<input name="orgDept.deptAddr" class="easyui-validatebox" required="true"/>
					</td>
				</tr>
					<tr>
					<td class="title">是否开启:<font color="red">*</font>:</td>
						<td colspan="3"><x:combobox name="orgDept.status" list="isActiveList"
								required="true" textField="codeName" valueField="codeNo"
								cssStyle="width:180px;" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="save" iconCls="icon-save" text="save" click="doAddSave" effect="round" />
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round" />
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">

			function callBackArea(row) {
                  if(row&&row.id&&row.areaName){
					  $("#area_id").val(row.id);
					  $("#area_name").val(row.areaName);
				  }
			}

			function doAddSave(){
				if ($("#orgDeptAddForm").form("validate")) {
					var url = '<s:url value="/orgDept/orgDeptManage_doAdd.jhtml"/>';
					doPost(url, formToObject("orgDeptAddForm"),
							function(result) {
								if (!printError(result)) {
									closeWindow("project_add_win");
									dataTable.refresh();
								}
							});

				}
			}
			function doAddCancel(){
				closeWindow("project_add_win");
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
