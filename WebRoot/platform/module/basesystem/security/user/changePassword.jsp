<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="head">
	<link rel="stylesheet" type="text/css" href="<s:url value='/platform/themes/simple/css/funcmain.css'/>" />
	</tiles:putAttribute>
	<tiles:putAttribute name="form">
		<form method="post" class="busi_form" id="changePwdForm" >
					<s:hidden name="passwordChg.sysUserID"/>
						<table>
							<tr>
								<td class="title" ><s:text name="user.number" />:</td>
								<td ><input type="text" id="passwordChg_userNo" name="passwordChg.userNo" maxlength="20"  class="easyui-validatebox"  required="true" validType="userNo" value="${passwordChg.userNo }" <s:if test="session.UserLogonInfo == null" >onblur="listMi2();"</s:if> <s:if test="session.UserLogonInfo != null">readonly="readonly"</s:if>  /><font color="red">*</font></td>
							</tr>
							<tr>
								<td class="title" ><s:text name="current_password"/>:</td>
								<td><input  type="password" name="passwordChg.oldPassword" maxlength="20"  class="easyui-validatebox"  required="true" validType="safepass" /><font color="red">*</font></td>
							</tr>
							
							<tr>
								<td class="title" ><s:text name="new_password"/>:</td>
								<td><input type="password" name="passwordChg.newPassword" maxlength="20"  id="newPassword" class="easyui-validatebox" required="true" validType="safepass" /><font color="red">*</font></td>
							</tr>
							<tr>
								<td class="title" ><s:text name="confirm_new_password"/>:</td>
								<td><input type="password" name="passwordChg.confirmPassword" maxlength="20"  class="easyui-validatebox" required="true" validType="equalTo['newPassword']" /><font color="red">*</font></td>
							</tr>
							<s:if test="session.UserLogonInfo == null" >
								<tr>
									<td class="title" ><s:text name="branch.mi"/>:</td>
									<td id="passwordChg_miNo_td">
										<input type="text" id="passwordChg_miNo" name="passwordChg.miNo" panelHeight="50" panelWidth="130" class="easyui-combobox" >
									</td>
								</tr>
							</s:if>
						</table>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	
<script type="text/javascript">
	function doSave(){
		if($("#changePwdForm").form("validate")){
			$.messager.confirm(global.alert,global.update_confirm_info,function(r){
				if(r){
					var url = "<s:url value='/security/user_changePassword.jhtml'/>";
					windowFormSubmitAndClose(url,"changePwdForm","change_pwd");
				}
				
			});

		}
	}
	var currentUserNo2 = "";
	function listMi2(){
		var userNo=$("#passwordChg_userNo").val();
		var url='<s:url value="/security/member_queryByUserNo.jhtml"/>';
		if($("#passwordChg_userNo").validatebox('isValid')){
			if(userNo==currentUserNo2){
				return;
			}else{
				currentUserNo2=userNo;
			}
			$("#passwordChg_miNo").combobox('clear');
			doPostNoMask(url,{'userNo':userNo},function(result){
				if(result){
					var obj=str2obj(result);
					if(obj.error){
						error(obj.error);
					}else{
						if(obj.list){
							$("#passwordChg_miNo").combobox({
							data:obj.list,
							valueField:'miNo',
							textField:'miName'
						});
						if(obj.list.length==1){
							$("#passwordChg_miNo").combobox('select',obj.list[0].miNo);
						}
						}
						
					}
				}
			});
		}
	}
	
	function doCancel(){
		$("#change_pwd").window("close");
	}
</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>
