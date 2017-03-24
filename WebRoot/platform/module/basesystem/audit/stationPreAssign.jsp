<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_FLOW_FORM">
<tiles:putAttribute name="flow">
		<div id="step">
			<span id="step_list">
				<span  class="step_first"><s:text name="product"/><s:text name="audit"/><s:text name="set"/></span>
				<span  class="step_nomal"><s:text name="set"/><s:text name="auditStation"/></span>
				<span  class="step_nomal step_current"><s:text name="auditStation"/><s:text name="assign"/></span>
			</span>
			<span id="step_tool">
				
				<x:button iconCls="icon-back" onClick="step_return()" text="return"/>
			</span>
		</div>
</tiles:putAttribute>
<tiles:putAttribute name="form">
		<table class="detail_table">
				<tr>
					
					<td class="detail_head_title" colspan="4">
					<form style="margin:0px;padding:0px;border:0px;" id="auditStationQueryForm">
						<s:hidden id="auditStationId" name="auditStation.id"/>
					当前岗位:<input class="inputSel" readonly id="currentStation" value="${auditStation.auditStationName}"/>
					<i>（${auditRoute.auditRouteName }）</i>
					</form>
					</td>
				</tr>
				<tr>
					<td class="detail_head_title">
					<s:text name="enAssign"/><s:text name="role"/>
					</td>
					<td class="detail_head_tool" >
					<x:button icon="icon-add" click="addAssignRole" text="assign,role"/>
					</td>
					<td class="detail_head_title" style="width:200px;"><s:text name="assigned"/><s:text name="role"/></td>
					<td   class="detail_head_tool">
					<x:button icon="icon-cancel" click="cancelAssignRole" text="cancel,assign"/>
					</td>
				</tr>
				<tr>
					<td class="detail_td" colspan="2">
					<x:datagrid id="roleListGrid" autoload="false" pagebar="false" height="140" form="auditStationQueryForm" url="/audit/audit_preAssingRoleList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="roleName" title="role,name" align="left" width="120"/>
							<x:column field="remark" title="remark" align="left" width="150"/>
						</x:columns>
					</x:datagrid>
					</td>
					<td class="detail_td"  colspan="2">
					<x:datagrid id="assignRoleListGrid" pagebar="false" height="140"  form="auditStationQueryForm" autoload="false" url="/audit/audit_assignedRoleList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="roleName" title="role,name" align="left" width="120"/>
							<x:column field="remark" title="remark" align="left" width="150"/>
						</x:columns>
					</x:datagrid>
					</td>
				</tr>
				<tr>
					<td class="detail_head_title">
					<s:text name="enAssign"/><s:text name="user"/>
					</td>
					<td class="detail_head_tool" >
					<x:button icon="icon-add" click="addAssignUser" text="assign,user"/>
					</td>
					<td class="detail_head_title" style="width:200px;"><s:text name="assigned"/><s:text name="user"/></td>
					<td   class="detail_head_tool">
					<x:button icon="icon-cancel" click="cancelAssignUser" text="cancel,assign"/>
					</td>
				</tr>
				<tr>
					<td class="detail_td" colspan="2">
					<x:datagrid id="userListGrid" autoload="false" height="110"  form="auditStationQueryForm" url="/audit/audit_preAssingUserList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="userName" title="userName" align="left" width="120"/>
							<x:column field="userNo" title="userNo" align="left" width="100"/>
						</x:columns>
					</x:datagrid>
					</td>
					<td class="detail_td"  colspan="2">
					<x:datagrid id="assignUserListGrid" pagebar="false" height="140"  form="auditStationQueryForm" autoload="false" url="/audit/audit_assignedUserList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="userName" title="userName" align="left" width="100"/>
							<x:column field="userNo" title="userNo" align="left" width="100"/>
						</x:columns>
					</x:datagrid>
					</td>
				</tr>
			</table>


			

</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="selectStationWin" style="display:none;width:600px;">
		<div class="win_tool_area">
			<x:button id="auditStationSelect" icon="icon-yes" text="choose"/>
			<x:button id="auditStationCancel" icon="icon-no"  text="cancel"/>
		</div>
		<div class="win_data_area">
		<form id="selectStationForm">
			<input type="hidden" id="auditRouteId" name="auditRoute.id" value="${auditRoute.id}"/>
		</form>
		<x:datagrid id="auditStationList" pagebar="false" form="selectStationForm" url="/audit/audit_queryRouteStationsTemplate.jhtml"  autoload="false">
			<x:columns>
				<x:column field="" checkbox="true"/>
				<x:column field="auditStationName" title="auditStationName" align="left"  width="120"/>
				<x:column field="auditStationPrivilege" title="auditPrivilege" width="70" align="right" formatter="format2Money"/>
				<x:column field="auditStationRemark" title="auditStationRemark" align="left"  width="130"/>
			</x:columns>
		</x:datagrid>
		</div>
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">


<script type="text/javascript">
	var stationId='<s:property value="auditStation.id"/>';
	var auditRouteId='<s:property value="auditRoute.id"/>';
	auditStationList.onDblClickRow=function(rowIndex,row){
		stationId=row.id;
		$("#auditStationId").val(row.id);
		$("#currentStation").val(row.auditStationName);
		$("#selectStationWin").window("close");
		userListGrid.load();
		assignUserListGrid.load();
		roleListGrid.load();
		assignRoleListGrid.load();
	};
	var showFlag=true;
	$("#currentStation").unbind().click(function(){
		if(showFlag){
			$("#selectStationWin").show();
			$("#selectStationWin").window({
				title:'<s:text name="auditStation"/>',
				resizable:false,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				closable:true,
				modal:true
			});
		}
		auditStationList.load();
		$("#selectStationWin").window("open");
	});
	$("#auditStationSelect").unbind().click(function(){
		if(isSingleSelected(auditStationList)){
			var row=auditStationList.getSelectedFirstRow();
			stationId=row.id;
			$("#auditStationId").val(row.id);
			$("#currentStation").val(row.auditStationName);
			$("#selectStationWin").window("close");
			userListGrid.load();
			assignUserListGrid.load();
			roleListGrid.load();
			assignRoleListGrid.load();
		}
	});
	$("#auditStationCancel").unbind().click(function(){
		$("#selectStationWin").window("close");
	});
	
	function initPage(){
		userListGrid.load();
		assignUserListGrid.load();
		roleListGrid.load();
		assignRoleListGrid.load();
	}
	function addAssignUser(){
		if(isSelected(userListGrid)){
			var uids=getMutSelected(userListGrid,"userId");
			var url='<s:url value="/audit/audit_assignUserAdd.jhtml"/>';
			assignUserListGrid.call(url,{'assignUserId':uids,'auditStation.id':stationId},function(){
				userListGrid.load();
			});
		}
	}
	function cancelAssignUser(){
		if(isSelected(assignUserListGrid)){
			var uids=getMutSelected(assignUserListGrid,"userId");
			var url='<s:url value="/audit/audit_assignUserCancel.jhtml"/>';
			userListGrid.call(url,{'assignUserId':uids,'auditStation.id':stationId},function(){
				assignUserListGrid.load();
			});
		}
	}
	function addAssignRole(){
		if(isSelected(roleListGrid)){
			var rids=getMutSelected(roleListGrid,"roleId");
			var url='<s:url value="/audit/audit_assignRoleAdd.jhtml"/>';
			assignRoleListGrid.call(url,{'assignRoleId':rids,'auditStation.id':stationId},function(){
				roleListGrid.load();
			});
		}
	}
	function cancelAssignRole(){
		if(isSelected(assignRoleListGrid)){
			var rids=getMutSelected(assignRoleListGrid,"roleId");
			var url='<s:url value="/audit/audit_assignRoleCancel.jhtml"/>';
			roleListGrid.call(url,{'assignRoleId':rids,'auditStation.id':stationId},function(){
				assignRoleListGrid.load();
			});
		}
	}
	function step_return(){
		var url='<s:url value="/audit/audit_setAuditRouteTemplate.jhtml"/>?auditRoute.id='+auditRouteId;
		redirectUrl(url);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
