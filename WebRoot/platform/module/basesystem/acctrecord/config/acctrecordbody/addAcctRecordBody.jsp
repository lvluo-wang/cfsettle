<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">	
	<tiles:putAttribute name="form">
		<form class="busi_form" id="busi_form">
				<input type="hidden" id="ids" name="ids"/>
				<input type="hidden" id="status" />
				<input type="hidden" name="func.version" id="add_version"/>
			<table>
				<tbody>
					<tr>
						<td class="title"><s:text name="AcctRecordBody.groupNo"/>:</td>
						<td>
							<input id="acctRecordBody_groupNo" name="acctRecordBody.groupNo"  maxlength="50" class="easyui-validatebox" required="true" /><font color=red> *</font>
						</td>
					</tr>
					<tr>
						
						<td class="title"><s:text name="AcctRecordBody.valueType"/>:</td>
						<td>
							<x:combobox id="acctRecordBody_valueType" required="true" name="acctRecordBody.valueType" list="valueTypeList" onchange="changeValueType(this)"/>
							<font color=red> *</font>
						</td>
					</tr>
					<tr>
						<td class="title"><s:text name="name"/>:</td>
						<td>
							<input id="acctRecordBody_name" name="acctRecordBody.name"  maxlength="25" class="easyui-validatebox" required="true"/><font color=red> *</font>
						</td>
					</tr>
				</tbody>
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
				if($("#busi_form").form("validate")){
					var url = "<s:url value='/acctConfig/acctRecordBodyAction_createAcctRecordBody.jhtml'/>";
					//formSubmitForDatagrid(url,"editForm","dataTable");
					var parm=formToObject("busi_form");
					doPost(url,parm,function(){						
						$('#add_edit').window('close');
						if(dataTable){
							dataTable.refresh();
						}
					});					
					
				}
			}
			
			function changeValueType(t){		
				
				$("#acctRecordBody_name").val($("#acctRecordBody_valueType").xcombobox("getValue"));
			}
			function doCancel(){
				$('#add_edit').window('close');
			}
			
			changeValueType($("#acctRecordBody_valueType").xcombobox("getValue"));
		</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>