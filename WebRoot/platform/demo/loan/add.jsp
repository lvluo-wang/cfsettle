<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form class="busi_form" id="addForm">
			<input type="hidden" id="ids" name="ids"/>
			<input type="hidden" id="status" />
			<input type="hidden" name="func.version" id="add_version"/>
				<table>
					<colgroup>
						<col width="45%"/>
						<col width="55%"/>
					</colgroup>
					<tbody>
						<tr>
							<td class="title">贷款编号:</td>
							<td><input name="loanInfo.loanNo" id="add_loanNo" class="easyui-validatebox" required="true" ></input></td>
						</tr>
						<tr>
							<td class="title">贷款人名称:</td>
							<td><input name="loanInfo.userName" id="add_userName" class="easyui-validatebox" required="true" ></input></td>
						</tr>
						<tr>
							<td class="title">贷款金额:</td>
							<td><input name="loanInfo.money" id="add_money" class="easyui-validatebox" required="true" ></input></td>
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
					var url = "<s:url value='/demo/loan_addSave.do'/>";
					doPost(url,formToObject("addForm"),function(result){
						if(!printError(result)){
							$('#add_edit').window('close');
						}
						dataGrid.refresh();
					});
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>