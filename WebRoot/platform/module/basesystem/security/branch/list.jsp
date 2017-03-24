<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/branch-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
			<x:button icon="icon-add" click="doAdd" text="add"/>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-remove" click="doRemove" text="del"/>
			<span class="separator"></span>
			<x:button icon="icon-set" click="doBrchFunc" text="sysfunc"/>
			<x:button iconCls="icon-copy" text="权限复制" click="doBrchFuncCopy" />
			<s:if test="showAudit==\"1\"">
			<x:button id="commitAudit" icon="icon-audit" click="doCommit" text="commitAudit"/>
			<x:button id="revokeAudit" icon="icon-revoke" click="doRevoke" text="revokeAudit"/>
			<x:button id="viewAudit" icon="icon-search" click="viewAuditProcess" text="viewAuditProcess"/>
			</s:if>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td><s:text name="brch.brchNo"/>:</td>
					<td><input name="brch.brchNo" id="main_brch_brchNo" ></input></td>
					<td><s:text name="brch.name_"/>:</td>
					<td><input name="brch.brchName" id="main_brch_brchName" ></input></td>
					<s:if test="isSaas!=\"1\"">
						<td><s:text name="brch.parentName"/>:</td>
						<td><div class="searchBox">
							<input id="main_parent_brchName"  class="inputSel" disabled="disabled"/><x:button click="selectSuperBranch" text="" icon="icon-search"/>
							<s:hidden name="brch.treeCode" id="main_brch_treeCode" />
						    </div>
						</td>
					</s:if>
					<td>
					<x:button icon="icon-search" click="mainDoSearch" text="query"/>
					</td>
				</tr>
			</table>
			</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="branchListDG" url="/security/brch_query.jhtml" form="mainQueryForm" >
			<x:columns>
				<x:column title="" field="brchId" checkbox="true" width="20" />
				<x:column title="brch.brchNo" field="brchNo" width="100" align="left" />
				<x:column title="brch.name_" field="brchName" width="180" align="left" />
				<x:column title="memberInfo.miNo" field="miNo" width="100" align="left" />
				<s:if test="isSaas==\"1\"">
				<x:column title="memberInfo.miName" field="miName" width="120" align="left" />
				</s:if>
				<s:else>
				<x:column title="brch.parentName" field="parentBrchName" width="180" align="left" />
				</s:else>
				<x:column title="funcStatus" field="funcStatus" width="80" align="left" formatter="brchlistFormatter"/>
				<x:column title="address" field="address" width="200" align="left" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="brch_add_edit" style="display:none;width:500px;"></div>
	<div id="brch_func" style="display:none;width:600px;"></div>
	<div id="qbrch" style="display:none;width:500px;"></div>
	<div id="mainSel" style="display:none;width:630px;"></div>
	<div id="auditProcessView"  style="display:none;width:500px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
var keys=['B016'];
var code=new CodeUtil(keys);
code.loadData();
function brchlistFormatter(value,field,row,rowIndex){
	if(field == "funcStatus"){
		return code.getValue("B016",value);
	}
	return "";
}
//proxy$(function(){showAudit();});
function initPage(){
	enterKeyPressInit([{'id':'main_brch_brchNo'},{'id':'main_brch_brchName'},{'id':'main_parent_brchName'}],"mainDoSearch()");
	showAudit();
}
	function showAudit(){
		var show=${showAudit};
		if(show!='1'){
			$("#commitAudit").hide();
			$("#revokeAudit").hide();
			$("#viewAudit").hide();
		}
	}
	function doSearchM(parm){
		dataGrid.queryByParam(parm);
	}
	function doAdd(){
		var url='<s:url value="/security/brch_input.jhtml"/>';
		//$("#brch_add_edit").html("");
		requestAtWindow(url,"brch_add_edit","<s:text name='add'/>");
	}
	function doEdit(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var selectedId = branchListDG.getSelectedIds();
				var url='<s:url value="/security/brch_input.jhtml"/>?id='+selectedId;
				//$("#brch_add_edit").html("");
				requestAtWindow(url,"brch_add_edit","<s:text name='edit'/>");
	       }
	}
	
	function doRemove(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num > 0){
				var selectedId = branchListDG.getSelectedIds();
				$.messager.confirm(global.alert, global.del_confirm_info, function(r){
					if(r){
						var url = '<s:url value="/security/brch_del.jhtml"/>?ids='+selectedId;
                        doPost(url,{},function(result){
                            printError(result);
                            branchListDG.refresh();
                         });
					}
				});
	       }else{
	    	   info(global.notSelectInfo);
			   return;
	       }
	}
	
	function mainDoSearch(){
		branchListDG.load();
	}
	
	function doBrchFunc(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var selectedId = branchListDG.getSelectedIds();
				var treeCode = branchListDG.getSelectedField("treeCode");
				var url = "<s:url value='/security/brch_brchPopedom.jhtml'/>";
				redirectUrl(url,{"brch.brchId":selectedId,"brch.treeCode":treeCode});
	       }
	}
	
	function doSubsys(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var selectedId = branchListDG.getSelectedIds();
				var treeCode = branchListDG.getSelectedField("treeCode");
				var url = "<s:url value='/security/brch_assignSubsys.jhtml'/>";
				redirectUrl(url,{"brch.brchId":selectedId,"brch.treeCode":treeCode});
	       }
	}
	
	function checkBeforeCommit(key,val){
		var ret = true;
		try{
			var rows = branchListDG.getSelectedRows();
			for( var i = 0; i < rows.length; i++ ){
				if( rows[i][key] != val ){
					ret = false;
					break;
				}
			}
		}catch(e){}
		return ret;
	}

	function doCommit(){
       var num = branchListDG.getSelectedRowNum();
       if(num>0){
			$.messager.confirm(global.alert,"<s:text name="commitAuditPrompt"/>", function(r){
				if(r){
					//只有分配待审核的才能提交
					var check = checkBeforeCommit('funcStatus','<%=com.upg.ucars.mapping.basesystem.security.ReBrchFunc.STATUS_ASSIGNNING%>');
					if( ! check ){
						$.messager.alert(global.alert,global.branch_doCommit_info,'info');
						return;
					}
					var selectedIds = branchListDG.getSelectedIds();
					var url = "<s:url value='/security/brch_batchCommitBrchFuncAudit.jhtml'/>";
					doPost(url,{"ids":selectedIds},function(result){
						if(result){
							var obj = str2obj(result);
							if(obj.error){
								error(obj.error);
								return;
							}
						}
						branchListDG.refresh();
					});
				}
			});
       }
	}
	
	function doRevoke(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num > 0){
				$.messager.confirm(global.alert,"<s:text name="revokeAuditPrompt"/>", function(r){
					if(r){
						//只有分配待审核的才能提交
						var check = checkBeforeCommit('funcStatus','<%=com.upg.ucars.mapping.basesystem.security.ReBrchFunc.STATUS_CHECKING%>');
						if( ! check ){
							$.messager.alert(global.alert,global.branch_doRevoke_info,'info');
							return;
						}
						var selectedIds = branchListDG.getSelectedIds();
						var url = "<s:url value='/security/brch_batchRevokeBrchFuncAudit.jhtml'/>";
						doPost(url,{"ids":selectedIds},function(result){
							if(result){
								var obj=str2obj(result);
								if(obj.error){
									error(obj.error);
									return;
								}
							}
							branchListDG.refresh();
						});
					}
				});
	       }
	}
	
	function viewAuditProcess(){
	       var num = branchListDG.getSelectedRowNum();
	       if(num > 0){
	           if(num > 1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var funcStatus = branchListDG.getSelectedField("funcStatus");
				if( funcStatus == '<%=com.upg.ucars.mapping.basesystem.security.ReBrchFunc.STATUS_UN_ASSIGN%>' || funcStatus == '<%=com.upg.ucars.mapping.basesystem.security.ReBrchFunc.STATUS_ASSIGNNING%>' ){
					info(global.cannot_view_uncommit_process);
					return;
				}
				var selectedId = branchListDG.getSelectedIds();
				var url = "<s:url value='/security/brch_viewBrchFuncAuditProcess.jhtml'/>?brch.brchId="+selectedId;
				requestAtWindow(url,"auditProcessView",'<s:text name="auditProcess"/>');
	       }
	}

	function selectSuperBranch() {
		chooseSuperBranch('',selectSuperCallback);
	}

	function selectSuperCallback(row){
		if(row && row.treeCode && row.brchName){
			$("#main_parent_brchName").val(row.brchName);
			$("#main_brch_treeCode").val(row.treeCode);
		}else{
			$("#main_parent_brchName").val("");
			$("#main_brch_treeCode").val("");
		}
	}
	
	function doBrchFuncCopy(){
	   if(isSingleSelected(branchListDG)){
			var selectedId=branchListDG.getSelectedField("brchId");
			var url = '<s:url value="/security/brch_toCopyBrchFunc.jhtml?auditEntityId="/>' + selectedId;
			redirectUrl(url);
		}
		
	}
	
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>