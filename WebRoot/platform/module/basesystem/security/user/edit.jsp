<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
		<form method="post" id="editForm" class="busi_form">
			<table>
			    <colgroup>
                   <col width="35%"/>
                   <col width="65%"/>
                </colgroup>
			 <tbody>
			 	<tr>
					<td class="title">岗位:</td>
					<td>
						<x:combobox name="user.posCode"  valueField="codeNo" id="pos_code_edit" value="${user.posCode}" textField="codeName" list="posCodeList" pleaseSelect="false" cssStyle="width:142px;"/>
						<input name="user.userId" value="${user.userId}" type="hidden" id="user_id"/>
						<input name="user.brchId" value="${user.brchId}" type="hidden"/>
					</td>
				</tr>
				<tr>
					<td class="title">手机号码:</td>
					<td><input name="user.userNo" value="${user.userNo}" class="easyui-validatebox" maxlength="30" required="true" validType="mobile" /><font color="red">*</font></td>
				</tr>
				<tr>
					<td class="title"><s:text name="user" /><s:text name="name" />:</td>
					<td><input name="user.userName" value="${user.userName}" class="easyui-validatebox" required="true" validType="userName" /><font color="red">*</font></td>
				</tr>
				<tr>
					<td class="title"><s:text name="user"/><s:text name="email"/>:</td>
					<td>
						<input name="user.email" value="${user.email}" class="easyui-validatebox" required="true" maxlength="50" validType="email"/><font color="red">*</font>
						<input name="user.userType" value="6" type="hidden" />
						<input name="user.brchId" value="1" type="hidden" />
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
			
	<tiles:putAttribute name="button">
		<x:button id="btn_edit_member" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	    
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	$(function(){
		var posCode="${user.posCode}";
		if(posCode=="01"){
			$('#user_team_tr_edit').show();
		}else{
			$('#user_team_tr_edit').hide();
			$('#user_team_id_edit').xcombobox('setValue','');
		}
	});
	function chooseBranch() {
		var url = '<s:url value="/security/user_toChooseBrch.jhtml"/>';
		requestAtWindow(url, "sel","<s:text name="choose"/><s:text name="branch"/>");
	}
	function doSave() {
		if ($("#editForm").form("validate")) {
			var url = "<s:url value='/security/user_updateUser.jhtml'/>";
			var parm=formToObject("editForm");
			doPost(url,parm,function(result){
				if(result){
					var o=str2obj(result);
					if(o.error){
						error(o.error);
						return;
					}
				}
				userMainDG.refresh();
				$('#user_add_edit').window('close');
			});
		}

	}
	function doCancel() {
		$("#user_add_edit").window("close");
	}
	
	function chooseBranchCallback(row){
	 if(row && row.brchName && row.brchId){
			$("#brchName").val(row.brchName);
			$("#brchValue").val(row.brchId);
		}
	}
	
	function loadDeptEditList(){
		var areaId = $("#user_area_id_edit").xcombobox("getValue");
		var userId = $('#user_id').val();
		$("#user_dept_id_edit").xcombobox("reload",{'url':'<s:url value="/orgDept/orgDeptManage_getCombobox.jhtml"/>?searchBean.ownedArea='+areaId+'&searchBean.status=1'+'&id='+userId});
	}
	
	function loadTeamEditList(){
		var teamId = $("#user_dept_id_edit").xcombobox("getValue");
		var userId = $('#user_id').val();
		$("#user_team_id_edit").xcombobox("reload",{'url':'<s:url value="/orgTeam/orgTeamManage_getCombobox.jhtml"/>?searchBean.ownedDept='+teamId+'&searchBean.status=1'+'&id='+userId});
	}
	
	function loadAreaEditList(){
		var posCode = $('#pos_code_add').xcombobox("getValue");
		var userId = $('#user_id').val();
		$("#user_area_id_edit").xcombobox("reload",{'url':'<s:url value="/orgArea/orgAreaManage_getCombobox.jhtml"/>?searchBean.status=1'+'&searchBean.posCode='+posCode+'&id='+userId});
	}
	
	function posCodeChange(){
		var posCode = $('#pos_code_edit').xcombobox("getValue");
		if(posCode=="01"){
			$('#user_team_tr_edit').show();
		}else{
			$('#user_team_tr_edit').hide();
			$('#user_team_id_edit').xcombobox('setValue','');
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>