<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseMetaSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseMetaCancel" icon="icon-no"  text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseMetaQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="codemeta.key"/>:</td>
					<td><input name="cm.key" ></input></td>
					<td class="title"><s:text name="codemeta.name"/>:</td>
					<td><input name="cm.name" ></input></td>
					<td><x:button id="chooseMetaQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="chooseCodeMetaDataTable" url="/web_ui/choose_queryCodeMeta.jhtml" autoload="false" form="chooseMetaQueryForm">
			<x:columns>
				<x:column field="id" checkbox="true"/>
				<x:column field="key" title="codemeta.key" width="80"/>
				<x:column field="name" title="codemeta.name" width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
ChooseContext["choose_code_meta"].init=function(){
	$("#chooseMetaQueryForm").form("clear");
	chooseCodeMetaDataTable.load();
};
chooseCodeMetaDataTable.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_code_meta"].callback&&$.isFunction(ChooseContext["choose_code_meta"].callback)){
		ChooseContext["choose_code_meta"].callback(rowData);
	}
	ChooseContext["choose_code_meta"].close();
};
$("#chooseMetaQuery").unbind().click(function(){
	chooseCodeMetaDataTable.load();
});
$("#chooseMetaSelect").unbind().click(function(){
	if(isSingleSelected(chooseCodeMetaDataTable)){
		if(ChooseContext["choose_code_meta"].callback&&$.isFunction(ChooseContext["choose_code_meta"].callback)){
			var row=chooseCodeMetaDataTable.getSelectedFirstRow();
			ChooseContext["choose_code_meta"].callback(row);
		}
		ChooseContext["choose_code_meta"].close();
	}
});
$("#chooseMetaCancel").unbind().click(function(){
	if(ChooseContext["choose_code_meta"].callback&&$.isFunction(ChooseContext["choose_code_meta"].callback)){
		ChooseContext["choose_code_meta"].callback();
	}
	ChooseContext["choose_code_meta"].close();
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>