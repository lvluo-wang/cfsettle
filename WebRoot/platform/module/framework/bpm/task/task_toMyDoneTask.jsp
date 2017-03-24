<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="view" click="doView" />
		<x:button iconCls="icon-view" text="撤回" click="doUndo" />
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
					<td class="title">处理时间:</td>
					<td>
						 <input class="Wdate" type="text" name="searchBean.taskEndFrom" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
						-
						 <input class="Wdate" type="text" name="searchBean.taskEndTo" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
					</td>
				</tr>
				<tr>
					<td class="title">任务名:</td>
					<td>
						<input name="searchBean.taskDescription" value="${searchBean.taskDescription}"/> 
					</td>
					<td class="title">状态:</td>
					<td>
						<x:combobox name="searchBean.instanceStatus" valueField="codeNo" textField="codeName" list="instanceStatusList" />
					</td>
					<td colspan="2"><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/task_listMyDoneTask.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户名称"  field="col1" width="200" align="left"/>
				<x:column title="编号"  field="col9" width="120" align="center"/>
				<x:column title="流程类型"  field="prodName" width="120"/>
				<x:column title="任务名"  field="taskCnName" width="120"/>
				<x:column title="流程状态"  field="instanceStatus" width="100" formatter="formatInstanceStatus"/>
				<x:column title="提交人"  field="creatorName" width="80"/>
				<x:column title="处理时间"  field="taskEnd" width="100" formatter="format2Minute"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	
	
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	var keys=["<%=ComDictKeyConst.INSTANCE_STATUS_CODE%>"];
	var code=new CodeUtil(keys);
	code.loadData();
	
	function doQuery(){
		dataTable.load();
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
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var p = {
				taskId:dataTable.getSelectedField("taskId"),
				entityId:dataTable.getSelectedField("entityId"),
				processName:dataTable.getSelectedField("processName"),
				processId:dataTable.getSelectedField("instanceId"),
				'ib.entityType':dataTable.getSelectedField("entityType"),
				'ib.id':dataTable.getSelectedField("id"),
				'backUrl':'<s:url value="/bpm/task_toMyDoneTask.jhtml"/>'
			};
			var url = '<s:url value="/preGuarantee/task_viewProcess.jhtml"/>';
			if (typeof getProcessViewUrl != 'undefined' && $.isFunction(getProcessViewUrl)) {
				doPost('<s:url value="/bpm/task_getEntityObject.jhtml"/>?ib.id=' + p["ib.id"],null,function(result){
					var tmp = getProcessViewUrl(p["ib.entityType"],$.parseJSON(result));
					if (tmp) {
						url = tmp;
					}
					redirectUrl(url,p);
				});
			} else {
				redirectUrl(url,p);
			}
		}
	}
	function doUndo(){
		if(isSingleSelected(dataTable)){
			var taskId=dataTable.getSelectedField("taskId");
			dataTable.call('<s:url value="/preGuarantee/task_dealTask.jhtml"/>',{
				taskId:taskId,
				'tpr.isDeal':'true',
				'tpr.pass':'撤回'
			},function(result){
				info("撤回成功");
			});
		}
	}
	function formatInstanceStatus(val){
		return code.getValue("<%=ComDictKeyConst.INSTANCE_STATUS_CODE%>", val);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
