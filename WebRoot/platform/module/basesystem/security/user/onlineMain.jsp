<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/user-validate.js"/>"></script>
	</tiles:putAttribute>
<!-- 由此开始页面布局 -->
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-set" click="setOffline" text="user.setOfflineStatus"/>
		</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table id="dataTable"></table>
		<x:datagrid id="dataTable" url="/security/user_listUsers.jhtml?olp=${olp}" autoload="false">
			<x:columns>
				<x:column title="" checkbox="true" field="userId" width="20"/>
				<x:column title="no" field="userNo" align="left" width="100"/>
				<x:column title="name" field="userName" align="left" width="200"/>
				<x:column title="type" field="userType" align="left" width="100" formatter="getTypeName"/>
				<x:column title="memberInfo.miName" field="miNo" align="left" width="200" formatter="getMiNoName"/>
				<x:column title="status" field="status" align="left" width="100" formatter="getStatusName"/>
				<x:column title="roleStatus" field="roleStatus" align="left" width="100" formatter="getRoleStatusName"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
var keys=['B016','B002','B003'];
var code=new CodeUtil(keys);
function initPage(){
	code.loadData();
	dataTable.load();
}
function getCodeName(key,cn){
	var ret = '';
	if( key && cn ){
		ret = code.getValue(key,cn);
	}
	return ret;
}
function getStatusName(val){
	return getCodeName("B003",val);
}
function getMiNoName(val,field,r){
	return r.miName;
}
function getRoleStatusName(val){
	return getCodeName("B016",val);
}
function getTypeName(val){
	return getCodeName("B002",val);
}
function setOffline(){
	if(isSelected(dataTable)){
		$.messager.confirm(global.alert,"<s:text name="user.setOfflineStatus.prompt"/>", function(r){
			if(r){
				var rows = dataTable.getSelectedRows();
				for(var i = 0; i <rows.length; i++ ){
					if (rows[i].status != '<%=com.upg.ucars.mapping.basesystem.security.Buser.STATUS_ON_LINE%>'){					
						$.messager.alert(global.alert,global.user_setOffline_onlyonline_error,'info');
						return;
					}
				}
				dataTable.call('<s:url value="/security/user_setUserOffline.jhtml"/>',{ids:dataTable.getSelectedFields("userId")});
			}
		});
	}
}
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>