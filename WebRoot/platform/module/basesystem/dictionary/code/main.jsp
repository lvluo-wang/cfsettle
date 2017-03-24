<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd" />
		<x:button iconCls="icon-edit" text="edit" click="doEdit" />
		<x:button iconCls="icon-remove" text="del" click="doRemove" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title"><s:text name="no"/>:</td>
					<td><input name="code.codeNo"" id="code_codeNo" ></input></td>
					<td class="title"><s:text name="name"/>:</td>
					<td><input name="code.codeName"" id="code_codeName" ></input></td>
					<td class="title"><s:text name="codemeta.key"/>:</td>
					<td>
					<input id="query_code_codeKey_name"  class="inputSel" onClick="chooseCodeMeta(chooseMainCodeMetaCallback)" />
					<s:hidden name="code.codeKey" id="queryCodeMetaKey" />
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="language"/>:</td>
					<td>
						<x:combobox name="code.langType" list="langType" valueField="codeNo" textField="codeName"/>
					</td>
					<td colspan="2"><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>

			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/dictionary/code_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="no"  field="codeNo" align="left" width="100"/>
				<x:column title="name" field="codeName" align="left" width="150"/>
				<x:column title="codemeta.key" field="codeKey" align="left" width="100" />
				<x:column title="language" field="langType" align="left" width="100" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="code_add_edit" style="width:400px;display:none;"></div>
	<div id="codeMetaWindow"  style="width:410px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/dictionary/code_addOrEditPage.jhtml'/>";
		requestAtWindow(url,"code_add_edit","<s:text name="add"/>");		
	}
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/dictionary/code_addOrEditPage.jhtml' />?id="+selectedId;
			requestAtWindow(url,"code_add_edit","<s:text name="edit"/>");
		}
	}	
	function doRemove(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
			dataTable.call('<s:url value="/dictionary/code_batchDelete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
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
		$("#query_code_codeKey_name").val(n);
		$("#queryCodeMetaKey").val(v);
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
