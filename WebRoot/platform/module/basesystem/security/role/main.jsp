<%@ page import="com.upg.ucars.basesystem.security.core.user.UserManager"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="head">
	<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/role-validate.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="doAdd" text="add"/>
		<x:button icon="icon-edit" click="doEdit" text="edit"/>
		<x:button icon="icon-remove" click="doRemove" text="del"/>
		<x:button icon="icon-edit" click="doRoleFunc" text="set,sysfunc"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form">
			<table><tr>
				<td><s:text name="role"/><s:text name="name"/>:</td>
				<td><input name="role.roleName" id="role_roleName" ></input></td>
				<td><x:button icon="icon-search" click="doQuery" text="query"/></td>
			</tr></table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="roleMainDG" url="/security/role_listRoles.jhtml" form="queryForm" >
			<x:columns>
				<x:column title="" field="roleId" checkbox="true" width="20" />
				<x:column title="name" field="roleName" width="200" align="left" />
				<x:column title="role,type" field="roleType" width="150" align="left" formatter="roleFormatter"/>
				<s:if test="showBrch">
					<x:column title="branch" field="brchId" width="180" align="left" formatter="roleFormatter"/>
				</s:if>
				<x:column title="remark" field="remark" width="300" align="left" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="role_add_edit"  style="width:460px;display:none;"></div>
		<div id="role_func" style="width:600px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
	<script type="text/javascript">
	var keys=['B001'];
	var code=new CodeUtil(keys);
	code.loadData();
	function roleFormatter(value,field,row,rowIndex){
		if(field == "roleType"){
			return code.getValue('B001',value);
		}else if(field == "brchId"){
				return '<s:property value="session.UserLogonInfo.branchName"/>';
		}
		return "";	
	}

function initPage(){
	//roleMainDG.load();
	enterKeyPressInit([{'id':'role_roleName','keypress':'doQuery()'}]);
}
	//工具栏方法实现区域
	function doQuery(){
		roleMainDG.load();
	}
	function doAdd(){
		var url="<s:url value='/security/role_toAdd.jhtml'/>";
		requestAtWindow(url,"role_add_edit","<s:text name="add"/><s:text name="role"/>");		
	}
	function doEdit(){
	       var num = roleMainDG.getSelectedRowNum();
	       if(num>0){
	           if(num>1){
	              info(global.singleSelectInfo);
	              return;
	           }
	           var selectedId = roleMainDG.getSelectedField("roleId");
	           var url="<s:url value='/security/role_toEdit.jhtml' />?id="+selectedId;
	           requestAtWindow(url, "role_add_edit", "<s:text name="edit"/><s:text name="role"/>");
	       }else{
	           info(global.notSelectInfo);
	       }
	}	
	function doRemove(){
	       if(roleMainDG.getSelectedRowNum()>0){
	           $.messager.confirm(global.alert,'<s:text name="prompt_delete_role"/>',
	                  function(r) {
	                     if (r) {
	                         var ids = roleMainDG.getSelectedIds();
	                         var url = "<s:url value='/security/role_deleteRole.jhtml'/>";
	                         doPost(url,{"ids":ids},function(result){
	                            printError(result);
	                            roleMainDG.refresh();
	                         });
	                     }
	                  });
	       }else{
	           info(global.notSelectInfo);
	       }
	}
	
	function doRoleFunc(){
	       if(roleMainDG.getSelectedRowNum()>0){
				var selectedId = roleMainDG.getSelectedIds();
				var url = "<s:url value='/security/role_roleFuncMain.jhtml'/>?roleId="+selectedId;
				redirectUrl(url,"role_func","<s:text name="set"/><s:text name="role"/><s:text name="sysfunc"/>");
	       }else{
	           info(global.notSelectInfo);
	       }
	}

	
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
