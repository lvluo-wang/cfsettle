<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button id="chooseAuditRouteSelect" icon="icon-yes" text="choose"/>
		<x:button id="chooseAuditRouteCancel" icon="icon-no"  text="cancel"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="chooseAuditRouteForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="name"/>:</td>
					<td><input name="auditRoute.auditRouteName" id="c_audit_route_name"></input></td>
					<td><x:button id="chooseAuditRouteQuery" icon="icon-search" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
	<x:datagrid id="chooseAuditRouteTable" url="/web_ui/choose_queryAuditRoute.jhtml" autoload="false" form="chooseAuditRouteForm">
			<x:columns>
				<x:column field="id" checkbox="true"/>
				<x:column field="auditRouteName" title="name" width="120"/>
				<x:column field="auditNodeExecMode" title="audit_node_mode" width="90" formatter="chooseAuditRouteNodeModeFormat"/>
				<x:column field="auditMode" title="audit_mode" width="60" formatter="chooseAuditRouteModeFormat"/>
				<x:column field="auditRouteRemark" title="remark" width="200"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	var chooseAuditRouteCode=new CodeUtil(['B011','B012']);
	chooseAuditRouteCode.loadData();
	ChooseContext["choose_audit_route"].init=function(){
		$("#chooseAuditRouteForm").form("clear");
		chooseAuditRouteTable.load();
	};
	chooseAuditRouteTable.onDblClickRow=function(rowIndex,rowData){
		if(ChooseContext["choose_audit_route"].callback&&$.isFunction(ChooseContext["choose_audit_route"].callback)){
			ChooseContext["choose_audit_route"].callback(rowData);
		}
		ChooseContext["choose_audit_route"].close();
	};
	$("#chooseAuditRouteQuery").unbind().click(function(){
		chooseAuditRouteTable.load();
	});
	$("#chooseAuditRouteSelect").unbind().click(function(){
		if(isSingleSelected(chooseAuditRouteTable)){
			if(ChooseContext["choose_audit_route"].callback&&$.isFunction(ChooseContext["choose_audit_route"].callback)){
				ChooseContext["choose_audit_route"].callback(chooseAuditRouteTable.getSelectedFirstRow());
			}
			ChooseContext["choose_audit_route"].close();
		}
		
	});
	$("#chooseAuditRouteCancel").unbind().click(function(){
		if(ChooseContext["choose_audit_route"].callback&&$.isFunction(ChooseContext["choose_audit_route"].callback)){
			ChooseContext["choose_audit_route"].callback();
		}
		ChooseContext["choose_audit_route"].close();
	});
	function chooseAuditRouteModeFormat(value){
		return chooseAuditRouteCode.getValue("B011",value);
	}
	function chooseAuditRouteNodeModeFormat(value){
		return chooseAuditRouteCode.getValue("B012",value);
	}
	
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>