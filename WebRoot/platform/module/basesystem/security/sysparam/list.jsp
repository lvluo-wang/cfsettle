<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<%
	//普通用户、总部管理员、机构管理员
	boolean isShowAuditMenu = UserManager.isBrchNormalUser() || UserManager.isBrchLocalManager() || UserManager.isBrchGlobalManager();
%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
		<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/sysparam-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
		<s:if test='showAudit=="0"'>
			<x:button icon="icon-add" click="doAdd" text="add"/>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-remove" click="doRemove" text="del"/>
		</s:if>
		<s:else>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-audit" click="doCommit" text="commitAudit"/>
			<x:button icon="icon-revoke" click="doRevoke" text="revokeAudit"/>
			<x:button icon="icon-search" click="viewAuditProcess" text="viewAuditProcess"/>
		</s:else>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="queryForm" class="query_form">
				<table><tr>
					<td class="title"><s:text name="param.code"/>:</td>
					<td><input name="sp.paramKey" id="sp_paramKey" maxLength="20"></input></td>
					<td class="title"><s:text name="param.name"/>:</td>
					<td><input name="sp.paramName" id="sp_paramName" maxLength="20" ></input></td>
					<s:if test='showAudit=="1"'>
						<td class="title"><s:text name="status"/>:</td>
						<td>
							<x:combobox id="sp_status" name="sp.status" valueField="codeNo" textField="codeName" list="statusTypeList" />
						</td>
					</s:if>
					<td>
						<x:button icon="icon-search" click="doQuery" text="query"/>
					</td>
				</tr></table>
			</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="sysParamListDG" url="/security/sys_param_query.jhtml" form="queryForm" >
			<x:columns>
				<x:column title="" field="paramId" checkbox="true" width="20" />
				<x:column title="param.code" field="paramKey" width="200" align="left" />
				<s:if test='showAudit=="1"'>
					<x:column title="param.name" field="paramName" width="200" align="left" />
					<x:column title="param_value" field="tempValueDisp" width="80" align="left" formatter="formatStringLen"/>
					<x:column title="user_value" field="paramValueDisp" width="80" align="left" formatter="formatStringLen"/>
					<x:column title="status" field="statusDisp" width="80" align="center" />
					
					<x:column title="modifier" field="modifyUserIdDisp" width="80" align="left" />
					<x:column title="checker" field="reviewUserName" width="80" align="left" />
					<x:column title="memberInfo.miNo" field="miNo" width="100" align="left" />
					
				</s:if>
				<s:else>
					<x:column title="param.name" field="paramName" width="200" align="left" />
					<x:column title="param_value" field="tempValueDisp" width="100" align="left" />
					<x:column title="user_value" field="paramValueDisp" width="100" align="left" />
				</s:else>
				<x:column title="remark" field="remark" width="200" align="left" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="sysparam_add_edit" style="width:500px;display:none;"></div>
	<div id="qbrch" style="width:500px;display:none;"></div>
	<div id="auditProcessView" style="width:500px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
function initPage(){
	doQuery();
};
function doAdd(){
	var url='<s:url value="/security/sys_param_input.jhtml"/>';
	requestAtWindow(url,"sysparam_add_edit","<s:text name='add'/>");		
}
function doEdit(){
	if(isSingleSelected(sysParamListDG)){
		var status = sysParamListDG.getSelectedField("status");
		if( status == "2" ){
			error(global.cannot_edit_checking_sysparam);
			return;
		}
		var selectedId = sysParamListDG.getSelectedField("paramId");
		var url='<s:url value="/security/sys_param_input.jhtml"/>?id='+selectedId;
		requestAtWindow(url,"sysparam_add_edit","<s:text name='edit'/>");
	}
}

function doQuery(){
	sysParamListDG.load();
}

function doRemove(){
	if(isSelected(sysParamListDG)){	
		$.messager.confirm(global.alert,global.del_confirm_info, function(r){
			if(r){
				sysParamListDG.call('<s:url value="/security/sys_param_del.jhtml"/>',{ids:sysParamListDG.getSelectedFields("paramId")});
			}
		});
	}
}
function doSave(){
	  if($("#editForm").form("validate")){
		var url = "<s:url value='/security/sys_param_save.jhtml'/>";
		var parm = formToObject("editForm");
		doPost(url,parm,function(result){
			if(!printError(result)){
				doCancel();
				sysParamListDG.refresh();
			}
		});
	  }
	}
function doCancel(){
	closeWindow("sysparam_add_edit");
}
function chooseCodeMetaCallBack_(row){
	var n = '', v = '';
	if(row&&row.key&&row.name){
		n = row.name;
		v = row.key;
	}
	$("#codeMetaName").val(n);
	$("#add_sp_codeKey").val(v);
	var codeKey = $("#add_sp_codeKey").val();
	if(codeKey){
		$("#sp_temp_value_inp").empty();
		$("#sp_tempValue_combo").xcombobox("enable").show().xcombobox("validate");
		$("#sp_tempValue_combo").xcombobox("reload",{url:'<s:url value="/dictionary/dictionary_getCodeListByKey.jhtml"/>?code.codeKey='+codeKey});
	}else{
		$("#sp_tempValue_combo").xcombobox("disable").hide().xcombobox("disValidate");
		$("#sp_temp_value_inp").empty().html('<input class="easyui-validatebox" required="true" name="sp.tempValue" style="width:100px;" maxLength="30" validType="char_num_ul" />');
		if($.parser){
			$.parser.parse($("#sp_temp_value_inp"));
		}
	}
}
</script>

<s:if test='showAudit=="1"'>
	<script type="text/javascript">
		function checkBeforeCommit(grid,key,val){
			var ret = true;
			if( grid && key ){
				try{
					var rows = grid.getSelectedRows();
					for( var i = 0; i < rows.length; i++ ){
						if( rows[i][key] != val ){
							ret = false;
							break;
						}
					}
				}catch(e){
				}
			}
			return ret;
		}
		
		function doCommit(){
			if(isSelected(sysParamListDG)){		
				$.messager.confirm(global.alert,"<s:text name="commitAuditPrompt"/>", function(r){
					if(r){
						//只有未复核的才能提交
						var check = checkBeforeCommit(sysParamListDG,'status','<%=com.upg.ucars.mapping.basesystem.security.SysParam.STATUS_UNCHECK%>');
						if( ! check ){
							$.messager.alert(global.alert,global.sys_param_doCommit_info,'info');
							return;
						}
						var url = "<s:url value='/security/sys_param_batchCommitAudit.jhtml'/>";
						sysParamListDG.call('<s:url value="/security/sys_param_batchCommitAudit.jhtml"/>',{ids:sysParamListDG.getSelectedFields("paramId")});
					}
				});
				
			}
		}
		function doRevoke(){
			
			if(isSelected(sysParamListDG)){		
				$.messager.confirm(global.alert,"<s:text name="revokeAuditPrompt"/>", function(r){
					if(r){
						//只有复核中的才能撤销
						var check = checkBeforeCommit(sysParamListDG,'status','<%=com.upg.ucars.mapping.basesystem.security.SysParam.STATUS_CHECKING%>');
						if( ! check ){
							$.messager.alert(global.alert,global.sys_param_doRevoke_info,'info');
							return;
						}
						sysParamListDG.call('<s:url value="/security/sys_param_batchRevokeAudit.jhtml"/>',{ids:sysParamListDG.getSelectedFields("paramId")});
					}
				});
				
			}
		}
		function viewAuditProcess(){
			if(isSingleSelected(sysParamListDG)){
				var status = sysParamListDG.getSelectedField("status");
				if( status == '<%=com.upg.ucars.mapping.basesystem.security.SysParam.STATUS_UNCHECK%>' ){
					info(global.cannot_view_uncommit_process);
					return;
				}
				var selectedId=sysParamListDG.getSelectedField("paramId");
				var url = "<s:url value='/security/sys_param_viewAuditProcess.jhtml'/>?id="+selectedId;
				requestAtWindow(url,"auditProcessView",'<s:text name="auditProcess"/>');
			}
		}
		function formatStringLen(val){
			var result = val;
			if (val && val.length > 10) {
				result = val.substring(0,10) + "...";
			}
			return result;
		}
	</script>
</s:if>	
	</tiles:putAttribute>
</tiles:insertDefinition>