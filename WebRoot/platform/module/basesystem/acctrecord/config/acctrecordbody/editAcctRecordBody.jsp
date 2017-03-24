<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form  id="busi_form" class="busi_form">
			<input type="hidden" id="acctRecordBody_id" name="acctRecordBody.id" value="${acctRecordBody.id}"/>
			<input type="hidden" id="acctRecordBody_serialNo" name="acctRecordBody.serialNo" value="${acctRecordBody.serialNo}"/>
			<input type="hidden" id="status" />
			<input type="hidden" name="func.version" id="add_version"/>
			<table>
						<tbody>
							<tr>
								<td class="title"><label for="<s:text name="AcctRecordBody.groupNo"/>"><s:text name="AcctRecordBody.groupNo"/>:</label></td>
								<td>
									<input id="acctRecordBody_groupNo"   maxlength="50" name="acctRecordBody.groupNo" value="${acctRecordBody.groupNo}" class="easyui-validatebox" required="true" /><font color=red> *</font>
								</td>							
							</tr>
							
							<tr>
								
								<td class="title"><label for="<s:text name="AcctRecordBody.valueType"/>"><s:text name="AcctRecordBody.valueType"/>:</label></td>
								<td>
									<x:combobox id="acctRecordBody_valueType" list="valueTypeList" required="true" name="acctRecordBody.valueType" onchange="changeValueType(this);"/>
									<font color=red> *</font>
								</td>
							</tr>
							<tr>
								<td class="title"><label for="<s:text name="name"/>"><s:text name="name"/>:</label></td>
								<td>
									<input id="acctRecordBody_name" name="acctRecordBody.name"  maxlength="25" value="${acctRecordBody.name}" class="easyui-validatebox" required="true"/><font color=red> *</font>
								</td>
							</tr>
						</tbody>
					</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="minoInfoSelect" class="easyui-window" closed="true" modal="true"  style="width:800px;height:400px;padding:5px;background: #fafafa;">
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">	
		$(function(){//初始化
			//changeValueType(document.getElementById('acctRecordBody_valueType'));
			
		});

			function doSave(){
				if($("#busi_form").form("validate")){
					var url = "<s:url value='/acctConfig/acctRecordBodyAction_modifyAcctRecordBody.jhtml'/>";
					//formSubmitForDatagrid(url,"editForm","dataTable");
					//$('#add_edit').window('close');
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
				var text = t.options[t.selectedIndex].text;
				$("#acctRecordBody_name").attr('value',text);
			}
			
			function doCancel(){
				$('#add_edit').window('close');
			}
			var str = $("#acctItem_pointName").val();
			$("#acctItem_pointName").val(str+code.getValue('B023','${acctItem.pointId_eventNo}'));
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>