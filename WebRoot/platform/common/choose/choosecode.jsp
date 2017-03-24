<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseCodeSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseCodeCancel" icon="icon-no"  text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseCodeQueryForm" class="query_form">
			<input type="hidden" id="chooseCodeKey" name="code.codeKey"/>
			<table>
				<tr>
					<td class="title"><s:text name="no"/>:</td>
					<td><input name="code.codeNo" ></input></td>
					<td class="title"><s:text name="name"/>:</td>
					<td><input name="code.codeName" ></input></td>
					<td><x:button id="chooseCodeQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="chooseCodeDataTable" url="/web_ui/choose_queryCode.jhtml" autoload="false" form="chooseCodeQueryForm">
			<x:columns>
				<x:column field="id" checkbox="true"/>
				<x:column field="codeNo" title="no" width="80"/>
				<x:column field="codeName" title="name" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_code"].init=function(){
	$("#chooseCodeQueryForm").form("clear");
	if(ChooseContext["choose_code"].codeKey){
		$("#chooseCodeKey").val(ChooseContext["choose_code"].codeKey);
	}
	chooseCodeDataTable.load();
};
chooseCodeDataTable.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_code"].callback&&$.isFunction(ChooseContext["choose_code"].callback)){
		ChooseContext["choose_code"].callback(rowData);
	}
	ChooseContext["choose_code"].close();
};
$("#chooseCodeQuery").unbind().click(function(){
	chooseCodeDataTable.load();
});
$("#chooseCodeSelect").unbind().click(function(){
	if(isSingleSelected(chooseCodeDataTable)){
		if(ChooseContext["choose_code"].callback&&$.isFunction(ChooseContext["choose_code"].callback)){
			var row=chooseCodeDataTable.getSelectedFirstRow();
			ChooseContext["choose_code"].callback(row);
		}
		ChooseContext["choose_code"].close();
	}
});
$("#chooseCodeCancel").unbind().click(function(){
	if(ChooseContext["choose_code"].callback&&$.isFunction(ChooseContext["choose_code"].callback)){
		ChooseContext["choose_code"].callback();
	}
	ChooseContext["choose_code"].close();
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>