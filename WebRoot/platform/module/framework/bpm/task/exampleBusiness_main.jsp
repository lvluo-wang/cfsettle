<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags"%>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	<tiles:putAttribute name="tool">
		<x:button iconCls="icon-add" text="add" click="doAdd" />
		<x:button iconCls="icon-edit" text="edit" click="toEdit" />
		<x:button iconCls="icon-remove" text="del" click="doRemove" />
		<x:button iconCls="icon-audit" text="commit" click="doApply" />
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
			<form id="mainQueryForm" class="query_form">
			<table>
				<tr>
					<td class="title">客户名称:</td>
					<td><input name="eb.customerName"" id="eb_customerName" ></input></td>
					<td><x:button iconCls="icon-search" text="query" click="doQuery"/></td>
				</tr>
			</table>

			
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/bpm/exampleBusiness_list.jhtml" autoload="true" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="客户名称"  field="customerName" align="left" width="300"/>
				<x:column title="项目类型" field="projectType" align="left" width="50"/>
				<x:column title="融资金额" field="financingAmount" align="left" width="50"/>
				<x:column title="放款日期" field="startingDate" align="left" width="100" formatter="format2Date"/>
				<x:column title="到期日期" field="expirDate" align="left" width="100" formatter="format2Date" />
				<x:column title="解保日期" field="status" align="left" width="100" />
				<x:column title="A角" field="roleA" align="left" width="50" />
				<x:column title="B角" field="roleB" align="left" width="100" />
				<x:column title="状态" field="status" align="left" width="100" />
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="examplebusiness_add_edit" style="width:400px;display:none;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/bpm/exampleBusiness_toAdd.jhtml'/>";
		requestAtWindow(url,"examplebusiness_add_edit","<s:text name="add"/>");		
	}
	function toEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/bpm/exampleBusiness_toEdit.jhtml' />?id="+selectedId;
			requestAtWindow(url,"examplebusiness_add_edit","<s:text name="edit"/>");
		}
	}	
	function doRemove(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,global.prompt_delete, function(r){
				if(r){
			dataTable.call('<s:url value="/bpm/exampleBusiness_delete.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
	function doApply(){
		if(isSelected(dataTable)){	
			$.messager.confirm(global.alert,"发起流程", function(r){
				if(r){
			dataTable.call('<s:url value="/bpm/exampleBusiness_apply.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
				}
			});
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
