<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
			<s:hidden name="task.id"></s:hidden>
			<s:hidden name="task.funcId"></s:hidden>
			<s:hidden name="task.procId"></s:hidden>
			<s:hidden name="task.status"></s:hidden>   
                  <table>
				 <colgroup>
					<col width="40%"/>
		            <col width="60%"/>
	            </colgroup>	
	            <tbody>
                   <tr>
						<td class="title"><s:text name="task"/>:</td>
						<td><input name="task.taskName" value="${task.taskName}" readonly="readonly"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="task"/><s:text name="name"/>:</td>
						<td><input name="task.taskCnName" value="${task.taskCnName}" id="role.roleName" class="easyui-validatebox" required="true" maxLength="25"  validType="loginName"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="task"/><s:text name="type"/>:</td>
						<td>
						<x:combobox name="task.taskType" required="true" list="taskTypeList" textField="codeName" valueField="codeNo"/>
						<font color=red>*</font>
						</td>
					</tr>	
					<tr>
						<td class="title">排序:</td>
						<td><input name="task.sortNo" value="${task.sortNo}" class="easyui-validatebox" maxLength="100" ></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title"><s:text name="task"/>url:</td>
						<td><input name="task.url" value="${task.url}" id="role_url" class="easyui-validatebox" maxLength="100" ></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title">备注:</td>
						<td><input name="task.remark" value="${task.remark}" ></input></td>
					</tr>
                 </tbody> 
            </table>
		</form>	 	
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" effect="round"  text="cancel"/>		
	</tiles:putAttribute>
	<tiles:putAttribute name="end">		
	<script type="text/javascript">
		function doSave(){
		  if($("#busi_form").form("validate")){		
			
			$.messager.confirm(global.alert,global.update_confirm_info,function(r){
				if(r){
					var url = "<s:url value='/bpm/publish_taskSave.jhtml'/>";					
					var parm=formToObject("busi_form");
					doPost(url,parm,function(){
						dataTable1.refresh();
						$('#add_edit').window('close');
					});
				}
				
			});
		  }
		}
		function doCancel(){
			$("#add_edit").window("close");
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>