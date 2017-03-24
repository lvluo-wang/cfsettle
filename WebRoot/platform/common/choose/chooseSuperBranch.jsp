<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseSuperBranchSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseSuperBranchCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseSuperBranchForm" class="query_form">
		<input type="hidden" name="id" id="branchId"/>
			<table>
				<tr>
					<td class="title"><s:text name="branch"/><s:text name="name"/>:</td>
					<td><input name="brch.brchName"></input></td>
					<td class="title"><s:text name="branch"/><s:text name="branch"/><s:text name="no"/>:</td>
					<td><input name="brch.brchNo"></input></td>
					<td><x:button id="chooseSuperBranchQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="chooseSuperBranchGrid" singleSelect="true" url="/security/brch_superiorQuery.jhtml" autoload="false" form="chooseSuperBranchForm">
			<x:columns>
				<x:column field="brchId" checkbox="true"/>
				<x:column field="brchNo" title="branch,no" width="100"/>
				<x:column field="brchName" title="brch.name_" width="150"/>
				<x:column field="miNo" title="memberInfo.miNo" width="80"/>
				<x:column field="unionBankNo" title="union_bank_no" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_superBranch"].init=function(){
	$("#chooseSuperBranchForm").form("clear");
	//chooseSuperBranchGrid.updateParams=function(){return {'id':ChooseContext["choose_superBranch"].curBranchId}};
	$("#branchId").val(ChooseContext["choose_superBranch"].curBranchId);
	chooseSuperBranchGrid.load();
};
chooseSuperBranchGrid.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_superBranch"].callback&&$.isFunction(ChooseContext["choose_superBranch"].callback)){
		ChooseContext["choose_superBranch"].callback(rowData);
	}
	ChooseContext["choose_superBranch"].close();
};
$("#chooseSuperBranchQuery").unbind().click(function(){
	chooseSuperBranchGrid.load();
});
$("#chooseSuperBranchSelect").unbind().click(function(){
	//if(isSingleSelected(chooseSuperBranchGrid)){
		if(ChooseContext["choose_superBranch"].callback&&$.isFunction(ChooseContext["choose_superBranch"].callback)){
			ChooseContext["choose_superBranch"].callback(chooseSuperBranchGrid.getSelectedFirstRow());
		}
		ChooseContext["choose_superBranch"].close();
	//}
	
});
$("#chooseSuperBranchCancel").unbind().click(function(){
	//if(ChooseContext["choose_superBranch"].callback&&$.isFunction(ChooseContext["choose_superBranch"].callback)){
	//	ChooseContext["choose_superBranch"].callback();
	//}
	ChooseContext["choose_superBranch"].close();
});
</script>	
	</tiles:putAttribute>
</tiles:insertDefinition>

