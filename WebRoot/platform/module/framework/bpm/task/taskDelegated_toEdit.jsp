<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="taskDelegated_editForm">
		<input type="hidden" name="humnTaskActorDelegate.id" value="${humnTaskActorDelegate.id}"/>
		<input type="hidden" name="humnTaskActorDelegate.actor" value="${humnTaskActorDelegate.actor}"/>
		<input type="hidden" name="humnTaskActorDelegate.creator" value="${humnTaskActorDelegate.creator}"/>
		<input type="hidden" name="humnTaskActorDelegate.createTime" value="${humnTaskActorDelegate.createTime}"/>
		<input type="hidden" name="humnTaskActorDelegate.inEffect" value="${humnTaskActorDelegate.inEffect}"/>
			<h3 align="center">修改委托办理</h3>
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title"><s:text name="taskDelegated.actor"/>: </td>
						<td>
							<input type="hidden" name="humnTaskActorDelegate.delegator" id="delegator_id" value="${humnTaskActorDelegate.delegator}"/>
							<input id="delegator_name" onClick="chooseBrchUser(delegatorCallBack)" value="${humnTaskActorDelegate.delegatorName}" class="inputSel easyui-validatebox" required="true"/><font color=red>*</font>
						</td>
					</tr>
				   	<tr>
				   		<td class="title"><s:text name="taskDelegated.startTime"/>: </td>
						<td>
							<input id="startingDate_add" name="humnTaskActorDelegate.startTime" value='<s:date name="humnTaskActorDelegate.startTime" format="yyyy-MM-dd HH:mm:00"/>' class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" style="width:160px" class="easyui-validatebox" required="true"/><font color=red>*</font>
						</td>	
					</tr>
					<tr>
						<td class="title"><s:text name="taskDelegated.endTime"/>: </td>	
						<td>
							<input id="expirDate_add" name="humnTaskActorDelegate.endTime" value='<s:date name="humnTaskActorDelegate.endTime" format="yyyy-MM-dd HH:mm:00"/>' class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})" style="width:160px" class="easyui-validatebox" required="true"/><font color=red>*</font>
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
		if($("#taskDelegated_editForm").form("validate")){
			var url = '<s:url value="/bpm/taskDelegated_doEdit.jhtml"/>';
			doPost(url,formToObject("taskDelegated_editForm"),function(result){
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