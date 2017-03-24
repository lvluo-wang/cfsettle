<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
		<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/user-validate.js"/>"></script>
	</tiles:putAttribute>
<!-- 由此开始页面布局 -->
	<tiles:putAttribute name="tool">
		<s:if test='olp != "1"'>
			<x:button icon="icon-add" click="doAdd" text="add"/>
			<x:button icon="icon-edit" click="doEdit" text="edit"/>
			<x:button icon="icon-remove" click="doRemove" text="del"/>
			<x:button icon="icon-yes" click="doUserRole" text="assign,role"/>
			<x:button icon="icon-search" click="doUserFunc" text="view,sysfunc"/>
			<x:button icon="icon-reload" click="doResetPassword" text="resetPassword"/>
			<x:button icon="icon-ok" click="changeStatus(2)" text="user.status.outLine"/>
			<x:button icon="icon-no" click="changeStatus(4)" text="user.status.close"/>
			</s:if>
		<s:else>
			<x:button id="main_setOffline" icon="icon-set" click="setOffline" text="user.setOfflineStatus"/>
		</s:else>
		<s:if test='showAudit=="1"'>
				<x:button id="commitAudit" icon="icon-audit" click="doCommit" text="commitAudit"/>
				<x:button id="revokeAudit" icon="icon-revoke" click="doRevoke" text="revokeAudit"/>
				<x:button id="viewAudit" icon="icon-search" click="viewAuditProcess" text="viewAuditProcess"/>
			</s:if>
		
	</tiles:putAttribute>
	
	<s:if test='olp != "1"'>
		<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
				<table><tr>
					<td><s:text name="no"/>:</td>
					<td><input style="width:100px;" name="user.userNo" id="main_user_userNo"/></td>
					<td><s:text name="name"/>:</td>
					<td><input style="width:100px;" name="user.userName" id="main_user_userName"/></td>
					<s:if test='userTypeIsBrch=="1"'>
					<td><s:text name="brch"/>:</td>
					<td>
						<div class="searchBox">
							<input class="inputSel" id="main_user_brch_name" disabled="disabled" />
							<x:button click="chooseTreeBranch(mainQueryBranchCallback)" text="" icon="icon-search"/>
							<input id="main_user_brch_id" type="hidden" name="user.brchId" />
						</div>
					</td>
					<td><s:text name="role"/><s:text name="name"/>:</td>
					<td><input name="searchBean.roleName" ></input></td>
					</s:if>
					
					<td><s:text name="roleStatus"/>:</td>
					<td>
						<x:combobox id="main_user_roleStatus" name="user.roleStatus" valueField="codeNo" textField="codeName" list="roleTypeList" psValue="-1"/>
					</td>
					<td>
						<x:button icon="icon-search" click="mainDoSearch" text="query"/>
					</td>
				</tr></table>
			</form>
		</tiles:putAttribute>
	</s:if>
	<tiles:putAttribute name="data">
		<x:datagrid id="userMainDG" pagesize="20" url="/security/user_listUsers.jhtml?olp=${olp}" form="mainQueryForm" >
			<x:columns>
				<x:column title="" field="userId" checkbox="true" width="20" />
				<s:if test='userTypeIsBrch=="1"'>
					<x:column title="branch" field="brchIdDesc" width="150" align="left"/>
				</s:if>
				<s:if test='userTypeIsSaas=="1"'>
					<x:column title="memberInfo.miName" field="miNo" width="100" align="left" formatter="userFormatter"/>
				</s:if>
				
				<x:column title="no" field="userNo" width="100" align="left" />
				<x:column title="name" field="userName" width="100" align="left" />
				<x:column title="type" field="userType" width="100" align="left" formatter="userFormatter" />
				<x:column title="email" field="email" width="200" align="left"/>
				<x:column title="status" field="status" width="50" align="left" formatter="userFormatter"/>
				<x:column title="roleStatus" field="roleStatus" width="100" align="left" formatter="userFormatter"/>
			</x:columns>
		</x:datagrid>
		
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="user_add_edit"  style="width:480px;display:none;"></div>
	<div id="auditProcessView" style="width:500px;display:none;"></div>
	<div id="user_role" style="width:400px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
var keys=['B016','B002','B003'];
var code=new CodeUtil(keys);
code.loadData();
function userFormatter(value,field,row,rowIndex){
	if(field == "userType"){
		return code.getValue("B002",value);
	}else if(field == "miNo"){
		return row.miName;;	
	}else if(field == "status"){
		return code.getValue("B003",value);
	}else if(field == "roleStatus"){
		return code.getValue("B016",value);
	}
	return "";	
}

function initPage(){
	enterKeyPressInit([{'id':'main_user_userNo'},{'id':'main_user_userName'},{'id':"main_user_roleStatus"}],"mainDoSearch()");

}

	
	function mainDoSearch(){
		userMainDG.load();	
	}

	function changeStatus(status){
       var num = userMainDG.getSelectedRowNum();
       if(num>0){
   		var selectedIds = userMainDG.getSelectedIds();
		if(selectedIds){
			var url="<s:url value='/security/user_batchChangeStatus.jhtml' />?ids="+selectedIds+"&user.status="+status;
			doPost(url,{},function(result){
				if(result){
					var obj=str2obj(result);
					if(obj.error){
						error(obj.error);
						return;
					}
				}
			});
			userMainDG.refresh();
		}
       }
	}
	
	function doAdd(){
		var url="<s:url value='/security/user_toAdd.jhtml'/>?flag="+Math.random()*99999;
		requestAtWindow(url,"user_add_edit","<s:text name="add"/><s:text name="user"/>");		
	}
	
	function doEdit(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var rs = userMainDG.getSelectedField("roleStatus");
				if( rs == '<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_AUDITING%>'){
					error(global.modify_auditing_info);
					return;
				}
	           var selectedId = userMainDG.getSelectedField("userId");
	           var url="<s:url value='/security/user_toAdd.jhtml' />?id="+selectedId;
	           requestAtWindow(url,"user_add_edit","<s:text name="edit"/><s:text name="user"/>");
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	
	function doRemove(){
		var ids = userMainDG.getSelectedIds();
		if(ids.length<1){
			info(global.notSelectInfo);
			return;
		}
		var rss = userMainDG.getSelectedMutFields(["roleStatus"]);  //getMultiSelectedArray("dataTable","roleStatus");
		for( var i = 0; i<rss.length;i++ ){
			if(rss[i]["roleStatus"] != '0'){
				info(global.delete_role_user_info);
				return;
			}
		}
		$.messager.confirm(global.alert,"<s:text name="prompt_delete_user"/>", function(r){
			if(r){
				var url = "<s:url value='/security/user_deleteUser.jhtml'/>?ids="+ids;
                doPost(url,{},function(result){
                    printError(result);
                    userMainDG.refresh();
                 });
			}
		});
	}
	
	function doUserRole(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var selectedId = userMainDG.getSelectedField("userId");
			   var roleStatus = userMainDG.getSelectedField("roleStatus");
				if( '<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_AUDITING%>' == roleStatus ){
					info(global.modify_auditing_info);
					return;
				}
				var url = "<s:url value='/security/user_userRoleMain.jhtml'/>?userId="+selectedId;
				requestAtWindow(url,"user_role","<s:text name="set"/><s:text name="user"/><s:text name="role"/>");	
	       }
	}
	
	function doUserFunc(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var selectedId = userMainDG.getSelectedField("userId");
				var url = "<s:url value='/security/user_userFuncTree.jhtml'/>?userId="+selectedId;
				requestAtWindow(url,"user_role","<s:text name="user"/><s:text name="sysfunc"/>");
	       }
	}
	
	function doResetPassword(){
       var num = userMainDG.getSelectedRowNum();
       if(num>0){
			var selectedIds = userMainDG.getSelectedIds();
			$.messager.confirm(global.alert,"<s:text name="prompt_reset_password"/>", function(r){
				if(r){
					var url = "<s:url value='/security/user_resetPassword.jhtml'/>?ids="+selectedIds;
	                doPost(url,{},function(result){
	                    printError(result);
	                    userMainDG.refresh();
	                 });
				}
			});
       }
	}

	function checkBeforeCommit(key,val){
		var ret = true;
		try{
			var rows = userMainDG.getSelectedRows();
			for( var i = 0; i < rows.length; i++ ){
				if( rows[i][key] != val ){
					ret = false;
					break;
				}
			}
		}catch(e){
		}
		return ret;
	}
	
	
	function doCommit(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num > 0){
				$.messager.confirm(global.alert,"<s:text name="commitAuditPrompt"/>", function(r){
					if(r){
						//只有分配待审核的才能提交
						var check = checkBeforeCommit('roleStatus','<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_ASSIGN_AUDITING%>');
						if( ! check ){
							$.messager.alert(global.alert,global.branch_doCommit_info,'info');
							return;
						}
						var selectedIds = userMainDG.getSelectedIds();
						var url = "<s:url value='/security/user_batchCommitUserRoleAudit.jhtml'/>";
						doPost(url,{"ids":selectedIds},function(result){
							if(result){
								var obj = str2obj(result);
								if(obj.error){
									error(obj.error);
									return;
								}
							}
							userMainDG.refresh();
						});
					}
				});
	       }
	}
	
	function doRevoke(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num > 0){
				$.messager.confirm(global.alert,"<s:text name="revokeAuditPrompt"/>", function(r){
					if(r){
						//只有分配待审核的才能提交
						var check = checkBeforeCommit('roleStatus','<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_AUDITING%>');
						if( ! check ){
							$.messager.alert(global.alert,global.branch_doRevoke_info,'info');
							return;
						}
						var selectedIds = userMainDG.getSelectedIds();
						var url = "<s:url value='/security/user_batchReworkUserRoleAudit.jhtml'/>";
						doPost(url,{"ids":selectedIds},function(result){
							if(result){
								var obj = str2obj(result);
								if(obj.error){
									error(obj.error);
									return;
								}
							}
							userMainDG.refresh();
						});
					}
				});
	       }
	}
	
	function viewAuditProcess(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num > 1){
	              info(global.singleSelectInfo);
	              return;
	           }
				var roleStatus = userMainDG.getSelectedField("roleStatus");
				if( typeof roleStatus == 'undefined' ||  roleStatus == '<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_UN_ASSIGN%>' || roleStatus == '<%=com.upg.ucars.mapping.basesystem.security.Buser.ROLE_STATUS_ASSIGN_AUDITING%>' ){
					info(global.cannot_view_uncommit_process);
					return;
				}
				var selectedId =  userMainDG.getSelectedIds();
				var url = "<s:url value='/security/user_viewUserRoleAuditProcess.jhtml'/>?userId="+selectedId;
				requestAtWindow(url,"auditProcessView",'<s:text name="auditProcess"/>');
	       }
	}
	
	function setOffline(){
	       var num = userMainDG.getSelectedRowNum();
	       if(num > 0){
				$.messager.confirm(global.alert,"<s:text name="user.setOfflineStatus.prompt"/>", function(r){
					if(r){
						var check = checkBeforeCommit('status','<%=com.upg.ucars.mapping.basesystem.security.Buser.STATUS_ON_LINE%>');
						if( ! check ){
							$.messager.alert(global.alert,global.user_setOffline_onlyonline_error,'info');
							return;
						}
						var selectedIds = userMainDG.getSelectedIds();
						var url = "<s:url value='/security/user_setUserOffline.jhtml'/>";
						doPost(url,{"ids":selectedIds},function(result){
							if(result){
								var obj=str2obj(result);
								if(obj.error){
									error(obj.error);
									return;
								}
							}
							userMainDG.refresh();
						});
					}
				});
	       }
	}
	
	function mainQueryBranchCallback(row){
		var brchName = '';
		var brchId = '';
		if(row && row.brchId && row.brchName){
			brchName=row.brchName;
			brchId=row.brchId;
		}
		$("#main_user_brch_name").val(brchName);
		$("#main_user_brch_id").val(brchId);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>