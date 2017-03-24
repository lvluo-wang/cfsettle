<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form id="attr_editForm" class="busi_form">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
					<tr>
						<td class="title" ><s:text name="name"/></td>
						<td>${pa.name}</td>
					</tr>
					<tr>
						<td class="title"><s:text name="value"/>:</td>
						<td >
							<span>
						<s:if test="codeList != null">
							<x:combobox name="mpa.attributeValue" list="codeList" valueField="codeNo" textField="codeName" required="true"/>
						</s:if>
						<s:else>
							<input name="mpa.attributeValue" value="${mpa.attributeValue}" class="easyui-validatebox" required="true" maxLength="30"></input>
						</s:else>
							</span>
						<font color=red>*</font>
					</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="mpa.id" value="${mpa.id}"/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button effect="round" iconCls="icon-save" text="save" click="doAeSave"/>
		<x:button effect="round" iconCls="icon-cancel" text="cancel" click="doAeCancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	function doAeSave(){
		if($("#attr_editForm").form("validate")){
			var url = "<s:url value='/product/product_editMPA.jhtml'/>";
			var parm = formToObject("attr_editForm");
			doPost(url,parm,function(result){
				if(!printError(result)){
					doAeCancel();
					attrsTable.refresh();
				}
			});
		  }
	}
	function doAeCancel(){
		closeWindow("attr_edit");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
