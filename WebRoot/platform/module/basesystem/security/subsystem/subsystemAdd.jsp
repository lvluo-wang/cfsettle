<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	 <tiles:putAttribute name="form">
		<form method="post" id="addForm" class="busi_form">
				<table>
				 <colgroup>
                    <col width="35%"/>
                    <col width="65%"/>
                 </colgroup>
				 <tbody>
				<tr>
					<td class="title"><s:text name="name"/>:</td>
					<td><input name="subsystem.subsysName"  class="easyui-validatebox" required="true" maxLength="20"  validType="subsysName" ></input><font color="red">*</font></td>
				</tr>				
				<tr>
					<td class="title" ><s:text name="subsystem.type"/>:</td>
					<td>
						<x:combobox name="subsystem.type" value="${subsystem.type}" valueField="codeNo" textField="codeName" list="typeList" pleaseSelect="false"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="title" ><s:text name="remark"/>:</td>
					<td><textarea class="easyui-validatebox" validType="length[0,100]" name="subsystem.remark" /></td>
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
					var url = "<s:url value='/security/subsystem_save.jhtml'/>";
					doPost(url,formToObject("addForm"),function(result){
						if(result){
							var obj=str2obj(result);
							if(obj.error){
								error(obj.error);
							}
							return;
						}
						subsysMainDG.refresh();
						$('#subsystem_add_edit').window('close');
					});
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>