<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
<tiles:putAttribute name="end">
<script type="text/javascript">
	function initPage(){
		auditStationList.load();
	}
	//分配岗位人员
	function assignUser(){
		if(isSingleSelected(auditStationList)){
			var selectedId=getSelected(auditStationList,"auditStationId");
			var url='<s:url value="/audit/audit_assignUserSelect.jhtml"/>?auditStation.id='+selectedId;
			redirectUrl(url);
		}
		
	}
	var viewFlag=true;
	var currentStationName="";
	function viewAssignedUser(){
		if(isSingleSelected(auditStationList)){
			var selectedId=getSelected(auditStationList,"auditStationId");
			var selectedName=getSelected(auditStationList,"auditStationName");
			$("#currentStationId").val(selectedId);
			currentStationName=selectedName;
			if(viewFlag){
				$("#viewAssignUserWin").show();
				viewFlag=false;
			}
			$("#viewAssignUserWin").window({
				title:'<s:text name="auditUser"/>',
				resizable:false,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				closable:true,
				modal:true
			});
			viewAssignUserList.load();
			//var url='<s:url value="/audit/audit_viewAssignedUser.jhtml"/>?auditStation.id='+selectedId;
			//redirectUrl(url);
		}
	}
	function format2Station(value){
		return currentStationName;
	}
	function doMainQuery(){
		auditStationList.load();
	}
	function doMainReset(){
		$("#mainQueryForm").form('clear');
	}
	function chooseBranchCallback(row){
		if(row&&row.brchName&&row.brchNo&&row.brchId){
			$("#brchName").val(row.brchName);
			$("#brchId").val(row.brchId);
		}
	}
</script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
	<x:button iconCls="icon-view" onClick="viewAssignedUser()" text="view,auditUser"/>
</tiles:putAttribute>
<tiles:putAttribute name="query">
	<form id="mainQueryForm" class="query_form">
		<table>
			<tr>
				<td><s:text name="auditRoute"/>:</td>
				<td>
				<input name="auditStation.auditRouteId" />
				</td>
				<td><s:text name="brch"/>:</td>
				<td>
				<input id="brchName" readonly  class="inputSel" onClick="chooseBranch(chooseBranchCallback)"/>
				<s:hidden id="brchId" name="auditStation.createBrchId" />
				</td>
				<td><s:text name="auditPrivilege"/>(<=):</td>
				<td>
				<input name="auditStation.auditStationPrivilege" id=""audit_station_privilege class="easyui-numberbox" />
				</td>
				<td>
					<x:button iconCls="icon-search"  onClick="doMainQuery()" text="query"/>
					<x:button iconCls="icon-reload" onClick="doMainReset()" text="reset"/>
				</td>
			</tr>
		</table>
	</form>
</tiles:putAttribute>
<tiles:putAttribute name="data">
	<x:datagrid id="auditStationList" singleSelect="true" url="/audit/audit_auditStationUserList.jhtml" form="mainQueryForm" autoload="false">
		<x:columns>
			<x:column field="" checkbox="true"/>
			<x:column field="brchName" title="stationCreateBrch" align="left" width="120"/>
			<x:column field="auditRouteName" title="auditRoute" align="left"  width="120"/>
			<x:column field="auditStationName" title="auditStationName" align="left"  width="100"/>
			<x:column field="userNumber" title="assigned,auditUser" align="right" width="70"/>
			<x:column field="auditStationPrivilege" title="auditPrivilege" width="70" align="right" formatter="format2Money"/>
			<x:column field="auditStationRemark" title="auditStationRemark" align="left"  width="130"/>
			<x:column field="auditRouteRemark" title="auditRouteRemark" align="left"  width="120"/>
		</x:columns>
	</x:datagrid>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="user_select" style="width:480px;display:none;"></div>
	<div id="auditRouteWindow" style="width:450px;display:none;"></div>
	<div id="viewAssignUserWin" style="width:480px;display:none">
		<form id="viewAssignUserForm">
			<input type="hidden" id="currentStationId" name="auditStation.id"/>
		</form>
		<x:datagrid id="viewAssignUserList" pagebar="false" url="/audit/audit_viewAssignedUser.jhtml" form="viewAssignUserForm" autoload="false">
			<x:columns>
				<x:column field="userNo" title="userNo" align="left" width="100"/>
				<x:column field="userName" title="userName" align="left" width="130"/>
				<x:column field="" title="auditStationName"  align="left" formatter="format2Station" width="150"/>
			</x:columns>
		</x:datagrid>
	</div>
</tiles:putAttribute>
</tiles:insertDefinition>