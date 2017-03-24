<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-view" text="view" click="doView" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<input type="hidden" name="searchBean.col10" value="<%=com.upg.ubsp.constant.ComDictItemConst.CAREER_TYPE_GUARANTEE + "," +com.upg.ubsp.constant.ComDictItemConst.CAREER_TYPE_BONDS %>"/>
			<table>
				<tr>
					<td class="title">项目类型:</td>
					<td>
						<x:combobox name="searchBean.projectType" valueField="codeNo" textField="codeName" list="projectType" />
					</td>
					<td class="title">受理时间:</td>
					<td>
						 <input class="Wdate" type="text" name="searchBean.fromDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
						至
						 <input class="Wdate" type="text" name="searchBean.toDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
					</td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/task_listDoneTask.jhtml" autoload="true" form="mainQueryForm" singleSelect="true">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="项目编号"  field="col9" align="left" width="100"/>
				<x:column title="客户名称"  field="col1" align="left" width="200"/>
				<x:column title="项目类型"  field="prodName" align="left" width="100"/>
				<x:column title="融资金额" field="financingAmount" align="right" width="80" formatter="formatCurrency"/>
				<x:column title="期限（月）" field="deadline" align="left" width="60" />
				<x:column title="A角色" field="col2" align="left" width="60"/>
				<x:column title="B角色" field="col3" align="left" width="60" />
				<x:column title="受理人" field="taskActorName" align="left" width="60" />
				<x:column title="处理时间" field="taskEnd" align="left" width="100" formatter="format2Minute"/>
				<x:column title="状态" field="taskCnName" align="left" width="120" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	
	
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
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
			redirectUrl('<s:url value="/preGuarantee/task_viewProcess.jhtml"/>',p);
		}
	}
	function formatString2Date(val){
		var ret = val;
		if(val){
			ret = val.replace(/ .*/,'');
		}
		return ret;
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
