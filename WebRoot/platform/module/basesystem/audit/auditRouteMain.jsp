<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
		<tiles:putAttribute name="tool">
			<x:button iconCls="icon-add" onClick="toAddRoute()" text="addAuditRoute"/>
			<x:button iconCls="icon-edit" onClick="toEditRoute()" text="editAuditRoute"/>
			<x:button iconCls="icon-ok" onClick="toSetRoute()" text="setAuditRoute"/>
		</tiles:putAttribute>
		<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
				<table>
					<tr>
						<td><s:text name="auditRouteName"/>:</td>
						<td><input name="auditRoute.auditRouteName" id="audit_route_name" /></td>
						<td>
							<x:button iconCls="icon-search" onClick="doMainQuery()" text="query"/>
						</td>
					</tr>
				</table>
			</form>
		</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="auditRouteListGrid" url="/audit/audit_auditRouteList.jhtml" autoload="false" form="mainQueryForm">
			<x:columns>
				<x:column field="id" checkbox="true"/>
				<x:column field="auditRouteName" title="auditRouteName" width="120" align="left"/>
				<x:column field="auditNodeExecMode" title="auditNodeExecMode" formatter="auditRouteNodeModeFormat" align="left" width="120"/>
				<x:column field="auditMode" title="auditMode" formatter="auditRouteModeFormat" align="left" width="100"/>
				<x:column field="auditRouteRemark" title="auditRouteRemark" align="left" width="200"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<div id="auditRoute_add_edit"  style="width:400px;display:none;"></div>
	</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">
	var keys=['B011','B012'];
	var code=new CodeUtil(keys);
	function initPage(){
		code.loadData();
		auditRouteListGrid.load();
	}
	function toAddRoute(){
		var url='<s:url value="/audit/audit_addAuditRoute.jhtml"/>';
		requestAtWindow(url,'auditRoute_add_edit','<s:text name="add"/>');
	}
	function toEditRoute(){
		if(isSingleSelected(auditRouteListGrid)){
			var selectedId=getSelected(auditRouteListGrid,"id");
			var url='<s:url value="/audit/audit_editAuditRoute.jhtml"/>?auditRoute.id='+selectedId;
			requestAtWindow(url,'auditRoute_add_edit','<s:text name="edit"/>');
		}
		
	}
	function toSetRoute(){
		if(isSingleSelected(auditRouteListGrid,"id")){
			var selectedId=getSelected(auditRouteListGrid,"id");
			var url='<s:url value="/audit/audit_setAuditRouteTemplate.jhtml"/>?auditRoute.id='+selectedId;
			redirectUrl(url);
		}
	}
	function doMainQuery(){
		auditRouteListGrid.load();
	}
	function auditRouteModeFormat(value){
		return code.getValue("B011",value);
	}
	function auditRouteNodeModeFormat(value){
		return code.getValue("B012",value);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>