<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-remove" click="doRemove" text="del"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" class="query_form">
			<table><tr>
				<td class="title"><s:text name="begin_tm"/>:</td>
				<td>
					<input class="Wdate" type="text" name="userLog.logTmBegin" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
				</td>
				<td class="title"><s:text name="end_tm"/>:</td>
				<td>
					<input class="Wdate" type="text" name="userLog.logTmEnd" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
				</td>

				<s:if test="member_branch==1">
					
					<td class="title"><s:text name="memberInfo.miNo"/>:</td>
					<td>
						<input id="memberInfo_miName"  class="inputSel" onClick="chooseMember(chooseMemberCallBack)"/>
						<s:hidden name="userLog.miNo" id="CHOOSE_MINO" />
					</td>
				</s:if>
				<s:elseif test="member_branch==2">
					<td class="title"><s:text name="brch.name_"/>:</td>
					<td>
						<input id="branch_brchName"  class="inputSel" onClick="chooseBranch()"/>
						<s:hidden name="userLog.brchId" id="branch_brchId" />
					</td>
				</s:elseif>
				<s:else>
				</s:else>

				<td >
					<x:button iconCls="icon-search" click="doQuery" text="query"/>
					<x:button iconCls="icon-reload" click="doRest" text="reset"/>
				</td>
			</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
				<table id="dataTable"></table>
			<x:datagrid id="dataTable" url="/security/userlog_query.jhtml" pagesize="20" autoload="false" form="queryForm">
				<x:columns>
					<x:column title="" checkbox="true" field="logId" width="20"/>
					<s:if test="member_branch==1">
					<x:column title="memberInfo.miNo" field="miNo" align="left" width="100"/>
					</s:if>
					<s:if test="member_branch==2">
					<x:column title="brch.name_" field="brchName" align="left" width="150"/>
					</s:if>
					<x:column title="user" field="sysUserName" align="left" width="150"/>
					<x:column title="gmt_op" field="logTm" align="left" width="150" formatter="format2Time"/>
					<x:column title="op_info" field="logInfo" align="left" width="200"/>
				</x:columns>
			</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="chooseMiNoWindow" style="width:380px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
function initPage(){
	dataTable.load();
	$("#branch_tree").combotree();
}
function doQuery(){
	dataTable.load();
}
//工具栏方法实现
function doRemove(){
	if(isSelected(dataTable)){
		dataTable.call('<s:url value="/security/userlog_del.jhtml"/>',{ids:dataTable.getSelectedFields("logId")});
	}
}
function doRest(){
	$('#queryForm').form('clear');
}	
function chooseMemberCallBack(row){
	var n = '',v='';
	if(row && row.miName && row.miNo){
		v = row.miNo;
		n = row.miName;
	}
	$("#memberInfo_miName").val(n);
	$("#CHOOSE_MINO").val(v);
}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>