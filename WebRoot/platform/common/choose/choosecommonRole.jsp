<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseCommonRoleSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseCommonRoleCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseCommonRoleForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="role"/><s:text name="name"/>:</td>
					<td><input name="commonRole.roleName" ></input></td>
					<td><x:button id="chooseBranchQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="chooseCommonRoleGrid" singleSelect="true" url="/web_ui/choose_queryCommonRole.jhtml" autoload="false" form="chooseCommonRoleForm">
			<x:columns>
				<x:column field="roleId" checkbox="true"/>
				<x:column field="roleName" title="role,name" width="100"/>
				<x:column field="remark" title="role,remark" width="200"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_common_role"].init=function(){
	$("#chooseCommonRoleForm").form("clear");
	chooseCommonRoleGrid.load();
};
chooseCommonRoleGrid.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_common_role"].callback&&$.isFunction(ChooseContext["choose_common_role"].callback)){
		ChooseContext["choose_common_role"].callback(rowData);
	}
	ChooseContext["choose_common_role"].close();
};
$("#chooseBranchQuery").unbind().click(function(){
	chooseCommonRoleGrid.load();
});
$("#chooseBranchSelect").unbind().click(function(){
	if(isSingleSelected(chooseBranchGrid)){
		if(ChooseContext["choose_common_role"].callback&&$.isFunction(ChooseContext["choose_common_role"].callback)){
			ChooseContext["choose_common_role"].callback(chooseCommonRoleGrid.getSelectedFirstRow());
		}
		ChooseContext["choose_common_role"].close();
	}
	
});
$("#chooseBranchCancel").unbind().click(function(){
	if(ChooseContext["choose_common_role"].callback&&$.isFunction(ChooseContext["choose_common_role"].callback)){
		ChooseContext["choose_common_role"].callback();
	}
	ChooseContext["choose_common_role"].close();
});
</script>	
	</tiles:putAttribute>
</tiles:insertDefinition>

