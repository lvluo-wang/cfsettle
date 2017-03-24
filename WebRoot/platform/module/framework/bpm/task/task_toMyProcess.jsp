<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_QUERY_DATA">
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户名称:</td>
					<td>
						<input name="ibSearchBean.col1" value="${ibSearchBean.col1}"/> 
					</td>
					<td class="title">流程类型:</td>
					<td>
						<input id="productInfo_prodName" class="inputSel" onClick="chooseBusiProduct(chooseBusiProductCallback)"/>
						<s:hidden id="productInfo_id" name="productInfo.id" />
					</td>
					<td class="title">状态:</td>
					<td>
						<x:combobox name="ibSearchBean.instanceStatus" valueField="codeNo" textField="codeName" list="instanceStatusList" />
					</td>
					<td class="title">提交时间:</td>
					<td>
						 <input class="Wdate" type="text" name="ibSearchBean.createStartTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
						-
						 <input class="Wdate" type="text" name="ibSearchBean.createEndTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/task_listMyProcess.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id"/>
				<x:column title="客户名称"  field="col1" width="200" align="left"/>
				<x:column title="编号"  field="col9" width="120" align="center"/>
				<x:column title="流程类型"  field="prodName" width="120"/>
				<x:column title="提交人"  field="creatorName" width="80"/>
				<x:column title="提交时间"  field="createTime" width="100" formatter="format2Date"/>
				<x:column title="状态"  field="instanceStatus" width="80" align="center" formatter="formatInstanceStatus"/>
				<x:column title="操作"  field="id" width="80" formatter="operateContent"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="task_history" style="width:760px;height:450px;padding:3px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	var keys=["<%=ComDictKeyConst.INSTANCE_STATUS_CODE%>"];
	var code=new CodeUtil(keys);
	code.loadData();
	
	function formatInstanceStatus(value){
		return code.getValue("<%=ComDictKeyConst.INSTANCE_STATUS_CODE%>", value);
	}
	
	function doQuery(){
		dataTable.load();
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
				'backUrl':'<s:url value="/bpm/task_doneTask.jhtml"/>'
			};
			redirectUrl('<s:url value="/projectFlow/task_viewProcess.jhtml"/>',p);
		}
	}
	function operateContent(value,field,row){
		var content = '<a href="javascript:void(0)" onclick="doViewTask(\''+row.id+'\',\'' + row.entityId + '\')">流程记录</a>';
		return content;
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
	function doViewTask(id,entityId) {
		var url="<s:url value='/platform/module/framework/bpm/task/instance_task_history.jsp'/>?entityId="+entityId+"&instanceBusinessId="+id;
		requestAtWindow(url, "task_history", "审批记录");
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
