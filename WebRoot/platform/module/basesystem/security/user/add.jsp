<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
 	<tiles:putAttribute name="form">
				<form method="post" id="addForm" class="busi_form" >	
					<input type="hidden" id="logonInfo_branchId" value="${session.UserLogonInfo.branchId}"/>	
					<s:hidden name="user.userId" />
					<s:hidden name="user.brchId" id="brchValue"/>

						<table>
						 <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
						 <tbody>
						 <tr>
							<td class="title">岗位:</td>
							<td>
								<x:combobox name="user.posCode" id="pos_code_add" valueField="codeNo" textField="codeName" list="posCodeList" pleaseSelect="false" cssStyle="width:142px;" onchange="posCodeChange"/>
							</td>
						</tr>
						<tr>
							<td class="title">手机号码:</td>
							<!-- validType="serialNumbertextLength[3,18]" invalidMessage="请输入3至18位字符" -->
							<td><input name="user.userNo" value="${user.userNo}" class="easyui-validatebox" required="true" maxlength="20"/><font color="red">*</font></td>
						</tr>
						
						<tr>
							<td class="title"><s:text name="user"/><s:text name="name"/>:</td>
							<td><input name="user.userName" value="${user.userName}" class="easyui-validatebox" required="true" maxlength="20" validType="userName"/><font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="user"/><s:text name="email"/>:</td>
							<td><input name="user.email" value="${user.email}" class="easyui-validatebox" required="true" maxlength="50" validType="email"/><font color="red">*</font></td>
						</tr>
						
						<tr>
							<td class="title"><s:text name="user"/><s:text name="type"/>:</td>
							<td>
								<x:combobox id="user_userType" name="user.userType"  value="${user.userType}" valueField="codeNo" textField="codeName" list="userTypeList" pleaseSelect="false" cssStyle="width:142px;"/>
							</td>
						</tr>
						<%
							if( ! UserManager.isImplementation()){
						%>
								<!--
								<tr id="brchDiv">
									<td class="title"><s:text name="belong_branch"/>:</td>
									<td><input class="formPannel_input" id="logonInfo_branchName" 
										<s:if test="user != null ">
											value="${branch.brchName}"  
										</s:if>
										<s:else>
											value="${session.UserLogonInfo.branchName}"  
										</s:else>
									disabled="true"/></td>						
								</tr>
								-->
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
									<x:combobox id="user_area_id" name="user.areaId"  value="${user.areaId}" valueField="id" textField="areaName" list="userAreaList" pleaseSelect="false" onchange="loadDeptList" required="true" cssStyle="width:142px;"/>
								</td>
							</tr>
							<tr id="user_dept_tr">
								<td class="title">归属/负责营业部:</td>
								<td>
									<x:combobox id="user_dept_id" name="user.deptId"  value="${user.deptId}" valueField="id" textField="deptName" list="userDeptList" pleaseSelect="false" onchange="loadTeamList" cssStyle="width:142px;"/>
								</td>
							</tr>
							<tr id="user_team_tr">
								<td class="title">归属/负责团队:</td>
								<td>
									<x:combobox id="user_team_id" name="user.teamId"  value="${user.teamId}" valueField="id" textField="teamName" list="userTeamList" pleaseSelect="false" cssStyle="width:142px;"/>
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
	function changeUserType(isInit){		
		var userType = $("#user_userType").xcombobox("getValue");
		if (userType=='6'){
			$("#brchDiv").show();
			$("#chooseBrchDiv").hide();
			if(!isInit){
				$("#brchValue").val($("#logonInfo_branchId").val());
				$("#brchName").val($("#brchValue").val());				
				$('#logonInfo_branchName').val('${session.UserLogonInfo.branchName}');
			}
		}else{
			$("#brchDiv").hide();
			$("#chooseBrchDiv").show();
			if( ! isInit ){
				$("#brchValue").val('');
				$("#brchName").val('');
			}
		}
		
	}	
	
	//changeUserType(true);
		
	
	//function chooseBranch() {
	//	var url='<s:url value="/security/user_toChooseBrch.jhtml"/>';
	//	requestAtWindow(url,"sel",'<s:text name="choose"/><s:text name="branch"/>');
	//}
	function chooseBranchCallback(row){
		 if(row && row.brchName && row.brchId){
				$("#brchName").val(row.brchName);
				$("#brchValue").val(row.brchId);
			}
	}
	
	function doSave(){		
		if($("#addForm").form("validate")){
			var url = "<s:url value='/security/user_saveOrUpUser.jhtml'/>";
			var parm=formToObject("addForm");
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
	function doCancel(){
		$("#user_add_edit").window("close");
	}
	
	function loadDeptList(){
		var areaId = $("#user_area_id").xcombobox("getValue");
		$("#user_dept_id").xcombobox("reload",{'url':'<s:url value="/orgDept/orgDeptManage_getCombobox.jhtml"/>?searchBean.ownedArea='+areaId+'&searchBean.status=1'});
	}
	
	function loadTeamList(){
		var teamId = $("#user_dept_id").xcombobox("getValue");
		$("#user_team_id").xcombobox("reload",{'url':'<s:url value="/orgTeam/orgTeamManage_getCombobox.jhtml"/>?searchBean.ownedDept='+teamId+'&searchBean.status=1'});
	}
	
	function posCodeChange(){
		var posCode = $('#pos_code_add').xcombobox("getValue");
		if(posCode=="03"){
			$('#user_team_tr_add').hide();
			$('#user_dept_tr_add').show();
		}else if(posCode=="04"){
			$('#user_dept_tr_add').hide();
		}else{
			$('#user_dept_tr_add').show();
			$('#user_team_tr_add').show();
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>