<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>

<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="exampleBusiness_addForm">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%">
				</colgroup>
				<tbody>
					<tr>
						<td class="title">客户名称:</td>
						<td><input name="eb.customerName" value="${eb.customerName}" class="easyui-validatebox" required="true" validType="codeNo" maxLength="15"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title">A角色:</td>
						<td><input name="eb.arole" value="${eb.arole}" class="easyui-validatebox" required="true" maxLength="25"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title">B角色:</td>
						<td><input name="eb.brole" value="${eb.brole}" class="easyui-validatebox" required="true" maxLength="25"></input><font color=red>*</font></td>
					</tr>
					<tr>
						<td class="title">融资金额:</td>
						<td><input name="eb.financeAmount" value="${eb.financeAmount}" class="easyui-validatebox" required="true" maxLength="25"></input><font color=red>*</font></td>
					</tr>
					
				</tbody>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button iconCls="icon-save" text="save" click="doSave" effect="round"/>
		<x:button iconCls="icon-cancel" text="cancel" click="doCancel" effect="round"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	function doSave(){
		if($("#exampleBusiness_addForm").form("validate")){
			var url = '<s:url value="/bpm/exampleBusiness_add.jhtml"/>';
			doPost(url,formToObject("exampleBusiness_addForm"),function(result){
				if(!printError(result)){
					closeWindow("examplebusiness_add_edit");
					dataTable.refresh();
				}
			});
		}
	}
	function doCancel(){
		closeWindow("examplebusiness_add_edit");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>