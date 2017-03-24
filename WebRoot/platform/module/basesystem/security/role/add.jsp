<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
				<form method="post" id="editForm" class="busi_form" >
						<table>
						 <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
				 		<tbody>
						<tr>
							<td class="title"><s:text name="name"/>:</td>
							<td><input name="role.roleName" id="role.roleName" class="easyui-validatebox" required="true" maxLength="20"  validType="roleName" ></input><font color="red">*</font></td>
						</tr>							
						<tr>
							<td class="title"><s:text name="role"/><s:text name="type"/>:</td>
							<td>
								<x:combobox name="role.roleType" valueField="codeNo" textField="codeName" list="roleTypeList" pleaseSelect="false"/>
							</td>
						</tr>
						<tr>
							<td class="title"><s:text name="remark"/>:</td>
							<td><input name="role.remark" maxLength="50"></input></td>
						</tr>
						</tbody>
						</table>
				</form> 
	</tiles:putAttribute>
			
	<tiles:putAttribute name="button">
		<x:button click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			function doSave(){
				if($("#addForm").form("validate")){
					var url = "<s:url value='/security/role_addRole.jhtml'/>";
					doPost(url,formToObject("editForm"),function(result){
						if(result){
							var obj=str2obj(result);
							if(obj.error){
								error(obj.error);
							}
							return;
						}
						roleMainDG.refresh();
						$('#role_add_edit').window('close');
					});
				}
			}
			
			function doCancel(){
				$("#role_add_edit").window("close");
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>