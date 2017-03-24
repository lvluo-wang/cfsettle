<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-audit" text="进入处理" click="doProcess" />
		<x:button iconCls="icon-edit" text="任务领用" click="doHoldTask" />
		<x:button iconCls="icon-remove" text="撤销领用" click="doCancelHold" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户名称:</td>
					<td>
						<input name="searchBean.col1" value="${searchBean.col1}"/> 
					</td>
					<td class="title">流程类型:</td>
					<td>
						<input id="productInfo_prodName" class="inputSel" onClick="chooseBusiProduct(chooseBusiProductCallback)"/>
						<s:hidden id="productInfo_id" name="productInfo.id" />
					</td>
					<td class="title">提交时间:</td>
					<td>
						 <input class="Wdate" type="text" name="searchBean.fromDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
						-
						 <input class="Wdate" type="text" name="searchBean.toDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
					</td>
				</tr>
				<tr>
					<td class="title">任务名:</td>
					<td>
						<input name="searchBean.taskDescription" value="${searchBean.taskDescription}"/> 
					</td>
					<td class="title">发起人业务团队:</td>
					<td>
						<div class="searchBox">
							<input class="inputSel" id="searchBean_brchNames" disabled="disabled" value="${searchBean.brchNames}" title='${searchBean.brchNames}'/>
							<x:button click="chooseTreeBranch(chooseBranchCallback,null,{'checkbox':true})" text="" icon="icon-search"/>
							<input id="searchBean_brchIds" type="hidden" name="searchBean.brchIds" value="${searchBean.brchIds}"/>
						</div>
					</td>
					<td class="title">任务领用:</td>
					<td>
						<x:combobox name="searchBean.holdCond" data="[{'codeNo':'1','codeName':'我领用的任务'},{'codeNo':'2','codeName':'待领用任务'}]" textField="codeName" valueField="codeNo" />
						<x:button iconCls="icon-search" text="query" click="doQuery"/>
					</td>
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/task_listMyTask.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户名称"  field="col1" width="200" align="left"/>
				<x:column title="编号"  field="col9" width="120" align="center"/>
				<x:column title="流程类型"  field="prodName" width="120"/>
				<x:column title="任务名"  field="taskCnName" width="120"/>
				<x:column title="处理人"  field="taskActorName" width="80"/>
				<x:column title="提交人"  field="creatorName" width="80"/>
				<x:column title="提交时间"  field="createTime" width="100" formatter="format2Date"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	
	
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	dataTable.onLoadSuccess=function(rows,data){
		try{
			window.parent.showTaskNum(data.total);
			dataTable.onLoadSuccess=function(rows,data){
			};
		}catch(e){}
	}
	function doQuery(){
		dataTable.load();
	}
	function doHoldTask(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,"确认要领取", function(r){
				if(r){
					var url = '<s:url value="/bpm/task_doHoldTask.jhtml"/>';
					var ids = dataTable.getSelectedFields("taskId");
					var param = {ids:ids};
					dataTable.call(url,param,function(result){
						info("领取成功");
					});
				}
			});
			
		}
	}
	function doCancelHold(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,"确认要撤销领取", function(r){
				if(r){
					var url = '<s:url value="/bpm/task_doCancelHold.jhtml"/>';
					var ids = dataTable.getSelectedFields("taskId");
					var param = {ids:ids};
					dataTable.call(url,param,function(result){
						info("撤销成功");
					});
				}
			});
		}
	}
	
	function doProcess(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var param = {
				'ib.processName':dataTable.getSelectedField("processName"),
				'ib.taskName':dataTable.getSelectedField("taskName"),
				'ib.prodNo':dataTable.getSelectedField("prodNo"),
				'taskId':dataTable.getSelectedField("taskId")
			};
			var url="<s:url value='/bpm/task_toProcess.jhtml' />";
			doPost(url,param,function(result){
				if(result){
					if(result.charAt(0)=='/'){
						var p = {
							taskId:dataTable.getSelectedField("taskId"),
							entityId:dataTable.getSelectedField("entityId"),
							processName:dataTable.getSelectedField("processName"),
							processId:dataTable.getSelectedField("instanceId"),
							'ib.entityType':dataTable.getSelectedField("entityType"),
							'ib.id':dataTable.getSelectedField("id"),
							'backUrl':'<s:url value="/bpm/task_toMyTask.jhtml"/>'
						};
						redirectUrl(result,p);
					}else{
		        		printError(result);
					}
				}
			});
		}
	}
	function chooseBusiProductCallback(row){
		var productInfoProdName = '';
		var productInfoId = '';
		if(row){
			productInfoProdName = row.prodName;
			productInfoId=row.id;
		}
		$("#productInfo_prodName").val(productInfoProdName);
		$("#productInfo_id").val(productInfoId);
	}
	function chooseBranchCallback(row){
		var brchNames = '';
		var brchIds = '';
		if (row) {
			if (row.brchNames) {
				brchNames = row.brchNames;
			}
			if (row.brchIds) {
				brchIds = row.brchIds;
			}
		}
		$("#searchBean_brchNames").val(brchNames).attr("title",brchNames);
		$("#searchBean_brchIds").val(brchIds);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
