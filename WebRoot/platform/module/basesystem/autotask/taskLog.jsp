<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	
	<tiles:putAttribute name="tool">
		<x:button icon="icon-remove" click="doRemove" text="del"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" style="margin:0px;padding:0px;">
				<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="日期从"/></label>
				<input name="bean.startDate" class="easyui-datebox" validType="dateValidator" maxLength="10" value="<fmt:formatDate value="${bean.startDate}" pattern="yyyy-MM-dd"/>" />
				<label><s:text name="到"/></label>
				<input name="bean.endDate" class="easyui-datebox" validType="dateValidator" maxLength="10" value="<fmt:formatDate value="${bean.endDate}" pattern="yyyy-MM-dd"/>" />
				
				<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="任务"/>:</label>
				<select style="width:150px;" name="bean.taskId" class="lang_select">
					<option value="">全部</option>
					 <s:iterator value="autoTaskList" >
							<option value="${id}">${name}</option>
					 </s:iterator>
				</select>
				<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="是否查主任务"/>:</label>
				<select style="width:40px;" name="bean.isMainTask" class="lang_select">
					<option value="1">是</option>
					<option value="0">否</option>					
				</select>
				<label style='margin-right:0.5em;margin-left:1em;margin-bottom:2px;text-align:right;'><s:text name="任务类型"/>:</label>
				<select style="width:90px;"  name="bean.taskType" class="lang_select">
					<option value="">全部</option>
					 <s:iterator value="taskTypeList" >
							<option value="${codeNo}">${codeName}</option>
					 </s:iterator>
				</select>
				<x:button icon="icon-search" click="doSearch" text="query"/>
			</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" form="mainQueryForm" url="/autotask/log_list.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="taskName" field="name"  width="120" />
				<x:column title="memberName" field="memberName"  width="120" />
				<x:column title="startTime" field="startTime"  width="120" formatter="format2Time"/>
				<x:column title="endTime" field="endTime"  width="120" formatter="format2Time"/>
				<x:column title="runDate" field="runDate"  width="120" formatter="format2Date"/>
				<x:column title="status" field="status"  width="36" formatter="status_column"/>
				<x:column title="remark" field="errMessage"  width="180" />
				
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:750px;display:none"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">


<script type="text/javascript">
var keys=['B031','B032','B033'];
var code=new CodeUtil(keys);

function initPage(){	
	code.loadData();
	dataTable.load();
};
function status_column(value,field,row,index){
	var info = code.getValue('B033',value);
	if ('1'==value)
		return "<font color='black'>"+info+"</font>";
	if ('2'==value)
		return "<font color='blue'>"+info+"</font>";	          				
	if ('6'==value)
		return "<font color='green'>"+info+"</font>";
	
	return "<font color='red'>"+info+"</font>";
}
function doSearch(){
	dataTable.load();	
}
	
function doRemove(){
	var ids=getMutSelected(dataTable);
	if(ids.length<1){
		info(global.notSelectInfo);
		return;
	}
	$.messager.confirm(global.alert, global.del_confirm_info, function(r){
		if(r){
			var url='<s:url value="/autotask/log_del.jhtml"/>';					
			doPost(url,
					{"ids":ids},
					function(result){
						if(result){
							var obj=str2obj(result);
							if(obj.error){
								error(obj.error);
							}
						}
						dataTable.refresh();
			});
		}
	});
	
}
	
</script>

</tiles:putAttribute>
</tiles:insertDefinition>