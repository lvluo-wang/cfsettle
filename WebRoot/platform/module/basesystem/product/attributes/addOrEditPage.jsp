<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
		<form id="attr_addForm" class="busi_form">
			<table>
				<colgroup>
					<col width="40%"/>
					<col width="60%"/>
				</colgroup>
				<tbody>
				<tr>
					<td class="title"><s:text name="attribute.key"/>:</td>
					<td ><input name="pa.key" value="${pa.key}" class="easyui-validatebox" required="true" maxLength="50" validType="char_num_ul"></input><font color=red>*</font></td>
				</tr>
				<tr>
					<td class="title"><s:text name="name"/>:</td>
					<td><input name="pa.name" value="${pa.name}" class="easyui-validatebox" required="true" maxLength="50" validType="chs_char_num_ul"></input><font color=red>*</font></td>
				</tr>
				<tr>
					<td class="title" ><s:text name="codemeta.key"/>:</td>
					<td >
					<input id="add_code_codeKey" value="${cm.name}" class="inputSel" onClick="chooseCodeMeta(chooseCodeMetaCallBack)" />
					<s:hidden name="pa.codeMetaKey" id="addCodeMetaKey" />
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="value"/>:</td>
					<td>
						<x:combobox id="pa_value_combo" name="pa.value" valueField="codeNo" textField="codeName" list="codeList"/>
						<span id="pa_value_inp">
						<input class="easyui-validatebox" required="true" name="pa.value" value="${pa.value}" style="width:100px;" maxLength="30" validType="char_num_ul"/>
						</span>
						<span id="pa_value_div">
					<font color=red>*</font>
				</td>
				</tr>
				</tbody>
			</table>
			<input type="hidden" name="pa.id" value="${pa.id}"/>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button effect="round" iconCls="icon-save" text="save" click="doSave"/>
		<x:button effect="round" iconCls="icon-cancel" text="cancel" click="doCancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script>
	<s:if test="pa.codeMetaKey != null">
		$("#pa_value_inp").empty();
	</s:if>
	<s:else>
		$("#pa_value_combo").xcombobox("disable").hide().xcombobox("disValidate");
	</s:else>
	function doSave(){
		if($("#attr_addForm").form("validate")){
			var url = "<s:url value='/product/attr_addOrEditAttr.jhtml'/>";
			var parm = formToObject("attr_addForm");
			doPost(url,parm,function(result){
				if(!printError(result)){
					doCancel();
					dataTable.refresh();
				}
			});
		  }
	}
	function doCancel(){
		closeWindow("attr_add_edit");
	}
	function chooseCodeMetaCallBack(row){
		var n = '', v = '';
		if(row&&row.key&&row.name){
			n = row.name;
			v = row.key;
		}
		$("#add_code_codeKey").val(n);
		$("#addCodeMetaKey").val(v);
		var codeKey = $("#addCodeMetaKey").val();
		if(codeKey){
			$("#pa_value_inp").empty();
			$("#pa_value_combo").xcombobox("enable").show().xcombobox("validate");
			$("#pa_value_combo").xcombobox("reload",{url:'<s:url value="/dictionary/dictionary_getCodeListByKey.jhtml"/>?code.codeKey='+codeKey});
		}else{
			$("#pa_value_combo").xcombobox("disable").hide().xcombobox("disValidate");
			$("#pa_value_inp").empty().html('<input class="easyui-validatebox" required="true" name="pa.value" style="width:100px;" maxLength="30" validType="char_num_ul"/>');
			if($.parser){
				$.parser.parse($("#pa_value_inp"));
			}
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>