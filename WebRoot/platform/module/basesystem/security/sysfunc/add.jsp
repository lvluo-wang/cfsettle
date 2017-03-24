<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
	<tiles:putAttribute name="form">
				<form  id="addForm" class="busi_form">
					<s:hidden name="parentFunc.funcId"/>
					<table>
						<colgroup>
						<col width="45%"/>
						<col width="55%"/>
						</colgroup>
						<tbody>
						<tr>
							<td  class="title"><s:text name="sysfunc.parentFuncName"/>:</td>
							<td><input value="${parentFunc.funcName}" name="parentFunc.funcName" disabled="true"/></td>
						</tr>
						<tr>
							<td class="title" ><s:text name="sysfunc.funcName"/>:</td>
							<td><input id="sysfunc_funcName" class="easyui-validatebox"  required="true" validType="funcName" maxLength="50" name="sysfunc.funcName"/><font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="sysfunc.funcNameKey"/>:</td>
							<td><input  id="sysfunc_funcNameKey" class="easyui-validatebox"  required="true" validType="funcKey" maxlength="50" name="sysfunc.funcNameKey"/><font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="sysfunc.funcType"/>:</td>
							<td>
								<x:combobox name="sysfunc.funcType" id="funcTypeSel" valueField="codeNo" textField="codeName" list="funcType" onchange="changeFuncType" pleaseSelect="false"/>
							</td>
						</tr>
						<tr>
							<td class="title"><s:text name="sysfunc.useType"/>:</td>
							<td>
								<x:combobox name="sysfunc.useType" valueField="codeNo" textField="codeName" list="useType" pleaseSelect="false"/>
							</td>
						</tr>
						<tr>
							<td  class="title">URL:</td>
							<td><input id="sysfunc_url" class="easyui-validatebox" value="/test.jsp" name="sysfunc.url" maxLength="200"/></td>
						</tr>
						
						<tr>
							<td  class="title"><s:text name="sysfunc.remark"/>:</td>
							<td><textarea  id="sysfunc_remark" class="easyui-validatebox" name="sysfunc.remark" validType="length[0,250]"/></td>
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
	function changeFuncType(){
		var funcType = $("#funcTypeSel").val();
		if(funcType=='0'){
			$('#sysfunc_url').validatebox({
				required:false
				});
		}
		if(funcType=='1' || funcType=='2'){
			$('#sysfunc_url').validatebox({
				required:true
				});
		}
	}
	changeFuncType();
	function doSave(){
		if($("#addForm").form("validate")){
			var url = "<s:url value='/security/sysfunc_save.jhtml'/>";
			var parm=$("#addForm").serialize();
			doPost(url,parm,callBack);
			parm=null;
		}
			
		
	}
	function callBack(result){
		if(result){
			if(result.indexOf('{')==0){
				var obj=eval('('+result+')');
				if(obj.error){
					error(obj.error);
					obj=null;
					return;
				}
			}
		}
		var node=$("#funcTree").tree('getSelected');
		$("#funcTree").tree('reload',node.target);
		$("#sysfunc_add_edit").window('close');
		node=null;
	}
	function doCancel(){
		$("#sysfunc_add_edit").window("close");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>