<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="taskDelegated_addForm">
		<input type="hidden" name="humnTaskActorDelegate.actor" value="${user.sysUserId}"/>
		<input type="hidden" name="humnTaskActorDelegate.creator" value="${user.sysUserId}"/>
			<h3 align="center">新建委托办理</h3>
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="taskDelegated.actor"/>: </td>
						<td>
							<input type="hidden" name="humnTaskActorDelegate.delegator" id="delegator_id" />
							<input id="delegator_name" onClick="chooseBrchUser(delegatorCallBack)" class="inputSel easyui-validatebox" required="true"/><font color=red>*</font>
						</td>
					</tr>
				   	<tr>
				   		<td class="title"><s:text name="taskDelegated.startTime"/>: </td>
						<td>
							<input id="startingDate_add" name="humnTaskActorDelegate.startTime" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" style="width:160px" class="easyui-validatebox" required="true"/><font color=red>*</font>
						</td>	
					</tr>
					<tr>
						<td class="title"><s:text name="taskDelegated.endTime"/>: </td>	
						<td>
							<input id="expirDate_add" name="humnTaskActorDelegate.endTime" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" style="width:160px" class="easyui-validatebox" required="true"/><font color=red>*</font>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" text="save" click="doAddSave" effect="round"/>
		<x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	function doAddSave(){
		if($("#taskDelegated_addForm").form("validate")){
			var url = '<s:url value="/bpm/taskDelegated_doAdd.jhtml"/>';
			doPost(url,formToObject("taskDelegated_addForm"),function(result){
				if(!printError(result)){
					closeWindow("project_add_win");
					dataTable.refresh();
				}
			});
		}
	}
	function delegatorCallBack(row) {
		var userId = '';
		var userName = '';
		if (row && row.userId && row.userName) {
			userId = row.userId;
			userNo = row.userNo;
			userName = row.userName;
		}
		$("#delegator_id").val(userId);
		$("#delegator_name").val(userName);
	}
	
	function doAddCancel() {
		closeWindow("project_add_win");
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>