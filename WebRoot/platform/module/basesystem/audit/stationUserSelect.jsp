<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_BLANK">
<tiles:putAttribute name="body">
	<div class="func_flow_area">
		<div id="step">
			<span id="step_list">
				<span  class="step_first"><s:text name="stationUserSet"/></span>
				<span  class="step_nomal step_current"><s:text name="assignAuditUser"/></span>
			</span>
			<span id="step_tool">
				
				<x:button iconCls="icon-back" onClick="step_return()" text="return"/>
			</span>
		</div>
	</div>
	<div class="func_form_area">

			<table class="detail_table">
				<tr>
					
					<td class="detail_head_title" colspan="4">
					<form style="margin:0px;padding:0px;border:0px;" id="auditStationQueryForm">
						<s:hidden id="auditStationId" name="auditStation.id"/>
					当前岗位:<input class="inputSel" readonly id="currentStation" value="${auditStation.auditStationName}"/>
					</form>
					</td>
				</tr>
				<tr>
					<td class="detail_head_title">
					<s:text name="enAssignUsers"/>
					</td>
					<td class="detail_head_tool" >
					<x:button icon="icon-add" click="addAssignUser" text="addAuditUser"/>
					</td>
					<td class="detail_head_title" style="width:200px;"><s:text name="assignedUsers"/></td>
					<td   class="detail_head_tool">
					<x:button icon="icon-cancel" click="cancelAssignUser" text="cancelAuditUser"/>
					</td>
				</tr>
				<tr>
					<td class="detail_td" colspan="2">
					<x:datagrid id="userListGrid" autoload="false" form="auditStationQueryForm" url="/audit/audit_assingUserList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="userName" title="userName" align="left" width="100"/>
							<x:column field="userNo" title="userNo" align="left" width="100"/>
						</x:columns>
					</x:datagrid>
					</td>
					<td class="detail_td"  colspan="2">
					<x:datagrid id="assignUserListGrid" pagebar="false" form="auditStationQueryForm" autoload="false" url="/audit/audit_assignedUserList.jhtml">
						<x:columns>
							<x:column field="" checkbox="true"/>
							<x:column field="userName" title="userName" align="left" width="100"/>
							<x:column field="userNo" title="userNo" align="left" width="100"/>
						</x:columns>
					</x:datagrid>
					</td>
				</tr>
			</table>
	</div>
	<div id="selectStationWin" style="display:none;width:600px;">
		<div class="win_tool_area">
			<x:button id="auditStationSelect" icon="icon-yes" text="choose"/>
			<x:button id="auditStationCancel" icon="icon-no"  text="cancel"/>
		</div>
		<div class="win_data_area">
		<x:datagrid id="auditStationList" url="/audit/audit_auditStationUserList.jhtml"  autoload="false">
			<x:columns>
				<x:column field="" checkbox="true"/>
				<x:column field="brchName" title="stationCreateBrch" align="left" width="120"/>
				<x:column field="auditRouteName" title="auditRoute" align="left"  width="120"/>
				<x:column field="auditStationName" title="auditStationName" align="left"  width="100"/>
				<x:column field="auditStationPrivilege" title="auditPrivilege" width="70" align="right" formatter="format2Money"/>
				<x:column field="auditStationRemark" title="auditStationRemark" align="left"  width="130"/>
				<x:column field="auditRouteRemark" title="auditRouteRemark" align="left"  width="120"/>
			</x:columns>
		</x:datagrid>
		</div>
	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">


<script type="text/javascript">
	var stationId='<s:property value="auditStation.id"/>';
	auditStationList.onDblClickRow=function(rowIndex,row){
		stationId=row.auditStationId;
		$("#auditStationId").val(row.auditStationId);
		$("#currentStation").val(row.auditStationName);
		$("#selectStationWin").window("close");
		userListGrid.load();
		assignUserListGrid.load();
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
			stationId=row.auditStationId;
			$("#auditStationId").val(row.auditStationId);
			$("#currentStation").val(row.auditStationName);
			$("#selectStationWin").window("close");
			userListGrid.load();
			assignUserListGrid.load();
		}
	});
	$("#auditStationCancel").unbind().click(function(){
		$("#selectStationWin").window("close");
	});
	
	function initPage(){
		userListGrid.load();
		assignUserListGrid.load();
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
	var enFlag=true;
	
	//保存分配
	function saveAssign(){
		var url='<s:url value="/audit/audit_assignUserSave.jhtml"/>';
		$("#assignUser").datagrid("selectAll");
		var ids=getMutSelected("assignUser","userId");
		$.messager.confirm(global.alert,'<s:text name="tipAssignUser"/>',function(r){
			if(r){
				doPost(url,
				{"assignUserId":ids,'auditStation.id':stationId},
				function(result){
					if(result){
						if(result.indexOf('{')==0){
							var obj=parseObj(result);
							if(obj.error){
								error(obj.error);
								return;
							}
						}
					}
					var url='<s:url value="/audit/audit_assignUserSelect.jhtml"/>?auditStation.id='+stationId;
					redirectUrl(url);
					
				});
			}
		});
		
	}
	//删除已分配的
	function deleteAssign(){
		var rows=$("#assignUser").datagrid('getSelections');
		if(rows){
			for(var i=0;i<rows.length;i++){
				var r=rows[i];
				var index=$("#assignUser").datagrid('getRowIndex',r);
		      	$("#assignUser").datagrid("deleteRow",index);
			}
			userDataGrid.refresh();
		}
	}
	function cancelAssign(){
		$("#user_select").window("close");
	}
	function step_next(){	
		var url = "<s:url value='/audit/audit_auditStationUserMain.jhtml'/>";	
		redirectUrl(url);
		
		
	} 
	function step_return(){
		var url = "<s:url value='/audit/audit_auditStationUserMain.jhtml'/>";	
		redirectUrl(url);
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
