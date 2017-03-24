<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.upg.ucars.basesystem.dictionary.util.DictionaryUtil"%>
<%@page import="com.upg.ubsp.constant.ComDictKeyConst"%>
<%@page import="net.easytodo.keel.util.SecurityUtils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd" />
		<x:button iconCls="icon-edit" text="edit" click="doEdit" />
		<x:button iconCls="icon-remove" text="del" click="doRemove" />
		<x:button iconCls="icon-view" text="view" click="doView" />
		<span class="separator"></span>
		<x:button iconCls="icon-audit" click="startDelegate" text="启动委托办理"/>
		<x:button iconCls="icon-no" click="endDelegate" text="取消委托办理"/>
		<span class="separator"></span>
		<x:button iconCls="icon-view" text="委托审批记录" click="doDelegateTask" />
	</tiles:putAttribute>
	<tiles:putAttribute name="data"> 
		<x:datagrid id="dataTable" url="/bpm/taskDelegated_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="taskDelegated.actor" field="delegatorName" align="center" width="100"/>
				<x:column title="taskDelegated.startTime" field="startTime" align="center" width="170" formatter="format2Minute"/>
				<x:column title="taskDelegated.endTime" field="endTime" align="center" width="100" formatter="format2Minute"/>
				<x:column title="taskDelegated.inEffect" field="inEffect" align="center" width="80" formatter="formatInEffect"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>

	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="project_add_win" style="width:420px;none;display:none;"></div>
	<div id="task_audit_history" style="width:880px;padding:3px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	var keys=["<%=ComDictKeyConst.C0002%>","<%=ComDictKeyConst.C0004%>","<%=ComDictKeyConst.PROJECT_STATUS_CODE%>"];
	var code=new CodeUtil(keys);
	code.loadData();

	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/bpm/taskDelegated_toAdd.jhtml'/>";
		requestAtWindow(url,"project_add_win","<s:text name='add'/>");
	}
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/bpm/taskDelegated_toEdit.jhtml' />?id="+selectedId;
			requestAtWindow(url,"project_add_win","<s:text name='edit'/>");
		}
	}
	
	function doView(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/bpm/taskDelegated_toView.jhtml' />?id="+selectedId;
			requestAtWindow(url,"project_add_win","<s:text name='view'/>");
		}
	}
	function doDelegateTask(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/bpm/taskDelegated_toDelegateTask.jhtml' />?id="+selectedId;
			requestAtWindow(url,"task_audit_history","<s:text name='view'/>");
		}
	}
	function doRemove(){
		if(isSelected(dataTable)){
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
					dataTable.call('<s:url value="/bpm/taskDelegated_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	
	function startDelegate(){
		if(isSingleSelected(dataTable)){
			$.messager.confirm(global.alert,"确定启动委托吗", function(r){
				if(r){
					var id =dataTable.getSelectedFields("id");
					var url = '<s:url value="/bpm/taskDelegated_startDelegate.jhtml"/>';
					dataTable.call(url,{id:id},function(result){
						info("启动成功");
					});
				}
			});	
		}
	}
	
	function endDelegate(){
		if(isSingleSelected(dataTable)){
			$.messager.confirm(global.alert,"确定取消委托吗", function(r){
				if(r){
					var id =dataTable.getSelectedFields("id");
					var url = '<s:url value="/bpm/taskDelegated_endDelegate.jhtml"/>';
					dataTable.call(url,{id:id},function(result){
						info("取消成功");
					});
				}
			});
		}
	}
	function formatInEffect(value){
		if(value == '1'){
			return '有效';
		}
		else{
			return '无效';
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
