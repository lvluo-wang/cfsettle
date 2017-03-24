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
			<form id="mainQueryForm" class="query_form" >
				<table>
					<tr>
						<td class="title"><s:text name="name"/>:</td>
						<td><input name="pa.name"" id="attribute_name" ></input></td>
						<td>
							<x:button iconCls="icon-search" text="query" click="doQuery"/>
						</td>
					</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" url="/product/attr_list.jhtml" autoload="false" form="mainQueryForm">
			<x:columns>
				<x:column title="" checkbox="true" field="id" />
				<x:column title="name" field="name" align="left" width="120"/>
				<x:column title="attribute.key" field="key" align="left" width="120"/>
				<x:column title="value" field="valueDisp" align="left" width="120"/>
				<x:column title="codemeta.key,name" field="codeMetaDisp" align="left" width="120"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
	<!-- 弹出窗口定义开始 -->
	<div id="attr_add_edit" style="display:none;width:350px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">
<script type="text/javascript">
	function initPage(){
		doQuery();
	}
	
	//工具栏方法实现区域
	function doQuery(){
		dataTable.load();
	}
	function doAdd(){
		var url="<s:url value='/product/attr_addOrEditPage.jhtml'/>";
		requestAtWindow(url,"attr_add_edit","<s:text name="add"/>");		
	}
	function doEdit(){
		if(isSingleSelected(dataTable)){
			var selectedId=dataTable.getSelectedField("id");
			var url="<s:url value='/product/attr_addOrEditPage.jhtml' />?id="+selectedId;
			requestAtWindow(url,"attr_add_edit","<s:text name="edit"/>");
		}
	}	
	function doRemove(){
		if(isSelected(dataTable)){
			dataTable.call('<s:url value="/product/attr_deleteAttrs.jhtml"/>',{ids:dataTable.getSelectedFields("id")});
		}
	}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
