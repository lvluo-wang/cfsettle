<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">
	<tiles:putAttribute name="tool">			
		<x:button icon="icon-edit" click="doEdit" text="edit"/>
		<x:button icon="icon-back" click="doBackProcess" text="back"/>					
	</tiles:putAttribute>
	<tiles:putAttribute name="data">		
		<form name="qform" id="qform">
			<s:hidden name="procId"></s:hidden>
		</form>	
		<x:datagrid id="dataTable1" autoload="false" form="qform" url="/bpm/publish_queryTaskData.jhtml" pagesize="15">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="task.taskName"  field="taskName"   width="150"/>
				<x:column title="task.taskCnName"  field="taskCnName"   width="250" formatter="formatTaskCnName"/>
				<x:column title="task.taskType"   field="taskType"  width="80" formatter="type_column"/>
				<x:column title="排序" field="sortNo" width="30"/>
				<x:column title="task,url" field="url" width="200"/>
				<x:column title="remark" field="remark" width="100" align="left"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit" style="width:500px;padding:5px;background:#fafafa;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
		<script type="text/javascript">
			var keys=["B022"];
			var code=new CodeUtil(keys);
			
			function initPage(){	
				code.loadData();
				dataTable1.load();
			}
			
			function type_column(value){
				return '<span>'+code.getValue("B022",value)+'</span>';
			}
		
			function doEdit(){
				if(isSingleSelected(dataTable1)){
					var selectedId=getSelected(dataTable1,"id");
					var url="<s:url value='/bpm/publish_toTaskEdit.jhtml' />?id="+selectedId+"&flag="+Math.random()*99999;
					requestAtWindow(url,"add_edit",'<s:text name="edit"/>');
				}
		
			}
			
			function doBackProcess(){
				var url = "<s:url value='/bpm/publish_main.jhtml'/>";
				redirectUrl(url);
				
			}
			function formatTaskCnName(val,field,row){
				var taskCnNameExt = '';
				if ( row.oldTask == '1') {
					taskCnNameExt = '<span style="color:#f00;">(遗留)</span>';
				}
				return val + taskCnNameExt;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>