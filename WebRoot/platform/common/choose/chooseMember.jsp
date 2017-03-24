<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseMemberSelect" icon="icon-yes"  text="choose"/>
		<x:button id="chooseMemberCancel" icon="icon-no" text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseMemberForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="member"/><s:text name="name"/>:</td>
					<td><input name="name" id="name" ></input></td>
					<td class="title"><s:text name="member"/><s:text name="no"/>:</td>
					<td><input name="serialNo" id="serialNo"  ></input></td>
					<td><x:button id="chooseMemberQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="chooseMemberGrid" url="/web_ui/choose_queryMember.jhtml" autoload="false" form="chooseMemberForm">
			<x:columns>
				<x:column field="" checkbox="true"/>
				<x:column field="miNo" title="member,no" width="100"/>
				<x:column field="miName" title="member,name" width="150"/>
				<x:column field="miType" title="member,type" width="80" formatter="chooseMemberFormatMiType"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">

var chooseMemberCode=new CodeUtil(['B004']);
chooseMemberCode.loadData();
ChooseContext["choose_member"].init=function(){
	$("#chooseMemberForm").form("clear");
	chooseMemberGrid.load();
};
chooseMemberGrid.onDblClickRow=function(rowIndex,rowData){
	if(ChooseContext["choose_member"].callback&&$.isFunction(ChooseContext["choose_member"].callback)){
		ChooseContext["choose_member"].callback(rowData);
	}
	ChooseContext["choose_member"].close();
};
$("#chooseMemberQuery").unbind().click(function(){
	chooseMemberGrid.load();
});
$("#chooseMemberSelect").unbind().click(function(){
	if(isSingleSelected(chooseMemberGrid)){
		if(ChooseContext["choose_member"].callback&&$.isFunction(ChooseContext["choose_member"].callback)){
			ChooseContext["choose_member"].callback(chooseMemberGrid.getSelectedFirstRow());
		}
		ChooseContext["choose_member"].close();
	}
	
});
$("#chooseMemberCancel").unbind().click(function(){
	if(ChooseContext["choose_member"].callback&&$.isFunction(ChooseContext["choose_member"].callback)){
		ChooseContext["choose_member"].callback();
	}
	ChooseContext["choose_member"].close();
});
function chooseMemberFormatMiType(value){
	return chooseMemberCode.getValue("B004",value);
}


</script>
</tiles:putAttribute>
</tiles:insertDefinition>