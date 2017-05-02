<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
 	<tiles:putAttribute name="form">
		<form method="post" id="addForm" class="busi_form" >	
			<table>
			 <colgroup>
                   <col width="35%"/>
                   <col width="65%"/>
                </colgroup>
			 <tbody>
				 <tr>
					<td class="title">岗位:</td>
					<td>
						<x:combobox name="user.posCode" id="pos_code_add" valueField="codeNo" textField="codeName" list="posCodeList" pleaseSelect="false" cssStyle="width:142px;"/>
					</td>
				</tr>
				<tr>
					<td class="title">手机号码:</td>
					<!-- validType="serialNumbertextLength[3,18]" invalidMessage="请输入3至18位字符" -->
					<td><input name="user.userNo" value="${user.userNo}" class="easyui-validatebox" required="true" maxlength="20" validType="mobile"/><font color="red">*</font></td>
				</tr>
				
				<tr>
					<td class="title"><s:text name="user"/><s:text name="name"/>:</td>
					<td><input name="user.userName" value="${user.userName}" class="easyui-validatebox" required="true" maxlength="20" validType="userName"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td class="title"><s:text name="user"/><s:text name="email"/>:</td>
					<td>
						<input name="user.email" value="${user.email}" class="easyui-validatebox" maxlength="50" validType="email"/><font color="red">*</font>
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
		var posCode = $('#pos_code_add').xcombobox("getValue");
		var areaId = $("#user_area_id").xcombobox("getValue");
		$("#user_dept_id").xcombobox("reload",{'url':'<s:url value="/orgDept/orgDeptManage_getCombobox.jhtml"/>?searchBean.ownedArea='+areaId+'&searchBean.status=1'+'&searchBean.posCode='+posCode});
	}
	
	function loadTeamList(){
		var posCode = $('#pos_code_add').xcombobox("getValue");
		var teamId = $("#user_dept_id").xcombobox("getValue");
		$("#user_team_id").xcombobox("reload",{'url':'<s:url value="/orgTeam/orgTeamManage_getCombobox.jhtml"/>?searchBean.ownedDept='+teamId+'&searchBean.status=1'+'&searchBean.posCode='+posCode});
	}
	
	function loadAreaList(){
		var posCode = $('#pos_code_add').xcombobox("getValue");
		$("#user_area_id").xcombobox("reload",{'url':'<s:url value="/orgArea/orgAreaManage_getCombobox.jhtml"/>?searchBean.status=1'+'&searchBean.posCode='+posCode});
	}
	
	function posCodeChange(){
		var posCode = $('#pos_code_add').xcombobox("getValue");
		if(posCode=="01"){
			$('#user_team_tr').show();
		}else{
			$('#user_team_tr').hide();
			$('#user_team_id').xcombobox('setValue','');
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>