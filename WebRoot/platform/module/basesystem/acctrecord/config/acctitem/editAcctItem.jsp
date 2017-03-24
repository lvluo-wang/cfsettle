<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form  id="busi_form" class="busi_form">
					<table>
						
						<tbody>
							<tr>
								<td class="title"><label for="<s:text name="AcctPoint.pointName"/>"><s:text name="AcctPoint.pointName"/>:</label></td>
								<td>
									<input id="acctItem_pointName" class="inputSel easyui-validatebox" readonly="true" value="${acctItem.pointId_prodNo_prodName}" onclick="selectAcctPoint('acctItem_pointId','acctItem_pointName','acctPointSelect')"/>
									<input type=hidden id="acctItem_pointId" name="acctItem.pointId" value="${acctItem.pointId}">
									<img src='<s:url value="/platform/static/images/icon_clear.PNG"/>' style="cursor:hand" onclick="clearComponentValue('acctItem_pointName:acctItem_pointId')">
								</td>
								
								<td class="title"><label for="<s:text name="AcctItem.belongType"/>"><s:text name="AcctItem.belongType"/>:</label></td>
								<td>
									<x:combobox id="acctItem_belongType" required="true"  name="acctItem.belongType" textField="codeName" valueField="codeNo" list="belongTypeList"/>
									<font color=red> *</font>
								</td>
								
							</tr>
							<tr>
								<td class="title"><label for="<s:text name="AcctItem.itemNo"/>"><s:text name="AcctItem.itemNo"/>:</label></td>
								<td>
									<input id="acctItem_itemNo" name="acctItem.itemNo"  maxlength="50" class="easyui-validatebox" required="true" value="${acctItem.itemNo}"/><font color=red> *</font>
								</td>
								<td class="title"><label for="<s:text name="AcctItem.itemName"/>"><s:text name="AcctItem.itemName"/>:</label></td>
								<td>
									<input id="acctItem_itemName" name="acctItem.itemName"  maxlength="25" class="easyui-validatebox" required="true" value="${acctItem.itemName}"/><font color=red> *</font>
								</td>
							</tr>
							
							<tr>
								<td class="title"><label for="<s:text name="AcctItem.valueType"/>"><s:text name="AcctItem.valueType"/>:</label></td>
								<td>
									<x:combobox id="acctItem_valueType" required="true"  textField="codeName" valueField="codeNo" name="acctItem.valueType" list="valueTypeList"/>
									<font color=red> *</font>
								</td>
								<td  class="title"><label for="<s:text name="AcctItem.express"/>"><s:text name="AcctItem.express"/>:</label></td>
								<td>
									<input id="acctItem_express" name="acctItem.express"  maxlength="100" class="easyui-validatebox" required="true" value="${acctItem.express}"/><font color=red> *</font>
								</td>
							</tr>
						</tbody>
					</table>
					<div style="font-size:12; line-height:0.6">
						<p align="left">记账项表达式说明:</p>
						<p align="left"><font color="blue">&nbsp;&nbsp;表达式，以‘$:’开头，如$:entity.firstName；下拉列表，用{key1:value1,key2:value2}表示，如:{1:是,0:否}；
						</font>
						</p>
						<p align="left"><font color="blue">&nbsp;&nbsp;固定值，直接填写；待定的值，填写null。</font></p>
					</div>
					<div>
						<input type="hidden" id="ids" name="acctItem.id" value="${acctItem.id}"/>
						<input type="hidden" id="status" />
						<input type="hidden" name="func.version" id="add_version"/>
					</div>
				</form> 
	</tiles:putAttribute>
	
	<tiles:putAttribute name="button">
		<x:button icon="icon-save" click="doSave" effect="round" text="save"/>
		<x:button icon="icon-cancel" click="doCancel" effect="round" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">	
			function doSave(){
				if($("#editForm").form("validate")){
					
					if($('#acctItem_belongType').val()==2){
						if($('#acctItem_pointName').val()!=""){
							$.messager.alert("提示","记账点名称必须为空!");
							return;
						}
						if($('#acctItem_miNo').val()==""){
							$.messager.alert("提示","接点编号不能为空!");
							return;
						}
					}
					if($('#acctItem_belongType').val()==3){
						if($('#acctItem_pointName').val()==""){
							$.messager.alert("提示","记账点名称不能为空!");
							return;
						}
						if($('#acctItem_miNo').val()==""){
							$.messager.alert("提示","接点编号不能为空!");
							return;
						}
					}
					var url = "<s:url value='/acctConfig/acctItemAction_modifyAcctItem.jhtml'/>";
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
			
			function doCancel(){
				$('#add_edit').window('close');
			}
			var str = $("#acctItem_pointName").val();
			$("#acctItem_pointName").val(str+code.getValue('B023','${acctItem.pointId_eventNo}'));
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>