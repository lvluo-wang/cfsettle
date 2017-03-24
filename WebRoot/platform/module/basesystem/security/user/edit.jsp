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
					<td class="title"><s:text name="user" /><s:text name="no" />:</td>
					<td><input name="user.userNo" value="${user.userNo}" class="easyui-validatebox" maxlength="30" validType="userNo" required="true" /><font color="red">*</font></td>
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
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
			
	<tiles:putAttribute name="button">
		<x:button id="btn_edit_member" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>s
	    
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function chooseBranch() {
		var url = '<s:url value="/security/user_toChooseBrch.jhtml"/>';
		requestAtWindow(url, "sel","<s:text name="choose"/><s:text name="branch"/>");
	}
	function doSave() {
		if ($("#editForm").form("validate")) {
			$.messager.confirm(
							global.alert,
							global.update_confirm_info,
							function(r) {
								if (r) {
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
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>