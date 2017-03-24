<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd"/>
		<x:button iconCls="icon-edit" text="edit" click="doEdit"/>
		<x:button iconCls="icon-remove" text="del" click="doRemove"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="codemeta.key"/>:</td>
					<td><input name="cm.key" id="codemeta_key" ></input></td>
					<td class="title"><s:text name="codemeta.name"/>:</td>
					<td><input name="cm.name" id="codemeta_name" ></input></td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<table id="dataTable"></table>
		<x:datagrid id="dataTable" url="/dictionary/codeMeta_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id"/>
				<x:column title="codemeta.key" field="key" align="left" width="100"/>
				<x:column title="codemeta.name" field="name" align="left" width="200"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<!-- 弹出窗口定义开始 -->
		<div id="codemeta_add_edit" style="width:350px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	//工具栏方法实现区域
	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/dictionary/codeMeta_addOrEditPage.jhtml'/>";
		requestAtWindow(url,"codemeta_add_edit","<s:text name="add"/>");		
	}
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/dictionary/codeMeta_addOrEditPage.jhtml' />?id="+selectedId;
			requestAtWindow(url,"codemeta_add_edit","<s:text name="edit"/>");
		}
	}	
	function doRemove(){
		if(isSelected(dataTable)){
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
					dataTable.call('<s:url value="/dictionary/codeMeta_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
