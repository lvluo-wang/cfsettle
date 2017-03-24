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
					<td class="title"><s:text name="table"/></td>
					<td><input name="fcm.tableName"" id="fieldcodemap_tableName" ></input></td>
					<td class="title"><s:text name="field"/></td>
					<td><input name="fcm.fieldName"" id="fieldcodemap_fieldName" ></input></td>
					<td class="title"><s:text name="codemeta.key"/></td>
					<td><input id="query_fcm_codeKey_name"  class="inputSel" onClick="chooseCodeMeta(chooseMainCodeMetaCallback)" />
						<s:hidden name="fcm.codeKey" id="queryCodeMetaKey" />
					</td>
					<td>
						<x:button iconCls="icon-search" text="query" click="doQuery"/>
					</td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/dictionary/fieldcodemap_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" field="id" checkbox="true" />
				<x:column title="table" field="tableName" align="left" width="150" />
				<x:column title="field" field="fieldName" align="left" width="150" />
				<x:column title="codemeta.key" field="codeKey" align="left"  width="100"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="fieldcodemap_add_edit" style="display:none;width:400px;"></div>
		<div id="codeMetaWindow"  style="display:none;width:410px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	
	//工具栏方法实现区域
	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/dictionary/fieldcodemap_addOrEditPage.jhtml'/>";
		requestAtWindow(url,"fieldcodemap_add_edit","<s:text name="add"/>");		
	}
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/dictionary/fieldcodemap_addOrEditPage.jhtml' />?id="+selectedId;
			requestAtWindow(url,"fieldcodemap_add_edit","<s:text name="edit"/>");
		}
	}	
	function doRemove(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
			dataTable.call('<s:url value="/dictionary/fieldcodemap_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	function chooseMainCodeMetaCallback(row){
		var n = '',v='';
		if(row&&row.key&&row.name){
			n = row.name;
			v=row.key;
		}
		$("#query_fcm_codeKey_name").val(n);
		$("#queryCodeMetaKey").val(v);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
