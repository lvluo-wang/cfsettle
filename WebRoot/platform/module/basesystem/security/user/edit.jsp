<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
		<form method="post" id="editForm" class="busi_form">
		<s:hidden name="user.userId"/>
		<s:hidden name="user.brchId" id="brchValue" />
			<table>
			    <colgroup>
                   <col width="35%"/>
                   <col width="65%"/>
                </colgroup>
			 <tbody>
			 	<tr>
					<td class="title">岗位:</td>
					<td>
						<x:combobox name="user.posCode"  valueField="codeNo" id="pos_code_edit" value="${user.posCode}" textField="codeName" list="posCodeList" pleaseSelect="true" cssStyle="width:142px;" onchange="posCodeEditChange"/>
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
					<td><input name="user.email" value="${user.email}" class="easyui-validatebox" required="true" maxlength="50" validType="email"/><font color="red">*</font></td>
				</tr>
						
				<tr>
					<td class="title"><s:text name="user" /><s:text name="type" />:</td>
					<td>
						<x:combobox id="user_userType" name="user.userType"  value="${user.userType}" valueField="codeNo" textField="codeName" list="userTypeList" pleaseSelect="false"/>
					</td>
				</tr>
				<%
							if( ! UserManager.isImplementation()){
						%>
								<tr id="chooseBrchDiv">
									<td class="title"><s:text name="belong_branch"/>:</td>
									<td>
									<div class="searchBox">
										<input id="brchName" readonly="readonly" name="brchName" value="${branch.brchName}" class="easyui-validatebox" required="true"/>
										<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onClick="chooseTreeBranch(chooseBranchCallback)"></a>
									</div>
									</td>			
								</tr>
						<%
							}
						%>
						<tr>
							<td class="title">归属/负责区域:</td>
							<td>
								<x:combobox id="user_area_edit_id" name="user.areaId"  value="${user.areaId}" valueField="id" textField="areaName" list="userAreaList" pleaseSelect="true" onchange="loadDeptEditList" required="" cssStyle="width:142px;"/>
							</td>
						</tr>
						<tr id="user_dept_tr_edit">
							<td class="title">归属/负责营业部:</td>
							<td>
								<x:combobox id="user_dept_edit_id" name="user.deptId"  value="${user.deptId}" valueField="id" textField="deptName" list="userDeptList" pleaseSelect="true" onchange="loadTeamEditList" cssStyle="width:142px;"/>
							</td>
						</tr>
						<tr id="user_team_tr_edit">
							<td class="title">归属/负责团队:</td>
							<td>
								<x:combobox id="user_team_edit_id" name="user.teamId"  value="${user.teamId}" valueField="id" textField="teamName" list="userTeamList" pleaseSelect="true" cssStyle="width:142px;"/>
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
		if(posCode=="03"){
			$('#user_team_tr_edit').hide();
		}else if(posCode=="04"){
			$('#user_team_tr_edit').hide();
			$('#user_dept_tr_edit').hide();
		}else{
			$('#user_dept_tr_edit').show();
			$('#user_team_tr_edit').show();
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
		var areaId = $("#user_area_edit_id").xcombobox("getValue");
		$("#user_dept_edit_id").xcombobox("reload",{'url':'<s:url value="/orgDept/orgDeptManage_getCombobox.jhtml"/>?searchBean.ownedArea='+areaId+'&searchBean.status=1'});
	}
	
	function loadTeamEditList(){
		var teamId = $("#user_dept_edit_id").xcombobox("getValue");
		$("#user_team_edit_id").xcombobox("reload",{'url':'<s:url value="/orgTeam/orgTeamManage_getCombobox.jhtml"/>?searchBean.ownedDept='+teamId+'&searchBean.status=1'});
	}
	
	function posCodeEditChange(){
		var posCode = $('#pos_code_edit').xcombobox("getValue");
		if(posCode=="03"){
			$('#user_team_tr_edit').hide();
			$('#user_team_edit_id').xcombobox("setValue",'');
			$('#user_dept_tr_edit').show();
			loadDeptEditList();
		}else if(posCode=="04"){
			$('#user_team_tr_edit').hide();
			$('#user_team_edit_id').xcombobox("setValue",'');
			$('#user_dept_tr_edit').hide();
			$('#user_dept_edit_id').xcombobox("setValue",'');
		}else{
			$('#user_dept_tr_edit').show();
			$('#user_team_tr_edit').show();
			loadDeptEditList();
			loadTeamEditList();
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>