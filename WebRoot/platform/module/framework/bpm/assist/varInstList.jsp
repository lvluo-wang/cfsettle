<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>

<tiles:insertDefinition name="WIN_TOOL_DATA">
	
	<!-- 由此开始页面布局 -->
	<tiles:putAttribute name="tool">
		<x:button icon="icon-add" click="doAdd" text="set"/>
		<x:button icon="icon-remove" click="doRemove" text="del"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<form name="qform" id="qform">
			<s:hidden name="id"></s:hidden>
		</form>
		<x:datagrid id="variableDataTable" autoload="false" form="qform" url="/bpm/activity_queryVarInstData.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="键" field="name"   width="160" />
				<x:column title="类型"  field="type"   width="180" formatter="type_column"/>
				<x:column title="值"  field="value"   width="240" formatter="value_column"/>
			</x:columns>
		</x:datagrid>	
	</tiles:putAttribute>

			
	<tiles:putAttribute name="window">
		<div id="add_edit_variable"  style="width:400px;display:none"></div>
	</tiles:putAttribute>

	<tiles:putAttribute name="end">
		
<script type="text/javascript">

function initPage(){
	variableDataTable.load();
}

	function type_column(value,field,rowData,index){
		var val = "";
		if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_String"/>')
			val = 'java.lang.String';
		else if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Long"/>')
			val = 'java.lang.Long';								
		else if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Integer"/>')
			val = 'java.lang.Integer';
		else if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Boolean"/>')
			val = 'java.lang.Boolean';
		else if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Double"/>')
			val = 'java.lang.Double';
		else if (value == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Date"/>'){
			val = 'java.util.Date';
		}
		return val;
	}
	
	function value_column(value,field,rowData,index){
		var dataType = rowData.type;
		var val = "";
		if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_String"/>')
			val = rowData.stringVal;
		else if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Long"/>')
			val = rowData.longVal;								
		else if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Integer"/>')
			val = rowData.intVal;
		else if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Boolean"/>')
			val = rowData.booleanVal;
		else if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Double"/>')
			val = rowData.doubleVal;
		else if (dataType == '<s:property value="@com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO@Type_Date"/>'){
			val = DateFormat.format(new Date(rowData.dateVal.time),
			"yyyy-MM-dd");
		}		
		return val;
	}

	//工具栏方法实现区域
	function doQuery(){
		var parm=formToObject("queryForm");
		dataGrid.queryByParam(parm);
	}
	
	function doAdd(){
		var url="<s:url value='/bpm/activity_toAddVar.jhtml'/>?flag="+Math.random()*99999;
		url += "&id="+$('#id').val();
		requestAtWindow(url,"add_edit_variable","设置上下文");
	}	
	
	function doRemove(){
		if(isSingleSelected(variableDataTable)){
			
			$.messager.confirm(global.alert,global.del_confirm_info, function(ok){
				if(ok){
					var ids=$('#id').val();
					var name=getSelected(variableDataTable,"name");
					var url = "<s:url value='/bpm/activity_delVarInst.jhtml'/>?id="+ids;
					url = url + "&varName="+name;
					//requestAndRefresh(url,"variableDataTable");
					doPost(url,	null,
							function(){
								variableDataTable.refresh();
							}
					);
				}
			});
		}
	}
	

	
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

