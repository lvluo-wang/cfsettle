<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
<tiles:putAttribute name="end">
<script type="text/javascript">
	var dataGrid;//数据分页组件
	var prodId='${auditHistSearch.prodId}';
	var brchId='${auditHistSearch.brchId}';
	if(prodId&&prodId.length>1){
		$("#prodInput").hide();
		$("#prodLabel").hide();
	}
	if(brchId&&brchId.length>1){
		$("#brchLabel").hide();
		$("#brchInput").hide();
	}
	var keys=['B013'];
	var code=new CodeUtil(keys);
	function doQuery(){
		auditHistQueryGrid.load();
		
	}
	function resetQuery(){
		$('#queryForm').form('clear');
	}
	function initPage(){
		
		code.loadData();
		auditHistQueryGrid.load();
	}
	function format2auditStatus(value){
		return code.getValue('B013',value);
	}
	function chooseBranchCallback(row){
		if(row&&row.brchName&&row.brchNo&&row.brchId){
			$("#brchName").val(row.brchName);
			$("#brchContain").val(row.brchId);
		}
	}
	function chooseProductCallBack(row){
		if(row&&row.id&&row.prodName){
			$("#prodName").val(row.prodName);
			$("#prodId").val(row.id);
		}
	}
	function viewAuditProcess(){
		if(isSingleSelected(auditHistQueryGrid)){
			var id=getSelected(auditHistQueryGrid,"ID");
			var url='<s:url value="/audit/audit_viewAuidtProcess.jhtml"/>?auditTask.id='+id;
			requestAtWindow(url,"auditProcessView",'<s:text name="auditProcess"/>');
		}
		
	}
</script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
	<x:button icon="icon-view" text="viewAuditProcess" click="viewAuditProcess"/>
</tiles:putAttribute>
<tiles:putAttribute name="query">
	<form id="queryForm"  class="query_form"  >
		<s:hidden name="auditHistSearch.brchId" id="brchId"/>
		<s:hidden name="auditHistSearch.prodId" id="prodId"/>
		<s:hidden name="auditHistSearch.auditActor"/>
		<table>
			<tr>
				<td id="prodLabel">
					<s:text name="product"/>:
				</td>
				<td id="prodInput">
					<input id="prodName"  class="inputSel" onClick="chooseProduct(chooseProductCallBack)"  />
				</td>
				<td class="title">
					<s:text name="auditStatus"/>:
				</td>
				<td >
					<x:combobox list="auditStatus" textField="codeName" valueField="codeNo" name="auditHistSearch.status"/>
				</td>
				<td id="brchLabel" class="title">
					<s:text name="brch"/>:
				</td>
				<td id="brchInput">
					<input id="brchName" class="inputSel" onClick="chooseBranch(chooseBranchCallback)"    />
					<INPUT id="brchContain" type="checkbox" name="auditHistSearch.contrainChild"/>
					<label id="brchContainLabel">(<s:text name="containChildBrch"/>)</label>
				</td>
				
			</tr>
			<tr>
				<td class="title">
					<s:text name="start_date"/>:
				</td>
				<td >
					<input class="Wdate" onClick="WdatePicker()" name="auditHistSearch.startDate" id="startDate" />	
				</td>
				<td class="title">
					<s:text name="end_tm"/>
				</td>
				<td>
					<input class="Wdate" onClick="WdatePicker()" name="auditHistSearch.endDate" id="endDate"/>					
				</td>
				<td colspan="2">
					<x:button icon="icon-search" text="query" click="doQuery"/>
					<x:button icon="icon-reload" text="reset" click="resetQuery"/>
				</td>
			</tr>
		</table>
	</form>
</tiles:putAttribute>
			<!-- data -->
<tiles:putAttribute name="data">
	<x:datagrid id="auditHistQueryGrid" url="/audit/audit_auditHistList.jhtml" form="queryForm" autoload="false">
		<x:columns>
			<x:column field="" checkbox="true"/>
			<x:column field="BRCH_NAME" title="brch" width="120"/>
			<x:column field="PROD_NO" title="prodNo" width="80"/>
			<x:column field="PROD_ALIAS" title="prodAlias" width="60"/>
			<x:column field="AT_STATUS" title="auditStatus" width="60" formatter="format2auditStatus"/>
			<x:column field="AT_CREATE_TIME" title="auditTaskCreateTime" width="110" formatter="format2Time"/>
			<x:column field="AT_DONE_TIME" title="auditTaskDoneTime" width="110" formatter="format2Time"/>
			<x:column field="USER_NAME" title="auditTaskAuthor" width="100"/>
			<x:column field="AUDIT_REMARK" title="auditRemark" width="120"/>
		</x:columns>
	</x:datagrid>
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="auditProcessView"   style="width:700px;display:none;">
	</div>
</tiles:putAttribute>
</tiles:insertDefinition>