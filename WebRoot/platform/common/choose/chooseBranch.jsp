<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseBranchSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseBranchCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseBranchForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="branch"/><s:text name="name"/>:</td>
					<td><input name="branch.brchName" id="branch.brchName"  ></input></td>
					<td class="title"><s:text name="branch"/><s:text name="branch"/><s:text name="no"/>:</td>
					<td><input name="branch.brchNo" id="branch.brchNo"   ></input></td>
					<td><x:button id="chooseBranchQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="chooseBranchGrid" singleSelect="true" url="/web_ui/choose_queryBranch.jhtml" autoload="false" form="chooseBranchForm">
			<x:columns>
				<x:column field="brchId" checkbox="true"/>
				<x:column field="brchNo" title="branch,no" width="100"/>
				<x:column field="brchName" title="branch,name" width="150"/>
				<x:column field="miNo" title="memberInfo.miNo" width="80"/>
				<x:column field="unionBankNo" title="union_bank_no" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_branch"].init=function(){
	$("#chooseBranchForm").form("clear");
	chooseBranchGrid.load();
};
chooseBranchGrid.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_branch"].callback&&$.isFunction(ChooseContext["choose_branch"].callback)){
		ChooseContext["choose_branch"].callback(rowData);
	}
	ChooseContext["choose_branch"].close();
	var target = ChooseContext["choose_branch"].target;
	if (target) {
		$(target).focus();
	}
	return false;
};
$("#chooseBranchQuery").unbind().click(function(){
	chooseBranchGrid.load();
});
$("#chooseBranchSelect").unbind().click(function(){
	if(isSingleSelected(chooseBranchGrid)){
		if(ChooseContext["choose_branch"].callback&&$.isFunction(ChooseContext["choose_branch"].callback)){
			ChooseContext["choose_branch"].callback(chooseBranchGrid.getSelectedFirstRow());
		}
		ChooseContext["choose_branch"].close();
		var target = ChooseContext["choose_branch"].target;
		if (target) {
			$(target).focus();
		}
		return false;
	}
	
});
$("#chooseBranchCancel").unbind().click(function(){
	if(ChooseContext["choose_branch"].callback&&$.isFunction(ChooseContext["choose_branch"].callback)){
		ChooseContext["choose_branch"].callback();
	}
	ChooseContext["choose_branch"].close();
	var target = ChooseContext["choose_branch"].target;
	if (target) {
		$(target).focus();
	}
	return false;
});
</script>	
	</tiles:putAttribute>
</tiles:insertDefinition>

