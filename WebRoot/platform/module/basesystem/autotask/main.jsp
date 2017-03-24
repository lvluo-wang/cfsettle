<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="FUNC_TOOL_DATA">	
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="toAdd" text="add"/>
		<x:button icon="icon-edit" click="toEdit" text="edit"/>
		<x:button icon="icon-remove" click="doRemove" text="del"/>
		<span class="separator"></span>
		<x:button icon="icon-open" click="doOpen" text="taskOpen"/>
		<x:button icon="icon-close" click="doClose" text="taskStop"/>				
	</tiles:putAttribute>	
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" url="/autotask/define_list.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="taskName" field="name"  width="120"/>
				<x:column title="className" field="className"  width="360"/>
				<x:column title="cronExpr" field="cronExpr"  width="120"/>
				<x:column title="taskType" field="taskType"  width="80" formatter="taskType_column"/>
				<x:column title="status" field="status"  width="60" formatter="status_column"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="add_edit"  style="width:750px;display:none;"></div>	
		<div id="taskListWindow" style="width:420px;height:350px;display:none;"></div>
		<div id="memberListWindow" style="width:420px;height:350px;display:none;"></div>			
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
var keys=['B031','B032'];
var code=new CodeUtil(keys);

	function initPage(){				
	   code.loadData();
	   dataTable.load();//数据分页标签加载远程数据
	}	
	
	function taskType_column(value){
		return code.getValue('B031',value);
	}
	function status_column(value){
		var info = code.getValue('B032',value);
		if ('1'==value)
			return "<font color='blue'>"+info+"</font>";
		else
			return info;
	}

	function toAdd(){
		var url='<s:url value="/autotask/define_toAdd.jhtml"/>';
		requestAtWindow(url,'add_edit','<s:text name="add"/>');
	}
	
	function toEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"id");
			var url='<s:url value="/autotask/define_toEdit.jhtml"/>?id='+selectedId;
			requestAtWindow(url,'add_edit','<s:text name="edit"/>');
		}
	}
	
function doRemove(){	
	if(isSingleSelected(dataTable)){
		var ids=getSelected(dataTable,"id");		
		$.messager.confirm(global.alert, global.del_confirm_info, function(r){
			if(r){
				var url='<s:url value="/autotask/define_del.jhtml"/>';					
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
	
	
	
	
	
}
function doOpen(){
	if(isSingleSelected(dataTable)){
		var ids=getSelected(dataTable,"id");		
		var url='<s:url value="/autotask/define_openTask.jhtml"/>';			
		doPost(url,
				{"ids":ids},
				function(result){
					printError(result);
					dataTable.refresh();
		});
	}
				
	
}

function doClose(){
	if(isSingleSelected(dataTable)){
		var ids=getSelected(dataTable,"id");		
		var url='<s:url value="/autotask/define_closeTask.jhtml"/>';			
		doPost(url,
				{"ids":ids},
				function(result){
					printError(result)
					dataTable.refresh();
					
		});
	}
}


</script>

</tiles:putAttribute>
</tiles:insertDefinition>