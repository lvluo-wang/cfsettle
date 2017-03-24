<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
<tiles:putAttribute name="form">
				<form method="post" id="editForm" class="busi_form" >
					<s:hidden name="brch.miNo" id="bMiNoValue"/>
					<s:hidden name="brch.brchId"/>
					<s:hidden name="brch.parentTreeCode" id="brchPValue" />
					<s:hidden name="brch.brchLevel"/>
					<s:hidden name="brch.treeCode"/>
					<s:hidden name="brch.version"/>
						<table>
						 <colgroup>
		                    <col width="35%"/>
		                    <col width="65%"/>
		                 </colgroup>
						 <tbody>
						<%
							if( UserManager.isSaasManager() || UserManager.isSaasMaintenance() ){
						%>
						<tr>
							<td class="title"><s:text name="memberInfo.miNo"/>:</td>
							<td>
							<div class="searchBox">
								<input id="bMiNo" value='<s:property value="brch.miNo"/>' disabled="true"/>
								<s:if test="%{!hasSubBranches}">
									<x:button iconCls="icon-search" onClick="chooseMember(chooseMemberCallback)"/>
								</s:if>
							</div>
							</td>
						</tr>
						<%
							}
						%>
						<tr>
							<td class="title"><s:text name="brch.brchNo"/>:</td>
							<td>
							  <input id = "brchNo" class="easyui-validatebox" required="true" value="<s:property value='brch.brchNo'/>" validType="brchNo"  maxLength="20" name="brch.brchNo" />
							  <s:hidden id="codeKey" value="%{@com.upg.ucars.constant.AutoGenCodeConst@BRCH_NO}"/>
							  <a href="#" class="easyui-linkbutton" style="height:22px" plain="true" iconCls="icon-code"  onClick="genCode('codeKey','brchNo',this);"></a>	
							  <font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="brch.name_"/>:</td>
							<td><input class="easyui-validatebox" id="bName" required="true" value="<s:property value='brch.brchName'/>" validType="brchName" maxLength="20" name="brch.brchName"/><font color="red">*</font></td>
						</tr>
						<tr>
							<td class="title"><s:text name="province"/>:</td>
							<td>
								<x:combobox id="brch_province" name="brch.province" onchange="changeCity"  required="true" valueField="id" textField="nameCn"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="title"><s:text name="city"/>:</td>
							<td>
								<x:combobox id="brch_city" name="brch.city"  required="true" valueField="id" textField="nameCn"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="title"><s:text name="address"/>:</td>
							<td>
								<input class="easyui-validatebox" required="true" name="brch.address" value="<s:property value='brch.address'/>" maxLength="50" />
							</td>
						</tr>
						<%
							if(UserManager.isBrchGlobalManager() || UserManager.isBrchLocalManager()){
						%>
							<tr>
								<td class="title">统计属性:</td>
								<td>
									<x:combobox name="brch.ledgerAttr" list="ledgerAttrs"  valueField="codeNo" textField="codeName"/>
								</td>
							</tr>
							<tr>
								<td class="title"><s:text name="brch.parentName"/>:</td>
								<td>
									<div class="searchBox">
										<input class="inputSel" id="brchPName" disabled="disabled" value='<s:property value="brch.parentBrchName"/>' name="brch.parentBrchName"/>
										<x:button click="selectSuper" text="" icon="icon-search"/>
									</div>
								</td>
							</tr>
						<%
							}
						%>
						</tbody>
					</table>
				</form> 
	</tiles:putAttribute>
	<tiles:putAttribute name="button">
		<x:button id="btn_add_sysfunc" click="doSave" text="save" effect="round" icon="icon-save"/>
		<x:button click="doCancel" text="cancel" effect="round" icon="icon-cancel"/>
		<div id="choose_superBranch" style="display:none;width:600px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	
<script type="text/javascript">
	$(function(){
		$("#brch_province").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getProvinces.jhtml"/>'});
		changeCity($("#brch_province").xcombobox("getValue"));
	});
	function changeCity(provinceId){
		if(provinceId){
			$("#brch_city").xcombobox("reload",{'url':'<s:url value="/dictionary/dictionary_getAreaListByPid.jhtml"/>?areaPid='+provinceId});
		}
	}


	function chooseMemberCallback(row){
		if(row&&row.miNo&&row.miName){
			$("#bMiNo").val(row.miNo);
			$("#bMiNoValue").val(row.miNo);
		}
	}
	
	function selectSuper() {
		//$("#choose_superBranch").html("");
		chooseTreeBranch(selectParentCallback)
		//chooseSuperBranch('${id}',selectParentCallback);
	}
	
	function doSave(){
	  if($('#bMiNo').val()=='') {
	  	$.messager.alert(global.error,'<s:text name="memberInfo.miNo"/><s:text name="is_not_null"/>','info');
	  	return;
	  }
	  var url = "<s:url value='/security/brch_save.jhtml'/>";
		var parm = formToObject("editForm");
		doPost(url,parm,function(result){
			if(result){
				var o = str2obj(result);
				if(o.error){
					error(o.error);
					return;
				}
			}
			branchListDG.refresh();
			$('#brch_add_edit').window('close');
		});
	}
	
	function doCancel(){
		$("#brch_add_edit").window("close");
	}
	
	function selectParentCallback(row){
		if(row && row.treeCode && row.brchName){
			$("#brchPName").val(row.brchName);
			$("#brchPValue").val(row.treeCode);
		}
	}
</script>
	</tiles:putAttribute>

</tiles:insertDefinition>
