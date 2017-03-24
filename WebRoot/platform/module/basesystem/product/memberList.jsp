<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>


<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
	
	<tiles:putAttribute name="tool">
		<x:button icon="icon-set" click="doProduct" text="product,distribution"/>		
	</tiles:putAttribute>
	<tiles:putAttribute name="query">
		<form id="queryForm" >
			<table>
				<tr>
					<td><s:text name="memberInfo.miNo"/>:</td>
					<td><input name="memberInfo.miNo"></input></td>
					<td><s:text name="memberInfo.miName"/>:</td>
					<td><input name="memberInfo.miName"></input></td>
					<td><x:button icon="icon-search" click="doSearch" text="query"/></td>
				</tr>
			</table>
		</form>
	</tiles:putAttribute>
	<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" form="queryForm" autoload="false" url="/product/product_data.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="memberInfo.miNo" field="miNo"  width="120"/>
				<x:column title="memberInfo.miName" field="miName"  width="120"/>
				<x:column title="memberInfo.miType" field="miType"  width="360" formatter="miType_column"/>
				<x:column title="memberInfo.status" field="isOpen"  width="120" formatter="isOpen_column"/>
				<x:column title="memberInfo.miDt" field="miDt"  width="80" formatter="format2Date"/>
			</x:columns>
		</x:datagrid>
	</tiles:putAttribute>
	<tiles:putAttribute name="window">
		<div id="member_product" style="display:none;width:500px;"></div>
		<div id="error_view"   style="display:none;width:500px;"></div>
	</tiles:putAttribute>
	<tiles:putAttribute name="end">


<script type="text/javascript">
	var keys=["B004"];
	var code=new CodeUtil(keys);	
	function initPage(){
		code.loadData();
		dataTable.load();
	}
	function miType_column(value){
		return '<span>'+code.getValue("B004",value)+'</span>';
	}
	function isOpen_column(value,field,row,index){
		if (value=="0")
			return '<span style="color:red"><s:text name="member.status.close"/></span>';
		if (value=="1")
			return '<span style="color:green"><s:text name="member.status.open"/></span>';	
	}
	//工具栏具体实现方法
	function doSearch(){
		dataTable.load();
	}	
	function doProduct(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"miNo");
			var url="<s:url value='/product/product_toMemberProduct.jhtml' />?member.miNo="+selectedId;
			requestAtWindow(url,"member_product",'<s:text name="product"/><s:text name="distribution"/>');
		}
	}
	
</script>

</tiles:putAttribute>
</tiles:insertDefinition>