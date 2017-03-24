<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="FUNC_TOOL_QUERY_DATA">
<tiles:putAttribute name="head">
<script language="javascript" src="<s:url value="/platform/module/basesystem/security/validate/branch-validate.js"/>"></script>
</tiles:putAttribute>
<tiles:putAttribute name="tool">
	<x:button icon="icon-set" click="doBrchFunc" text="set,product"/>
	<x:button icon="icon-search" click="viewAuditProcess" text="button.view_brch_prod"/>					
</tiles:putAttribute>
<tiles:putAttribute name="query">

		<form id="mainQueryForm" class="query_form">
			<table><tr>
				<td class="title"><s:text name="brch.brchNo"/>:</td>
				<td><input style='margin-top:1px;border:#a4bed4 1px solid;'name="brch.brchNo" id="main_brch_brchNo" ></input></td>
				<td class="title"><s:text name="brch.name_"/>:</td>
				<td><input style='margin-top:1px;border:#a4bed4 1px solid;'name="brch.brchName" id="main_brch_brchName" ></input></td>
				<td class="title"><s:text name="brch.parentName"/>:</td>
				<td><input id="branch_brchName"  class="inputSel" onClick="chooseBranch()"/>
						<s:hidden name="brchId" id="branch_brchId" />
						<s:hidden name="brchNo" id="branch_brchNo"/>
						<s:hidden name="brch.treeCode" id="branch_treeCode" />
						<s:hidden name="unionBankNoId" id="branch_unionBrankNo" />
				
				<td><x:button icon="icon-search" click="mainDoSearch" text="query"/></td>
			</tr></table>
		</form>
</tiles:putAttribute>
<tiles:putAttribute name="data">
		<x:datagrid id="dataTable" autoload="false" form="mainQueryForm" url="/product/product_queryBrch.jhtml">
			<x:columns>
				<x:column field="id" checkbox="true" width="20" />
				<x:column title="brch.brchNo" field="brchNo"  width="120"/>
				<x:column title="brch.name_" field="brchName"  width="360"/>
				<x:column title="brch.parentName" field="parentBrchName"  width="120"/>
				<x:column title="存在待审核产品"   field="funcStatus"  width="80" formatter="status_column"/>
			</x:columns>
		</x:datagrid>	
</tiles:putAttribute>
<tiles:putAttribute name="window">
	<div id="brch_prod"  style="width:600px;display:none;">	</div>
	<div id="mainSel"  style="width:600px;display:none;">	</div>
	<div id="view_brch_prod"  style="width:450px;display:none;">	</div>
</tiles:putAttribute>
<tiles:putAttribute name="end">
<script type="text/javascript">

	function initPage(){	
		dataTable.load();		
		enterKeyPressInit([{'id':'main_brch_brchNo'},{'id':'main_brch_brchName'},{'id':'main_parent_brchName'}],"mainDoSearch()");
		
	}

	function status_column(value){
		if (value=='1')
			return "是";
		else
			return "否";
	}
	
	function doSearchM(parm){
		dataGrid.queryByParam(parm);		
	}
	
	function mainDoSearch(){
		dataTable.load();
	}
	
	function openQuery(){
		var url='<s:url value="/security/brch_queryCondi.jhtml"/>';
		requestAtWindow(url,"qbrch","<s:text name='query_view'/>");
	}
	function doBrchFunc(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"brchId");
			var treeCode = getSelected(dataTable,"treeCode");
			var url = "<s:url value='/product/product_toSetBranchProduct.jhtml'/>?";
			url = url + "id="+selectedId;
			requestAtWindow(url,"brch_prod","<s:text name='set'/><s:text name='product'/>");
			
		}
	}
	
	function viewAuditProcess(){
		if(isSingleSelected(dataTable)){
			var selectedId=getSelected(dataTable,"brchId");
			var url = "<s:url value='/product/product_toViewBranchProduct.jhtml'/>?id="+selectedId;
			requestAtWindow(url, "view_brch_prod", '产品权限');
		}
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>